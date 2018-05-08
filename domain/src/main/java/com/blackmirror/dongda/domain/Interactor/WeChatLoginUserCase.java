package com.blackmirror.dongda.domain.Interactor;

import com.blackmirror.dongda.domain.base.UserCase;
import com.blackmirror.dongda.domain.model.WeChatLoginBean;
import com.blackmirror.dongda.domain.repository.LoginRepository;

import io.reactivex.Observable;


/**
 * Created by Ruge on 2018-05-07 下午2:50
 */
public class WeChatLoginUserCase implements UserCase<WeChatLoginBean> {


    private final LoginRepository repository;

    public WeChatLoginUserCase(LoginRepository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<WeChatLoginBean> execute(String... args) {
        return null;
    }
}
