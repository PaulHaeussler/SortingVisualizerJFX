package UI;

import Calculations.Functions;
import Calculations.SortingEntry;
import Calculations.StepController;
import Calculations.RadixSteps;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
    @FXML public Slider slider_speed;

    private CategoryAxis inputXaxis = new CategoryAxis();
    private CategoryAxis resultXaxis = new CategoryAxis();
    private NumberAxis inputYaxis = new NumberAxis();
    private NumberAxis resultYaxis = new NumberAxis();
    private XYChart.Series inputSeries = new XYChart.Series();
    private XYChart.Series resultSeries = new XYChart.Series();


    public boolean T_auto_run = true;

    private int sleepTime = 50; //millis
    private Thread T_autorun = null;


    @Override
    public void initialize(URL location, ResourceBundle ressources){
        vbox_main.setFillWidth(true);
        hbox_lower.setFillHeight(true);
        //vbox_main.getChildren().remove(input);




        slider_speed.setMin(50);
        slider_speed.setMax(1000);
        slider_speed.setValue(50);
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
        for(Node n:input.lookupAll(".default-color0.chart-bar")){
            n.setStyle("-fx-bar-fill: Red;");
        }
    }

    public void updateStatus(){
        Platform.runLater(() -> {
            Lb_currElement.setText(Main.stepController.getCurrElement()+"");
            Lb_currNumber.setText(Main.stepController.getCountSortCurrNumber()+"");
            Lb_currPos.setText(Main.stepController.getCountSortCurrPosition()+"");
            Lb_steps.setText(StepController.totalSteps+"");
        });
    }


    public void setNewExplanation(String str){
        Platform.runLater(() -> {
            explanation.setWrapText(true);
            explanation.setText(str);
        });
    }

    public void markNewElement(boolean originalChart, SortingEntry element, ArrayList<SortingEntry> inputArr){
        Platform.runLater(() -> {
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
        });
    }

    public void updateChart(boolean originalChart, ArrayList<SortingEntry> entries){
        Platform.runLater(() -> {
            ArrayList<SortingEntry> list = Functions.checkForDoubles(entries);
            if(originalChart){
                inputSeries = new XYChart.Series();
                for(SortingEntry entry:list){
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


    public void nextStepClicked(){
        if(Main.stepController.isItFinished() && T_autorun != null){
            try{
                T_autorun.join();
            } catch (Exception e){
                e.printStackTrace();
            }
        } else {
            Platform.runLater(() -> Main.stepController.doNextStep());
        }
    }


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

    public void speedUpdate(Double newVal){
        sleepTime = (int)Math.round(newVal);
        System.out.println(sleepTime);
    }

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

    public void translateResults(){
        double i = 0;
        result.setOpacity(0.5);
        double steps = 0;
        if(T_auto_run){
            steps = 10;
        } else {
            steps = 3;
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
