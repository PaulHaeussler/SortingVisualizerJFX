package UI;

import Calculations.StepController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Main;

/**
 * Class for building all windows of this application.
 * @author Paul Häussler
 */

public class Window {

    public Stage InitStage;
    public Stage VisualizationStage;

    /**
     * Creates the initial window from its fxml.
     */

    public void initializeInit() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Init.fxml"));
            Parent root = loader.load();
            Main.initController = loader.getController();
            InitStage = new Stage();
            InitStage.setTitle("SortingVisualizer");
            InitStage.setScene(new Scene(root));

            InitStage.setOnCloseRequest(event -> {
                Platform.exit();
                System.exit(0);
            });

            InitStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the help window from its fxml.
     */

    public void initializeHelp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Help.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Algorithmeninformation");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the visualization window from its fxml, as well as the stepController for it, since it needs to be
     * created beforehand. Sets a listener to terminate all running threads on close.
     */

    public void initializeVisualization() {
        try {
            InitStage.hide();
            Main.stepController = new StepController(Main.pickedAlgo, Main.input);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Visualization.fxml"));
            Parent root = loader.load();
            Main.visualizationController = loader.getController();
            Main.visualizationRoot = root;
            VisualizationStage = new Stage();
            VisualizationStage.setTitle("Sorting Visualizer");
            VisualizationStage.setScene(new Scene(root));

            VisualizationStage.setOnCloseRequest(event -> {
                Platform.exit();
                System.exit(0);
            });


            VisualizationStage.show();



        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
