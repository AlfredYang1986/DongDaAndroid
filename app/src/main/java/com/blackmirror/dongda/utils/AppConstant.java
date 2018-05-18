package com.blackmirror.dongda.utils;

import com.blackmirror.dongda.R;

public class AppConstant {

    public static final int PERMISSION_REQUEST = 10010;
    public static final int CHOOSE_PIC=10011;
    public static final int PICTURE_CUT=10012;
    public static final int TAKE_PHOTO=10013;
    public static final String DEFAULT_CHARSET_NAME = "utf-8";

    public static final int FROM_PHONE_INPUT=100;
    public static final int FROM_NAME_INPUT=101;


    public static final int CARE_MORE_REQUEST_CODE=100;
    public static final int ART_MORE_REQUEST_CODE=101;
    public static final int SPORT_MORE_REQUEST_CODE=102;
    public static final int SCIENCE_REQUEST_CODE=103;
    public static final int MY_LIKE_REQUEST_CODE =114;
    public static final int SERVICE_DETAIL_REQUEST_CODE =115;
    public static final int FEATURE_DETAIL_REQUEST_CODE =116;
    public static final int ABOUT_USER_REQUEST_CODE =117;
    public static final int EDIT_USER_REQUEST_CODE =118;

    public static final int NO_GPS_PERMISSION =12;

    public static final int IMAGE_TOKEN_ERROR=10;


    public static final int CONNECT_TIMEOUT=15;
    public static final int READ_TIMEOUT=15;
    public static final int WRITE_TIMEOUT=60;
    /**
     * 网络请求错误码
     */
    public static final int NET_UNKNOWN_ERROR=-9999;
    public static final int NET_WORK_UNAVAILABLE=10010;
    public static final int CONNECT_TIMEOUT_EXCEPTION=-100;
    public static final int SOCKET_TIMEOUT_EXCEPTION=-101;
    public static final int CONNECT_EXCEPTION=-102;
    public static final int OTHER_EXCEPTION=-103;


    public static final int HOME_ART_ADAPTER=1;//1 art 2 sport 3 science
    public static final int HOME_SPORT_ADAPTER=2;//1 art 2 sport 3 science
    public static final int HOME_SCIENCE_ADAPTER=3;//1 art 2 sport 3 science

    public static final int OPEN_STATUS=1;//1 展开 2 关闭 3 中间状态
    public static final int CLOSE_STATUS=2;//1 展开 2 关闭 3 中间状态
    public static final int OTHER_STATUS=3;//1 展开 2 关闭 3 中间状态

    public static final int ENROL_TIME_PAY_CODE=100;
    public static final int ENROL_MB_PAY_CODE=101;
    public static final int FLEXIBLE_PAY_CODE=102;
    public static final int FIXED_PAY_CODE=103;

    //设置
    public static final int NO_SERVICE_CODE=104;
    public static final int SHOW_ORDER_CODE=105;
    public static final int EDIT_USER_INFO_CODE=106;

    //我的收藏
    public static final int MY_LIKE_CODE=107;




    public static final int[] teacher_bg_res_id = {
            R.drawable.avatar_0,
            R.drawable.avatar_1,
            R.drawable.avatar_2,
            R.drawable.avatar_3,
            R.drawable.avatar_4,
            R.drawable.avatar_5,
            R.drawable.avatar_6,
            R.drawable.avatar_7,
            R.drawable.avatar_8,
            R.drawable.avatar_9
    };


    /**
     * url地址
     */
    public static final String BASE_URL="http://192.168.100.115:9000";

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
