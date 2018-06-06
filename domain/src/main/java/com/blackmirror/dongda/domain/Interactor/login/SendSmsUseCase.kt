package com.blackmirror.dongda.domain.Interactor.login

import com.blackmirror.dongda.domain.model.SendSmsBean
import com.blackmirror.dongda.domain.repository.LoginRepository
import io.reactivex.Observable

class SendSmsUseCase(private val repository: LoginRepository){


    fun sendSms(phone: String): Observable<SendSmsBean> {
        return repository.sendSms(phone)
    }
}
