package Calculations;

import javafx.application.Platform;
import main.Main;

import java.util.ArrayList;

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
        //doNextRadixStep();

    }

    public void doNextStep(){
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
     * Step control overhead if RadixSort was picked. Contains the possibility of implementing RadixSortMSD.
     */

    private void doNextRadixStep(){
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
            if(countSortCurrPosition < 0 || countSortCurrPosition > maxLength || checkIfFinished(currentResults)) {
                setFinished();
            }
            Main.visualizationController.translateResults();
            input = currentResults;
            currentResults = new ArrayList<>();
            Main.visualizationController.updateChart(false, Functions.fillListwithEmpties(currentResults, input.size()));
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


    private void doNextBubbleStep(){
        if(bubbleSteps.checkIfBSIsFinished(input)) setFinished();
        if(bubbleN <= 1) setFinished();
        if(isFinished) return;
        currentResults = bubbleSteps.performBubbleSortStep(currentResults, bubbleI);
        bubbleI++;
        if(bubbleI >= bubbleN-1){
            bubbleI = 0;
            bubbleN--;
        }
    }


    private void setFinished(){
        isFinished = true;
        Platform.runLater(() -> Main.visualizationController.autorun.setText(Main.NEWRUN));
        Platform.runLater(() -> Main.visualizationController.explanation.replaceText(0, Main.visualizationController.explanation.getLength()-1, "Sortierung abgeschlossen!"));
        Platform.runLater(() -> Main.visualizationController.markNewElement(true, null, currentResults));
        Platform.runLater(() -> Main.visualizationController.input.setTitle("Ergebnis"));
        Platform.runLater(() -> Main.visualizationController.vbox_main.getChildren().remove(Main.visualizationController.result));
        Main.visualizationController.T_auto_run = false;
    }

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

