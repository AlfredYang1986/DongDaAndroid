package com.blackmirror.dongda.model.serverbean;

import com.blackmirror.dongda.model.BaseServerBean;

import java.util.List;

public class WeChatUserInfoServerBean extends BaseServerBean {

    /**
     * openid : OPENID
     * nickname : NICKNAME
     * sex : 1
     * province : PROVINCE
     * city : CITY
     * country : COUNTRY
     * headimgurl : http://wx.qlogo
     * .cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0
     * privilege : ["PRIVILEGE1","PRIVILEGE2"]
     * unionid :  o6_bmasdasdsad6_2sgVt7hMZOPfL
     */

    public String openid;
    public String nickname;
    public int sex;
    public String province;
    public String city;
    public String country;
    public String headimgurl;
    public String unionid;
    public List<String> privilege;
    public String errcode;
    public String errmsg;
    public boolean isSuccess;

    @Override
    public String toString() {
        return "WeChatUserInfoServerBean{" +
                "openid='" + openid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex=" + sex +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", unionid='" + unionid + '\'' +
                ", privilege=" + privilege +
                ", errcode='" + errcode + '\'' +
                ", errmsg='" + errmsg + '\'' +
                ", isSuccess=" + isSuccess +
                '}';
    }
}
