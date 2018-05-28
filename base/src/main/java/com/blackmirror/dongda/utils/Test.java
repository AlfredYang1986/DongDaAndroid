package com.blackmirror.dongda.utils;

/**
 * Create By Ruge at 2018-05-25
 */
public class Test {
    public static void main(String[] args){
        String str="dTuPHtt1Jkvaw/132";
        String substring = str.substring(0, str.lastIndexOf("132"))+0;
        System.out.println(substring);
    }
}
