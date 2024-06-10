
import org.example.DataController;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;


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
}
