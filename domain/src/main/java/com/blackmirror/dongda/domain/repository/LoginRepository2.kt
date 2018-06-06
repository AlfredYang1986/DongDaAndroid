package com.blackmirror.dongda.domain.repository

import com.blackmirror.dongda.domain.model.PhoneLoginBean
import com.blackmirror.dongda.domain.model.SendSmsBean
import com.blackmirror.dongda.domain.model.UpLoadWeChatIconDomainBean
import com.blackmirror.dongda.domain.model.WeChatLoginBean

import io.reactivex.Observable

interface LoginRepository2 : Repository {

    fun sendSms(phone: String): Observable<SendSmsBean>

    fun phoneLogin(phone: String, code: String, reg_token: String): Observable<PhoneLoginBean>

    fun weChatLogin(provide_uid: String, provide_token: String, provide_screen_name: String,
                    provide_name: String, provide_screen_photo: String): Observable<WeChatLoginBean>

    fun upLoadWeChatIcon(userIcon: String, imgUUID: String): Observable<UpLoadWeChatIconDomainBean>

}
