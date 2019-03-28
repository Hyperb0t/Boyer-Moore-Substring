package ru.kpfu.itis.hyperbot.BMsubstring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BMSubstringFinder {

    private static Map<Character, Integer> getStopSymbols(String template) {
        Map<Character, Integer> stopSymbols = new HashMap<>();
        for(int i = 0; i < template.length() - 1; i++) {
            stopSymbols.put(template.charAt(i), i);
        }
        if(!stopSymbols.containsKey(template.charAt(template.length() - 1))) {
            stopSymbols.put(template.charAt(template.length() - 1), template.length() - 1);
        }

        return stopSymbols;
    }

    private static Integer[] getSuffShifts(String template) {
        Integer[] suffShifts = new Integer[template.length() + 1];
        for(int i = template.length() - 1; i >= 0; i--) {
            if(template.charAt(template.length() - 1) != template.charAt(i)) {
                suffShifts[0] = template.length() - 1 - i;
                break;
            }
        }
        for(int i = 1; i < template.length(); i++) {
            String t = template.substring(template.length() - i);
            char beforeT = template.charAt(template.length() - i - 1);
            boolean found = false;
            int endOfSubstring = template.length() - i;
            while (!found) {
                int lastIndex = template.substring(0, endOfSubstring).lastIndexOf(t);
                if(lastIndex < 0) {
                    suffShifts[i] = template.length();
                    found = true;
                }
                else {
                    if (lastIndex == 0 || template.charAt(lastIndex - 1) != beforeT) {
                        found = true;
                        suffShifts[i] = template.length() - lastIndex - t.length();
                    } else {
                        endOfSubstring = lastIndex;
                    }
                }
            }
        }
        suffShifts[template.length()] = template.length();

        return suffShifts;
    }

    public static List<Integer> indexOf(String text, String template, Long[] iterations) {

        Map<Character, Integer> stopSymbols = getStopSymbols(template);
        Integer[] suffShifts = getSuffShifts(template);
        iterations[0] = 0l;

        List<Integer> result = new ArrayList<>();
        int i = template.length() - 1;
        while (i < text.length()) {
            boolean found = true;
            int moveI = 1;
            for(int j = 0; j < template.length(); j++) {
                iterations[0]++;
                //System.out.println(i + " " + j);
                if(template.charAt(template.length() - 1 - j) != text.charAt(i-j)) {
                    found = false;
                    if(j == 0) {
                        if(stopSymbols.containsKey(text.charAt(i-j))) {
                            moveI = template.length() - 1 - stopSymbols.get(text.charAt(i - j));
                        }
                        else {
                            moveI = template.length();
                        }
                    }
                    else {
                        moveI = suffShifts[j];
                    }
                    break;
                }
            }
            if(found) {
                result.add(i - template.length() + 1);
            }
            i += moveI;
        }
        return result;
    }
}
