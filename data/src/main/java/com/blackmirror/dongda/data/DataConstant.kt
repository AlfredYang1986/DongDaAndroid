package com.blackmirror.dongda.data

/**
 * Create By Ruge at 2018-07-02
 */
/**
 * 网络请求错误码
 */
val NET_UNKNOWN_ERROR = -9999
val NET_WORK_UNAVAILABLE = 10010
val CONNECT_TIMEOUT_EXCEPTION = -100
val SOCKET_TIMEOUT_EXCEPTION = -101
val CONNECT_EXCEPTION = -102
val OTHER_EXCEPTION = -103
val UPLOAD_WECHAT_ERROR = -104


/**
 * url地址
 */
//val BASE_URL = "http://192.168.100.115:9000"
val BASE_URL = "http://altlys.com:9000"

//收藏
val LIKE_PUSH_URL = "$BASE_URL/al/collections/push"
//取消收藏
val LIKE_POP_URL = "$BASE_URL/al/collections/pop"
//收藏列表
val LIKE_QUERY_LIST_URL = "$BASE_URL/al/user/collected/services"
//定位 附近的服务
val NEAR_SERVICE_URL = "$BASE_URL/al/location/near"
//发送短信验证码
val SEND_SMS_CODE_URL = "$BASE_URL/al/code/send"
//手机短信登录
val AUTH_SMS_CODE_URL = "$BASE_URL/al/auth/code"
//更新用户信息
val UPDATE_USER_INFO_URL = "$BASE_URL/al/profile/update"
//查询用户信息
val QUERY_USER_INFO_URL = "$BASE_URL/al/profile/query"
//获取OSS信息
val OSS_INFO_URL = "$BASE_URL/al/oss/gst"
//微信登陆
val WECHAT_LOGIN_URL = "$BASE_URL/al/auth/sns"
//主页信息
val HOME_PAGE_URL = "$BASE_URL/al/homepage/service"
//更多相关
val SERVICE_MORE_URL = "$BASE_URL/al/service/search"
//服务详细信息
val SERVICE_DETAIL_URL = "$BASE_URL/al/service/detail"
//申请成为服务者
val APPLY_SERVICE_URL = "$BASE_URL/al/apply/push"
//遍历品牌下的所有位置
val BRAND_ALL_LOC_URL = "$BASE_URL/al/brand/locations"
//遍历位置下的所有服务
val LOC_ALL_SERVICE_URL = "$BASE_URL/al/location/lst/service"
//发布招生
val ENROL_URL = "$BASE_URL/al/recruit/push"
