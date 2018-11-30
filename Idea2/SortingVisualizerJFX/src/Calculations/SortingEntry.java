package Calculations;

public class SortingEntry {

    private Integer index;
    private String value;
    private Long valAsLong;

    public SortingEntry(Integer pos, String val){
        index = pos;
        value = val;
        valAsLong = stringToLong(val);
    }


    private  Long stringToLong(String str) {
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

    public Long getValAsLong() {return valAsLong;}

    public void setValue(String val){value = val;}
}
