package com.blackmirror.dongda.data;

import com.blackmirror.dongda.model.request.PhoneLoginRequestBean;
import com.blackmirror.dongda.model.request.SendSmsRequestBean;
import com.blackmirror.dongda.model.response.PhoneLoginResponseBean;
import com.blackmirror.dongda.model.response.SendSmsResponseBean;

import io.reactivex.Observable;

/**
 * Created by Ruge on 2018-05-04 下午1:50
 */
public class LoginRepository extends BaseRepository {

    public Observable<PhoneLoginResponseBean> phoneLogin(PhoneLoginRequestBean bean){
        return LoginCmd.phoneLogin(bean);
    }

    public Observable<SendSmsResponseBean> sendSms(SendSmsRequestBean bean){
        return SendSmsCmd.sendSms(bean);
    }
}
