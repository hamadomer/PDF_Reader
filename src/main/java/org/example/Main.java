package org.example;

public class Main {
    public static void main(String[] args) {
//        PdfHandler pdf = new PdfHandler();
//        System.out.println(pdf.pdfToString("src\\main\\resources\\exo2_format1.pdf"));
//        PdfHandler pdfCreated = new PdfHandler();
//        pdfCreated.createPdf();
        Data mydata = new Data("Some title", "Omer", "hamad", "FR2123456456", "11/02/2021", "Some objet", "100");
        PdfHandler pdf = new PdfHandler();
        pdf.dataToPdf(mydata, "src\\main\\resources\\test.pdf");
    }
}
