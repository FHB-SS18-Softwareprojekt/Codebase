import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.stream.Stream;


public class PDFDocReader implements IDocumentReader {

    PDDocument document;

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

    public void savePDF(Stream<String> sentences, File file) throws IOException {
        String path = file.getPath();
        document = new PDDocument();
        int line = 1;

        InputStream font = getClass().getResourceAsStream("arial.ttf");

        PDPage page1 = new PDPage();
        document.addPage(page1);

        PDPageContentStream contentStream = new PDPageContentStream(document, page1);
        contentStream.beginText();
        contentStream.setFont(PDType0Font.load(document, font), 12);
        contentStream.newLineAtOffset(25, 750);
        contentStream.setLeading(14.5f);
        Iterator<String> iterator = sentences.iterator();
        while (iterator.hasNext()) {
            int start = 0;
            String text = iterator.next();
            for (int i = 85; i < text.length(); i = i + 85) {
                if (line > 50) {
                    line = 0;
                    contentStream.endText();
                    contentStream.close();
                    PDPage newPage = new PDPage();
                    document.addPage(newPage);

                    contentStream = new PDPageContentStream(document, newPage);
                    contentStream.beginText();
                    contentStream.setFont(PDType0Font.load(document, font), 12);
                    contentStream.newLineAtOffset(25, 750);
                    contentStream.setLeading(14.5f);
                }
                contentStream.showText(text.substring(start, i));
                if (!text.substring(i - 1, i).equals(" ")) {
                    while (!text.substring(i - 1, i).equals(" ") && !text.substring(i - 1, i).equals(".")) {
                        contentStream.showText(text.substring(i, i + 1));
                        i++;
                        start++;
                    }
                }
                contentStream.newLine();
                line++;

                start = start + 85;
            }
            line++;
            contentStream.showText(text.substring(start, text.length()));
            contentStream.newLine();
        }

        contentStream.endText();
        contentStream.close();
        document.save(path);
        document.close();
    }
}
