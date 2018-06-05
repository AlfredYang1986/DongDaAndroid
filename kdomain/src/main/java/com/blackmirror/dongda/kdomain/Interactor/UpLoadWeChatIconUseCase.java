package com.blackmirror.dongda.kdomain.Interactor;


import com.blackmirror.dongda.kdomain.model.UpLoadWeChatIconDomainBean;
import com.blackmirror.dongda.kdomain.repository.LoginRepository;

import io.reactivex.Observable;


/**
 * Created by Ruge on 2018-05-07 下午2:50
 */
public class UpLoadWeChatIconUseCase extends LoginUseCase<UpLoadWeChatIconDomainBean> {


    private final LoginRepository repository;

    public UpLoadWeChatIconUseCase(LoginRepository repository) {
        this.repository = repository;
    }


    public Observable<UpLoadWeChatIconDomainBean> upLoadWeChatIcon(String userIcon, String imgUUID) {
        return repository.upLoadWeChatIcon(userIcon, imgUUID);
    }
}
