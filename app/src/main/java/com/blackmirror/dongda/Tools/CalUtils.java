package com.blackmirror.dongda.Tools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

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

    /**
     * 获取MD5
     * @param seed
     * @return
     */
    public static String md5(String seed) {
        byte[] hash = null;

        try {
            hash = MessageDigest.getInstance("MD5").digest(seed.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }

    /**
     * 获取32位UUID
     * @return
     */
    public static String getUUID32(){
        return UUID.randomUUID().toString().toLowerCase();
    }
}
