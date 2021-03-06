import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Andre Matutat
 * Erstellt die GUI mithilfe der summarizerMain.fxml
 */
public class MainController extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("summarizerMain.fxml"));
        try {
            Scene scene = new Scene(loader.load());
            primaryStage.setTitle("!bigText");
            primaryStage.getIcons().add(new Image("file:Logo.png"));
            primaryStage.setMinHeight(550);
            primaryStage.setMinWidth(640);
            primaryStage.setScene(scene);

        } catch (IOException e) {

            e.printStackTrace();
        }
        primaryStage.show();
    }
}