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
        if (f != null)
            shortTextArea.setText(f.getPath());
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
            shortTextArea.setText(f.getPath());
    }

    @FXML
    private void sendText(ActionEvent event) {
        shortTextArea.setText(getText(longTextArea.getText()));
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

    private String getText(String s) {
        String t = "Test123";
        return t;
    }

}

