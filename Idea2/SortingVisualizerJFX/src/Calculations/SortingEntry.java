package Calculations;

public class SortingEntry {

    private Integer index;
    private String value;

    public SortingEntry(Integer pos, String val){
        index = pos;
        value = val;
    }

    public Integer getIndex(){
        return index;
    }

    public String getValue(){
        return value;
    }
}
