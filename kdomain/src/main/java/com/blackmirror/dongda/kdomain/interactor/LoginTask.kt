package com.blackmirror.dongda.kdomain.interactor

import com.blackmirror.dongda.data.DataConstant
import com.blackmirror.dongda.data.model.db.UserInfoDbBean
import com.blackmirror.dongda.data.model.request.PhoneLoginRequestBean
import com.blackmirror.dongda.data.model.request.SendSmsRequestBean
import com.blackmirror.dongda.data.model.request.WeChatLoginRequestBean
import com.blackmirror.dongda.data.model.response.PhoneLoginResponseBean
import com.blackmirror.dongda.data.model.response.SendSmsResponseBean
import com.blackmirror.dongda.data.model.response.WeChatLoginResponseBean
import com.blackmirror.dongda.data.net.execute
import com.blackmirror.dongda.data.repository.DbRepository
import com.blackmirror.dongda.data.repository.getSMS
import com.blackmirror.dongda.data.repository.phoneLogin
import com.blackmirror.dongda.data.repository.weChatLogin
import com.blackmirror.dongda.kdomain.model.PhoneLoginBean
import com.blackmirror.dongda.kdomain.model.SendSmsKdBean
import com.blackmirror.dongda.kdomain.model.WeChatLoginBean
import com.blackmirror.dongda.utils.AYPrefUtils
import io.reactivex.Observable

/**
 * Create By Ruge at 2018-06-11
 */
/**
 * 发送短信
 */
fun getSmsFromServer(phone: String): Observable<SendSmsKdBean> {
    return getSMS(phone, sms)
            .map {
                val bean = SendSmsKdBean()
                bean.isSuccess = it.status == "ok"

                it.result?.reg?.apply {
                    bean.phone = phone
                    bean.reg_token = reg_token
                    bean.is_reg = is_reg
                }

                bean.code = it.error?.code ?: DataConstant.NET_UNKNOWN_ERROR
                bean.message = it.error?.message ?: ""
                bean
            }
}

/**
 * 手机号登陆
 */
fun phoneLoginImpl(phone: String, code: String, reg_token: String): Observable<PhoneLoginBean> {
    return phoneLogin(phone, code, reg_token, pl).map {
        val bean = PhoneLoginBean()
        if ("ok" == it.status) {
            bean.isSuccess = true
            it.result?.apply {
                bean.auth_token = auth_token
            }?.user?.apply {
                bean.screen_name = screen_name
                bean.has_auth_phone = has_auth_phone
                bean.current_device_type = current_device_type
                bean.is_service_provider = is_service_provider
                bean.user_id = user_id
                bean.screen_photo = screen_photo
                bean.current_device_id = current_device_id
            }
        } else {
            bean.code = it.error?.code ?: DataConstant.NET_UNKNOWN_ERROR
            bean.message = it.error?.message ?: ""
        }
        //插入数据库
        if (bean.isSuccess) {
            AYPrefUtils.setUserId(bean.user_id)
            AYPrefUtils.setAuthToken(bean.auth_token)

            val dbBean = UserInfoDbBean()
            dbBean.is_current = 1//目前没什么卵用
            dbBean.screen_name = bean.screen_name
            dbBean.screen_photo = bean.screen_photo
            dbBean.user_id = bean.user_id
            dbBean.auth_token = bean.auth_token
            DbRepository.insertProfile(dbBean)
        }
        return@map bean
    }
}

fun weChatLoginImpl(provide_uid: String, provide_token: String, provide_screen_name: String, provide_name: String, provide_screen_photo: String): Observable<WeChatLoginBean> {
    return weChatLogin(provide_uid, provide_token, provide_screen_name, provide_name, provide_screen_photo, wl)
            .map {
                val bean = WeChatLoginBean()
                bean.isSuccess = "ok" == it.status

                it.result?.apply {
                    bean.auth_token = auth_token
                }?.user?.apply {
                    bean.screen_name = screen_name
                    bean.has_auth_phone = has_auth_phone
                    bean.current_device_type = current_device_type
                    bean.is_service_provider = is_service_provider
                    bean.user_id = user_id
                    bean.screen_photo = screen_photo
                    bean.current_device_id = current_device_id
                }

                bean.code = it.error?.code ?: DataConstant.NET_UNKNOWN_ERROR
                bean.message = it.error?.message ?: ""


                //插入数据库
                if (bean.isSuccess) {

                    AYPrefUtils.setUserId(bean.user_id)
                    AYPrefUtils.setAuthToken(bean.auth_token)

                    val dbBean = UserInfoDbBean()
                    dbBean.is_current = 1//目前没什么卵用
                    dbBean.screen_name = bean.screen_name
                    dbBean.screen_photo = bean.screen_photo
                    dbBean.user_id = bean.user_id
                    dbBean.auth_token = bean.auth_token
                    DbRepository.insertProfile(dbBean)
                }

                bean
            }
}

val wl = fun(provide_uid: String, provide_token: String, provide_screen_name: String, provide_name: String, provide_screen_photo: String): Observable<WeChatLoginResponseBean> {
    val bean = WeChatLoginRequestBean()
    val json = "{\"third\":{\"provide_uid\":\"" + provide_uid + "\",\"provide_token\":\"" + provide_token + "\",\"provide_screen_name\":\"" + provide_screen_name + "\"," +
            "\"provide_name\":\"" + provide_name + "\",\"provide_screen_photo\":\"" + provide_screen_photo + "\"}}"
    bean.json = json
    bean.url = DataConstant.WECHAT_LOGIN_URL
    return execute(bean, WeChatLoginResponseBean::class.java)
}

val pl = fun(phone: String, code: String, reg_token: String): Observable<PhoneLoginResponseBean> {
    val bean = PhoneLoginRequestBean()
    val json = "{\"phone\":\"$phone\",\"code\":\"$code\",\"reg_token\":\"$reg_token\"}"
    bean.json = json
    bean.url = DataConstant.AUTH_SMS_CODE_URL
    return execute(bean, PhoneLoginResponseBean::class.java)
}

val sms = fun(phone: String): Observable<SendSmsResponseBean> {
    val bean = SendSmsRequestBean()
    bean.phone_number = phone
    val json = "{\"phone\":\"$phone\"}"
    bean.json = json
    bean.url = DataConstant.SEND_SMS_CODE_URL
    return execute(bean, SendSmsResponseBean::class.java)

}
