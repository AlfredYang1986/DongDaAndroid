package com.blackmirror.dongda.domain.Interactor;

import com.blackmirror.dongda.domain.model.PhoneLoginBean;
import com.blackmirror.dongda.domain.repository.LoginRepository;

import io.reactivex.Observable;


/**
 * Created by Ruge on 2018-05-07 下午2:50
 */
public class PhoneLoginUseCase extends LoginUseCase<PhoneLoginBean> {


    private final LoginRepository repository;

    public PhoneLoginUseCase(LoginRepository repository) {
        this.repository = repository;
    }

    public Observable<PhoneLoginBean> phoneLogin(String phone, String code, String reg_token) {
        return repository.phoneLogin(phone, code, reg_token);
    }

}
