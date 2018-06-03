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
        int start=0;
        int line=1;

        File file;
        String OS =System.getProperty("os.name");

        if(OS.substring(0,5).equals("Linux")){
            file=new File("/usr/share/fonts/truetype/abyssinica/AbyssinicaSIL_R.ttf");
        }
        else if(OS.substring(0,7).equals("Windows")){
            file=new File("C:/Windows/Fonts/Arial.ttf");
        }
        else{
            file=new File("/Library/Fonts/Arial.ttf");
        }

        PDPage page1 = new PDPage();
        document.addPage(page1);

        PDPageContentStream contentStream = new PDPageContentStream(document, page1);
        contentStream.beginText();
        contentStream.setFont(PDType0Font.load(document, file), 12);
        contentStream.newLineAtOffset(25, 750);
        contentStream.setLeading(14.5f);
        for(int i=65; i<text.length();i=i+65) {
            contentStream.showText(text.substring(start, i));
            contentStream.newLine();
            line++;

            start = start + 65;
        }
        contentStream.showText(text.substring(start, text.length()));

        contentStream.endText();
        contentStream.close();
        document.save(System.getProperty("user.dir") + "/Summary.pdf");
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

    public String readPages(File doc,int start,int end)throws IOException{
        document=PDDocument.load(doc);

        PDFTextStripper reader = new PDFTextStripper();
        reader.setStartPage(start);
        reader.setEndPage(end);
        String pageText = reader.getText(document);

        System.out.println(pageText);
        return pageText;
    }

    public static void main(String[] args) {
        PDFReader reader = new PDFReader();
        try {
            //File file=new File("C:/Users/Marc/Desktop/Unbenannt 1.pdf");
            //reader.readPages(file,1,2);
            reader.savePDF("ABC");
        }
        catch (IOException e) {
            System.out.println("Fehler");
        }
    }
}
