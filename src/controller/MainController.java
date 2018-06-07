package controller;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController extends Application {
	static Stage ps;
	public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
    	ps=primaryStage;
        FXMLLoader loader =new FXMLLoader();
        loader.setLocation(getClass().getResource("summarizerMain.fxml"));
        try {
        	Scene scene = new Scene (loader.load());
			primaryStage.setScene(scene);
		} catch (IOException e) {

			e.printStackTrace();
		}
        primaryStage.show();
    }
}