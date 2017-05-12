package com.blackmirror.dongda.AY;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by alfredyang on 12/05/2017.
 */
public class AYSysHelperFunc {
    static AYSysHelperFunc instance;

    public static AYSysHelperFunc getInstance() {
        // TODO: 添加线程安全性代码
        if (instance == null) {
            instance = new AYSysHelperFunc();
        }
        return instance;
    }

    public String md5(String seed) {
        byte[] hash;

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
}
