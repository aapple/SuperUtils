package com.young.image.utils;

import java.util.regex.Pattern;

/**
 * Created by yaobin on 2017/10/11.
 */
public class NumberUtils {

    public static boolean isDouble(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }
}
