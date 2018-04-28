package com.blackmirror.dongda.model.uibean;

import com.blackmirror.dongda.model.BaseUiBean;
import com.blackmirror.dongda.model.serverbean.QueryUserInfoServerBean;

/**
 * Created by Ruge on 2018-04-28 上午11:36
 */
public class QueryUserInfoUiBean extends BaseUiBean {

    public String screen_name;
    public String description;
    public int has_auth_phone;
    public String owner_name;
    public int is_service_provider;
    public String user_id;
    public String company;
    public String screen_photo;
    public long date;
    public String address;
    public String contact_no;
    public String social_id;

    public QueryUserInfoUiBean(QueryUserInfoServerBean bean) {
        if (bean != null && "ok".equals(bean.status)) {
            isSuccess = true;
            if (bean.result != null && bean.result.profile != null) {
                screen_name = bean.result.profile.screen_name;
                description = bean.result.profile.description;
                has_auth_phone = bean.result.profile.has_auth_phone;
                owner_name = bean.result.profile.owner_name;
                is_service_provider = bean.result.profile.is_service_provider;
                user_id = bean.result.profile.user_id;
                company = bean.result.profile.company;
                screen_photo = bean.result.profile.screen_photo;
                date = bean.result.profile.date;
                address = bean.result.profile.address;
                contact_no = bean.result.profile.contact_no;
                social_id = bean.result.profile.social_id;
            }
        } else {
            if (bean != null && bean.error != null) {
                code = bean.error.code;
                message = bean.error.message;
            }
        }
    }
}
