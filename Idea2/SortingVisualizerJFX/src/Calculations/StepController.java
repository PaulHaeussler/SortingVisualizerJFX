package Calculations;

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
    private Steps steps;
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
        steps = new Steps(type);
        initList = list;
        maxLength = RadixSort.getMax(list);
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
        //doNextStep();

    }

    public void doNextStep(){
        if(startTime == 0){
            startTime = System.nanoTime();
        }
        totalSteps++;
        Main.visualizationController.updateStatus();
        if(isFinished) return;
        currentResults = steps.performCountSortStep(input, currentResults, countSortCurrNumber, countSortCurrElement, countSortCurrPosition);
        if(steps.checkIfCSIsFinished(input, currentResults)){
            if(type == SortAlgos.RadixLSD){
                countSortCurrPosition++;
            } else {
                countSortCurrPosition--;
            }
            if(countSortCurrPosition < 0 || countSortCurrPosition > maxLength){
                isFinished = true;
                return;
            }
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
}

