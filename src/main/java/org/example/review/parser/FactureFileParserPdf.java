package org.example.review.parser;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.example.review.model.Facture;
import org.example.review.model.ReportForOneFile;
import org.example.review.model.Facture.Type;

public class FactureFileParserPdf implements FactureFileParser{

    @Override
    public ReportForOneFile extractFactures(File file) {
        
        ReportForOneFile reportParser = new ReportForOneFile(file);
        
        try (PDDocument document = Loader.loadPDF(file)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);

            Map<String, String> dataMap = parseTextToMap(text);
            
            Facture facture = toFacture(dataMap);
            reportParser.addFacture(facture);

        }
        catch(Exception e) {
            reportParser.addError(e.getMessage(), e);
        }
        
        return reportParser;
    }
    
    public Facture toFacture(Map<String, String> data) {
        Facture facture = new Facture();
        facture.setType(toType(data.get("title")));
        facture.setDate(data.get("date"));
        facture.setMontant(data.get("montant"));
        facture.setNumero(data.get("facture"));
        facture.setNom(data.get("nom"));
        facture.setPrenom(data.get("prénom"));
        facture.setMontant(data.get("montant"));
        facture.setObjet(data.get("objet"));
        return facture;
    }
    
    public Facture.Type toType(String title){
        if ("Demande de paiement de facture".equalsIgnoreCase(title.trim())) {
            return Type.PAIEMENT;
        }
        else if ("Acquittement de facture".equalsIgnoreCase(title.trim())){
            return Type.ACQUITTEMENT;
        }
        else {
            throw new IllegalArgumentException("Aucun type de facture associé au titre "+title);
        }
    }
    
    public Map<String, String> parseTextToMap(String text) {
        Map<String, String> dataMap = new HashMap<>();
        String[] lines = text.split("\\r?\\n");
        
        if (lines.length==0) {
            throw new IllegalArgumentException("Aucune donnée dans le .pdf");
        }

        if (lines.length > 0) {
            dataMap.put("title", lines[0].trim());
        }

        for (String line : lines) {
            String[] parts = line.split(":", 2);
            if (parts.length == 2) {
                dataMap.put(parts[0].trim().toLowerCase(), parts[1].trim());
            }
        }

        return dataMap;
    }

}
