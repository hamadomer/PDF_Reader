package org.example.review.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReportForOneFile {
    
    public record ErrorExecution(String error, Exception e) {}
    
    public record FactureDescription(Facture facture, String description) {
        
        FactureDescription addDescription(String description) {
            return new FactureDescription(this.facture, description);
        }
        
    }
    
    private final File file;
    private final List<FactureDescription> facturesEtDescriptions;
    private final List<ErrorExecution> errors;
    
    public ReportForOneFile(File file) {
        this.file = file;
        this.facturesEtDescriptions = new ArrayList<FactureDescription>();
        this.errors = new ArrayList<ReportForOneFile.ErrorExecution>();
    }
    
    public ReportForOneFile addFacture(Facture facture) {
        this.facturesEtDescriptions.add(new FactureDescription(facture, null));
        return this;
    }
    
    public ReportForOneFile setFactureDescription(Facture facture, String description) {
        this.facturesEtDescriptions.replaceAll(fd->{
            if (fd.facture()==facture) { // égalité par mémoire en cas de doublon fonctionnel
                return fd.addDescription(description);
            } else {
                return fd;
            }
        });
        return this;
    }
    
    public ReportForOneFile addError(String error, Exception e) {
        this.errors.add(new ErrorExecution(error, e));
        return this;
    }

    public File getFile() {
        return file;
    }

    public List<Facture> getFactures() {
        return facturesEtDescriptions.stream()
                .map(fd->fd.facture())
                .toList();
    }
    
    public List<FactureDescription> getFacturesAvecDescriptionCalculee() {
        return facturesEtDescriptions.stream()
                .filter(fd->Objects.nonNull(fd.description()))
                .toList();
    }

    public List<ErrorExecution> getErrors() {
        return errors;
    }
    
    

}
