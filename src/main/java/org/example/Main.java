package org.example;

public class Main {
    public static void main(String[] args) {
        PdfHandler pdf = new PdfHandler();
        System.out.println(pdf.pdfToString("src\\main\\resources\\exo2_format1.pdf"));
        PdfHandler pdfCreated = new PdfHandler();
        pdfCreated.createPdf();
    }
}
