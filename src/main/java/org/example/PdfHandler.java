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
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;



public class PdfHandler {

    DataController object = new DataController();

    public String pdfToString(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("Path not found : " + path);
        }


        try (PDDocument document = Loader.loadPDF(file)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);

            Map<String, String> dataMap = object.parseTextToMap(text);

            return object.generateResponse(dataMap);

        }


    }

    public Data pdfToData(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new IOException("Path not found: " + path);
        }

        try (PDDocument document = Loader.loadPDF(file)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);

            String[] lines = text.split("\n");
            Map<String, String> values = new HashMap<>();

            for (int i =  0; i < lines.length; i++) {
               if(i == 0) {
                   values.put("Title", lines[i].trim());
               } else {
                   String[] keyValue = lines[i].split(":");
                   if (keyValue.length == 2) {
                       values.put(keyValue[0].trim(), keyValue[1].trim());
                   }
               }
            }

            return new Data(values.get("Title"), values.get("Nom"), values.get("Prénom"), values.get("Facture"), values.get("Date"), values.get("Objet"), values.get("Montant"));
        }
    }


    public void dataToPdf(Data data, String path) throws IOException {
        try (PDDocument document = new PDDocument()) {
            document.addPage(new PDPage());

            PDPageContentStream addText = new PDPageContentStream(document, document.getPage(0));
            addText.beginText();
            addText.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN), 12);
            addText.newLineAtOffset(25, 500);

            String titleText = data.getTitle();
            String nomText = "Nom : " + data.getNom();
            String prenomText = "Prénom : " + data.getPrenom();
            String factureText = "Facture : " + data.getFacture();
            String dateText = "Date : " + data.getDate();
            String objetText = "Objet : " + data.getObjet();
            String priceText = "Montant : " + data.getMontant();


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
            try {
                if (Files.exists(Path.of(path))) {
                    throw new FileAlreadyExistsException("File already exists: " + path);
                } else {
                    document.save(path);
                    System.out.println("Document saved successfully.");
                }
            } catch (FileAlreadyExistsException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("An error occurred while saving the document.");
            }
        }
    }
}