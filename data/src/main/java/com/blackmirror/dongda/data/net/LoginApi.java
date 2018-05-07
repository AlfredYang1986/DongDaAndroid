package com.blackmirror.dongda.data.net;

import com.blackmirror.dongda.data.AppConstant;
import com.blackmirror.dongda.data.model.request.PhoneLoginRequestBean;
import com.blackmirror.dongda.data.model.response.PhoneLoginResponseBean;

import io.reactivex.Observable;

/**
 * Created by Ruge on 2018-05-07 下午3:07
 */
public class LoginApi extends AYRemoteApi {
    public static Observable<PhoneLoginResponseBean> phoneLogin(String phone, String code, String reg_token){

        PhoneLoginRequestBean bean = new PhoneLoginRequestBean();
        String json="{\"phone\":\""+phone+"\",\"code\":\""+code+"\",\"reg_token\":\""+reg_token+"\"}";
        bean.json=json;
        bean.url= AppConstant.AUTH_SMS_CODE_URL;
        return execute(bean, PhoneLoginResponseBean.class);
    }
}
