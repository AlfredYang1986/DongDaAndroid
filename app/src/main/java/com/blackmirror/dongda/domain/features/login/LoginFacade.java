package com.blackmirror.dongda.domain.features.login;

import com.blackmirror.dongda.domain.Interactor.LoginTask;
import com.blackmirror.dongda.domain.Interactor.SendSmsTask;
import com.blackmirror.dongda.model.request.PhoneLoginRequestBean;
import com.blackmirror.dongda.model.request.SendSmsRequestBean;
import com.blackmirror.dongda.model.response.uibean.PhoneLoginBean;
import com.blackmirror.dongda.model.response.uibean.SendSmsBean;

/**
 * Created by Ruge on 2018-05-04 下午1:32
 */
public class LoginFacade implements LoginContract.LoginCommonFacade{


    protected LoginContract.View view;


    @Override
    public void setView(LoginContract.View view) {
        this.view=view;
    }

    @Override
    public void destroy() {

    }


    @Override
    public void login(PhoneLoginRequestBean bean) {
        LoginTask task = new LoginTask();
        task.executeTask(bean);
        task.setLoginCallback(new LoginTask.LoginCallback() {
            @Override
            public void onLoginSuccess(PhoneLoginBean response) {
                view.loginSuccess(response);
            }

            @Override
            public void onLoginError(PhoneLoginBean response) {
                view.loginError(response);
            }
        });
    }

    @Override
    public void sendSms(SendSmsRequestBean bean) {
        SendSmsTask task = new SendSmsTask();
        task.executeTask(bean);
        task.setSendSmsCallback(new SendSmsTask.SendSmsCallback() {
            @Override
            public void onSendSuccess(SendSmsBean response) {
                view.sendSmsSuccess(response);
            }

            @Override
            public void onSendError(SendSmsBean response) {
                view.sendSmsError(response);
            }
        });
    }
}
