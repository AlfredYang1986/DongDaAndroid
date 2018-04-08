package com.blackmirror.dongda.wxapi;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.blackmirror.dongda.Tools.AYApplication;
import com.blackmirror.dongda.Tools.LogUtils;
import com.blackmirror.dongda.Tools.ToastUtils;
import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.model.WeChatAccessTokenBean;
import com.blackmirror.dongda.model.WeChatUserInfoBean;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private static final int RETURN_MSG_TYPE_LOGIN = 1; //登录
    private static final int RETURN_MSG_TYPE_SHARE = 2; //分享
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        //这句没有写,是不能执行回调的方法的
        AYApplication.weChatApi.handleIntent(getIntent(), this);
    }



    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq baseReq) {

    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    //app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp baseResp) {
        LogUtils.d("xcx", "onResp:------>");
        LogUtils.d("xcx", "error_code:---->" + baseResp.errCode);
        int type = baseResp.getType(); //类型：分享还是登录
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //用户拒绝授权
                ToastUtils.showShortToast(mContext, "拒绝授权微信登录");
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //用户取消
                String message = "";
                if (type == RETURN_MSG_TYPE_LOGIN) {
                    message = "取消了微信登录";
                } else if (type == RETURN_MSG_TYPE_SHARE) {
                    message = "取消了微信分享";
                }
                ToastUtils.showShortToast(mContext, message);
                break;
            case BaseResp.ErrCode.ERR_OK:
                //用户同意
                if (type == RETURN_MSG_TYPE_LOGIN) {
                    //用户换取access_token的code，仅在ErrCode为0时有效
                    String code = ((SendAuth.Resp) baseResp).code;
                    LogUtils.d("xcx", "code:------>" + code);

                    //这里拿到了这个code，去做2次网络请求获取access_token和用户个人信息
//                    WXLoginUtils().getWXLoginResult(code, this);
                    getWeChatLoginResult(code,this);
                } else if (type == RETURN_MSG_TYPE_SHARE) {
                    ToastUtils.showShortToast(mContext, "微信分享成功");
                }
                break;
        }
    }

    private void getWeChatLoginResult(String code, WXEntryActivity activity) {
        final String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx66d179d99c9ba7d6" +
                "&secret=469c1beed3ecaa3a836767a5999beeb1&code="+code+"&grant_type=authorization_code";



        Observable.just(url)
                .map(new Function<String, WeChatAccessTokenBean>() {
                    @Override
                    public WeChatAccessTokenBean apply(String s) throws Exception {
                        return executeRequest(s);
                    }
                })
                .flatMap(new Function<WeChatAccessTokenBean, ObservableSource<WeChatUserInfoBean>>() {
                    @Override
                    public ObservableSource<WeChatUserInfoBean> apply(WeChatAccessTokenBean bean) throws Exception {
                        if (bean.isSuccess){
                            String user_url = "https://api.weixin.qq.com/sns/userinfo?access_token=" +
                                    bean.access_token+"&openid="+bean.openid;
                            return Observable.just(getUserInfo(user_url));
                        }
                        return Observable.just(new WeChatUserInfoBean());

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeChatUserInfoBean>() {
                    @Override
                    public void accept(WeChatUserInfoBean bean) throws Exception {
                        finish();
                    }
                });
    }

    private WeChatUserInfoBean getUserInfo(String url){
        LogUtils.d("xcx","getUserInfo "+Thread.currentThread().getName());
        WeChatUserInfoBean bean;
        Request request = new Request.Builder()
                .url(url).get().build();
        Call net_call = AYCommand.httpClient.newCall(request);
        try {
            Response response = net_call.execute();
            InputStream in = inputStreamAfterCheck(response);
            InputStreamReader iReader = new InputStreamReader(in);
            BufferedReader bReader = new BufferedReader(iReader);
            StringBuilder json = new StringBuilder();
            String line=null;
            while((line = bReader.readLine()) != null){
                json.append(line).append('\n');}
            bReader.close();
            iReader.close();
            in.close();
            //            T obj = (T)JSON.parseObject(json.toString(), myClass);
            LogUtils.d("xcx","返回的数据："+json.toString());
            bean = JSON.parseObject(json.toString(),WeChatUserInfoBean.class);
            if (bean != null && TextUtils.isEmpty(bean.errcode)){
                bean.isSuccess = true;
            }
            return bean;
        } catch (Exception e) {
            LogUtils.e(WXEntryActivity.class,e);
            bean = new WeChatUserInfoBean();
            bean.errcode = "400";
            bean.errmsg = e.getMessage();
        }
        LogUtils.d("xcx",bean.toString());
        return bean;
    }

    private Call net_call;

    private WeChatAccessTokenBean executeRequest(String url) {
        LogUtils.d("xcx","executeRequest "+Thread.currentThread().getName());

        WeChatAccessTokenBean bean;
        Request request = new Request.Builder()
                .url(url).get().build();
        net_call = AYCommand.httpClient.newCall(request);
        try {
            Response response = net_call.execute();
            InputStream in = inputStreamAfterCheck(response);
            InputStreamReader iReader = new InputStreamReader(in);
            BufferedReader bReader = new BufferedReader(iReader);
            StringBuilder json = new StringBuilder();
            String line=null;
            while((line = bReader.readLine()) != null){
                json.append(line).append('\n');}
            bReader.close();
            iReader.close();
            in.close();
            //            T obj = (T)JSON.parseObject(json.toString(), myClass);
            LogUtils.d("xcx","返回的数据："+json.toString());
            bean = JSON.parseObject(json.toString(),WeChatAccessTokenBean.class);
            if (bean != null && TextUtils.isEmpty(bean.errcode)){
                bean.isSuccess = true;
            }
            LogUtils.d("xcx",bean.toString());
            return bean;
        } catch (Exception e) {
            LogUtils.e(WXEntryActivity.class,e);
            bean = new WeChatAccessTokenBean();
            bean.errcode = "400";
            bean.errmsg = e.getMessage();
            LogUtils.d("xcx",bean.toString());
        }
        return bean;
    }

    private static InputStream inputStreamAfterCheck(Response response) throws IOException {
        InputStream input = response.body().byteStream();
        if ("gzip".equalsIgnoreCase(response.header("Content-Encoding"))) {
            input = new GZIPInputStream(input);
        }
        return input;
    }
}

