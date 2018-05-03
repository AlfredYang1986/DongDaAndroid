package com.blackmirror.dongda.command;

import com.alibaba.fastjson.JSON;
import com.blackmirror.dongda.model.BaseServerBean;
import com.blackmirror.dongda.utils.LogUtils;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.zip.GZIPInputStream;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by alfredyang on 23/05/2017.
 */
public abstract class AYRemoteCommand2 extends AYCommand2{



    public <T extends BaseServerBean, V> Observable<T> execute(V v, final Class<T> myClass) {
        return Observable.just(v).map(new Function<V, T>() {
            @Override
            public T apply(V v) throws Exception {
                Request request = new Request.Builder()
                        .url(getUrl()).post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "")).build();
                return executeRequest(request,myClass);
            }
        });
    }





    private <T extends BaseServerBean> T executeRequest(Request request,Class myClass) {
//        LogUtils.d("flag","做网络请求前的json数据: "+args.toString());

        try {
            Response response = httpClient.newCall(request).execute();
            InputStream in = inputStreamAfterCheck(response);
            InputStreamReader iReader = new InputStreamReader(in);
            BufferedReader bReader = new BufferedReader(iReader);
            StringBuilder json = new StringBuilder();
            String line = null;
            while ((line = bReader.readLine()) != null) {
                json.append(line).append('\n');
            }
            bReader.close();
            iReader.close();
            in.close();
            LogUtils.d("flag", "返回的数据：" + json.toString());
            T obj= (T) JSON.parseObject(json.toString(),myClass);

            return obj;
        } catch (ConnectTimeoutException e5){
            LogUtils.e(AYRemoteCommand.class,"ConnectTimeoutException: ", e5);
        } catch (SocketTimeoutException e1) {
            LogUtils.e(AYRemoteCommand.class,"SocketTimeoutException: ",e1);
        } catch (ConnectException e2) {
            LogUtils.e(AYRemoteCommand.class,"ConnectException: ",e2);
        } catch (Exception e4) {
            LogUtils.e(AYRemoteCommand.class,"Exception: ",e4);

        }
        return getErrorData(myClass);
    }

    private static InputStream inputStreamAfterCheck(Response response) throws IOException {
        InputStream input = response.body().byteStream();
        if ("gzip".equalsIgnoreCase(response.header("Content-Encoding"))) {
            input = new GZIPInputStream(input);
        }
        return input;
    }

    private <T extends BaseServerBean>T getErrorData(Class clz) {
        T obj = null;

        try {
            obj = (T) clz.newInstance();
            obj.error.code=9999;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }

    protected abstract String getUrl();

    protected abstract String getSuccessCallBackName();

    protected abstract String getFailedCallBackName();

}
