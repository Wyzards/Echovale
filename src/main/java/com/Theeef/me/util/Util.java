package com.Theeef.me.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Util {

    public static List<String> fitForLore(String string) {
        String buffer = "";
        List<String> list = new ArrayList<String>();

        for (String word : string.split(" "))
            if (buffer.length() + 1 + word.length() <= 40)
                buffer += " " + word;
            else {
                list.add(buffer);
                buffer = "";
            }

        return list;
    }

    /**
     * Converts Enum or constant name strings into display strings.
     * Replaces "_" with " ", puts all words to lowercase, capitalizes first letter
     *
     * @param toClean the enum name to clean
     * @return cleaned enum name
     */
    public static String cleanEnumName(String toClean) {
        toClean = toClean.toLowerCase().replaceAll("_", " ");
        String toReturn = "";

        for (String word : toClean.split(" "))
            toReturn += word.substring(0, 1).toUpperCase() + word.substring(1);

        return toReturn;
    }

}
