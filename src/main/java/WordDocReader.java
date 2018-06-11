import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import org.apache.commons.io.FilenameUtils;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

public class WordDocReader implements IDocumentReader {

    @Override
    public String readDocument(String path) throws IOException {
        File file = new File(path);
        String fileExtension = FilenameUtils.getExtension(file.getName());
        if (fileExtension.equalsIgnoreCase("doc")) {
            return readDocFile(file);
        } else if (fileExtension.equalsIgnoreCase("docx")) {
            return readDocxFile(file);
        } else {
            throw new IllegalArgumentException("File type not supported: " + fileExtension);
        }
    }

    protected String readDocFile(File file) throws IOException {
        try(FileInputStream inputStream = new FileInputStream(file.getAbsolutePath());
            HWPFDocument document = new HWPFDocument(inputStream);
            WordExtractor wordExtractor = new WordExtractor(document)) {
                return wordExtractor.getText().trim();
        }
    }

    protected String readDocxFile(File file) throws IOException {
        try(FileInputStream inputStream = new FileInputStream(file.getAbsolutePath());
            XWPFDocument document = new XWPFDocument(inputStream);
            XWPFWordExtractor wordExtractor = new XWPFWordExtractor(document)) {
            return wordExtractor.getText().trim();
        }
    }


}