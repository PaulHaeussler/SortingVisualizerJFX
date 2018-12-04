package Calculations;

import main.Main;

import java.util.ArrayList;

/**
 * class which contains the necessary single steps and methods to perform bubbleSort.
 * @author Paul Häussler
 */

public class BubbleSteps {

    /**
     * Checks whether the entry out of the given list for the parameter i is greater than the entry for i+1. If so,
     * switch the two.
     * @param list given list of SortingEntry s
     * @param i given index
     * @return the given list with the two values switched, or not
     */

    public ArrayList<SortingEntry> performBubbleSortStep(ArrayList<SortingEntry> list, int i){
        ArrayList<SortingEntry> elementsToSort = new ArrayList<>();
        elementsToSort.add(list.get(i));
        elementsToSort.add(list.get(i+1));
        Main.visualizationController.markNewElement(true, elementsToSort, list);
        if(list.get(i).getValAsLong() > list.get(i+1).getValAsLong()){
            SortingEntry entry = list.get(i);
            list.remove(i);
            list.add(i+1, entry);
        }
        Main.visualizationController.updateChart(true, list);
        return list;
    }

    /**
     * Checks for a list whether or not it is sorted.
     * @param list the list to check
     * @return boolean, true if the list is sorted
     */

    public boolean checkIfBSIsFinished(ArrayList<SortingEntry> list){
        boolean isSorted = true;
        for(int i = 0; i < list.size(); i++){
            if(i != list.size()-1){
                if(list.get(i).getValAsLong() > list.get(i+1).getValAsLong()){
                    isSorted = false;
                    break;
                }
            }
        }
        return isSorted;
    }
}
