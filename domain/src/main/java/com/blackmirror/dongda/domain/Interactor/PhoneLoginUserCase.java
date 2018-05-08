package com.blackmirror.dongda.domain.Interactor;

import com.blackmirror.dongda.domain.base.UserCase;
import com.blackmirror.dongda.domain.model.PhoneLoginBean;
import com.blackmirror.dongda.domain.repository.LoginRepository;

import io.reactivex.Observable;


/**
 * Created by Ruge on 2018-05-07 下午2:50
 */
public class PhoneLoginUserCase implements UserCase<PhoneLoginBean> {


    private final LoginRepository repository;

    public PhoneLoginUserCase(LoginRepository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<PhoneLoginBean> execute(String... args) {
        return null;
    }
}
