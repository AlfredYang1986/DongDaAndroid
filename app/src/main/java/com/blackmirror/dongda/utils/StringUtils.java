package com.blackmirror.dongda.utils;

/**
 * Created by Ruge on 2018-04-23 下午2:06
 */
public class StringUtils {
    public static boolean isNumber(String str){
        try {
            int i = Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e){
        }
        return false;
    }

    public static double getDoubleValue(String str){
        double d=-1;
        try {
             d = Double.parseDouble(str);
            return d;
        } catch (NumberFormatException e){
            return d;
        }
    }
}
