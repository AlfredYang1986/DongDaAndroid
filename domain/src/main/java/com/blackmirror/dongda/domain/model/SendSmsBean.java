package com.blackmirror.dongda.domain.model;

/**
 * Created by Ruge on 2018-04-19 下午6:09
 */
public class SendSmsBean extends BaseDataBean {

    public String status;//ok 成功 其他失败
    public String phone;
    public String reg_token;
    public int is_reg;

    @Override
    public String toString() {
        return "SendSmsBean{" +
                "status='" + status + '\'' +
                ", phone='" + phone + '\'' +
                ", reg_token='" + reg_token + '\'' +
                ", is_reg=" + is_reg +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", isSuccess=" + isSuccess +
                '}';
    }
}
