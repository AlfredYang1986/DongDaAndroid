package com.blackmirror.dongda.domain.Interactor;

import com.blackmirror.dongda.domain.model.SendSmsBean;
import com.blackmirror.dongda.domain.repository.LoginRepository;

import io.reactivex.Observable;

public class SendSmsUseCase extends LoginUseCase<SendSmsBean> {

    private final LoginRepository repository;

    public SendSmsUseCase(LoginRepository repository) {
        this.repository = repository;
    }


    public Observable<SendSmsBean> sendSms(String phone) {
        return repository.sendSms(phone);
    }
}
