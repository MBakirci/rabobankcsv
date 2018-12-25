package javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
    primaryStage.getIcons().add(new Image("/img/rabobankcsv.png"));
    primaryStage.setTitle("Rabobank CSV parser 2.0");
    primaryStage.setScene(new Scene(root, 400, 275));
    primaryStage.show();
  }
}
