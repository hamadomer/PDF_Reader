package org.example;

import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws CsvValidationException, IOException {
//        CsvHandler fromCsvToPdf = new CsvHandler();
//        Data mydata = fromCsvToPdf.csvToData("src\\main\\resources\\testCsv.csv", 0);
//        System.out.println(mydata.getData());
//        PdfHandler pdf = new PdfHandler();
//        pdf.dataToPdf(mydata, "src\\main\\resources\\exo2_format1.pdf" );
//        ObjectMethods csv = new ObjectMethods();
//        System.out.println(csv.generateResponse(mydata.getData()));
//        Execl test = new Execl();
//        System.out.println(test.wordPattern("aaaa", "dog cat cat dog"));
        CreatePdf pdf = new CreatePdf();
        pdf.dataToPdf();
    }
}
