package com.codestallions.spacemmo.util;

public class StringUtil {

    public static boolean isTextEntered(String... entries) {
        for (String entry : entries) {
            if (entry.isEmpty()) {
                return false;
            }
        }
        return true;
    }

}
