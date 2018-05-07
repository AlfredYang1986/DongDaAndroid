package com.blackmirror.dongda.data.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by alfredyang on 12/05/2017.
 */
public abstract class BaseApi {

    public static final OkHttpClient httpClient = new OkHttpClient().newBuilder()
            .connectTimeout(15, TimeUnit.SECONDS)
            //            .addNetworkInterceptor(new StethoInterceptor())
            .readTimeout(15,TimeUnit.SECONDS)
            .writeTimeout(60,TimeUnit.SECONDS)
            .build();
}
