package org.example.review;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.example.review.description.CalculDescriptionService;
import org.example.review.model.Facture;
import org.example.review.model.GlobalReport;
import org.example.review.model.ReportForOneFile;
import org.example.review.parser.FactureFileParserCsv;
import org.example.review.parser.FactureFileParserPdf;
import org.example.review.reportexporter.ReportExporter;
import org.example.review.reportexporter.ReportExporterFileCsv;
import org.example.review.reportexporter.ReportExporterMail;
import org.example.review.utils.FileUtils;

public class FacturesCalculDescriptionMain {
    
    private FactureFileParserCsv factureFileHandlerCsv;
    private FactureFileParserPdf factureFileParserPdf;
    private CalculDescriptionService calculDescriptionService;
    private List<ReportExporter> reportExporters;
    
    public FacturesCalculDescriptionMain() {
        this.factureFileHandlerCsv = new FactureFileParserCsv();
        this.factureFileParserPdf = new FactureFileParserPdf();
        this.calculDescriptionService = new CalculDescriptionService();
        this.reportExporters = List.of(new ReportExporterFileCsv(), new ReportExporterMail());
    }
    
    public void calculerDescriptionsDesFacturesFromDir(Path dir) {
        
        GlobalReport globalReport = new GlobalReport();
        
        File[] files = listFilesInDir(dir);
        List<ReportForOneFile> reports = parseFiles(files);
        calculerDescriptions(reports);
        
        globalReport.addAll(reports);
        
        notifierRapport(globalReport);
        
    }

    private List<ReportForOneFile> parseFiles(File[] files) {
        List<ReportForOneFile> reportsParser = new ArrayList<>();
        
        for (File file : files) {
            ReportForOneFile reportParser = tryParseFacturesFromFile(file);
            reportsParser.add(reportParser);
        }
        return reportsParser;
    }

    private File[] listFilesInDir(Path dir) {
        File directory = dir.toFile();
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("No directory found in the given path");
        }
        
        File[] files = directory.listFiles(f->f.isFile());
        return files;
    }

    private ReportForOneFile tryParseFacturesFromFile(File file) {
        
        String extension = FileUtils.getFileExtension(file.getName());
        
        ReportForOneFile report = null;
        
        switch (extension) {
            case "pdf" -> report = factureFileParserPdf.extractFactures(file);
            case "csv" -> report = factureFileHandlerCsv.extractFactures(file);
            default -> report = new ReportForOneFile(file).addError("no existing parser for file "+file.getAbsolutePath(), null);
        }
        
        return report;
        
    }
    
    private void calculerDescriptions(List<ReportForOneFile> reports) {
        for (ReportForOneFile rp : reports) {
            for (Facture facture : rp.getFactures()) {
                try {
                    String description = calculDescriptionService.calculerDescription(facture);
                    rp.setFactureDescription(facture, description);
                }
                catch(Exception e) {
                    rp.addError("erreur au calcul de la description pour la facture "+facture, e);
                }
            }
            
        }
    }
    
    private void notifierRapport(GlobalReport globalReport) {

        for (ReportExporter exporter : this.reportExporters) {
            try {
                exporter.notifyReport(globalReport);
            }
            catch(Exception e) {
                System.err.println("erreur lors de la notification de "+exporter.getClass().getName());
                e.printStackTrace();
            }
        }
        
    }
    


}
