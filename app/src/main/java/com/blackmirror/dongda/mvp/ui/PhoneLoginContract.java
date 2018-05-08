package com.blackmirror.dongda.mvp.ui;

import com.blackmirror.dongda.model.request.PhoneLoginRequestBean;
import com.blackmirror.dongda.model.request.SendSmsRequestBean;

public class PhoneLoginContract {
    public interface View<T>{

        void sendSmsSuccess(T bean);

        void loginSuccess(T bean);

        void onError(T t);

    }

    public interface LoginPresenter extends BasePresenter<View> {
        void login(PhoneLoginRequestBean bean);

        void sendSms(SendSmsRequestBean bean);
    }
}
