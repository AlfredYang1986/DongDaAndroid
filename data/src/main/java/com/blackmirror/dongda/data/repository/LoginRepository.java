package com.blackmirror.dongda.data.repository;

import com.blackmirror.dongda.data.model.request.PhoneLoginRequestBean;
import com.blackmirror.dongda.data.model.request.SendSmsRequestBean;
import com.blackmirror.dongda.data.model.response.PhoneLoginResponseBean;
import com.blackmirror.dongda.data.model.response.SendSmsResponseBean;
import com.blackmirror.dongda.data.net.LoginApi;
import com.blackmirror.dongda.data.net.LoginCmd;
import com.blackmirror.dongda.data.net.SendSmsCmd;

import io.reactivex.Observable;

/**
 * Created by Ruge on 2018-05-04 下午1:50
 */
public class LoginRepository implements Repository {

    public Observable<PhoneLoginResponseBean> phoneLogin(PhoneLoginRequestBean bean){
        return LoginCmd.phoneLogin(bean);
    }

    public Observable<SendSmsResponseBean> sendSms(SendSmsRequestBean bean){
        return SendSmsCmd.sendSms(bean);
    }

    @Override
    public Observable<PhoneLoginResponseBean> phoneLogin(String phone, String code, String reg_token) {
        return LoginApi.phoneLogin(phone, code, reg_token);
    }
}
