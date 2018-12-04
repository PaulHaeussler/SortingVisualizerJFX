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

/**
 * Main class, contains main methods and central variables like controllers.
 * @autor Paul Häussler
 */

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

    /**
     * Method inherited from Application, gets triggered by launch()
     * @param primaryStage first stage to be displayed, invisible in this case because the Init window gets called
     *                     immediatly after.
     * @throws Exception could throw an exception, but all exceptions are handled internally.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        window.initializeInit();
    }

    /**
     * Main method, starts the program.
     * @param args given arguments for the program, usually empty.
     */

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Calls the methods for displaying the Visulization window.
     */

    public static void runVisualization(){
        window.initializeVisualization();
    }

    /**
     * Initializes a rerun. Depending on whether the option to keep the entries was picked or not, the entries get
     * sorted by their initial index (return to their intial randomness) and get added to the table. Otherwise,
     * nothing gets added.
     */

    public static void reRun(){
        window.initializeInit();
        if(Main.keepData){
            ArrayList<SortingEntry> oldList = Main.stepController.getInitList();
            ArrayList<SortingEntry> sortedList = new ArrayList<>();
            int top = oldList.size();
            for(int i = 0; i < top; i++){
                SortingEntry smallest = new SortingEntry(Integer.MAX_VALUE, "");
                for(int j = 0; j < oldList.size(); j++){
                    if(oldList.get(j).getIndex() < smallest.getIndex()){
                        smallest = oldList.get(j);
                    }
                }
                sortedList.add(smallest);
                oldList.remove(smallest);
            }



            for(SortingEntry entry : sortedList){
                Main.initController.addItemsToTable(entry);
            }
        }
    }
}
