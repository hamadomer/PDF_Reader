package org.example.test.review;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.nio.file.Path;

import org.example.review.FacturesCalculDescriptionMain;
import org.junit.jupiter.api.Test;

public class FacturesCalculDescriptionMainTest {
    
    @Test
    public void testReadFileAndCreateDataNoExceptionThrownWhenGivenValidDir() {
        FacturesCalculDescriptionMain fcd = new FacturesCalculDescriptionMain();
        assertDoesNotThrow(() -> fcd.calculerDescriptionsDesFacturesFromDir(Path.of("src", "main", "resources")));
    }


}
