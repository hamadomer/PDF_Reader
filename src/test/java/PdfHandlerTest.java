import org.example.PdfHandler;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PdfHandlerTest {

        @Test
        public void testPdfReturnPreSetSString () {
            PdfHandler pdf = new PdfHandler();
            assertEquals("Demande de paiement MVP (1 000 000 dollars) via Facturo1999, de la facture Hurricane123", pdf.pdfToString("src\\test\\resources\\exo2_format1.pdf"));
            assertEquals("Acquittement automatique de la facture FACT10293", pdf.pdfToString("src\\test\\resources\\exo2_format2.pdf"));
        }

        @Test
        public void testObjetContainsTacos () {
            PdfHandler pdf = new PdfHandler();
            assertEquals("Commande Tacos (ShowFacture123)", pdf.pdfToString("src\\test\\resources\\tacos.pdf"));
        }

        @Test
        public void testObjectContainsDonuts () {
            PdfHandler pdf = new PdfHandler();
            assertEquals("Commande Donuts (ShowFacture123)", pdf.pdfToString("src\\test\\resources\\donuts.pdf"));
        }
        @Test
        public void testObjectContainsTacosAndDonuts () {
            PdfHandler pdf = new PdfHandler();
            assertEquals("Commande Donuts & Tacos (ShowFacture123)", pdf.pdfToString("src\\test\\resources\\donutstacos.pdf"));

        }

        @Test
        public void testFullNameMacron () {
            PdfHandler pdf = new PdfHandler();
            assertEquals("Acquittement automatique de la facture émise par le Président de la République française (ShowFacture123)", pdf.pdfToString("src\\test\\resources\\EMacron.pdf"));

        }
}