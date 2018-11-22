package Calculations;

import main.Main;

import java.awt.*;
import java.util.ArrayList;

public class StepController {

    private int countSortCurrNumber;
    private boolean isFinished;
    private int countSortCurrElement;
    private int countSortCurrPosition;
    private ArrayList<SortingEntry> currentResults;
    private ArrayList<SortingEntry> input;
    private ArrayList<SortingEntry> initList;
    private Steps steps = new Steps();
    private SortAlgos type;
    public int maxLength;

    public static int totalSteps = 0;

    public enum SortAlgos{
        RadixLSD,
        RadixMSD
    }

    public StepController(SortAlgos algoType, ArrayList<SortingEntry> list){

        type = algoType;
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
            countSortCurrPosition = maxLength;
        }
        doNextStep();

    }

    public void doNextStep(){
        totalSteps++;
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

    public boolean isFinished(){
        return isFinished;
    }

    public ArrayList<SortingEntry> getCurrentResults(){
        return currentResults;
    }

    public ArrayList<SortingEntry> getInput() {
        return input;
    }
}

