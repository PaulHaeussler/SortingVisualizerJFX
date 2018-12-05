package Calculations;

import main.Main;

import java.util.ArrayList;

/**
 * class containing the necessary single steps and functions to perform RadixSort
 * @author Paul Häussler
 */

public class RadixSteps {


    /**
     * This method performs a single countingSort step, as RadixSort uses CountingSort. If the current element at the
     * current position equals the current number it gets added to the results, which then are returned.
     * @param input the current complete input list
     * @param currResults the current results (incomplete)
     * @param currNumber current number in the cycle checked for, cycles between 0-9
     * @param currElement index of the current element in the given input list
     * @param currPosition currentResults + current Element if conditions are met
     * @return currentResults with the currentElement from input if conditions were met
     */

    public ArrayList<SortingEntry> performCountSortStep(ArrayList<SortingEntry> input, ArrayList<SortingEntry>
            currResults, int currNumber, int currElement, int currPosition) {

        long lng = input.get(currElement).getValAsLong();
        String str = input.get(currElement).getValAsLong().toString();
        int c = getIntAt(str, currPosition);

        ArrayList<SortingEntry> elementsToMark = new ArrayList<>();
        elementsToMark.add(input.get(currElement));
        Main.visualizationController.markNewElement(true, elementsToMark, input);

        if (c == currNumber) {
            currResults.add(input.get(currElement));
            Main.visualizationController.updateChart(false,
                    Functions.fillListwithEmpties(currResults, input.size()));
            Main.visualizationController.setNewExplanation("Da Wert " +
                    input.get(currElement).getValue() + " als Long " + lng + " an der Position "
                    + currPosition + "(" + c + "; von rechts nach links gezählt) gleich der momentan gesuchten Zahl "
                    + currNumber + " ist, wird er zu den Ergebnissen hinzugefügt!");
            Main.visualizationController.markNewElement(false, elementsToMark,
                    Functions.fillListwithEmpties(currResults, input.size()));
        } else {
            Main.visualizationController.setNewExplanation("Da Wert " + input.get(currElement).getValue() +
                    " als Long " + lng + " an der Position " + currPosition + "(" + c
                    + "; von rechts nach links gezählt) nicht gleich der momentan gesuchten Zahl " + currNumber +
                    " ist, wird er vorerst nicht zu den Ergebnissen hinzugefügt!");
            Main.visualizationController.markNewElement(false, null,
                    Functions.fillListwithEmpties(currResults, input.size()));
        }

        return currResults;
    }

    /**
     * Returns the number at the given position of the string
     * @param str string out of which the result is determined
     * @param pos position of the returned character
     * @return the character at the given postion of the given string converted to an integer
     */

    private int getIntAt(String str, int pos){
        String c;
        try{
            c = str.substring(str.length()-pos-1, str.length() - pos);
        } catch (Exception e){
            c = "0";
        }
        return Integer.valueOf(c);
    }

    /**
     * Checks whether CountingSort is finished by checking if the result array is full
     * @param input input value array
     * @param result to be checked result array
     * @return boolean, true if both arrays contain equal amount of elements
     */

    public boolean checkIfCSIsFinished(ArrayList<SortingEntry> input, ArrayList<SortingEntry> result){
        return input.size() == result.size();
    }
}
