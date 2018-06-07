import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WordDocReader_Test {

    private WordDocReader wordReader;
    private File noDocFile, noDocxFile, helloDoc, helloDocx;
    private String noDocPath, noDocxPath, noExtensionPath, unknownExtensionPath, helloDocPath, helloDocxPath;


    @BeforeEach
    void initEach() {
        wordReader = new WordDocReader();
        noDocPath = "./src/test/resources/word/test.doc";
        noDocxPath = "./src/test/resources/word/test.docx";
        noExtensionPath = "./src/test/resources/word";
        unknownExtensionPath = "./src/test/resources/word/test.dat";
        helloDocPath = "./src/test/resources/word/Hello.doc";
        helloDocxPath = "./src/test/resources/word/Hello.docx";

        noDocFile = new File(noDocPath);
        noDocxFile = new File(noDocxPath);
        helloDoc = new File(helloDocPath);
        helloDocx = new File(helloDocxPath);
    }

    @Test
    void test_readDocument_null() {
        assertThrows(NullPointerException.class, () -> wordReader.readDocument(null));
    }

    @Test
    void test_readDocument_noExtension() {
        assertThrows(IllegalArgumentException.class, () -> wordReader.readDocument(noExtensionPath));
    }

    @Test
    void test_readDocument_unknownExtension() {
        assertThrows(IllegalArgumentException.class, () -> wordReader.readDocument(unknownExtensionPath));
    }

    @Test
    void test_readDocument_noFile() {
        assertThrows(IOException.class, () -> wordReader.readDocument(noDocPath));
        assertThrows(IOException.class, () -> wordReader.readDocument(noDocxPath));
    }

    @Test
    void test_readDocument_normal() {
        try {
            assertEquals("Hello World", wordReader.readDocument(helloDocPath));
            assertEquals("Hello World", wordReader.readDocument(helloDocxPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test_readDocFile_null() {
        assertThrows(NullPointerException.class, () -> wordReader.readDocFile(null));
    }

    @Test
    void test_readDocFile_noFile() {
        assertThrows(IOException.class, () -> wordReader.readDocFile(noDocFile));
    }

    @Test
    void test_readDocFile_wrongExtension() {
        assertThrows(IllegalArgumentException.class, () -> wordReader.readDocFile(helloDocx));
    }

    @Test
    void test_readDocFile_normal() {
        try {
            assertEquals("Hello World", wordReader.readDocFile(helloDoc));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test_readDocxFile_null() {
        assertThrows(NullPointerException.class, () -> wordReader.readDocxFile(null));
    }

    @Test
    void test_readDocxFile_noFile() {
        assertThrows(IOException.class, () -> wordReader.readDocxFile(noDocxFile));
    }

    @Test
    void test_readDocxFile_wrongExtension() {
        assertThrows(IllegalArgumentException.class, () -> wordReader.readDocxFile(helloDoc));
    }

    @Test
    void test_readDocxFile_normal() {
        try {
            assertEquals("Hello World", wordReader.readDocxFile(helloDocx));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}