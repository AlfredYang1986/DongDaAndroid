package com.blackmirror.dongda.Tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ruge on 2018-04-10 下午1:42
 */
public class Test {
    public static void main(String[] args){
        String date = "2018-04-16T3:05:03Z";
        date = date.replace("Z", " UTC");//注意是空格+UTC
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss Z");//注意格式化的表达式
        try {
            Date d = format.parse(date);
            System.out.println("format "+d.getTime()/1000);
            long l = System.currentTimeMillis() / 1000;
            System.out.println("cur "+l);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
