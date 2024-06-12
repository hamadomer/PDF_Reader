package org.example.review.parser;

import java.io.File;
import java.util.List;

import org.example.review.model.Facture;
import org.example.review.model.GlobalReport;
import org.example.review.model.ReportForOneFile;

public interface FactureFileParser {
    
    ReportForOneFile extractFactures(File file);

}
