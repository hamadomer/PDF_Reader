package org.example.test.review.filehandler;

import java.io.File;
import java.nio.file.Path;

import org.example.review.model.ReportForOneFile;
import org.example.review.parser.FactureFileParserCsv;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FactureFileParserCsvTest {
    
    @Test
    public void parseValidCsv() {
        
        File validPdf = Path.of("src/test/resources/ReadFileTest/testCsv.csv").toFile();
        
        FactureFileParserCsv parser = new FactureFileParserCsv();
        ReportForOneFile rp = parser.extractFactures(validPdf);
        
        Assertions.assertTrue(rp.getErrors().isEmpty());
        
        rp.getFactures().forEach(facture->System.out.println(facture));
        // TODO faire mieux les asserts
    }

}
