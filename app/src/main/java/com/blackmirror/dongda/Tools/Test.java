package com.blackmirror.dongda.Tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

        Map<String,A> map=new HashMap<>();
        map.put("a",new A("aaa"));
        map.put("b",new A("bbb"));
        map.put("c",new A("ccc"));

        for (Map.Entry<String,A> entry:map.entrySet()){
           if (entry.getKey().equals("b")){
               entry.getValue().str="ddd";
           }
        }

        System.out.println(map.get("b").str);
    }

    public static class A {
        public String str;

        public A(String str) {
            this.str = str;
        }
    }

}
