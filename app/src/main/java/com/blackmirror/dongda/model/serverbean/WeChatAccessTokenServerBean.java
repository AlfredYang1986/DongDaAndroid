package com.blackmirror.dongda.model.serverbean;

import com.blackmirror.dongda.model.BaseServerBean;

public class WeChatAccessTokenServerBean extends BaseServerBean {

    /**
     * access_token : ACCESS_TOKEN
     * expires_in : 7200
     * refresh_token : REFRESH_TOKEN
     * openid : OPENID
     * scope : SCOPE
     * unionid : o6_bmasdasdsad6_2sgVt7hMZOPfL
     */
    public boolean isSuccess;

    public String access_token;
    public int expires_in;//有效期
    public String refresh_token;//刷新Token
    public String openid;//授权用户唯一标识
    public String scope;
    public String unionid;
    public String errcode;
    public String errmsg;

    @Override
    public String toString() {
        return "WeChatAccessTokenServerBean{" +
                "isSuccess=" + isSuccess +
                ", access_token='" + access_token + '\'' +
                ", expires_in=" + expires_in +
                ", refresh_token='" + refresh_token + '\'' +
                ", openid='" + openid + '\'' +
                ", scope='" + scope + '\'' +
                ", unionid='" + unionid + '\'' +
                ", errcode='" + errcode + '\'' +
                ", errmsg='" + errmsg + '\'' +
                '}';
    }
}
