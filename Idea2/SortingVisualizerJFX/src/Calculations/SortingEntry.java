package Calculations;

/**
 * Class for object creation of entries
 */

public class SortingEntry {

    private Integer index;
    private String value;
    private Long valAsLong;

    /**
     * Creates a SortingEntry object with a position and a value, as well as the value as a long
     * @param pos Index of the entry (for table for example)
     * @param val String value
     */

    public SortingEntry(Integer pos, String val){
        index = pos;
        value = val;
        valAsLong = stringToLong(val);
    }

    /**
     * Creates a long from the String characters
     * @param str String which will be converted
     * @return Long value of given String
     */

    private Long stringToLong(String str) {
        String result = "";
        Long lng = (long)0;
        for(int i = 0; i < str.length(); i++){
            char c = str.charAt(i);
            result += String.format("%03d",(long)c);
        }
        if(!(result.equals(""))){
            lng = Long.parseLong(result);
        }
        return lng;
    }

    public Integer getIndex(){
        return index;
    }

    public String getValue(){
        return value;
    }

    public Long getValAsLong() {
        return valAsLong;
    }

    public void setValue(String val){
        value = val;
    }
}
