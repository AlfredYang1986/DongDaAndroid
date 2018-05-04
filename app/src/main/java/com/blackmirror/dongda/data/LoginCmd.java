package com.blackmirror.dongda.data;

import com.blackmirror.dongda.model.request.PhoneLoginRequestBean;
import com.blackmirror.dongda.model.response.PhoneLoginResponseBean;
import com.blackmirror.dongda.utils.AppConstant;

import io.reactivex.Observable;

/**
 * Created by Ruge on 2018-05-04 下午1:52
 */
public class LoginCmd extends AYRemoteCommand2 {

    public static Observable<PhoneLoginResponseBean> phoneLogin(PhoneLoginRequestBean bean){

        String json="{\"phone\":\""+bean.phone_number+"\",\"code\":\""+bean.code+"\",\"reg_token\":\""+bean.reg_token+"\"}";
        bean.json=json;
        bean.url=AppConstant.AUTH_SMS_CODE_URL;
        return execute(bean, PhoneLoginResponseBean.class);
    }

}
