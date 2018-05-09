package com.blackmirror.dongda.presenter;


import com.blackmirror.dongda.data.model.request.PhoneLoginRequestBean;
import com.blackmirror.dongda.data.model.request.SendSmsRequestBean;
import com.blackmirror.dongda.domain.model.PhoneLoginBean;
import com.blackmirror.dongda.domain.model.SendSmsBean;
import com.blackmirror.dongda.domain.repository.LoginRepository;
import com.blackmirror.dongda.ui.PhoneLoginContract;
import com.blackmirror.dongda.utils.LogUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PhoneLoginPresenter implements PhoneLoginContract.PhoneLoginPresenter {

    private final LoginRepository repository;
    private PhoneLoginContract.View view;

    public PhoneLoginPresenter(LoginRepository repository,PhoneLoginContract.View view) {
        this.repository = repository;
        this.view = view;
    }

    @Override
    public void login(PhoneLoginRequestBean bean) {
        repository.phoneLogin(bean.phone_number,bean.code,bean.reg_token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PhoneLoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PhoneLoginBean bean) {
                        LogUtils.d("phoneLogin success");
                        view.loginSuccess(bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d("phoneLogin error");
                        view.loginError(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void sendSms(SendSmsRequestBean bean) {
        repository.sendSms(bean.phone_number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SendSmsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SendSmsBean bean) {
                        LogUtils.d("sendSms success");
                        view.sendSmsSuccess(bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d("sendSms error");
                        view.loginError(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void setView(PhoneLoginContract.View view) {

    }

    @Override
    public void destroy() {

    }
}
