package com.blackmirror.dongda.ui;


import com.blackmirror.dongda.data.model.request.PhoneLoginRequestBean;
import com.blackmirror.dongda.data.model.request.SendSmsRequestBean;
import com.blackmirror.dongda.domain.model.PhoneLoginBean;
import com.blackmirror.dongda.domain.model.SendSmsBean;

public class PhoneLoginContract {
    public interface View{
        void loginSuccess(PhoneLoginBean bean);

        void loginError(PhoneLoginBean bean);

        void sendSmsSuccess(SendSmsBean bean);

        void sendSmsError(SendSmsBean bean);
    }

    public interface PhoneLoginPresenter extends BasePresenter<View> {
        void login(PhoneLoginRequestBean bean);

        void sendSms(SendSmsRequestBean bean);
    }
}
