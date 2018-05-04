package com.blackmirror.dongda.domain.features.login;

import com.blackmirror.dongda.domain.base.BaseFacade;
import com.blackmirror.dongda.model.request.PhoneLoginRequestBean;
import com.blackmirror.dongda.model.request.SendSmsRequestBean;
import com.blackmirror.dongda.model.response.uibean.PhoneLoginBean;
import com.blackmirror.dongda.model.response.uibean.SendSmsBean;

/**
 * Created by Ruge on 2018-05-04 下午5:58
 */
public class LoginContract {
    public interface View{
        void loginSuccess(PhoneLoginBean bean);

        void loginError(PhoneLoginBean bean);

        void sendSmsSuccess(SendSmsBean bean);

        void sendSmsError(SendSmsBean bean);
    }

    public interface LoginCommonFacade extends BaseFacade<View>{
        void login(PhoneLoginRequestBean bean);

        void sendSms(SendSmsRequestBean bean);
    }
}
