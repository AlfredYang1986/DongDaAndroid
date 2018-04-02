package com.blackmirror.dongda.command;

import com.blackmirror.dongda.AY.AYSysNotifier;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by alfredyang on 12/05/2017.
 */
public abstract class AYCommand extends AYSysNotifier {

    protected static OkHttpClient httpClient = new OkHttpClient().newBuilder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .addNetworkInterceptor(new StethoInterceptor())
            .readTimeout(60,TimeUnit.SECONDS)
            .writeTimeout(60,TimeUnit.SECONDS)
            .build();

    public abstract <Args, Result> Result excute(Args ... defaultArgs);

    public String kDONGDABASEURL = "http://www.altlys.com:9000/";

    protected String cmd_name;
    public String getCmdName() {
        return cmd_name;
    }
    public void setCmdName(String n) {
        this.cmd_name = n;
    }

    protected void cancelNetCall(){

    }
}
