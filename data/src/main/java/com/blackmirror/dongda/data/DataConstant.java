package com.blackmirror.dongda.data;

public class DataConstant {

    /**
     * 网络请求错误码
     */
    public static final int NET_UNKNOWN_ERROR=-9999;
    public static final int NET_WORK_UNAVAILABLE=10010;
    public static final int CONNECT_TIMEOUT_EXCEPTION=-100;
    public static final int SOCKET_TIMEOUT_EXCEPTION=-101;
    public static final int CONNECT_EXCEPTION=-102;
    public static final int OTHER_EXCEPTION=-103;
    public static final int UPLOAD_WECHAT_ERROR=-104;


    /**
     * url地址
      */
    public static final String BASE_URL="http://192.168.100.115:9000";
//    public static final String BASE_URL="http://altlys.com:9000";

    //收藏
    public static final String LIKE_PUSH_URL=BASE_URL+"/al/collections/push";
    //取消收藏
    public static final String LIKE_POP_URL=BASE_URL+"/al/collections/pop";
    //收藏列表
    public static final String LIKE_QUERY_LIST_URL=BASE_URL+"/al/user/collected/services";
    //定位 附近的服务
    public static final String NEAR_SERVICE_URL=BASE_URL+"/al/location/near";
    //发送短信验证码
    public static final String SEND_SMS_CODE_URL=BASE_URL+"/al/code/send";
    //手机短信登录
    public static final String AUTH_SMS_CODE_URL=BASE_URL+"/al/auth/code";
    //更新用户信息
    public static final String UPDATE_USER_INFO_URL=BASE_URL+"/al/profile/update";
    //查询用户信息
    public static final String QUERY_USER_INFO_URL=BASE_URL+"/al/profile/query";
    //获取OSS信息
    public static final String OSS_INFO_URL=BASE_URL+"/al/oss/gst";
    //微信登陆
    public static final String WECHAT_LOGIN_URL=BASE_URL+"/al/auth/sns";
    //主页信息
    public static final String HOME_PAGE_URL=BASE_URL+"/al/homepage/service";
    //更多相关
    public static final String SERVICE_MORE_URL=BASE_URL+"/al/service/search";
    //服务详细信息
    public static final String SERVICE_DETAIL_URL=BASE_URL+"/al/service/detail";
    //申请成为服务者
    public static final String APPLY_SERVICE_URL=BASE_URL+"/al/apply/push";
    //遍历品牌下的所有位置
    public static final String BRAND_ALL_LOC_URL=BASE_URL+"/al/brand/locations";
    //遍历位置下的所有服务
    public static final String LOC_ALL_SERVICE_URL=BASE_URL+"/al/location/lst/service";
    //发布招生
    public static final String ENROL_URL=BASE_URL+"/al/recruit/push";



}
