package com.blackmirror.dongda.Tools;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ruge on 2018-04-20 下午5:06
 */
public class Test {
    public static void main(String[] args){
        Set<String> set=new HashSet<>();

        set.add("a");
        set.add("b");
        set.add("b");
        set.add("c");

        for (String s : set) {
            System.out.println(s);
        }
    }

}
