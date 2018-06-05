package com.blackmirror.dongda

import com.blackmirror.dongda.kdomain.Interactor.login.sendSms
import com.blackmirror.dongda.kdomain.model.SendSmsBean
import io.reactivex.Observable

/**
 * Create By Ruge at 2018-06-05
 */
fun a(phone:String):Observable<SendSmsBean> = sendSms(phone,f1)



val f1={
    str:String->
    Observable.create<SendSmsBean> {  }
}

fun g(str:String,get:(String)->Observable<SendSmsBean>): Observable<SendSmsBean> {
    return get(str)
}