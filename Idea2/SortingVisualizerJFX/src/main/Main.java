package main;

import Calculations.RadixSort;
import Calculations.RandomString;
import UI.Chart;
import UI.Window;
import UI.WindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../UI/Init.fxml"));
        primaryStage.setTitle("Hello World");
        //primaryStage.setScene(new Scene(root, 300, 275));

        Window window = new Window();
        window.initializeInit();




        long appStart = System.currentTimeMillis();

        RandomString rdmString = new RandomString(true,true,true);
        int listlength = 200;
        int stringlength = 6;
        ArrayList<String> arr = rdmString.newStringList(listlength,stringlength);
        ArrayList<Pair> arrPair = new ArrayList<Pair>();
        for(String str:arr){
            arrPair.add(new Pair(str,RadixSort.stringToLong(str)));
        }
        System.out.println(arr);
        ArrayList<Pair> sorted2 = RadixSort.radixSort(arrPair);
        System.out.println(sorted2);



        VBox vbox = new VBox();
        Chart chart = new Chart("RadixSort LSD", "yLabel", "xLabel", arrPair,600,800, arrPair.get(5).getKey() + "");
        chart.start(primaryStage);
        Chart chart2 = new Chart("RadixSort LSD sorted", "yLabel", "xLabel", sorted2, 400, 600, arrPair.get(5).getKey() + "");
        chart2.start(primaryStage);
        Button btn = new Button("Next step");
        vbox.getChildren().addAll(chart.getBarChart(), chart2.getBarChart(), btn);
        Scene scene = new Scene(vbox);
        primaryStage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
        primaryStage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
        primaryStage.setScene(scene);
        primaryStage.show();

        btn.setOnAction(actionEvent ->  {
            chart.markUpKey(arrPair.get(9).getKey().toString());
            chart2.markUpKey(arrPair.get(9).getKey().toString());

        });





        long appFinish = System.currentTimeMillis() - appStart;
        System.out.println("Finished sorting after " + appFinish + "ms, that's " + (float)(listlength * stringlength)/(float)appFinish + "ms per character!");
    }


    public static void main(String[] args) {
        launch(args);
    }

    private static ArrayList<String> toStringArray(ArrayList<Long> arr){
        ArrayList<String> result = new ArrayList<>();
        for(Long l:arr){
            result.add(l.toString());
        }
        return result;
    }
}
