package org.example.review.model;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.example.review.model.ReportForOneFile.FactureDescription;

public class GlobalReport {
    
    private final File inputDir;
    private final LocalDateTime date;
    private final List<ReportForOneFile> reports;
    
    public GlobalReport(File inputDir) {
        super();
        this.inputDir = inputDir;
        this.date = LocalDateTime.now();
        this.reports = new ArrayList<>();
    }

    public void addAll(List<ReportForOneFile> reports) {
        this.reports.addAll(reports);
    }
    
    public int getNbFacturesTraitees() {
        return (int) this.reports.stream()
            .filter(rp->!rp.getFactures().isEmpty())
            .flatMap(rp->rp.getFactures().stream())
            .count();
    }
    
    public Collection<FactureDescription> getFacturesTraitesAvecSucces() {
        return this.reports.stream()
            .flatMap(rp->rp.getFacturesAvecDescriptionCalculee().stream())
            .toList();
    }
    
    public int getNbFichiersTraites() {
        return this.reports.size();
    }
    
    public Collection<File> getFichiersTraitesAvecErreurs() {
        return this.reports.stream()
                .filter(rp->!rp.getErrors().isEmpty())
                .map(rp->rp.getFile())
                .toList();
    }
    
    public record FichierAvecErreur(File file, List<String> erreurs) {};
    
    public Collection<FichierAvecErreur> getFichiersAvecErreurs() {
        return this.reports.stream()
                .filter(rp->!rp.getErrors().isEmpty())
                .map(rp->{
                    List<String> erreurs = rp.getErrors().stream().map(e->e.error()).toList();
                    return new FichierAvecErreur(rp.getFile(), erreurs);
                })
                .toList();
    }

    public List<ReportForOneFile> getReports() {
        return reports;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public File getInputDir() {
        return inputDir;
    }
    
}
