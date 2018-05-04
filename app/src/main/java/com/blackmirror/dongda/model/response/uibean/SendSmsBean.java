package com.blackmirror.dongda.model.response.uibean;

import com.blackmirror.dongda.model.BaseUiBean;

/**
 * Created by Ruge on 2018-04-19 下午6:09
 */
public class SendSmsBean extends BaseUiBean {

    public String status;//ok 成功 其他失败
    public String phone;
    public String reg_token;
    public int is_reg;

}
