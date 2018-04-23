package com.blackmirror.dongda.Tools;

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
}
