import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PDFDocReader_Test {

    private PDFDocReader pdfReader;
    private String noExtensionPath, unknownExtensionPath, noFilePath, helloPdfPath;

    @BeforeEach
    void initEach() {
        pdfReader = new PDFDocReader();

        noExtensionPath = "./src/test/resources/pdf";
        unknownExtensionPath = "./src/test/resources/pdf/test.dat";
        noFilePath = "./src/test/resources/pdf/noFile.pdf\"";
        helloPdfPath = "./src/test/resources/pdf/HelloWorld.pdf";
    }

    @Test
    void test_readDocument_null() {
        assertThrows(NullPointerException.class, () -> pdfReader.readDocument(null));
    }

    @Test
    void test_readDocument_noExtension() {
        assertThrows(IllegalArgumentException.class, () -> pdfReader.readDocument(noExtensionPath));
    }

    @Test
    void test_readDocument_unknownExtension() {
        assertThrows(IllegalArgumentException.class, () -> pdfReader.readDocument(unknownExtensionPath));
    }

    @Test
    void test_readDocument_noFile() {
        assertThrows(IllegalArgumentException.class, () -> pdfReader.readDocument(noFilePath));
    }

    @Test
    void test_readDocument_normal() {
        try {
            assertEquals("hello world", pdfReader.readDocument(helloPdfPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}