import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WordDocReader_Test {

    private WordDocReader wordReader;
    private File docFile, docxFile, helloDoc, helloDocx;
    private String docPath, docxPath, noExtensionPath, unknownExtensionPath, helloDocPath, helloDocxPath;


    @BeforeEach
    void initEach() {
        wordReader = new WordDocReader();
        docPath = "./src/test/resources/word/test.doc";
        docxPath = "./src/test/resources/word/test.docx";
        noExtensionPath = "./src/test/resources/word";
        unknownExtensionPath = "./src/test/resources/word/test.dat";
        helloDocPath = "./src/test/resources/word/Hello.doc";
        helloDocxPath = "./src/test/resources/word/Hello.docx";

        docFile = new File(docPath);
        docxFile = new File(docxPath);
        helloDoc = new File(helloDocPath);
        helloDocx = new File(helloDocxPath);
    }

    @Test
    void readDocument() {
        assertThrows(NullPointerException.class, () -> wordReader.readDocument(null));
        assertThrows(IllegalArgumentException.class, () -> wordReader.readDocument(noExtensionPath));
        assertThrows(IllegalArgumentException.class, () -> wordReader.readDocument(unknownExtensionPath));
        assertThrows(IOException.class, () -> wordReader.readDocument(docPath));
        assertThrows(IOException.class, () -> wordReader.readDocument(docxPath));
        try {
            assertEquals("Hello World", wordReader.readDocument(helloDocPath));
            assertEquals("Hello World", wordReader.readDocument(helloDocxPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void readDocFile() {
        assertThrows(NullPointerException.class, () -> wordReader.readDocFile(null));
        assertThrows(IOException.class, () -> wordReader.readDocFile(docFile));
        assertThrows(IOException.class, () -> wordReader.readDocFile(docxFile));
        assertThrows(IllegalArgumentException.class, () -> wordReader.readDocFile(helloDocx));
        try {
            assertEquals("Hello World", wordReader.readDocFile(helloDoc));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void readDocxFile() {
        assertThrows(NullPointerException.class, () -> wordReader.readDocxFile(null));
        assertThrows(IOException.class, () -> wordReader.readDocxFile(docFile));
        assertThrows(IOException.class, () -> wordReader.readDocxFile(docxFile));
        assertThrows(IllegalArgumentException.class, () -> wordReader.readDocxFile(helloDoc));
        try {
            assertEquals("Hello World", wordReader.readDocxFile(helloDocx));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}