import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PDFDocReader_Test {

    private PDFDocReader pdfReader;
    private String noExtensionPath, unknownExtensionPath, helloPdfPath;

    @BeforeEach
    void initEach() {
        pdfReader = new PDFDocReader();

        noExtensionPath = "./src/test/resources/pdf";
        unknownExtensionPath = "./src/test/resources/pdf/test.dat";
        helloPdfPath = "./src/test/resources/pdf/HelloWorld.pdf";
    }

    @Test
    void readDocument() {
        assertThrows(NullPointerException.class, () -> pdfReader.readDocument(null));
        assertThrows(IllegalArgumentException.class, () -> pdfReader.readDocument(noExtensionPath));
        assertThrows(IllegalArgumentException.class, () -> pdfReader.readDocument(unknownExtensionPath));
        try {
            assertEquals("hello world", pdfReader.readDocument(helloPdfPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}