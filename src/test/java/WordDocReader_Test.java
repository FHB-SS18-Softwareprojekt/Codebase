import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WordDocReader_Test {

    private WordDocReader wordReader;
    private File docFile, docxFile, noExtension, unknownExtension;


    @BeforeEach
    void initEach() {
        wordReader = new WordDocReader();
        docFile = new File("./src/test/resources/word/test.doc");
        docxFile = new File("./src/test/resources/word/test.docx");
        noExtension =  new File("./src/test/resources/word");
        unknownExtension = new File("./src/test/resources/word/test.dat");
    }

    @Test
    void readDocument() {
        assertThrows(NullPointerException.class, () -> wordReader.readDocument(null));
        assertThrows(IllegalArgumentException.class, () -> wordReader.readDocument(noExtension));
        assertThrows(IllegalArgumentException.class, () -> wordReader.readDocument(unknownExtension));
        assertThrows(IOException.class, () -> wordReader.readDocument(docFile));
        assertThrows(IOException.class, () -> wordReader.readDocument(docxFile));
    }

    @Test
    void readDocFile() {
        assertThrows(NullPointerException.class, () -> wordReader.readDocFile(null));
        assertThrows(IOException.class, () -> wordReader.readDocFile(docFile));
        assertThrows(IOException.class, () -> wordReader.readDocFile(docxFile));
    }

    @Test
    void readDocxFile() {
        assertThrows(NullPointerException.class, () -> wordReader.readDocxFile(null));
        assertThrows(IOException.class, () -> wordReader.readDocxFile(docFile));
        assertThrows(IOException.class, () -> wordReader.readDocxFile(docxFile));
    }
}