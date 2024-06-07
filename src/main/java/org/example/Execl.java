package org.example;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Execl {
    public int titleToNumber(String columnTitle) {
        int result = 0;
        for (int i = 0; i < columnTitle.length(); i++) {
            int numb = Character.getNumericValue(columnTitle.charAt(i)) - 9;
            result += numb * (int) Math.pow(26, columnTitle.length() - 1 - i);
        }
        return result;
    }

    public boolean wordPattern(String pattern, String s) {
        int wordsAlredyex = 0;
        int ptrAlredyex = 0;
        ArrayList<Integer> wordsArr = new ArrayList<>();
        ArrayList<Integer> ptrArr = new ArrayList<>();
        HashSet<String> listword = new HashSet<>();
        HashSet<Character> listptr = new HashSet<>();
        String[] words = s.split(" ");
        if(pattern.length() != words.length) {
            return false;
        } else {
            for(int i = 0; i < words.length; i++) {
                if(!listword.add(words[i])) {
                    wordsAlredyex ++;
                    wordsArr.add(i);
                }
                listword.add(words[i]);
            }
            for (int i = 0; i < pattern.length(); i++) {
                if(!listptr.add(pattern.charAt(i))) {
                    ptrAlredyex++;
                    ptrArr.add(i);
                }
                listptr.add(pattern.charAt(i));
            }
        }
        if (listptr.size() != listword.size() && wordsAlredyex != ptrAlredyex) {
            System.out.println(wordsAlredyex +":" + ptrAlredyex);
            return false;
        } else {
            System.out.println(wordsAlredyex +":" + ptrAlredyex);
            return true;
        }
    }


}
