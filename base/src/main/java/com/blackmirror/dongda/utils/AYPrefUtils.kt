package com.blackmirror.dongda.utils

import android.content.Context
import android.content.SharedPreferences
import com.blackmirror.dongda.base.AYApplication

/**
 * Create By Ruge at 2018-12-07
 */
private val USER_INFO = "user_info"
private val WECHAT_ACCESSTOKEN = "access_token"
private val WECHAT_APPID = "wechat_appid"
private val WECHAT_SECRET = "wechat_secret"
private val WECHAT_EXPIRES_IN = "wechat_expires_in"//微信access_token有效期
private val WECHAT_REFRESH_TOKEN = "wechat_refresh_token"
private val WECHAT_OPENID = "wechat_openid"//授权用户唯一标识
private val WECHAT_UNIONID = "wechat_unionid"//用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的。
private val AUTH_TOKEN = "auth_token"//token
private val USER_ID = "user_id"//user_id
private val ACCESSKEYID = "accessKeyId"
private val SECURITYTOKEN = "SecurityToken"
private val ACCESSKEYSECRET = "accessKeySecret"
private val EXPIRATION = "Expiration"//
private val SETTING_FLAG = "setting_flag"//
private val IS_PHONE_LOGIN = "phone_login"//
private val IMG_UUID = "img_uuid"//

fun setImgUuid(uuid: String?) {
    setStringPref(AYApplication.appContext, USER_INFO, IMG_UUID, uuid)
}

fun getImgUuid(): String {
    return getSharedPreferences(AYApplication.appContext, USER_INFO).getString(IMG_UUID, "")
}

fun setIsPhoneLogin(flag: String) {
    setStringPref(AYApplication.appContext, USER_INFO, IS_PHONE_LOGIN, flag)
}

fun getIsPhoneLogin(): String {
    return getSharedPreferences(AYApplication.appContext, USER_INFO).getString(IS_PHONE_LOGIN, "")
}

fun setSettingFlag(setting_flag: String) {
    setStringPref(AYApplication.appContext, USER_INFO, SETTING_FLAG, setting_flag)
}

fun getSettingFlag(): String {
    return getSharedPreferences(AYApplication.appContext, USER_INFO).getString(SETTING_FLAG, "3")
}

fun setAccesskeyId(accessKeyId: String?) {
    setStringPref(AYApplication.appContext, USER_INFO, ACCESSKEYID, accessKeyId)
}

fun getAccesskeyId(): String {
    return getSharedPreferences(AYApplication.appContext, USER_INFO).getString(ACCESSKEYID, "")
}

fun setSecurityToken(SecurityToken: String?) {
    setStringPref(AYApplication.appContext, USER_INFO, SECURITYTOKEN, SecurityToken)
}

fun getSecurityToken(): String {
    return getSharedPreferences(AYApplication.appContext, USER_INFO).getString(SECURITYTOKEN, "")
}

fun setAccesskeySecret(accessKeySecret: String?) {
    setStringPref(AYApplication.appContext, USER_INFO, ACCESSKEYSECRET, accessKeySecret)
}

fun getAccesskeySecret(): String {
    return getSharedPreferences(AYApplication.appContext, USER_INFO).getString(ACCESSKEYSECRET, "")
}

fun setExpiration(Expiration: String?) {
    setStringPref(AYApplication.appContext, USER_INFO, EXPIRATION, Expiration)
}

fun getExpiration(): String {
    return getSharedPreferences(AYApplication.appContext, USER_INFO).getString(EXPIRATION, "")
}

fun setAuthToken(auth_token: String?) {
    setStringPref(AYApplication.appContext, USER_INFO, AUTH_TOKEN, auth_token)
}

fun getAuthToken(): String {
    return getSharedPreferences(AYApplication.appContext, USER_INFO).getString(AUTH_TOKEN, "")
}

fun setUserId(user_id: String?) {
    setStringPref(AYApplication.appContext, USER_INFO, USER_ID, user_id)
}

fun getUserId(): String {
    return getSharedPreferences(AYApplication.appContext, USER_INFO).getString(USER_ID, "")
}

fun setWeChatAccessToken(access_token: String) {
    setStringPref(AYApplication.appContext, USER_INFO, WECHAT_ACCESSTOKEN, access_token)
}

fun setWeChatAppId(appId: String) {
    setStringPref(AYApplication.appContext, USER_INFO, WECHAT_ACCESSTOKEN, appId)
}

fun setWeChatSecret(secret: String) {
    setStringPref(AYApplication.appContext, USER_INFO, WECHAT_ACCESSTOKEN, secret)
}

fun setWeChatExpiresIn(expiresIn: String) {
    setStringPref(AYApplication.appContext, USER_INFO, WECHAT_ACCESSTOKEN, expiresIn)
}

fun setWeChatRefreshToken(refresh_token: String) {
    setStringPref(AYApplication.appContext, USER_INFO, WECHAT_ACCESSTOKEN, refresh_token)
}

fun setWechatOpenId(openId: String) {
    setStringPref(AYApplication.appContext, USER_INFO, WECHAT_ACCESSTOKEN, openId)
}

fun getWechatAccessToken(): String {
    return getSharedPreferences(AYApplication.appContext, USER_INFO).getString(WECHAT_ACCESSTOKEN, "")
}

fun getWechatRefreshToken(): String {
    return getSharedPreferences(AYApplication.appContext, USER_INFO).getString(WECHAT_REFRESH_TOKEN, "")
}

fun getWechatOpenId(): String {
    return getSharedPreferences(AYApplication.appContext, USER_INFO).getString(WECHAT_OPENID, "")
}


private fun setStringPref(context: Context, name: String, key: String, value: String?) {
    getEditor(context, name).putString(key, value).commit()
}

private fun getEditor(context: Context, name: String): SharedPreferences.Editor {
    return getSharedPreferences(context, name).edit()
}

private fun getSharedPreferences(context: Context, name: String): SharedPreferences {
    return context.getSharedPreferences(name, Context.MODE_PRIVATE)
}