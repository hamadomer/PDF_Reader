import org.example.Data;
import org.example.PdfHandler;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PdfHandlerTest {

        @Test
        public void testPdfReturnPreSetSString () throws IOException {
            PdfHandler pdf = new PdfHandler();
            assertEquals("Demande de paiement MVP (1 000 000 dollars) via Facturo1999, de la facture Hurricane123", pdf.pdfToString("src\\test\\resources\\exo2_format1.pdf"));
            assertEquals("Acquittement automatique de la facture FACT10293", pdf.pdfToString("src\\test\\resources\\exo2_format2.pdf"));
        }

        @Test
        public void testObjetContainsTacos () throws IOException {
            PdfHandler pdf = new PdfHandler();
            assertEquals("Commande Tacos (ShowFacture123)", pdf.pdfToString("src\\test\\resources\\tacos.pdf"));
        }

        @Test
        public void testObjectContainsDonuts () throws IOException {
            PdfHandler pdf = new PdfHandler();
            assertEquals("Commande Donuts (ShowFacture123)", pdf.pdfToString("src\\test\\resources\\donuts.pdf"));
        }
        @Test
        public void testObjectContainsTacosAndDonuts () throws IOException {
            PdfHandler pdf = new PdfHandler();
            assertEquals("Commande Donuts & Tacos (ShowFacture123)", pdf.pdfToString("src\\test\\resources\\donutstacos.pdf"));

        }

        @Test
        public void testFullNameMacron () throws IOException {
            PdfHandler pdf = new PdfHandler();
            assertEquals("Acquittement automatique de la facture émise par le Président de la République française (ShowFacture123)", pdf.pdfToString("src\\test\\resources\\EMacron.pdf"));

        }

        @Test
        public void testCreatePdfFromObject () throws IOException {
            Data data = new Data("Demande de paiement de facture", "Hamad", "Omer", "FA74544", "12/04/1998", "Tacos", "100");
            PdfHandler pdf = new PdfHandler();
            pdf.dataToPdf(data, "src\\test\\resources\\TacosData.pdf");
            assertEquals("Commande Tacos (FA74544)", pdf.pdfToString("src\\test\\resources\\TacosData.pdf"));
        }
}