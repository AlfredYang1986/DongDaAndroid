package com.blackmirror.dongda.data;

import com.blackmirror.dongda.AY.AYSysNotifier;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by alfredyang on 12/05/2017.
 */
public abstract class AYCommand2 extends AYSysNotifier {

    public static final OkHttpClient httpClient = new OkHttpClient().newBuilder()
            .connectTimeout(15, TimeUnit.SECONDS)
            //            .addNetworkInterceptor(new StethoInterceptor())
            .readTimeout(15,TimeUnit.SECONDS)
            .writeTimeout(60,TimeUnit.SECONDS)
            .build();
}
