package com.blackmirror.dongda.kdomain.Interactor;


import com.blackmirror.dongda.kdomain.model.WeChatLoginBean;
import com.blackmirror.dongda.kdomain.repository.LoginRepository;

import io.reactivex.Observable;


/**
 * Created by Ruge on 2018-05-07 下午2:50
 */
public class WeChatLoginUseCase extends LoginUseCase<WeChatLoginBean> {


    private final LoginRepository repository;

    public WeChatLoginUseCase(LoginRepository repository) {
        this.repository = repository;
    }


    public Observable<WeChatLoginBean> weChatLogin(String provide_uid, String provide_token, String provide_screen_name,
                                                   String provide_name, String provide_screen_photo) {
        return repository.weChatLogin(provide_uid, provide_token, provide_screen_name, provide_name, provide_screen_photo);
    }
}
