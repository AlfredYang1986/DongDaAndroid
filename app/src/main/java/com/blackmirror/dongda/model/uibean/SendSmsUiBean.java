package com.blackmirror.dongda.model.uibean;

import com.blackmirror.dongda.model.serverbean.SendSmsServerBean;

/**
 * Created by Ruge on 2018-04-10 下午3:29
 */
public class SendSmsUiBean {
    public boolean isSuccess;
    public String status;//ok 成功 其他失败
    public String message;//错误信息
    public String phone;
    public String code;
    public String reg_token;
    public int is_reg;

    public SendSmsUiBean(SendSmsServerBean bean) {
        status=bean.status;
        if (bean != null && "ok".equals(bean.status)) {
            isSuccess = true;
            if ( bean.result != null && bean.result.reg != null) {
                phone=bean.result.reg.phone;
                code=bean.result.reg.code;
                reg_token=bean.result.reg.reg_token;
                is_reg=bean.result.reg.is_reg;
            }
        } else {
            isSuccess = false;
            if (bean!=null && bean.error!=null) {
                message = bean.error.message;
            }
        }

    }
}
