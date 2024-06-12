package org.example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Mail {
    private String date;
    private String fileName;
    private int numberOfScannedDocs;
    private int numberOfInvalidDocs;
    private ArrayList<String> namesOfInvalidDocs;

    public Mail(String date, String fileName, int numberOfScannedDocs, int numberOfInvalidDocs, String[] namesOfInvalidDocs) {
        this.date = date;
        this.fileName = fileName;
        this.numberOfScannedDocs = numberOfScannedDocs;
        this.numberOfInvalidDocs = numberOfInvalidDocs;
        this.namesOfInvalidDocs = new ArrayList<>(Arrays.asList(namesOfInvalidDocs));
    }

    public Mail() {
        this.namesOfInvalidDocs = new ArrayList<>();
    }

    public void setNamesOfInvalidDocs(String name) {
        this.namesOfInvalidDocs.add(name);
    }

    public int getNumberOfFilesInArray () {
        return namesOfInvalidDocs.size();
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setNumberOfInvalidDocs(int numberOfInvalidDocs) {
        this.numberOfInvalidDocs = numberOfInvalidDocs;
    }

    public void setNumberOfScannedDocs(int numberOfScannedDocs) {
        this.numberOfScannedDocs = numberOfScannedDocs;
    }

    public String getSujet() {

        return "Sujet : Rapport d'exécution du " + this.date;
    }

    public String getCorps() {
        return "Corps : \n\nSuite à l'exécution du traitement " + this.fileName + " :\n"
                + this.numberOfScannedDocs + " éléments en entrée.\n"
                + this.numberOfInvalidDocs + " éléments en erreur parmi les fichiers ci-dessous :\n"
                + this.namesOfInvalidDocs.toString();
    }
}
