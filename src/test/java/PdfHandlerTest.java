import org.example.PdfHandler;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PdfHandlerTest {

        @Test
        public void testPdfReturnPreSetSString () {
            PdfHandler pdf = new PdfHandler();
            assertEquals("Demande de paiement de la facture Hurricane123, pour un montant de 1 000 000 dollars, émise le 30/11/1975", pdf.pdfToString("src\\test\\resources\\exo2_format1.pdf"));
            assertEquals("Acquittement automatique de la facture FACT10293", pdf.pdfToString("src\\test\\resources\\exo2_format2.pdf"));
        }
}