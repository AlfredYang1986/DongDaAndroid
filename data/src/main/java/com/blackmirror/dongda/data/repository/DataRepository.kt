package com.blackmirror.dongda.data.repository

import com.blackmirror.dongda.data.model.response.PhoneLoginResponseBean
import com.blackmirror.dongda.data.model.response.SendSmsResponseBean
import com.blackmirror.dongda.data.model.response.WeChatLoginResponseBean
import io.reactivex.Observable

/**
 * Create By Ruge at 2018-06-11
 */

fun getSMS(phone: String, sendSms: (String) -> Observable<SendSmsResponseBean>): Observable<SendSmsResponseBean> {
    return sendSms(phone)
}

fun phoneLogin(phone: String, code: String, reg_token: String, login: (String, String, String) -> Observable<PhoneLoginResponseBean>): Observable<PhoneLoginResponseBean> {
    return login(phone, code, reg_token)
}

fun weChatLogin(provide_uid: String, provide_token: String, provide_screen_name: String,
                provide_name: String, provide_screen_photo: String,wlogin:(String,String,String,String,String)->Observable<WeChatLoginResponseBean>): Observable<WeChatLoginResponseBean> {
    return wlogin(provide_uid,provide_token,provide_screen_name,provide_name,provide_screen_photo)
}



