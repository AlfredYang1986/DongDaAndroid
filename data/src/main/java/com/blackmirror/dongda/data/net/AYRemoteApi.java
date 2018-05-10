package com.blackmirror.dongda.data.net;


import com.alibaba.fastjson.JSON;
import com.blackmirror.dongda.data.DataConstant;
import com.blackmirror.dongda.data.model.response.BaseResponseBean;
import com.blackmirror.dongda.data.utils.LogUtils;
import com.blackmirror.dongda.data.model.request.BaseRequestBean;

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
public class AYRemoteApi extends BaseApi {


    protected static <P extends BaseResponseBean, Q extends BaseRequestBean> Observable<P> execute(Q q, final Class<P> myClass) {
        return Observable.just(q).map(new Function<Q, P>() {
            @Override
            public P apply(Q q) throws Exception {
                LogUtils.d("flag", "做网络请求前的json数据: " + q.json.toString());
                Request request = new Request.Builder()
                        .url(q.url).post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), q.json.toString())).build();
                return executeRequest(request, myClass);
            }
        });
    }

    protected static <P extends BaseResponseBean> P executeRequest(Request request, Class myClass) {
        //        LogUtils.d("flag","做网络请求前的json数据: "+args.toString());
        int error_code;
        String error_message;
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
            P obj = (P) JSON.parseObject(json.toString(), myClass);

            return obj;
        } catch (ConnectTimeoutException e1) {
            error_code = DataConstant.CONNECT_TIMEOUT_EXCEPTION;
            error_message = e1.getMessage();
            LogUtils.e(AYRemoteApi.class, "ConnectTimeoutException: ", e1);

        } catch (SocketTimeoutException e2) {//服务器响应超时
            error_code = DataConstant.SOCKET_TIMEOUT_EXCEPTION;
            error_message = e2.getMessage();
            LogUtils.e(AYRemoteApi.class, "SocketTimeoutException: ", e2);

        } catch (ConnectException e3) {//服务器请求超时
            error_code = DataConstant.CONNECT_EXCEPTION;
            error_message = e3.getMessage();
            LogUtils.e(AYRemoteApi.class, "ConnectException: ", e3);

        } catch (Exception e4) {
            error_code = DataConstant.OTHER_EXCEPTION;
            error_message = e4.getMessage();
            LogUtils.e(AYRemoteApi.class, "Exception: ", e4);

        }
        P obj = null;

        try {
            obj = (P) myClass.newInstance();
            obj.error.code = error_code;
            obj.error.message = error_message;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }

    private static InputStream inputStreamAfterCheck(Response response) throws IOException {
        InputStream input = response.body().byteStream();
        if ("gzip".equalsIgnoreCase(response.header("Content-Encoding"))) {
            input = new GZIPInputStream(input);
        }
        return input;
    }

    private static <P extends BaseResponseBean> P getErrorData(Class clz) {
        P obj = null;

        try {
            obj = (P) clz.newInstance();
            obj.error.code = DataConstant.NET_UNKNOWN_ERROR;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }

}
