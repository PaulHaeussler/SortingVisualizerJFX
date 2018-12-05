package Calculations;

import javafx.application.Platform;
import main.Main;

import java.util.ArrayList;

/**
 * central controller for all sorting algorithms, contains all variables and results
 * @author Paul Häussler
 */

public class StepController {

    private int countSortCurrNumber;
    private boolean isFinished;
    private int countSortCurrElement;
    private int countSortCurrPosition;
    private int bubbleI;
    private int bubbleN;
    private ArrayList<SortingEntry> currentResults;
    private ArrayList<SortingEntry> input;
    private ArrayList<SortingEntry> initList;
    private RadixSteps radixSteps;
    private BubbleSteps bubbleSteps;
    private SortAlgos type;
    public int maxLength;

    public static int totalSteps = 0;
    public long startTime = 0;

    public enum SortAlgos{
        RadixLSD,
        BubbleSort
    }

    /**
     * constructor which initializes based on the given algorithm all necessary variables.
     * @param algoType the picked sorting algorithm
     * @param list the given input list which will be sorted
     */

    public StepController(SortAlgos algoType, ArrayList<SortingEntry> list){
        initList = list;
        input = list;
        type = algoType;
        radixSteps = new RadixSteps();
        bubbleSteps = new BubbleSteps();
        initList = list;
        maxLength = Functions.getMax(list);
        countSortCurrNumber = 0;
        countSortCurrElement = 0;
        bubbleI = 0;
        bubbleN = list.size();

        if(type == SortAlgos.BubbleSort){
            currentResults = input;
        } else {
            currentResults = new ArrayList<>();
        }

        if(type == SortAlgos.RadixLSD){
            countSortCurrPosition = 0;
        } else {
            countSortCurrPosition = maxLength-1;
        }
    }

    /**
     * method to pick the step method corresponding to the picked sorting algorithm
     */

    public void doNextStep(){
        totalSteps++;
        switch (type){
            case RadixLSD:
                doNextRadixStep();
                break;
            case BubbleSort:
                doNextBubbleStep();
                break;
        }
    }



    /**
     * Step control overhead if RadixSort was picked.
     * RadixSort runs Selection Sort by the integer of the value at a given position, in the case of LSD radix sort
     * starting at the least significant digit.
     */

    private void doNextRadixStep(){
        if(startTime == 0){
            startTime = System.nanoTime();
        }

        if(isFinished) return;


        Main.visualizationController.updateStatus();


        currentResults = radixSteps.performCountSortStep(input, currentResults, countSortCurrNumber,
                countSortCurrElement, countSortCurrPosition);


        if(radixSteps.checkIfCSIsFinished(input, currentResults)){
            if(type == SortAlgos.RadixLSD){
                countSortCurrPosition++;
            } else {
                countSortCurrPosition--;
            }
            if(countSortCurrPosition < 0 || countSortCurrPosition > maxLength || checkIfFinished(currentResults)) {
                setFinished();

            }
            Main.visualizationController.translateResults();
            input = currentResults;
            currentResults = new ArrayList<>();
            Main.visualizationController.updateChart(false, Functions.fillListwithEmpties(currentResults,
                    input.size()));
            Main.visualizationController.updateChart(true, input);
            countSortCurrNumber = 0;
            countSortCurrElement = 0;
            if(!isFinished){
            Main.visualizationController.setNewExplanation("Da Countingsort abegeschlossen ist, werden die Zwischen" +
                    "ergebnisse als neuer Input benutzt.");}
            return;
        }
        countSortCurrElement++;
        if(countSortCurrElement > input.size()-1) {
            countSortCurrElement = 0;
            increaseCurrNumber();
        }
    }

    /**
     * Advances the bubbleSort algorithm by a step.
     */

    private void doNextBubbleStep(){
        if(bubbleSteps.checkIfBSIsFinished(input)) setFinished();
        if(bubbleN <= 1) setFinished();
        if(isFinished) return;
        currentResults = bubbleSteps.performBubbleSortStep(currentResults, bubbleI);
        Platform.runLater(() -> Main.visualizationController.Lb_steps.setText(StepController.totalSteps+""));
        bubbleI++;
        if(bubbleI >= bubbleN-1){
            bubbleI = 0;
            bubbleN--;
        }
    }

    /**
     * Method to set the flag as well as edit the UI when the algorithm is finished.
     */

    private void setFinished(){
        isFinished = true;
        Platform.runLater(() -> Main.visualizationController.autorun.setText(Main.NEWRUN));
        Platform.runLater(() -> Main.visualizationController.explanation.replaceText(0,
                Main.visualizationController.explanation.getLength()-1, "Sortierung abgeschlossen!"));
        Platform.runLater(() -> Main.visualizationController.markNewElement(true, null,
                currentResults));
        Platform.runLater(() -> Main.visualizationController.input.setTitle("Ergebnis"));
        Platform.runLater(() ->
                Main.visualizationController.vbox_main.getChildren().remove(Main.visualizationController.result));
        Main.visualizationController.T_auto_run = false;
    }

    /**
     * Checks whether or not the given results are sorted.
     * @param results ArrayList of SortingEntrys to be checked
     * @return boolean, true if results is sorted
     */

    private boolean checkIfFinished(ArrayList<SortingEntry> results){
        boolean finished = true;
        for(int i = 0; i < results.size(); i++){
            if(i != results.size()-1){
                if(results.get(i).getValAsLong() > results.get(i+1).getValAsLong()){
                    finished = false;
                    break;
                }
            }
        }
        return finished;
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

    public SortAlgos getType(){return type;}
}

