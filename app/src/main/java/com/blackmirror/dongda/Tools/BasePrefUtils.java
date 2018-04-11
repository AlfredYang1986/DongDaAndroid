package com.blackmirror.dongda.Tools;

import android.content.Context;
import android.content.SharedPreferences;

public class BasePrefUtils {
    private static final String USER_INFO ="user_info";
    private static final String WECHAT_ACCESSTOKEN="access_token";
    private static final String WECHAT_APPID="wechat_appid";
    private static final String WECHAT_SECRET="wechat_secret";
    private static final String WECHAT_EXPIRES_IN="wechat_expires_in";//微信access_token有效期
    private static final String WECHAT_REFRESH_TOKEN="wechat_refresh_token";
    private static final String WECHAT_OPENID="wechat_openid";//授权用户唯一标识
    private static final String WECHAT_UNIONID="wechat_unionid";//用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的。
    private static final String AUTH_TOKEN="auth_token";//token
    private static final String USER_ID="user_id";//user_id

    public static void setAuthToken(String auth_token){
        setStringPref(AYApplication.appConext, USER_INFO,AUTH_TOKEN,auth_token);
    }

    public static String getAuthToken(){
        return getSharedPreferences(AYApplication.appConext, USER_INFO).getString(AUTH_TOKEN,"");
    }

    public static void setUserId(String user_id){
        setStringPref(AYApplication.appConext, USER_INFO,USER_ID,user_id);
    }

    public static String getUserId(){
        return getSharedPreferences(AYApplication.appConext, USER_INFO).getString(USER_ID,"");
    }

    public static void setWeChatAccessToken(String access_token){
        setStringPref(AYApplication.appConext, USER_INFO,WECHAT_ACCESSTOKEN,access_token);
    }

    public static void setWeChatAppId(String appId){
        setStringPref(AYApplication.appConext, USER_INFO,WECHAT_ACCESSTOKEN,appId);
    }

    public static void setWeChatSecret(String secret){
        setStringPref(AYApplication.appConext, USER_INFO,WECHAT_ACCESSTOKEN,secret);
    }

    public static void setWeChatExpiresIn(String expiresIn){
        setStringPref(AYApplication.appConext, USER_INFO,WECHAT_ACCESSTOKEN,expiresIn);
    }

    public static void setWeChatRefreshToken(String refresh_token){
        setStringPref(AYApplication.appConext, USER_INFO,WECHAT_ACCESSTOKEN,refresh_token);
    }

    public static void setWechatOpenId(String openId){
        setStringPref(AYApplication.appConext, USER_INFO,WECHAT_ACCESSTOKEN,openId);
    }

    public static String getWechatAccessToken(){
        return getSharedPreferences(AYApplication.appConext, USER_INFO).getString(WECHAT_ACCESSTOKEN,"");
    }

    public static String getWechatRefreshToken(){
        return getSharedPreferences(AYApplication.appConext, USER_INFO).getString(WECHAT_REFRESH_TOKEN,"");
    }

    public static String getWechatOpenId(){
        return getSharedPreferences(AYApplication.appConext, USER_INFO).getString(WECHAT_OPENID,"");
    }


    private static void setStringPref(Context context,String name,String key,String value){
        getEditor(context, name).putString(key, value).commit();
    }

    private static SharedPreferences.Editor getEditor(Context context, String name) {
        SharedPreferences.Editor editor = getSharedPreferences(context, name).edit();
        return editor;
    }

    private static SharedPreferences getSharedPreferences(Context context, String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

}
