package UI;

import Calculations.SortingEntry;
import Calculations.StepController;
import Calculations.Steps;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import main.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VisualizationController implements Initializable {

    @FXML public BarChart input;
    @FXML public BarChart result;
    @FXML public TextArea explanation;
    @FXML public Button nextStep;
    @FXML public Button autorun;
    @FXML public Label Lb_currPos;
    @FXML public Label Lb_currNumber;
    @FXML public Label Lb_currElement;
    @FXML public Label Lb_steps;

    private CategoryAxis inputXaxis = new CategoryAxis();
    private CategoryAxis resultXaxis = new CategoryAxis();
    private NumberAxis inputYaxis = new NumberAxis();
    private NumberAxis resultYaxis = new NumberAxis();
    private XYChart.Series inputSeries = new XYChart.Series();
    private XYChart.Series resultSeries = new XYChart.Series();

    public StepController stepController;

    @Override
    public void initialize(URL location, ResourceBundle ressources){

        input.setTitle("Input");
        result.setTitle("Zwischenergebnis");
        input.setLegendVisible(false);
        result.setLegendVisible(false);
        input.setAnimated(false);
        result.setAnimated(false);
        updateChart(true, Main.input);
        updateChart(false, Steps.fillListwithEmpties(new ArrayList<>(), Main.input.size()));

    }

    public void updateStatus(){
        Platform.runLater(new Runnable(){
            public void run(){
                Lb_currElement.setText(stepController.getCurrElement()+"");
                Lb_currNumber.setText(stepController.getCountSortCurrNumber()+"");
                Lb_currPos.setText(stepController.getCountSortCurrPosition()+"");
                Lb_steps.setText(StepController.totalSteps+"");
            }
        });
    }


    public void setNewExplanation(String str){
        Platform.runLater(new Runnable(){
            public void run(){
                explanation.setWrapText(true);
                explanation.setText(str);
            }
        });
    }

    public void markNewElement(boolean originalChart, SortingEntry element, ArrayList<SortingEntry> inputArr){
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                BarChart bc;
                ArrayList<SortingEntry> arr = inputArr;
                if(originalChart){
                    bc = input;
                } else {
                    bc = result;
                }

                int c = 0;
                for (int i = 0; i < arr.size(); i++) {
                    if (arr.get(i).equals(element)) {
                        c = i;
                        break;
                    }
                }

                int i = 0;
                for (Node n : bc.lookupAll(".default-color0.chart-bar")) {

                    if (c == i) {
                        n.setStyle("-fx-bar-fill: Maroon;");
                    } else {
                        n.setStyle("-fx-bar-fill: Red;");
                    }
                    i++;
                }
            }
        });



    }

    public void updateChart(boolean originalChart, ArrayList<SortingEntry> entries){
        Platform.runLater(new Runnable() {
            @Override
            public void run(){
                if(originalChart){
                    inputSeries = new XYChart.Series();
                    for(SortingEntry entry:entries){
                        inputSeries.getData().add(new XYChart.Data(entry.getValue(),entry.getValAsLong()));
                    }
                    input.getData().clear();
                    input.layout();
                    input.getData().add(inputSeries);
                } else {
                    resultSeries = new XYChart.Series();
                    for(SortingEntry entry:entries){
                        resultSeries.getData().add(new XYChart.Data(entry.getValue(), entry.getValAsLong()));
                    }
                    result.getData().clear();
                    result.layout();
                    result.getData().add(resultSeries);
                }
            }
        });

    }


    public void nextStepClicked(){

        stepController.doNextStep();
        System.out.println("Anzahl Steps: " + StepController.totalSteps);
    }


    public void autorunClicked(){
        Thread thread = new Thread(() -> {

            while(!stepController.isItFinished()){
                stepController.doNextStep();

                try{
                    Thread.sleep(50);
                } catch (Exception e){

                }

            }
        });
        thread.start();
        System.out.println("Laufzeit: " + (System.nanoTime() - stepController.startTime)/(long)1000/(long)1000/(long)100 + " Sekunden");
        for(SortingEntry entry: stepController.getCurrentResults()){
            System.out.println(entry.getIndex() + "-" + entry.getValue() + "-" + entry.getValAsLong());
        }
    }
}
