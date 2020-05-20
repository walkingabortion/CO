import bench.hdd.TestHDDWriteSpeed;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logging.ConsoleLogger;
import timer.Timer;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("resources/mainWindow.fxml"));

        primaryStage.setTitle("Welcome!");
        primaryStage.setScene(new Scene(root, 550, 550));
        primaryStage.show();



    }


    public static void main(String[] args) {
        launch(args);
    }
}