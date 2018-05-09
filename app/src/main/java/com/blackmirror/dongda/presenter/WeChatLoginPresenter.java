package com.blackmirror.dongda.presenter;


import com.blackmirror.dongda.data.model.request.WeChatLoginRequestBean;
import com.blackmirror.dongda.domain.model.WeChatLoginBean;
import com.blackmirror.dongda.domain.repository.LoginRepository;
import com.blackmirror.dongda.ui.WeChatLoginContract;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeChatLoginPresenter implements WeChatLoginContract.WeChatLoginPresenter {

    private final LoginRepository repository;
    private WeChatLoginContract.View view;

    @Inject
    public WeChatLoginPresenter(LoginRepository repository, WeChatLoginContract.View view) {
        this.repository = repository;
        this.view = view;
    }

    @Override
    public void weChatLogin(WeChatLoginRequestBean bean) {
        repository.weChatLogin(bean.provide_uid,bean.provide_token,bean.provide_screen_name,bean.provide_name,bean.provide_screen_photo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeChatLoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WeChatLoginBean bean) {
                        view.weChatLoginSuccess(bean);
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
