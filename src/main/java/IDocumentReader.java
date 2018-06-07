import java.io.File;
import java.io.IOException;

public interface IDocumentReader {
    String readDocument(String path) throws IOException;
}
