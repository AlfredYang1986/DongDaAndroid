package com.blackmirror.dongda.data.net

import com.blackmirror.dongda.data.DataConstant
import com.blackmirror.dongda.data.model.request.PhoneLoginRequestBean
import com.blackmirror.dongda.data.model.request.SendSmsRequestBean
import com.blackmirror.dongda.data.model.request.WeChatLoginRequestBean
import com.blackmirror.dongda.data.model.response.PhoneLoginResponseBean
import com.blackmirror.dongda.data.model.response.SendSmsResponseBean
import com.blackmirror.dongda.data.model.response.WeChatLoginResponseBean
import io.reactivex.Observable

/**
 * Create By Ruge at 2018-06-06
 */

fun sendSMS(phone: String): Observable<SendSmsResponseBean> {
    val bean = SendSmsRequestBean()
    bean.phone_number = phone
    val json = "{\"phone\":\"$phone\"}"
    bean.json = json
    bean.url = DataConstant.SEND_SMS_CODE_URL
    return execute(bean, SendSmsResponseBean::class.java)
}

fun phoneLogin(phone: String, code: String, reg_token: String) : Observable<PhoneLoginResponseBean>{
    val bean=PhoneLoginRequestBean()
    val json ="{\"phone\":\"$phone\",\"code\":\"$code\",\"reg_token\":\"$reg_token\"}"
    bean.json = json
    bean.url = DataConstant.AUTH_SMS_CODE_URL
    return execute(bean,PhoneLoginResponseBean::class.java)
}

fun wechatLogin(provide_uid: String, provide_token: String, provide_screen_name: String,
                provide_name: String, provide_screen_photo: String): Observable<WeChatLoginResponseBean> {
    val bean = WeChatLoginRequestBean()
    val json = "{\"third\":{\"provide_uid\":\"$provide_uid\",\"provide_token\":\"$provide_token\",\"provide_screen_name\":\"$provide_screen_name\",\"provide_name\":\"$provide_name\",\"provide_screen_photo\":\"$provide_screen_photo\"}}"
    bean.json = json
    bean.url = DataConstant.WECHAT_LOGIN_URL
    return AYRemoteApi.execute(bean, WeChatLoginResponseBean::class.java)
}

