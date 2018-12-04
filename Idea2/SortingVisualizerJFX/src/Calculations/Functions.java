package Calculations;

import java.util.ArrayList;

/**
 * class which contains auxiliary static functions.
 * @author Paul Häussler
 */

public class Functions {

    /**
     * Determines the maximum length of the long values of the sortingEntrys in the given list.
     * @param list an ArrayList containing SortingEntrys
     * @return the length of the longest long value of the sortingEntrys inside the list.
     */

    public static int getMax(ArrayList<SortingEntry> list){

        int m = 0;

        for(int i = 0; i < list.size(); i++){
            int n = list.get(i).getValAsLong().toString().length();
            if(n > m) m = list.get(i).getValAsLong().toString().length();
        }

        return m;
    }

    /**
     * Takes a given list, and adds empty entries (SortingEntrys with -1 and "") up to a given size.
     * @param list list to be expanded
     * @param fillToSize integer determining the size of the resulting list
     * @return the given list, inflated with empties
     */

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

    /**
     * checks whether or not there are double values in the given list of sorting entries. If so, adds a zero width
     * whitespace to it, so that they won't be identical. (BarChart will draw them over one another otherwise)
     * @param list list of SortingEntrys to be checked for duplicates in their values
     * @return the given list with each found duplicate made unique
     */

    public static ArrayList<SortingEntry> checkForDoubles(ArrayList<SortingEntry> list){

        for(int i = 0; i < list.size(); i++){
            int count = 1;
            for(int j = 0; j < list.size(); j++){
                if(list.get(i).getValue().equals(list.get(j).getValue()) && i != j){
                    SortingEntry entry = list.get(j);
                    list.remove(entry);
                    addWhitespace(entry, count);
                    list.add(j, entry);
                    count++;
                }
            }
        }
        return list;
    }

    /**
     * Adds the amount of zero-width whitespaces to the value of the given SortingEntry specified by count. This does
     * not affect the valAsLong of the SortingEntry.
     * @param entry the SortingEntry to be edited
     * @param count number of zero-width whitespaces to be added
     * @return the given SortingEntry with added whitespaces
     */

    private static SortingEntry addWhitespace(SortingEntry entry, int count){
        for(int i = 0; i < count; i++){
            entry.setValue(entry.getValue()+'\u200B');
        }
        return entry;
    }
}
