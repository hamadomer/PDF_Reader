package org.example;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PdfHandler {

    private static final String DEFAULT_RESPONSE = "No matched data has been found";

    public String pdfToString(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                throw new IOException("File does not exist");
            }

            PDDocument document = Loader.loadPDF(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            document.close();

            Map<String, String> dataMap = parseTextToMap(text);

            return generateResponse(dataMap);

        } catch (IOException e) {
            e.printStackTrace();
            return DEFAULT_RESPONSE;
        }
    }

    private static Map<String, String> parseTextToMap(String text) {
        Map<String, String> dataMap = new HashMap<>();
        String[] lines = text.split("\\r?\\n");

        if (lines.length > 0) {
            dataMap.put("Title", lines[0].trim());
        }

        for (String line : lines) {
            String[] parts = line.split(":", 2);
            if (parts.length == 2) {
                dataMap.put(parts[0].trim(), parts[1].trim());
            }
        }

        return dataMap;
    }

    private String generateResponse(Map<String, String> dataMap) {
        String title = dataMap.getOrDefault("Title", "");

        return switch (title) {
            case "Demande de paiement de facture" -> handlePaymentRequest(dataMap);
            case "Acquittement de facture" -> handleNameCondtions(dataMap);
            default -> DEFAULT_RESPONSE;
        };
    }

    private String handlePaymentRequest(Map<String, String> dataMap) {
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

    private String handleNameCondtions(Map<String, String> dataMap) {
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

    private String handleObjectConditions(Map<String, String> dataMap) {
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

    private String extractAmount(String montant) {
        Pattern pattern = Pattern.compile("\\d[\\d\\s]*");
        Matcher matcher = pattern.matcher(montant);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    public void createPdf() {
        try (PDDocument document = new PDDocument()) {
            document.addPage(new PDPage());

            PDPageContentStream addText = new PDPageContentStream(document, document.getPage(0));
            addText.beginText();
            addText.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);
            addText.newLineAtOffset(25, 500);

            String titleText = "Acquittement de facture";
            String nomText = "Nom : Macron";
            String prenomText = "Prénom : Emmanuel";
            String factureText = "Facture : ShowFacture123";
            String dateText = "Date : 06/05/2013";
            String objetText = "Objet : Donuts and Tacos";
            String priceText = "Montant : 1001 dollars";


            // Adding text in the form of string
            addText.showText(titleText);

            addText.newLine(); // Move to the next line
            addText.newLineAtOffset(0, -12); // Adjust the y-coordinate
            addText.showText(nomText);

            addText.newLine();
            addText.newLineAtOffset(0, -12);
            addText.showText(prenomText);

            addText.newLine();
            addText.newLineAtOffset(0, -12);
            addText.showText(factureText);

            addText.newLine();
            addText.newLineAtOffset(0, -12);
            addText.showText(dateText);

            addText.newLine();
            addText.newLineAtOffset(0, -12);
            addText.showText(objetText);

            addText.newLine();
            addText.newLineAtOffset(0, -12);
            addText.showText(priceText);


            // Ending the content stream
            addText.endText();
            addText.close();
            document.save("src\\main\\resources\\exo2_format3.pdf");
            System.out.println("Content added");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}