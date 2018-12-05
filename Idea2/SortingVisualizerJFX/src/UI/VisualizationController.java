package UI;

import Calculations.Functions;
import Calculations.SortingEntry;
import Calculations.StepController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller for the Visualization window, containing two BarCharts, explanation text box, butttons, sliders and
 * Information about the run.
 * @author Paul Häussler
 */

public class VisualizationController implements Initializable {

    @FXML public VBox vbox_main;
    @FXML public HBox hbox_lower;
    @FXML public VBox vbox_expl;
    @FXML public VBox vbox_stats;
    @FXML public HBox hbox_pos;
    @FXML public HBox hbox_num;
    @FXML public HBox hbox_elem;
    @FXML public HBox hbox_step;
    @FXML public BarChart input;
    @FXML public BarChart result;
    @FXML public TextArea explanation;
    @FXML public Button nextStep;
    @FXML public Button autorun;
    @FXML public Label Lb_currPos;
    @FXML public Label Lb_currNumber;
    @FXML public Label Lb_currElement;
    @FXML public Label Lb_steps;
    @FXML public Label Lb_id_currPos;
    @FXML public Label Lb_id_currNumber;
    @FXML public Label Lb_id_currElement;
    @FXML public Label Lb_id_steps;
    @FXML public Slider slider_speed;


    private XYChart.Series inputSeries = new XYChart.Series();
    private XYChart.Series resultSeries = new XYChart.Series();


    public boolean T_auto_run = true;

    private int sleepTime = 5; //millis
    private Thread T_autorun = null;

    /**
     * Gets called when the window gets created. Removes one BarChart and some information labels if the picked
     * algorithm is not RadixSort. Sets bounds and current values of the slider as well as its changeListener.
     * Configures the Charts.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources){
        vbox_main.setFillWidth(true);
        hbox_lower.setFillHeight(true);
        if(Main.stepController.getType() != StepController.SortAlgos.RadixLSD){
            vbox_main.getChildren().remove(result);
            hbox_elem.getChildren().remove(Lb_id_currElement);
            hbox_num.getChildren().remove(Lb_id_currNumber);
            hbox_pos.getChildren().remove(Lb_id_currPos);
        }


        slider_speed.setMin(5);
        slider_speed.setMax(1000);
        slider_speed.setValue(5);
        slider_speed.setShowTickLabels(true);
        slider_speed.setShowTickMarks(true);
        slider_speed.valueProperty().addListener((observable, oldValue, newValue) -> speedUpdate((double)newValue));

        input.setTitle("Input");
        result.setTitle("Zwischenergebnis");
        input.setLegendVisible(false);
        result.setLegendVisible(false);
        input.setAnimated(false);
        result.setAnimated(false);
        updateChart(true, Main.input);
        updateChart(false, Functions.fillListwithEmpties(new ArrayList<>(), Main.input.size()));
        markNewElement(true, null, Main.stepController.getInput());

    }

    /**
     * Updates the Labels displaying several parameters of RadixSort
     */

    public void updateStatus(){
        Platform.runLater(() -> {
            Lb_currElement.setText(Main.stepController.getCurrElement()+"");
            Lb_currNumber.setText(Main.stepController.getCountSortCurrNumber()+"");
            Lb_currPos.setText(Main.stepController.getCountSortCurrPosition()+"");
            Lb_steps.setText(StepController.totalSteps+"");
        });
    }

    /**
     * Updates the explanation text box
     * @param str String text to update it with
     */

    public void setNewExplanation(String str){
        Platform.runLater(() -> {
            explanation.setWrapText(true);
            explanation.setText(str);
        });
    }

    /**
     * Colors the given  objects darker than the rest. Colors everything red if there is null given for the specific
     * objects.
     * @param originalChart whether or not to color the orignal chart or the result chart
     * @param elements elements to color darker than the rest
     * @param inputArr all elements currently in the barchart
     */

    public void markNewElement(boolean originalChart, ArrayList<SortingEntry> elements,
                               ArrayList<SortingEntry> inputArr){
        Platform.runLater(() -> {
            BarChart bc;
            ArrayList<SortingEntry> arr = inputArr;
            if(originalChart){
                bc = input;
            } else {
                bc = result;
            }

            if(elements == null){
                for(Node n : bc.lookupAll(".default-color0.chart-bar")){
                    n.setStyle("-fx-bar-fill: Red;");
                }
                return;
            }

            ArrayList<Integer> c = new ArrayList<>();
            for (int i = 0; i < arr.size(); i++) {
                for(int j = 0; j < elements.size(); j++) {
                    if(arr.get(i).equals(elements.get(j))){
                        c.add(i);
                    }
                }
            }

            int i = 0;
            for (Node n : bc.lookupAll(".default-color0.chart-bar")) {
                n.setStyle("-fx-bar-fill: Red;");
                for(Integer cTemp:c){
                    if (cTemp == i) {
                        n.setStyle("-fx-bar-fill: Maroon;");
                    }
                }
                i++;
            }
        });
    }

    /**
     * Updates one of both charts with new given entries
     * @param originalChart boolean  to determine which chart gets updated
     * @param entries new values for the chart
     */

    public void updateChart(boolean originalChart, ArrayList<SortingEntry> entries){
        Platform.runLater(() -> {
            ArrayList<SortingEntry> list = Functions.checkForDoubles(entries);
            if(originalChart){
                inputSeries = new XYChart.Series();
                for(SortingEntry entry:list){
                    assert(true);
                    XYChart.Data chartData = new XYChart.Data(entry.getValue(), entry.getValAsLong());
                    inputSeries.getData().add(chartData);
                }
                input.getData().clear();
                input.layout();
                input.getData().add(inputSeries);
            } else {
                resultSeries = new XYChart.Series();
                for(SortingEntry entry:list){
                    XYChart.Data chartData = new XYChart.Data(entry.getValue(), entry.getValAsLong());
                    resultSeries.getData().add(chartData);
                }
                result.getData().clear();
                result.layout();
                result.getData().add(resultSeries);
            }
            System.out.println();
        });
    }

    /**
     * Gets triggered when nextStep was clicked. Calls  the stepcontroller and instructs it to move the current
     * algorithm one step further.
     */

    public void nextStepClicked(){
        if(Main.stepController.isItFinished()){
            return;
        } else {
            Platform.runLater(() -> Main.stepController.doNextStep());
        }
    }

    /**
     * Gets triggered when autorun is clicked. Starts a thread which automatically calls nextStep() with a given delay
     * in between the calls.
     */

    public void autorunClicked(){

        if(autorun.getText().equals(Main.NEWRUN)){
            newRun();
            return;
        }

        if(T_autorun != null && autorun.getText().equals(Main.PAUSE)) {
            T_auto_run = false;
            autorun.setText(Main.AUTORUN);
            return;
        } else if(T_autorun != null && autorun.getText().equals(Main.AUTORUN)){
            T_auto_run = true;
            autorun.setText(Main.PAUSE);
            return;
        }

        T_autorun = new Thread(() -> {
            while(true){
                while(!Main.stepController.isItFinished() && T_auto_run){
                    nextStepClicked();

                    try{
                        Thread.sleep(sleepTime);
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                }
                System.out.print("");
            }
        });
        T_autorun.start();
        autorun.setText(Main.PAUSE);

    }

    /**
     * Updates the time the autothread sleeps.
     * @param newVal new sleep delay as double in milliseconds
     */

    public void speedUpdate(Double newVal){
        sleepTime = (int)Math.round(newVal);
        System.out.println(sleepTime);
    }

    /**
     * Gets triggered when the user clicks on the chart. Displays a help window.
     */

    public void chartClicked(){
        Main.window.initializeHelp();
    }

    /**
     * Initializes a new run of the program. Asks whether the initial random values are to be kept or discarded.
     */

    public void newRun(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Neuer Durchlauf");
        alert.setContentText("Sollen die Zufallswerte übernommen oder verworfen werden?");
        ButtonType okButton = new ButtonType("Übernehmen", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("Verwerfen", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(okButton, noButton);
        alert.showAndWait().ifPresent(type -> {
            if (type == okButton) {
                Main.keepData = true;
                System.out.println("Übernehmen wurde gewählt");
                Main.window.VisualizationStage.hide();
                Main.reRun();
            } else if (type == noButton) {
                Main.keepData = false;
                System.out.println("Verwerfen wurde gewählt");
                Main.window.VisualizationStage.hide();
                Main.reRun();
            } else {
                alert.close();
            }
        });
    }

    /**
     * Method to visualize the shift of the values from the lower chart to the upper. Doesn't work properly and
     * is not used currently.
     */

    public void translateResults(){
        if(true) return; //decomissioned
        double i = 0;
        result.setOpacity(0.5);
        double steps = 0;
        if(T_auto_run){
            steps = 10;
        } else {
            steps = 1;
        }
        while(((vbox_main.getHeight()-hbox_lower.getHeight())/-2)+20 < i){
            result.setTranslateY(i);
            i -= steps;
            try{
                Thread.sleep(16);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        result.setTranslateY(0);
        result.setOpacity(1);
    }
}
