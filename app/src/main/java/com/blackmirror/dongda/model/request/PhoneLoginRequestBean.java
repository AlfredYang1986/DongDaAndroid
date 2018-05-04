package com.blackmirror.dongda.model.request;

import com.blackmirror.dongda.model.base.BaseRequestBean;

/**
 * Created by Ruge on 2018-05-04 下午2:21
 */
public class PhoneLoginRequestBean extends BaseRequestBean {
    public String phone_number;
    public String code;
    public String reg_token;
}
