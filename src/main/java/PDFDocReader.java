import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFDocReader implements IDocumentReader {
    @Override
    public String readDocument(String path) throws IOException {
        File file = new File(path);

        String fileExtension = FilenameUtils.getExtension(file.getName());
        if (fileExtension.equalsIgnoreCase("pdf")) {
            try (PDDocument document = PDDocument.load(file)) {
                // Create TextStripper
                PDFTextStripper pdfStripper = new PDFTextStripper();
                // Strip text from document (ignore the formatting)
                return pdfStripper.getText(document).trim();
            }
        } else {
            throw new IllegalArgumentException("File type not supported: " + fileExtension);
        }


    }
}
