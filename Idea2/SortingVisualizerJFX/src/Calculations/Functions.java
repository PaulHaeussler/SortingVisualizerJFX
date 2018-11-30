package Calculations;

import java.util.ArrayList;

public class Functions {

    public static int getMax(ArrayList<SortingEntry> list){

        int m = 0;

        for(int i = 0; i < list.size(); i++){
            int n = list.get(i).getValAsLong().toString().length();
            if(n > m) m = list.get(i).getValAsLong().toString().length();
        }

        return m;
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


    private static SortingEntry addWhitespace(SortingEntry entry, int count){
        for(int i = 0; i < count; i++){
            entry.setValue(entry.getValue()+'\u200B');
        }
        return entry;
    }
}
