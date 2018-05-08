package com.blackmirror.dongda.domain.model;

/**
 * Created by Ruge on 2018-04-19 下午6:09
 */
public class WeChatLoginBean extends BaseDataBean {

    public String auth_token;
    public String screen_name;
    public int has_auth_phone;
    public String current_device_type;
    public int is_service_provider;
    public String user_id;
    public String screen_photo;
    public String current_device_id;


    @Override
    public String toString() {
        return "PhoneLoginBean{" +
                "auth_token='" + auth_token + '\'' +
                ", screen_name='" + screen_name + '\'' +
                ", has_auth_phone=" + has_auth_phone +
                ", current_device_type='" + current_device_type + '\'' +
                ", is_service_provider=" + is_service_provider +
                ", user_id='" + user_id + '\'' +
                ", screen_photo='" + screen_photo + '\'' +
                ", current_device_id='" + current_device_id + '\'' +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", isSuccess=" + isSuccess +
                '}';
    }
}
