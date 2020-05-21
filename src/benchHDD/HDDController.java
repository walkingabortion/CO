package benchHDD;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class HDDController
{
    @FXML
    Button set_button;
    @FXML TextField path_text;

    public static String getPath() {
        return path;
    }

    public static String path;

    public void go_back()
    {
        setPath();

        Parent root= null;
        try {
            root = FXMLLoader.load(getClass().getResource("/resources/mainWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage= (Stage) set_button.getScene().getWindow();

        stage.setTitle("Welcome!");
        stage.setScene(new Scene(root, 476.0, 373.0));
        stage.show();

    }

    public void setPath()
    {
        path=path_text.getText();
    }


}


