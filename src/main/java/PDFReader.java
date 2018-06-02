import java.io.IOException;
import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFReader {
    PDDocument document;

    public PDFReader(){

    }

    public void savePDF(String text)throws IOException{
        document = new PDDocument();
        File file;
        PDPage page1 = new PDPage();
        String OS =System.getProperty("os.name");
        
        if(OS.substring(0,7).equals("Windows")){
            file=new File("C:/Windows/Fonts/Arial.ttf");
        }
        else if(OS.substring(0,5).equals("Linux")){
            file=new File("/usr/share/fonts");
        }
        else{
            file=new File("/Library/Fonts/Arial.ttf");
        }
        document.addPage( page1 );

        PDPageContentStream contentStream = new PDPageContentStream(document, page1);
        contentStream.beginText();
        contentStream.setFont(PDType0Font.load(document, file), 12);
        contentStream.newLineAtOffset(25, 750);
        contentStream.showText(text);
        contentStream.endText();
        contentStream.close();

        document.save(System.getProperty("user.dir")+"/Summary.pdf");
        document.close();
    }

    public String readPDF(File doc) throws IOException{
        document=PDDocument.load(doc);
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);
        System.out.println(text);
        document.close();
        return text;
    }

    public static void main(String[] args) {
        PDFReader reader = new PDFReader();
        try {
            //File file=new File("C:/Users/Marc/Desktop/apple.pdf");
            //reader.readPDF(file);
            reader.savePDF("ABC");
        }
        catch (IOException e) {
            System.out.println("Fehler");
        }
    }
}
