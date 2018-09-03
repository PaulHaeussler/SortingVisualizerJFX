package Calculations;

import java.io.CharConversionException;
import java.util.ArrayList;

public class SortingList {

    private ArrayList<Character> sortingList;

    public SortingList(){
    }

    public SortingList(ArrayList<Character> importedList){
        sortingList = importedList;
    }


    public ArrayList<Character> getSortingList() {
        return sortingList;
    }
}


