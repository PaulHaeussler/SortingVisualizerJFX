package Calculations;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class RandomString {


    private static final String upper = "ABCDEFGHIJLMNOPQRSTUVWXYZÄÖÜ";
    private static final String lower = upper.toLowerCase(Locale.ROOT);
    private static final String digits = "1234567890";
    private static final String specChars = "!§$%&/()=?`´^°²³{[]}ß@*+'#-_.:,;~|<>";
    private Random random;
    private char[] symbols;


    public RandomString(boolean useUpper, boolean useLower, boolean useDigits, boolean useSpecChars){
        random = new Random();
        String selected = "";
        if(useUpper) selected += upper;
        if(useLower) selected += lower;
        if(useDigits) selected += digits;
        if(useSpecChars) selected += specChars;
        symbols = selected.toCharArray();
    }

    public String newString(int length){
        char[] buffer = new char[length];
        for(int i = 0; i < length; i++){
            buffer[i] = symbols[random.nextInt(symbols.length)];
        }
        return new String(buffer);
    }


    public ArrayList<String> newStringList(int listlength, int stringlength){
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i < listlength; i++){
            if(stringlength == 4 && i == 5){
                list.add("SooS");
            }
            else if(stringlength == 4 && i == 6){
                list.add("MeeM");
            }
            list.add(this.newString(stringlength));
        }
        return list;
    }
}
