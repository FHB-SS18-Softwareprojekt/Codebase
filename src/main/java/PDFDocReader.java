import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFDocReader implements IDocumentReader {
    @Override
    public String readDocument(File file) throws IOException {
        try (PDDocument document = PDDocument.load(file)) {
            // Create TextStripper
            PDFTextStripper pdfStripper = new PDFTextStripper();
            // Strip text from document (ignore the formatting)
            return pdfStripper.getText(document);
        }
    }
}
