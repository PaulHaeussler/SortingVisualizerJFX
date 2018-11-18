package UI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import main.Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class WindowController implements Initializable {

    @FXML public ComboBox Cb_StrLength;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Integer> list //
                = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6);


        Cb_StrLength.setItems(list);
        Cb_StrLength.getSelectionModel().select(0);
    }
}
