package com.blackmirror.dongda.AY;

import android.util.Log;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    public AYSysObject createInstanceByName(String name) {
        AYSysObject result = null;
        try {
            Class clazz1 = Class.forName(name);
            Constructor c = clazz1.getConstructor();
            result = (AYSysObject)c.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Boolean handleNotifications(String name, JSONObject args, AYSysObject context) {
        return methodInvoke(name, args, context);
    }

    protected Boolean methodInvoke(String name, JSONObject args, AYSysObject context) {
        Boolean result = true;
        try {
            Method method = context.getClass().getMethod(name, JSONObject.class);
            method.invoke(context, args);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            result = false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            result = false;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            result = false;
        }

        if (!result) {
            Log.i("method Invoke", "method invoke error");
        }

        return result;
    }

    public Boolean copyFile(String origin, String destination) {
        InputStream in = null;
        OutputStream out = null;
        Boolean result = true;
        try {
            in = new FileInputStream(new File(origin));
            File of = new File(destination);

            if (!of.exists())
                of.createNewFile();

            out = new FileOutputStream(of);
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            result = false;
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        } finally {
            try {
                if (in != null)
                    in.close();

                if (out != null)
                    out.close();

            } catch (IOException e) {
                e.printStackTrace();
                result = false;
            }
        }

        return result;
    }
}
