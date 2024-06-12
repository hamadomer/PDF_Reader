
import com.opencsv.exceptions.CsvValidationException;
import org.example.CsvHandler;
import org.example.Data;
import org.example.DataController;

import org.example.PdfHandler;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class DataControllerTest {

    @Test
    public void testReturnErrorWhenDirNotFound() {
        DataController data = new DataController();
        FileNotFoundException exception = assertThrows(FileNotFoundException.class, () -> data.readFileAndCreateData("some-random-dir-that-doesnot-exist"));
        assertEquals("No directory found in the path given", exception.getMessage());
    }

    @Test
    public void testReadFileAndCreateDataNoExceptionThrownWhenGivenValidDir() {
        DataController data = new DataController();
        assertDoesNotThrow(() -> data.readFileAndCreateData("src\\main\\resources"));
    }

    @Test
    public void testReadFileAndCreateDataWithWorkingData() throws CsvValidationException, IOException {
        Path outputPath = Paths.get("src/test/resources/ReadFileTest/output.csv");
        if (Files.exists(outputPath)) {
            Files.delete(outputPath);
        }

        DataController returnSome = new DataController();
        returnSome.readFileAndCreateData("src/test/resources/ReadFileTest/");


        List<String> lines = Files.readAllLines(outputPath);
        lines.toArray(new String[0]);
        assertEquals("\"numero\",\"description\"", lines.get(0));
        assertEquals("\"Hurricane123\",\"Demande de paiement MVP (1 000 000 dollars) via Facturo1999, de la facture Hurricane123\"", lines.get(1));
        assertEquals("\"Hurricane123\",\"Acquittement automatique de la facture Hurricane123 MVP (1 000 000 dollars)\"", lines.get(2));
    }
}
