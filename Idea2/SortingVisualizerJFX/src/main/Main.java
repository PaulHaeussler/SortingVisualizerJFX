package main;

import Calculations.SortingEntry;
import Calculations.StepController;
import UI.VisualizationController;
import UI.Window;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    public static Window window = new Window();
    public static ArrayList<SortingEntry> input;
    public static StepController.SortAlgos pickedAlgo;
    public static VisualizationController visualizationController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window.initializeInit();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void runVisualization(){
        window.initializeVisualization();
    }
}
