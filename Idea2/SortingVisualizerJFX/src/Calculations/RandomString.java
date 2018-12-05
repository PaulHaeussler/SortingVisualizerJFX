package Calculations;


import java.util.Locale;
import java.util.Random;

/**
 * Class containing methods for creating strings of random characters
 * @author Paul Häussler
 */

public class RandomString {

    private static final String upper = "ABCDEFGHIJLMNOPQRSTUVWXYZÄÖÜ";
    private static final String lower = upper.toLowerCase(Locale.ROOT);
    private static final String digits = "1234567890";
    private static final String specChars = "!§$%&/()=?`´^°²³{[]}ß@*+'#-_.:,;~|<>";
    private Random random;
    private char[] symbols;

    /**
     * Constructor, prepares String for random generation based on picked options
     * @param useUpper utilize upper characters
     * @param useLower utilize lower characters
     * @param useDigits utilize digits
     * @param useSpecChars utilize special characters
     */

    public RandomString(boolean useUpper, boolean useLower, boolean useDigits, boolean useSpecChars){
        random = new Random();
        String selected = "";
        if(useUpper) selected += upper;
        if(useLower) selected += lower;
        if(useDigits) selected += digits;
        if(useSpecChars) selected += specChars;
        symbols = selected.toCharArray();
    }

    /**
     * creates a new String for the given length out of the String of possible characters
     * @param length length of the random string
     * @return String of random character
     */

    public String newString(int length){
        char[] buffer = new char[length];
        for(int i = 0; i < length; i++){
            buffer[i] = symbols[random.nextInt(symbols.length)];
        }
        return new String(buffer);
    }
}
