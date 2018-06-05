package com.blackmirror.dongda.kdomain.Interactor


import com.blackmirror.dongda.kdomain.model.SendSmsBean
import com.blackmirror.dongda.kdomain.repository.LoginRepository

import io.reactivex.Observable

class SendSmsUseCase2(private val repository: LoginRepository) : LoginUseCase<SendSmsBean>() {


    fun sendSms(phone: String): Observable<SendSmsBean> {
        return repository.sendSms(phone)
    }
}
