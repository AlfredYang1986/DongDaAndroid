package com.blackmirror.dongda.data.net;

import com.blackmirror.dongda.data.DataConstant;
import com.blackmirror.dongda.data.model.response.SendSmsResponseBean;
import com.blackmirror.dongda.data.model.request.SendSmsRequestBean;

import io.reactivex.Observable;

/**
 * Created by Ruge on 2018-05-04 下午1:52
 */
public class SendSmsCmd extends AYRemoteApi {

    public static Observable<SendSmsResponseBean> sendSms(SendSmsRequestBean bean){
        String json="{\"phone\":\""+bean.phone_number+"\"}";
        bean.json=json;
        bean.url= DataConstant.SEND_SMS_CODE_URL;
        return execute(bean, SendSmsResponseBean.class);
    }

}
