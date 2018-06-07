package controller;


import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class summerizerMainController {






    @FXML
    private MenuItem importButton;

    @FXML
    private MenuItem exportButton;

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
	    void changeSliderText(MouseEvent event) {
	    	sliderText.setText(getSliderValue()+"%");
	    }

	    private int getSliderValue(){
	    	double rv= 0.3+0.4*slider.getValue()/100;
	    	rv=rv*100;
	    	return  (int) rv;
	    }
	    @FXML
	    void exportText(ActionEvent event) {
	    	FileChooser fc= new FileChooser();
	    	File f =fc.showSaveDialog(null);
	    	if (f!=null)
	    	shortTextArea.setText(f.getPath());
	    }

	    @FXML
	    void importText(ActionEvent event) {
	    	FileChooser fc= new FileChooser();
	    	File f =fc.showOpenDialog(null);
	    	if (f!=null)
	    	shortTextArea.setText(f.getPath());
	    }

	    @FXML
	    void sendText(ActionEvent event) {
	    	shortTextArea.setText(getText(longTextArea.getText()));
	    }
	    String getText(String s){
	    	return s+"yay";
	    }

	}

