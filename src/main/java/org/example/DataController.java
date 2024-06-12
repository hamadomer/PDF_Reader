package org.example;

import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataController {

    private static final Logger logger = LogManager.getLogger("CSVErrorLogger");
    ArrayList<Data> dataList = new ArrayList<>();
    Mail mail = new Mail();
    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    String fileName;
    int scanndDocs = 0;
    int invaildDocs = 0;

    public Map<String, String> parseTextToMap(String text) {
        Map<String, String> dataMap = new HashMap<>();
        String[] lines = text.split("\\r?\\n");

        if (lines.length > 0) {
            dataMap.put("Title", lines[0].trim());
        } else {
            throw new FormatFlagsConversionMismatchException("File is empty", 'F');
        }

        for (String line : lines) {
            String[] parts = line.split(":", 2);
            if (parts.length == 2) {
                dataMap.put(parts[0].trim(), parts[1].trim());
            }
        }

        return dataMap;
    }

    public String generateResponse(Map<String, String> dataMap) {
        String title = dataMap.getOrDefault("Title", "");

        return switch (title) {
            case "Demande de paiement de facture" -> handlePaymentRequest(dataMap);
            case "Acquittement de facture" -> handleNameCondtions(dataMap);
            default -> "Not Found";
        };
    }

    public String handlePaymentRequest(Map<String, String> dataMap) {
        String[] dateParts = dataMap.get("Date").split("/");
        int year = dateParts.length == 3 ? Integer.parseInt(dateParts[2]) : 0;

        String amountString = extractAmount(dataMap.get("Montant"));
        int money = amountString.isEmpty() ? 0 : Integer.parseInt(amountString.replace(" ", ""));

        if (money > 1000 && year < 2000) {
            return "Demande de paiement MVP (" + dataMap.get("Montant") + ") via Facturo1999, de la facture " + dataMap.get("Facture");
        } else if (money > 1000) {
            return "Demande de paiement MVP (" + dataMap.get("Montant") + "), de la facture " + dataMap.get("Facture") + ", émise le " + dataMap.get("Date");
        } else {
            return handleObjectConditions(dataMap);
        }
    }

    public String handleNameCondtions(Map<String, String> dataMap) {
        String fullName = dataMap.get("Prénom") + " " + dataMap.get("Nom");
        String amountString = extractAmount(dataMap.getOrDefault("Montant", ""));
        int money = amountString.isEmpty() ? 0 : Integer.parseInt(amountString.replace(" ", ""));

        switch (fullName) {
            case "Emmanuel Macron":
                return "Acquittement automatique de la facture émise par le Président de la République française (" + dataMap.get("Facture") + ")";
            case "Joe Biden":
                return "Acquittement automatique de la facture émise par le Président des Etats-Unis d'Amérique (" + dataMap.get("Facture") + ")";
            case "Elon Musk":
                return "Acquittement automatique de la facture émise par le Directeur Général de Tesla (" + dataMap.get("Facture") + ")";
            case "Ursula von der Leyen":
                return "Acquittement automatique de la facture émise par la Présidente de la commission européenne (" + dataMap.get("Facture") + ")";
            default:
                if (money > 1000) {
                    return "Acquittement automatique de la facture " + dataMap.get("Facture") + " MVP (" + dataMap.get("Montant") + ")";
                }
                break;
        }

        return "Acquittement automatique de la facture " + dataMap.get("Facture");
    }

    public String handleObjectConditions(Map<String, String> dataMap) {
        String object = dataMap.get("Objet");

        if (object.contains("Donuts") && object.contains("Tacos")) {
            return "Commande Donuts & Tacos (" + dataMap.get("Facture") + ")";
        } else if (object.contains("Donuts")) {
            return "Commande Donuts (" + dataMap.get("Facture") + ")";
        } else if (object.contains("Tacos")) {
            return "Commande Tacos (" + dataMap.get("Facture") + ")";
        } else {
            return "Demande de paiement à effectuer via Facturo1999 de la facture " + dataMap.get("Facture") + ", pour un montant de " + dataMap.get("Montant");
        }
    }

    public String extractAmount(String montant) {
        Pattern pattern = Pattern.compile("\\d[\\d\\s]*");
        Matcher matcher = pattern.matcher(montant);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    public void readFileAndCreateData(String path) throws IOException, CsvValidationException {
        CsvHandler csvReader = new CsvHandler();
        PdfHandler pdfReader = new PdfHandler();
        File directory = new File(path);
        if (!directory.isDirectory()) {
            throw new FileNotFoundException("No directory found in the path given");
        }
        fileName = path;
        File[] files = directory.listFiles();

        if (files != null) {
            try (FileWriter outputfile = new FileWriter("src/test/resources/ReadFileTest/output.csv");
                 CSVWriter writer = new CSVWriter(outputfile)) {

                String[] header = {"numero", "description"};
                writer.writeNext(header);

                for (File file : files) {
                    String numero;
                    String description;

//                  Data data = pdfReader.pdfToData(file.getPath());
                    Data data = processDocs(file.getPath());
                    try {
                        if(data.getFacture() != null) {
                            numero = data.getFacture();
                            description = generateResponse(data.getData());
                            String[] input = {numero, description};
                            writer.writeNext(input);
                        } else {
                            throw new NullPointerException();
                        }
                    } catch ( NullPointerException e) {
                        String errorMsg = String.format("%s;%s", file.getPath(), "This Document doesn't have a valid facture");
                        logger.error(errorMsg);
                    }
                }
                mail.setDate(date);
                mail.setNumberOfInvalidDocs(invaildDocs);
                mail.setNumberOfScannedDocs(scanndDocs);
                mail.setFileName(fileName);
                System.out.println(mail.getSujet() + "\n" + mail.getCorps());
            }
        }
    }

    private Data processDocs(String path) throws IOException {



        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("Path not found: " + path);
        }

        if (file.isFile()) {
            if (file.getName().endsWith(".pdf")) {
                return processPdf(file);
            } else if (file.getName().endsWith(".csv")) {
                return processCsv(file);
            }
        }
        String errorMsg = String.format("%s;%s", file.getPath(), "The given path isn't a valid File");
        logger.error(errorMsg);
        return null;
    }

    private Data processPdf(File file) throws IOException {
        scanndDocs++;
        try (PDDocument document = Loader.loadPDF(file)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            if (text.trim().isEmpty()) {
                String errorMsg = String.format("%s;%s", file.getPath(), "File is empty");
                logger.error(errorMsg);
                invaildDocs++;
                mail.setNamesOfInvalidDocs(file.getPath());
            }

            String[] lines = text.split("\n");
            Map<String, String> values = new HashMap<>();
            for (int i = 0; i < lines.length; i++) {
                if (i == 0) {
                    values.put("Title", lines[i].trim());
                } else {
                    String[] keyValue = lines[i].split(":");
                    if (keyValue.length == 2) {
                        values.put(keyValue[0].trim(), keyValue[1].trim());
                    } else {
                        String errorMsg = String.format("%s;%s", file.getPath(), "The file doesn't respect the key : value format");
                        logger.error(errorMsg);
                        invaildDocs++;
                        mail.setNamesOfInvalidDocs(file.getPath());
                    }
                }
            }

            return new Data(values.get("Title"), values.get("Nom"), values.get("Prénom"), values.get("Facture"), values.get("Date"), values.get("Objet"), values.get("Montant"));
        }
    }

    private Data processCsv(File file) {
        scanndDocs++;
        try (FileReader fileReader = new FileReader(file); CSVReaderHeaderAware csvReader = new CSVReaderHeaderAware(fileReader)) {
            List<Data> dataList = new ArrayList<>();
            Map<String, String> values;
            while ((values = csvReader.readMap()) != null) {
                String title;
                if ("paiement".equals(values.get("Title"))) {
                    title = "Demande de paiement de facture";
                } else if ("Acquittement".equals(values.get("Title"))) {
                    title = "Acquittement de facture";
                } else {
                    String errorMsg = String.format("%s;%s", file.getPath(), "Type of facture isn't supported");
                    logger.error(errorMsg);
                    title = "empty";
                    invaildDocs++;
                    mail.setNamesOfInvalidDocs(file.getPath());
                }
                dataList.add(new Data(title, values.get("nom"), values.get("prenom"), values.get("numero"), values.get("date"), values.get("objet"), values.get("montant")));
            }

            if (!dataList.isEmpty()) {
                return dataList.get(0); // Assuming you want to return the first item.
            } else {
                return new Data("empty", "empty", "empty", "empty", "empty", "empty", "empty");
            }
        } catch (IOException | CsvValidationException | NullPointerException e) {
            String errorMsg = String.format("%s;%s", file.getPath(), "empty Csv file");
            logger.error(errorMsg);
            invaildDocs++;
            mail.setNamesOfInvalidDocs(file.getPath());
            return new Data("empty", "empty", "empty", "empty", "empty", "empty", "empty");
        }
    }


}
