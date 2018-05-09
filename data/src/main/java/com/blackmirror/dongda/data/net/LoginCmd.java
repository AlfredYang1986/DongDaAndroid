package com.blackmirror.dongda.data.net;


import com.blackmirror.dongda.data.DataConstant;
import com.blackmirror.dongda.data.model.request.PhoneLoginRequestBean;
import com.blackmirror.dongda.data.model.response.PhoneLoginResponseBean;

import io.reactivex.Observable;

/**
 * Created by Ruge on 2018-05-04 下午1:52
 */
public class LoginCmd extends AYRemoteApi {

    public static Observable<PhoneLoginResponseBean> phoneLogin(PhoneLoginRequestBean bean){

        String json="{\"phone\":\""+bean.phone_number+"\",\"code\":\""+bean.code+"\",\"reg_token\":\""+bean.reg_token+"\"}";
        bean.json=json;
        bean.url= DataConstant.AUTH_SMS_CODE_URL;
        return execute(bean, PhoneLoginResponseBean.class);
    }

}
