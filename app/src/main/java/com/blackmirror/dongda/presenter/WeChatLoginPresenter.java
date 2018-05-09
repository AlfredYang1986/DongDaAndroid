package com.blackmirror.dongda.presenter;


import com.blackmirror.dongda.domain.Interactor.WeChatLoginUseCase;
import com.blackmirror.dongda.domain.model.WeChatLoginBean;
import com.blackmirror.dongda.ui.WeChatLoginContract;
import com.blackmirror.dongda.utils.AYPrefUtils;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeChatLoginPresenter implements WeChatLoginContract.WeChatLoginPresenter {

    @Inject
    WeChatLoginUseCase weChat;

    private WeChatLoginContract.View view;

    @Inject
    public WeChatLoginPresenter(WeChatLoginContract.View view) {
        this.view = view;
    }

    @Override
    public void weChatLogin(String provide_uid, String provide_token, String provide_screen_name,
                            String provide_name, String provide_screen_photo) {
        weChat.weChatLogin(provide_uid, provide_token, provide_screen_name, provide_name, provide_screen_photo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeChatLoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WeChatLoginBean bean) {
                        if (bean.isSuccess){
                            AYPrefUtils.setUserId(bean.user_id);
                            AYPrefUtils.setAuthToken(bean.auth_token);
                            view.weChatLoginSuccess(bean);
                        }else {
                            view.weChatLoginError(bean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.weChatLoginError(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void setView(WeChatLoginContract.View view) {

    }

    @Override
    public void destroy() {

    }
}
