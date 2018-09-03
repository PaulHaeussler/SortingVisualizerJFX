package Calculations;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class RadixSort {


    /**
     *
     * @param listToSort
     * @param charpos position of the char by which the array gets sorted, going right to left starting at zero (0:=123*4*)
     * @return
     */
    private static ArrayList<String> selectionSort(ArrayList<String> listToSort, long charpos){

        long[] arr = new long[0];
        int n = arr.length;

        // The output character array that will have sorted arr
        long output[] = new long[n];

        // Create a count array to store count of individual
        // characters and initialize count array as 0
        long count[] = new long[256];
        for (int i=0; i<256; ++i)
            count[i] = 0;

        // store count of each character
        for (int i=0; i<n; ++i)
            ++count[(int)arr[i]];


        // Change count[i] so that count[i] now contains actual(
        // position of this character in output array
        for (int i=1; i<=255; ++i)
            count[i] += count[i-1];

        // Build the output character array
        for (int i = 0; i<n; ++i)
        {
            output[(int)(count[(int)(arr[i])]-1)] = arr[i];
            --count[(int)arr[i]];
        }

        // Copy the output array to arr, so that arr now
        // contains sorted characters
        for (int i = 0; i<n; ++i)
            arr[i] = output[i];



        return null;
    }

    private static ArrayList<Long> countingSort(ArrayList<Long> listToSort, int charpos, long m){


        ArrayList<Long> result = new ArrayList<>();

        for(long i = 0; i < 10; i++)
        {
            for(Long lng : listToSort)
            {
                String strlong = lng.toString();
                String str = "";
                int subStrSrt = strlong.length()-charpos;
                int subStrEnd = subStrSrt + 1;
                if(subStrEnd > strlong.length()) subStrEnd = strlong.length();
                if(subStrSrt < 0)
                {
                    str = "";
                } else {
                    str = strlong.substring(subStrSrt, subStrEnd);
                }
                if(str == "") str = "0";
                if(Long.valueOf(str) == i){
                    result.add(lng);
                }
            }
        }

        return result;
    }


    public static ArrayList<Long> radixSort(ArrayList<String> listToSort){

        ArrayList<Long> longArr = stringArrToLongArr(listToSort);
        ArrayList<Long> longPreserve = longArr;

        long m = getMax(longArr);

        for(int i = 1; i < m; i++) {
            longArr = countingSort(longArr, i, m);
        }

        ArrayList<String> result = new ArrayList<>();

        for(int i = 0; i < longArr.size(); i++)
        {
            for(int j = 0; j < longPreserve.size(); j++)
            {
                if(longPreserve.get(j) == longArr.get(i)){
                    result.add(listToSort.get(j));
                }
            }
        }

        System.out.println(result);


        return longArr;

    }



    private static long getMax(ArrayList<Long> list){

        long m = 0;

        for(int i = 0; i < list.size(); i++){
            long n = list.get(i);
            if(n > m) m = list.indexOf(n);
        }

        return m;
    }

    private static int[] stringToIntArray(String str){
        int[] intArr = new int[str.length()];
        for(int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            intArr[i] = Character.getNumericValue(c);
        }
        return intArr;
    }


    private static ArrayList<Long> stringArrToLongArr(ArrayList<String> list){
        ArrayList<Long> arr = new ArrayList<>();
        for(String str : list) {
            arr.add(stringToLong(str));
        }
        return arr;
    }


    private static Long stringToLong(String str) {
        String result = "";
        for(int i = 0; i < str.length(); i++){
            char c = str.charAt(i);
            result += String.format("%02d",Character.getNumericValue(c));
        }
        Long lng = Long.parseLong(result);
        return lng;
    }
}
