package com.blackmirror.dongda.data.net;

import com.blackmirror.dongda.data.DataConstant;
import com.blackmirror.dongda.data.model.request.PhoneLoginRequestBean;
import com.blackmirror.dongda.data.model.request.SendSmsRequestBean;
import com.blackmirror.dongda.data.model.request.WeChatLoginRequestBean;
import com.blackmirror.dongda.data.model.response.PhoneLoginResponseBean;
import com.blackmirror.dongda.data.model.response.SendSmsResponseBean;
import com.blackmirror.dongda.data.model.response.WeChatLoginResponseBean;

import io.reactivex.Observable;

/**
 * Created by Ruge on 2018-05-07 下午3:07
 */
public class LoginApi extends AYRemoteApi {

    public static Observable<SendSmsResponseBean> sendSms(String phone){
        SendSmsRequestBean bean = new SendSmsRequestBean();
        bean.phone_number = phone;
        String json="{\"phone\":\""+phone+"\"}";
        bean.json=json;
        bean.url= DataConstant.SEND_SMS_CODE_URL;
        return execute(bean, SendSmsResponseBean.class);
    }

    public static Observable<PhoneLoginResponseBean> phoneLogin(String phone, String code, String reg_token){

        PhoneLoginRequestBean bean = new PhoneLoginRequestBean();
        String json="{\"phone\":\""+phone+"\",\"code\":\""+code+"\",\"reg_token\":\""+reg_token+"\"}";
        bean.json=json;
        bean.url= DataConstant.AUTH_SMS_CODE_URL;
        return execute(bean, PhoneLoginResponseBean.class);
    }


    public static Observable<WeChatLoginResponseBean> wechatLogin(String provide_uid, String provide_token, String provide_screen_name,
                                                                  String provide_name, String provide_screen_photo){
        WeChatLoginRequestBean bean = new WeChatLoginRequestBean();
        String json="{\"third\":{\"provide_uid\":\""+provide_uid+"\",\"provide_token\":\""+provide_token+"\",\"provide_screen_name\":\""+provide_screen_name+"\"," +
                "\"provide_name\":\""+provide_name+"\",\"provide_screen_photo\":\""+provide_screen_photo+"\"}}";
        bean.json=json;
        bean.url= DataConstant.WECHAT_LOGIN_URL;
        return execute(bean, WeChatLoginResponseBean.class);
    }

}
