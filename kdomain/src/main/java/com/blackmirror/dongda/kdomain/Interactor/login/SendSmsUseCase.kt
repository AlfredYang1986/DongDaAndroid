package com.blackmirror.dongda.kdomain.Interactor.login

import com.blackmirror.dongda.kdomain.model.SendSmsBean
import io.reactivex.Observable

/**
 * Create By Ruge at 2018-06-05
 */

fun sendSms(phone: String, sendSMS: (String) -> Observable<SendSmsBean>): Observable<SendSmsBean> {
    return sendSMS(phone)
}