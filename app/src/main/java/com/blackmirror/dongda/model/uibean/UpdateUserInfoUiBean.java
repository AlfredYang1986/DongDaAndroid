package com.blackmirror.dongda.model.uibean;

import com.blackmirror.dongda.model.BaseUiBean;
import com.blackmirror.dongda.model.serverbean.UpdateUserInfoServerBean;

/**
 * Created by Ruge on 2018-04-19 下午6:58
 */
public class UpdateUserInfoUiBean extends BaseUiBean {

    public String screen_name;
    public String description;
    public int has_auth_phone;
    public String owner_name;
    public int is_service_provider;
    public String user_id;
    public String company;
    public String screen_photo;
    public long date;
    public String token;
    public String address;
    public String contact_no;
    public String social_id;

    public UpdateUserInfoUiBean(UpdateUserInfoServerBean bean) {
        if (bean!=null && "ok".equals(bean.status)){
            isSuccess=true;
            inData(bean);
        }else {
            if (bean!=null && bean.error!=null){
                code=bean.error.code;
                message=bean.error.message;
            }
        }
    }

    private void inData(UpdateUserInfoServerBean bean) {

    }
}
