package Calculations;

import javafx.application.Platform;
import main.Main;

import java.util.ArrayList;

public class StepController {

    private int countSortCurrNumber;
    private boolean isFinished;
    private int countSortCurrElement;
    private int countSortCurrPosition;
    private ArrayList<SortingEntry> currentResults;
    private ArrayList<SortingEntry> input;
    private ArrayList<SortingEntry> initList;
    private RadixSteps radixSteps;
    private SortAlgos type;
    public int maxLength;

    public static int totalSteps = 0;
    public long startTime = 0;

    public enum SortAlgos{
        RadixLSD,
        RadixMSD
    }

    public StepController(SortAlgos algoType, ArrayList<SortingEntry> list){

        type = algoType;
        radixSteps = new RadixSteps(type);
        initList = list;
        maxLength = Functions.getMax(list);
        countSortCurrNumber = 0;
        countSortCurrElement = 0;
        initList = list;
        input = list;
        currentResults = new ArrayList<>();
        if(type == SortAlgos.RadixLSD){
            countSortCurrPosition = 0;
        } else {
            countSortCurrPosition = maxLength-1;
        }
        //doNextRadixStep();

    }

    public void doNextStep(){
        switch (type){
            case RadixLSD:
                doNextRadixStep();
                break;
        }
    }



    /**
     * Step control overhead if RadixSort was picked. Contains the possibility of implementing RadixSortMSD.
     */

    public void doNextRadixStep(){
        if(startTime == 0){
            startTime = System.nanoTime();
        }

        if(isFinished) return;

        totalSteps++;
        Main.visualizationController.updateStatus();


        currentResults = radixSteps.performCountSortStep(input, currentResults, countSortCurrNumber, countSortCurrElement, countSortCurrPosition);


        if(radixSteps.checkIfCSIsFinished(input, currentResults)){
            if(type == SortAlgos.RadixLSD){
                countSortCurrPosition++;
            } else {
                countSortCurrPosition--;
            }
            if(countSortCurrPosition < 0 || countSortCurrPosition > maxLength){
                isFinished = true;
                System.out.println("Laufzeit: " + (System.nanoTime() - startTime)/(long)1000/(long)1000/(long)1000 + " Sekunden");
                Platform.runLater(() -> Main.visualizationController.autorun.setText(Main.NEWRUN));
                Main.visualizationController.T_auto_run = false;
                return;
            }
            Main.visualizationController.translateResults();
            input = currentResults;
            currentResults = new ArrayList<>();
            Main.visualizationController.updateChart(true, input);
            countSortCurrNumber = 0;
            countSortCurrElement = 0;
            return;
        }
        countSortCurrElement++;
        if(countSortCurrElement > input.size()-1) {
            countSortCurrElement = 0;
            increaseCurrNumber();
        }
    }

    private void increaseCurrNumber(){
        countSortCurrNumber++;
        if(countSortCurrNumber>9) countSortCurrNumber = 0;
    }

    public boolean isItFinished(){
        return isFinished;
    }

    public ArrayList<SortingEntry> getCurrentResults(){
        return currentResults;
    }

    public ArrayList<SortingEntry> getInput() {
        return input;
    }

    public int getCurrElement(){
        return countSortCurrElement;
    }

    public int getCountSortCurrNumber(){
        return countSortCurrNumber;
    }

    public int getCountSortCurrPosition(){
        return countSortCurrPosition;
    }

    public ArrayList<SortingEntry> getInitList(){return initList;}
}

