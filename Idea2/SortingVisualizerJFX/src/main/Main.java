package main;

import Calculations.RadixSort;
import Calculations.RandomString;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();




        long appStart = System.currentTimeMillis();

        RandomString rdmString = new RandomString(true,true,true);
        int listlength = 20;
        int stringlength = 5;
        ArrayList<String> arr = rdmString.newStringList(listlength,stringlength);
        System.out.println(arr);
        ArrayList<Long> sorted2 = RadixSort.radixSort(arr);
        System.out.println(sorted2);


        long appFinish = System.currentTimeMillis() - appStart;
        System.out.println("Finished sorting after " + appFinish + "ms, that's " + (float)(listlength * stringlength)/(float)appFinish + "ms per character!");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
