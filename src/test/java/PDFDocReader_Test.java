import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PDFDocReader_Test {

    private PDFDocReader pdfReader;

    @BeforeEach
    void initEach() {
        pdfReader = new PDFDocReader();
    }


    @Test
    void readDocument() {
        assertThrows(NullPointerException.class, () -> pdfReader.readDocument(null));
    }
}