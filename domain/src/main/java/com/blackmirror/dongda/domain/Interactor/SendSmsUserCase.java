package com.blackmirror.dongda.domain.Interactor;

import com.blackmirror.dongda.domain.base.UserCase;
import com.blackmirror.dongda.domain.model.SendSmsBean;
import com.blackmirror.dongda.domain.repository.LoginRepository;

import io.reactivex.Observable;

public class SendSmsUserCase implements UserCase<SendSmsBean> {

    private final LoginRepository repository;

    public SendSmsUserCase(LoginRepository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<SendSmsBean> execute(String... args) {
        return repository.sendSms(args[0]);
    }
}
