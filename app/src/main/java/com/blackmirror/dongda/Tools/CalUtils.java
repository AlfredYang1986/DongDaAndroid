package com.blackmirror.dongda.Tools;

/**
 * Created by Ruge on 2018-04-17 下午2:33
 */
public class CalUtils {
    /**
     * 求最大公约数数
     * @param a
     * @param b
     * @return
     */
    public static int getGongyue(int a,int b)
    {
        int gongyue=0;
        if(a<b) {   //交换a、b的值
            a=a+b;
            b=a-b;
            a=a-b;
        }
        if(a%b==0) {
            gongyue = b;
        }
        while(a % b>0) {
            a=a%b;
            if(a<b) {
                a=a+b;
                b=a-b;
                a=a-b;
            }
            if(a%b==0) {
                gongyue = b;
            }
        }
        return gongyue;
    }
}
