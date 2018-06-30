import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Actionevents der SummerizerGUI
 *
 * @author Andre Matutat
 */
public class SummarizerGUIController {

    @FXML
    private MenuItem importButton;

    @FXML
    private MenuItem exportButton;

    @FXML
    private MenuItem helpButton;

    @FXML
    private Text sliderText;

    @FXML
    private Slider slider;

    @FXML
    private Button okButton;

    @FXML
    private TextArea longTextArea;

    @FXML
    private TextArea shortTextArea;

    private final TextParser textParser;
    private final Summarizer summarizer;

    public SummarizerGUIController() {
        ConfigLink config = new ConfigLink(new File(getClass().getResource("../resources/config").getFile()));
        this.textParser = new TextParser(config);
        this.summarizer = new Summarizer(textParser);
    }

    @FXML
    private void changeSliderText(MouseEvent event) {
        sliderText.setText(getSliderValue() + "%");
    }

    private int getSliderValue() {
        double rv = 30 + 0.4 * slider.getValue();
        return (int) rv;
    }

    @FXML
    private void exportText(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        File f = fc.showSaveDialog(null);
        if (f != null) {
            //TODO: Export
        }
    }

    @FXML
    private void importText(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("DOC", "*.doc"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("DOCX", "*.docx"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));
        File f = fc.showOpenDialog(null);
        if (f != null)
            try {
                String[] text = this.textParser.getTextFromPath(f.getPath());
                String textFormatted = text[0] + "\n" + text[1];
                this.longTextArea.setText(textFormatted);
            } catch (IOException e) {
            	showError("Fehler beim Importieren: "+e.getMessage());
            }
    }

    @FXML
    private void sendText(ActionEvent event) {
        String[] split = longTextArea.getText().split("\n", 1);
        float amount = (100 - getSliderValue()) / 100f;
        List<Sentence> summarized = split.length > 1 ? this.summarizer.summarize(split[1], split[0], amount) : this.summarizer.summarize(split[0], "", amount);
        String text = summarized.stream().map(Sentence::getText).collect(Collectors.joining("\n -","- ",""));
        shortTextArea.setText(text);
    }


    @FXML
    private void showHelp(ActionEvent event) {
        Label secondLabel = new Label("Hier steht eine tolle Anleitung");
        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(secondLabel);
        Scene secondScene = new Scene(secondaryLayout, 230, 100);
        Stage newWindow = new Stage();
        newWindow.setTitle("Hilfe");
        newWindow.setScene(secondScene);
        newWindow.show();

    }

    @FXML
    private void showError(String errMsg){
    	  Label secondLabel = new Label(errMsg);
          StackPane secondaryLayout = new StackPane();
          secondaryLayout.getChildren().add(secondLabel);
          Scene secondScene = new Scene(secondaryLayout, 230, 100);
          Stage newWindow = new Stage();
          newWindow.setTitle("FEHLER");
          newWindow.setScene(secondScene);
          newWindow.show();

    }


}

