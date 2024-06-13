package org.example.review.reportexporter;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import org.example.Mail;
import org.example.review.model.GlobalReport;

public class ReportExporterMail implements ReportExporter {
    
    @Override
    public void notifyReport(GlobalReport globalReport) {
        
        Collection<File> fichiersAvecErreurs = globalReport.getFichiersTraitesAvecErreurs();
        Collection<String> nomDesFichiersAvecErreurs = fichiersAvecErreurs.stream()
                .map(f->f.getAbsolutePath())
                .toList();
        
        Mail mail = new Mail();
        mail.setFileName(globalReport.getInputDir().getPath());
        mail.setDate(globalReport.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        mail.setNumberOfScannedDocs(globalReport.getNbFichiersTraites());
        mail.setNamesOfInvalidDocs(String.join(",", nomDesFichiersAvecErreurs));
        mail.setNumberOfInvalidDocs(fichiersAvecErreurs.size());
        
        System.out.println("Envoi du mail "+mail.getSujet());
        System.out.println(mail.getCorps());
    }

}
