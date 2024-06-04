package org.example;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PdfHandler {

    public String pdfToString ( String path ) {
       try{
           String filePath = path;
           File file = new File(filePath);
           if (!file.exists()) {
               throw new IOException();
           }

            PDDocument document = Loader.loadPDF(file);
            PDFTextStripper pdfStripper = new PDFTextStripper();


            String text = pdfStripper.getText(document);



            Map<String, String> dataMap = parseTextToMap(text);


            if(dataMap.get("Title").equals("Acquittement de facture")) {
                System.out.println("Acquittement automatique de la facture " + dataMap.get("Facture"));
                return "Acquittement automatique de la facture " + dataMap.get("Facture");
            }
            if(dataMap.get("Title").equals("Demande de paiement de facture")) {
                System.out.println("Demande de paiement de la facture " + dataMap.get("Facture") + ", pour un montant de " + dataMap.get("Montant") + ", émise le " + dataMap.get("Date"));
                return "Demande de paiement de la facture " + dataMap.get("Facture") + ", pour un montant de " + dataMap.get("Montant") + ", émise le " + dataMap.get("Date");
            }
       } catch (IOException e) {
           e.printStackTrace();
       }
        return "faild";
    }

    private static Map<String, String> parseTextToMap(String text) {
        Map<String, String> dataMap = new HashMap<>();


        String[] lines = text.split("\\r?\\n");
        String[] title = Arrays.copyOfRange(lines, 0, 1);

        dataMap.put("Title", title[0].trim());
        for (String line : lines) {

            String[] parts = line.split(":", 2);
            if (parts.length == 2) {
                String key = parts[0].trim();
                String value = parts[1].trim();
                dataMap.put(key, value);
            }
        }
        return dataMap;
    }
}
