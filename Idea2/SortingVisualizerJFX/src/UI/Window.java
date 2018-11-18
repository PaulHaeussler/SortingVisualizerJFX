package UI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Window {


    public void initializeInit() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Init.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("SortingVisualizer");
            stage.setScene(new Scene(root));
            stage.show();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
