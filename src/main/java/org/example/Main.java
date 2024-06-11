package org.example;

import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws CsvValidationException, IOException {
        PdfHandler pdf = new PdfHandler();
        Data mydata = pdf.pdfToData("src/main/resources/exo2_format2.pdf");
//
////        CsvHandler fromCsvToPdf = new CsvHandler();
////        Data mydata = fromCsvToPdf.csvToData("src\\main\\resources\\testCsv.csv", 0);
//
////        DataController test = new DataController();
////        test.generateResponse(mydata.getData());
//        CsvHandler csv = new CsvHandler();
//        System.out.println();
//        csv.dataToCsv(mydata, "src/main/resources/test2.csv");
        DataController returnSome = new DataController();
        returnSome.readFileAndCreateData("src/test/resources/ReadFileTest/");
    }
}
