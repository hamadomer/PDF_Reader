package org.example;

import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws CsvValidationException, IOException {
        CsvHandler fromCsvToPdf = new CsvHandler();
        Data mydata = fromCsvToPdf.csvToData("src\\main\\resources\\testCsv.csv", 0);
        System.out.println(mydata.getObjet());
        ObjectMethods csv = new ObjectMethods();
        System.out.println(csv.generateResponse(mydata.getData()));
    }
}
