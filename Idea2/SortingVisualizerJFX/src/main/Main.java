package main;

import Calculations.SortingEntry;
import Calculations.StepController;
import UI.InitController;
import UI.VisualizationController;
import UI.Window;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    public static Window window = new Window();
    public static ArrayList<SortingEntry> input;
    public static StepController.SortAlgos pickedAlgo;
    public static InitController initController;
    public static VisualizationController visualizationController;
    public static Parent visualizationRoot;
    public static StepController stepController;
    public static boolean keepData = false;

    public static final String NEWRUN = "New Run";
    public static final String AUTORUN = "Autorun";
    public static final String PAUSE = "Pausieren";

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

    public static void reRun(){
        window.initializeInit();
        if(Main.keepData){
            for(SortingEntry entry:Main.stepController.getInitList()){
                initController.addItemsToTable(initController.Table, entry);
            }
        }
    }
}
