
package controller;


import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
/**
 * Actionevents der SummerizerGUI
 * @author Andre Matutat
 *
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
	    private  void changeSliderText(MouseEvent event) {
	    	sliderText.setText(getSliderValue()+"%");
	    }

	    private int getSliderValue(){
	    	double rv= 0.3+0.4*slider.getValue()/100;
	    	rv=rv*100;
	    	return  (int) rv;
	    }

	    @FXML
	    private void exportText(ActionEvent event) {
	    	FileChooser fc= new FileChooser();
	    	fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF","*.pdf"));
	    	File f =fc.showSaveDialog(null);
	    	if (f!=null)
	    	shortTextArea.setText(f.getPath());
	    }

	    @FXML
	    private  void importText(ActionEvent event) {
	    	FileChooser fc= new FileChooser();
	    	File f =fc.showOpenDialog(null);
	    	if (f!=null)
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
	    private String getText(String s){
	    	return s+"yay";
	    }

	}

