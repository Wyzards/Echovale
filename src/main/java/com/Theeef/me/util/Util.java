package com.Theeef.me.util;

import java.util.ArrayList;
import java.util.List;

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

}
