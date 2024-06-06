package org.example;

import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class CsvHandler {


    ArrayList <Data> dataList = new ArrayList<>();

    public Data csvToData(String path, int index) throws IOException, CsvValidationException {
        try (FileReader fileReader = new FileReader(path);
             CSVReaderHeaderAware csvReader = new CSVReaderHeaderAware(fileReader)) {

            Map<String, String> values;
            while ((values = csvReader.readMap()) != null) {
                String type;
                if ("paiement".equals(values.get("type"))) {
                    type = "Demande de paiement de facture";
                } else {
                    type = "Acquittement de facture";
                }
                dataList.add(new Data(type, values.get("nom"), values.get("prenom"), values.get("numero"), values.get("date"), values.get("objet"), values.get("montant")));
            }
        }

        if (!dataList.isEmpty()) {
            return dataList.get(index);
        } else {
            return new Data("empty", "empty", "empty", "empty", "empty", "empty", "empty");
        }
    }
    }

