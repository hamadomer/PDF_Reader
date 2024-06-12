package org.example.review.reportexporter;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.example.review.model.GlobalReport;

import com.opencsv.CSVWriter;

public class ReportExporterFileCsv implements ReportExporter {
    
    private static final String PATH_SUCCESS_OUTPUT = "src/test/resources/ReadFileTest/success_%s.csv";
    private static final String PATH_ERRORS_OUTPUT = "src/test/resources/ReadFileTest/errors_%s.csv";
    
    @Override
    public void notifyReport(GlobalReport globalReport) {
        
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_hhmmss"));
        File successOuputFile = new File(String.format(PATH_SUCCESS_OUTPUT, now));
        File errorsOuputFile = new File(String.format(PATH_ERRORS_OUTPUT, now));
        
        ecrireSuccess(globalReport, successOuputFile);
        ecrireErreurs(globalReport, errorsOuputFile);
    }

    private void ecrireSuccess(GlobalReport globalReport, File successOuputFile) {
        try (FileWriter outputfile = new FileWriter(successOuputFile); CSVWriter writer = new CSVWriter(outputfile)) {
            String[] header = {"numero", "description"};
            writer.writeNext(header);
            globalReport.getFacturesTraitesAvecSucces().forEach(fd->{
                String[] input = {fd.facture().getNumero(), fd.description()};
                writer.writeNext(input);
            });
        }
        catch(Exception e) {
            throw new RuntimeException("erreur lors de la notification des succes", e);
        }
    }
    
    private void ecrireErreurs(GlobalReport globalReport, File errorsOuputFile) {
        try (FileWriter outputfile = new FileWriter(errorsOuputFile); CSVWriter writer = new CSVWriter(outputfile)) {
            String[] header = {"nomFichier", "erreur"};
            writer.writeNext(header);
            globalReport.getFichiersAvecErreurs().forEach(fe->{
                String[] input = {fe.file().getAbsolutePath(), String.join("/", fe.erreurs())};
                writer.writeNext(input);
            });
        }
        catch(Exception e) {
            throw new RuntimeException("erreur lors de la notification des echecs", e);
        }
    }


        

}
