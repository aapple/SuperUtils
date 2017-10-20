package com.young.image.utils;

/**
 * Created by yaobin on 2017/10/11.
 */
public class NumberUtils {

    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
