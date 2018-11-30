package Calculations;

import main.Main;

import java.util.ArrayList;

public class RadixSteps {

    private StepController.SortAlgos type;

    public RadixSteps(StepController.SortAlgos algorithm){
        type = algorithm;
    }

    public ArrayList<SortingEntry> performCountSortStep(ArrayList<SortingEntry> input, ArrayList<SortingEntry> currResults, int currNumber, int currElement, int currPosition) {
        long lng = input.get(currElement).getValAsLong();
        String str = input.get(currElement).getValAsLong().toString();
        int c = getIntAt(str, currPosition);

        Main.visualizationController.markNewElement(true, input.get(currElement), input);

        if (c == currNumber) {
            currResults.add(input.get(currElement));
            Main.visualizationController.updateChart(false, Functions.fillListwithEmpties(currResults, input.size()));
            Main.visualizationController.setNewExplanation("Da Wert " + input.get(currElement).getValue() + " als Long " + lng + " an der Position " + currPosition + "(" + c + "; von rechts nach links gezählt) gleich der momentan gesuchten Zahl " + currNumber + " ist, wird er zu den Ergebnissen hinzugefügt!");
            Main.visualizationController.markNewElement(false, input.get(currElement), Functions.fillListwithEmpties(currResults, input.size()));
        } else {
            Main.visualizationController.setNewExplanation("Da Wert " + input.get(currElement).getValue() + " als Long " + lng + " an der Position " + currPosition + "(" + c + "; von rechts nach links gezählt) nicht gleich der momentan gesuchten Zahl " + currNumber + " ist, wird er vorerst nicht zu den Ergebnissen hinzugefügt!");

        }

        return currResults;
    }

    private int getIntAt(String str, int pos){
        String c;
        try{
            c = str.substring(str.length()-pos-1, str.length() - pos);
        } catch (Exception e){
            c = "0";
        }
        return Integer.valueOf(c);
    }

    public boolean checkIfCSIsFinished(ArrayList<SortingEntry> input, ArrayList<SortingEntry> result){
        return input.size() == result.size();
    }
}
