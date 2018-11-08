package UI;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;

public class Chart extends Application {

    private String title;
    private String yLabel;
    private String xLabel;
    private ArrayList<Pair> arr;
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private BarChart<String, Number> bc;
    private int width;
    private int height;
    private String markUpStr;


    public Chart(String title, String yLabel, String xLabel, ArrayList<Pair> arr, int width, int height, String markUpStr){
        this.title = title;
        this.yLabel = yLabel;
        this.xLabel = xLabel;
        this.arr = arr;
        this.xAxis = new CategoryAxis();
        this.yAxis = new NumberAxis();
        this.width = width;
        this.height = height;
        this.markUpStr = markUpStr;
    }

    @Override public void start(Stage stage){

        stage.setTitle(title);
        this.bc = new BarChart<String, Number>(xAxis, yAxis);
        bc.setTitle(title);
        yAxis.setLabel(yLabel);
        xAxis.setLabel(xLabel);
        XYChart.Series series = new XYChart.Series();



        for(int i = 0; i < arr.size(); i++){
            XYChart.Data data = new XYChart.Data(arr.get(i).getKey(), arr.get(i).getValue());
            series.getData().add(data);

        }



        Scene scene = new Scene(bc, width, height);
        bc.getData().addAll(series);



        //stage.setScene(scene);
        scene.getStylesheets().add("style.css");
        //stage.show();

        int c = 0;
        for(int i = 0; i < arr.size(); i++){
            if(arr.get(i).getKey().toString().equals(markUpStr)){
                c = i;
                break;
            }
        }

        int i = 0;
        for(Node n:bc.lookupAll(".default-color0.chart-bar")){
            System.out.println(n.idProperty());
            if(c == i){
                n.setStyle("-fx-bar-fill: Maroon;");
            } else {
                n.setStyle("-fx-bar-fill: Red;");
            }
            i++;
        }


    }

    public BarChart<String, Number> getBarChart(){
        return this.bc;
    }

}
