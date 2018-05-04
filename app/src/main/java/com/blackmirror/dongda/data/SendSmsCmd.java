package com.blackmirror.dongda.data;

import com.blackmirror.dongda.model.request.SendSmsRequestBean;
import com.blackmirror.dongda.model.response.SendSmsResponseBean;
import com.blackmirror.dongda.utils.AppConstant;

import io.reactivex.Observable;

/**
 * Created by Ruge on 2018-05-04 下午1:52
 */
public class SendSmsCmd extends AYRemoteCommand2 {

    public static Observable<SendSmsResponseBean> sendSms(SendSmsRequestBean bean){
        String json="{\"phone\":\""+bean.phone_number+"\"}";
        bean.json=json;
        bean.url=AppConstant.SEND_SMS_CODE_URL;
        return execute(bean, SendSmsResponseBean.class);
    }

}
