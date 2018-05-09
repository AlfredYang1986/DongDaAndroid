package com.blackmirror.dongda.command;

import com.blackmirror.dongda.AY.AYSysNotifier;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.base.AYApplication;
import com.blackmirror.dongda.utils.AppConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by alfredyang on 12/05/2017.
 */
public abstract class AYCommand extends AYSysNotifier {

    public static OkHttpClient httpClient = new OkHttpClient().newBuilder()
            .connectTimeout(AppConstant.CONNECT_TIMEOUT, TimeUnit.SECONDS)
//            .addNetworkInterceptor(new StethoInterceptor())
            .readTimeout(AppConstant.READ_TIMEOUT,TimeUnit.SECONDS)
            .writeTimeout(AppConstant.WRITE_TIMEOUT,TimeUnit.SECONDS)
            .build();

    public abstract <Args, Result> Result execute(Args ... defaultArgs);

    public String kDONGDABASEURL = "http://www.altlys.com:9000/";

    protected String cmd_name;
    public String getCmdName() {
        return cmd_name;
    }
    public void setCmdName(String n) {
        this.cmd_name = n;
    }

    protected JSONObject getErrorNetData() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"status\":\"error\",")
                .append("\"error\":{")
                .append("\"code\":")
                .append(AppConstant.NET_WORK_UNAVAILABLE)
                .append(",")
                .append("\"message\":\"")
                .append(AYApplication.appContext.getString(R.string.net_work_error))
                .append("\"}}");
        JSONObject object = null;
        try {
            return new JSONObject(sb.toString());
        } catch (JSONException e1) {

        }
        return object;
    }
}
