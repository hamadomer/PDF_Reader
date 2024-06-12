package org.example.test.review.filehandler;

import java.io.File;
import java.nio.file.Path;

import org.example.review.model.ReportForOneFile;
import org.example.review.parser.FactureFileParserPdf;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FactureFileParserPdfTest {
    
    @Test
    public void parseValidPdf() {
        
        File validPdf = Path.of("src/test/resources/ReadFileTest/exo2_format1.pdf").toFile();
        
        FactureFileParserPdf parser = new FactureFileParserPdf();
        ReportForOneFile rp = parser.extractFactures(validPdf);
        
        Assertions.assertTrue(rp.getErrors().isEmpty());
        
        rp.getFactures().forEach(facture->System.out.println(facture));
        // TODO faire mieux les asserts
    }

}
