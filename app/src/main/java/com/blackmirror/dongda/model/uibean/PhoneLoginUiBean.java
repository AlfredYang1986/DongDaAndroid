package com.blackmirror.dongda.model.uibean;

import com.blackmirror.dongda.model.BaseUiBean;
import com.blackmirror.dongda.model.serverbean.PhoneLoginServerBean;

/**
 * Created by Ruge on 2018-04-19 下午6:09
 */
public class PhoneLoginUiBean extends BaseUiBean {

    public String auth_token;
    public String screen_name;
    public int has_auth_phone;
    public String current_device_type;
    public int is_service_provider;
    public String user_id;
    public String screen_photo;
    public String current_device_id;

    public PhoneLoginUiBean(PhoneLoginServerBean bean) {
        if (bean != null && "ok".equals(bean.status)) {
            isSuccess = true;
            initData(bean);
        } else {
            if (bean != null && bean.error != null) {
                code = bean.error.code;
                message = bean.error.message;
            }
        }
    }

    private void initData(PhoneLoginServerBean bean) {
        if (bean.result != null) {
            auth_token = bean.result.auth_token;
        }
        if (bean.result != null && bean.result.user != null) {
            screen_name=bean.result.user.screen_name;
            has_auth_phone=bean.result.user.has_auth_phone;
            current_device_type=bean.result.user.current_device_type;
            is_service_provider=bean.result.user.is_service_provider;
            user_id=bean.result.user.user_id;
            screen_photo=bean.result.user.screen_photo;
            current_device_id=bean.result.user.current_device_id;
        }
    }
}
