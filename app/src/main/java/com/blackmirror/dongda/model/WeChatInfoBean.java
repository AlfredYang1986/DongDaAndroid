package com.blackmirror.dongda.model;

/**
 * Created by Ruge on 2018-04-11 下午4:01
 */
public class WeChatInfoBean extends BaseBean {
    public String userId;//获取用户账号
    public String userName;//获取用户名字
    public String userIcon;//获取用户头像
    public String userGender; //获取用户性别，m = 男, f = 女，如果微信没有设置性别,默认返回null
    public String token;//授权Token
}
