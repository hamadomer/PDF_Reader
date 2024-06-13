package org.example.review.parser;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.example.review.model.Facture;
import org.example.review.model.ReportForOneFile;
import org.example.review.model.Facture.Type;

import com.opencsv.CSVReaderHeaderAware;

public class FactureFileParserCsv implements FactureFileParser{

    @Override
    public ReportForOneFile extractFactures(File file) {

        ReportForOneFile reportParser = new ReportForOneFile(file);
        
        try (FileReader fileReader = new FileReader(file); CSVReaderHeaderAware csvReader = new CSVReaderHeaderAware(fileReader)) {
            Map<String, String> values;
            while ((values = csvReader.readMap()) != null) {
                try {
                    Facture facture = toFacture(values);
                    reportParser.addFacture(facture);
                }
                catch(Exception e) {
                    reportParser.addError(e.getMessage(), e);
                }
            }
        }
        catch(Exception e) {
            reportParser.addError(e.getMessage(), e);
        }
        
        return reportParser;
        
    }

    public Facture toFacture(Map<String, String> data) {
        Facture facture = new Facture();
        facture.setType(toType(data.get("type")));
        facture.setDate(data.get("date"));
        facture.setMontant(data.get("montant"));
        facture.setNumero(data.get("numero"));
        facture.setNom(data.get("nom"));
        facture.setPrenom(data.get("prenom"));
        facture.setMontant(data.get("montant"));
        facture.setObjet(data.get("objet"));
        return facture;
    }
    
    public Facture.Type toType(String type){
        if (Objects.nonNull(type)) {
            if ("paiement".equalsIgnoreCase(type.trim())) {
                return Type.PAIEMENT;
            }
            else if ("acquittement".equalsIgnoreCase(type.trim())){
                return Type.ACQUITTEMENT;
            }
        }
        throw new IllegalArgumentException("Aucun type de facture associé au titre "+type);
    }

}
