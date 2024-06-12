package org.example;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CsvHandler {

    public void dataToCsv(Data data, String path) throws IOException {
        try (FileWriter outputfile = new FileWriter(path)) {
            CSVWriter writer = new CSVWriter(outputfile);
            String[] header = {"Title", "Nom", "Prénom", "Facture", "Date", "Objet", "Montant"};
            writer.writeNext(header);
            String[] info = {data.getTitle(), data.getNom(), data.getPrenom(), data.getFacture(), data.getDate(), data.getObjet(), data.getMontant()};
            writer.writeNext(info);
        }
    }

}

