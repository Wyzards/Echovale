package com.Theeef.me.util;

import com.google.common.collect.Lists;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Util {

    public static List<String> fitForLore(String string) {
        StringBuilder buffer = new StringBuilder();
        List<String> list = new ArrayList<>();

        while (string.length() > 0) {
            // Add first word of string to buffer
            buffer.append(string.substring(0, string.contains(" ") ? string.indexOf(' ') + 1 : string.length()));
            // Remove first word from string
            string = string.contains(" ") ? string.substring(string.indexOf(' ') + 1) : "";

            if (buffer.length() >= 40) {
                list.add(buffer.toString().trim());
                string = ChatColor.getLastColors(buffer.toString()) + string;
                buffer.setLength(0);
            }
        }

        if (ChatColor.stripColor(buffer.toString().trim()).length() > 0)
            list.add(buffer.toString());

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
        if (toClean.equals("TWO_HANDED"))
            return "Two-Handed";

        toClean = toClean.toLowerCase().replaceAll("_", " ");
        String toReturn = "";

        for (String word : toClean.split(" "))
            toReturn += word.substring(0, 1).toUpperCase() + word.substring(1) + " ";

        return toReturn.trim();
    }

}
