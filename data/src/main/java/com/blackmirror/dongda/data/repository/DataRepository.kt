package com.blackmirror.dongda.data.repository

import com.blackmirror.dongda.data.model.response.*
import io.reactivex.Observable

/**
 * Create By Ruge at 2018-06-11
 */

fun getSMS(phone: String, sendSms: (String) -> Observable<SendSmsResponseBean>): Observable<SendSmsResponseBean> {
    return sendSms(phone)
}

fun phoneLogin(phone: String, code: String, reg_token: String, login: (String, String, String) -> Observable<PhoneLoginResponseBean>): Observable<PhoneLoginResponseBean> {
    return login(phone, code, reg_token)
}

fun weChatLogin(provide_uid: String, provide_token: String, provide_screen_name: String,
                provide_name: String, provide_screen_photo: String, wlogin: (String, String, String, String, String) -> Observable<WeChatLoginResponseBean>): Observable<WeChatLoginResponseBean> {
    return wlogin(provide_uid, provide_token, provide_screen_name, provide_name, provide_screen_photo)
}

fun upLoadWeChatIcon(userIcon: String, imgUUID: String, upload: (String, String) -> Observable<UpLoadImgResponseBean>): Observable<UpLoadImgResponseBean> {
    return upload(userIcon, imgUUID)
}

fun searchHomeData(action: () -> Observable<SearchServiceResponseBean>): Observable<SearchServiceResponseBean> {
    return action()
}

fun likePush2Server(service_id: String, like: (String) -> Observable<LikePushResponseBean>): Observable<LikePushResponseBean> {
    return like(service_id)
}

fun likePop2Server(service_id: String, pop: (String) -> Observable<LikePopResponseBean>): Observable<LikePopResponseBean> {
    return pop(service_id)
}

/**
 * 获取收藏列表
 * @return
 */
fun getMyLikeData(action: () -> Observable<LikeResponseBean>): Observable<LikeResponseBean> {
    return action()
}

fun queryUserInfo(query: () -> Observable<UserInfoResponseBean>): Observable<UserInfoResponseBean> {
    return query()
}

fun applyService(brand_name: String, name: String, category: String, phone: String, city: String,
                 apply: (String, String, String, String, String) -> Observable<ApplyServiceResponseBean>): Observable<ApplyServiceResponseBean> {
    return apply(brand_name, name, category, phone, city)
}

/**
 * 详细信息
 * @return
 */
fun getDetailInfo(service_id: String, info: (String) -> Observable<DetailInfoResponseBean>): Observable<DetailInfoResponseBean> {
    return info(service_id)
}

/**
 * 遍历品牌下的所有位置
 * @return
 */
fun getBrandAllLocation(brand_id: String, addr: (String) -> Observable<BrandAllLocResponseBean>): Observable<BrandAllLocResponseBean> {
    return addr(brand_id)
}

/**
 * 遍历品牌下的所有位置
 * @return
 */
fun getLocAllService(json: String, locations: String, getLoc: (String, String) -> Observable<LocAllServiceResponseBean>): Observable<LocAllServiceResponseBean> {
    return getLoc(json, locations)
}

/**
 * 发布招生
 * @return
 */
fun enrolStudent(json: String, enrol: (String) -> Observable<EnrolResponseBean>): Observable<EnrolResponseBean> {
    return enrol(json)
}

fun getServiceMoreData(skipCount: Int, takeCount: Int, serviceType: String, more: (Int, Int, String) -> Observable<CareMoreResponseBean>): Observable<CareMoreResponseBean> {
    return more(skipCount, takeCount, serviceType)
}

/**
 * 获取附近的服务
 * @return
 */
fun getNearService(latitude: Double, longitude: Double, near: (Double, Double) -> Observable<NearServiceResponseBean>): Observable<NearServiceResponseBean> {
    return near(latitude, longitude)
}

/**
 * 分两种情况：需要上传图片和不需要上传图片
 * 1 需要上传图片：获取OSS信息(如果需要)->上传图片到OSS服务器->修改用户信息->修改数据库
 * 2 不需要上传图片：获取OSS信息(如果需要)->修改用户信息->修改数据库
 *
 * @param bean
 */
fun <T, R> updateUserInfo(receiver: T, block: T.() -> R): R {
    return receiver.block()
}






