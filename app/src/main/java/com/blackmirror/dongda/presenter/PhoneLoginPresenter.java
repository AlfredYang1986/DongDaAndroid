package com.blackmirror.dongda.presenter;


import com.blackmirror.dongda.data.model.request.PhoneLoginRequestBean;
import com.blackmirror.dongda.data.model.request.SendSmsRequestBean;
import com.blackmirror.dongda.domain.Interactor.PhoneLoginUseCase;
import com.blackmirror.dongda.domain.Interactor.SendSmsUseCase;
import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.domain.model.PhoneLoginBean;
import com.blackmirror.dongda.domain.model.SendSmsBean;
import com.blackmirror.dongda.ui.PhoneLoginContract;
import com.blackmirror.dongda.utils.AppConstant;
import com.blackmirror.dongda.utils.LogUtils;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PhoneLoginPresenter implements PhoneLoginContract.PhoneLoginPresenter {

    @Inject
    SendSmsUseCase smsUseCase;
    @Inject
    PhoneLoginUseCase phoneUseCase;

    private PhoneLoginContract.View view;

    @Inject
    public PhoneLoginPresenter(PhoneLoginContract.View view) {
        this.view = view;
    }

    @Override
    public void login(PhoneLoginRequestBean bean) {
        phoneUseCase.phoneLogin(bean.phone_number,bean.code,bean.reg_token)
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
                        LogUtils.e(PhoneLoginPresenter.class,e);
                        if (view == null){
                            return;
                        }
                        BaseDataBean bean = new BaseDataBean();
                        bean.code = AppConstant.NET_UNKNOWN_ERROR;
                        bean.message = e.getMessage();
                        view.onError(bean);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void sendSms(SendSmsRequestBean bean) {
        smsUseCase.sendSms(bean.phone_number)
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
                        LogUtils.e(PhoneLoginPresenter.class,e);
                        if (view == null){
                            return;
                        }
                        BaseDataBean bean = new BaseDataBean();
                        bean.code = AppConstant.NET_UNKNOWN_ERROR;
                        bean.message = e.getMessage();
                        view.onError(bean);
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
