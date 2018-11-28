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
            Main.visualizationController.updateChart(false, fillListwithEmpties(currResults, input.size()));
            Main.visualizationController.setNewExplanation("Da Wert " + input.get(currElement).getValue() + " als Long " + lng + " an der Position " + currPosition + "(" + c + "; von rechts nach links gezählt) gleich der momentan gesuchten Zahl " + currNumber + "ist, wird er zu den Ergebnissen hinzugefügt!");
            Main.visualizationController.markNewElement(false, input.get(currElement), fillListwithEmpties(currResults, input.size()));
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

    public static ArrayList<SortingEntry> fillListwithEmpties(ArrayList<SortingEntry> list, int fillToSize){
        ArrayList<SortingEntry> listToFill = new ArrayList<>();
        for(int i = 0; i<list.size(); i++){
            listToFill.add(list.get(i));
        }
        while(listToFill.size() < fillToSize){
            listToFill.add(new SortingEntry(-1,""));
        }
        return listToFill;
    }
}
