package com.blackmirror.dongda.data.net

import android.text.TextUtils
import com.blackmirror.dongda.data.DataConstant
import com.blackmirror.dongda.data.model.db.UserInfoDbBean
import com.blackmirror.dongda.data.model.request.UpDateBean
import com.blackmirror.dongda.data.model.request.UpdateUserInfoRequestBean
import com.blackmirror.dongda.data.model.request.UploadImageRequestBean
import com.blackmirror.dongda.data.model.response.UpLoadImgResponseBean
import com.blackmirror.dongda.data.model.response.UpdateUserInfoResponseBean
import com.blackmirror.dongda.data.repository.DbRepository
import com.blackmirror.dongda.domain.model.UpdateUserInfoBean
import com.blackmirror.dongda.utils.AYPrefUtils
import io.reactivex.Observable

/**
 * Created by xcx on 2018/6/11.
 */
/**
 * 分两种情况：需要上传图片和不需要上传图片
 * 1 需要上传图片：获取OSS信息(如果需要)->上传图片到OSS服务器->修改用户信息->修改数据库
 * 2 不需要上传图片：获取OSS信息(如果需要)->修改用户信息->修改数据库
 *
 * @param bean
 */
fun updateUserInfo2(bean: UpDateBean): Observable<UpdateUserInfoBean> {
    if (!TextUtils.isEmpty(bean.imgUUID)) {//需要上传图片
        return updateUserInfoWithPhoto2(bean)
    } else {//不需要
        return updateUserInfoWithOutPhoto2(bean)
    }
}

private fun updateUserInfoWithPhoto2(requestBean: UpDateBean): Observable<UpdateUserInfoBean> {
    val bean = UploadImageRequestBean()
    bean.json = requestBean.json
    bean.imgUUID = requestBean.imgUUID
    bean.url = DataConstant.UPDATE_USER_INFO_URL
    return execute3(bean, UpLoadImgResponseBean::class.java)
            .flatMap {
                val infoBean = UpdateUserInfoBean()
                //修改用户信息
                if ("ok" == it.status) {
                    val b = UpdateUserInfoRequestBean()
                    b.json = requestBean.json
                    b.imgUUID = requestBean.imgUUID
                    b.url = DataConstant.UPDATE_USER_INFO_URL
                    execute(b, UpdateUserInfoResponseBean::class.java)
                            .map { bean ->
                                val infoBean = UpdateUserInfoBean()
                                trans2UpdateUserInfoBean2(bean, infoBean)

                                val dbBean = UserInfoDbBean()
                                dbBean.is_current = 1//目前没什么卵用
                                dbBean.screen_name = infoBean.screen_name
                                dbBean.screen_photo = infoBean.screen_photo
                                dbBean.user_id = infoBean.user_id
                                dbBean.auth_token = AYPrefUtils.getAuthToken()
                                DbRepository.updateProfile(dbBean)
                                infoBean
                            }
                } else {

                    infoBean.code = it.error?.code ?: DataConstant.NET_UNKNOWN_ERROR
                    infoBean.message = it.error?.message ?: ""

                    Observable.just(infoBean)
                }
            }
}

private fun updateUserInfoWithOutPhoto2(requestBean: UpDateBean): Observable<UpdateUserInfoBean> {
    val b = UpdateUserInfoRequestBean()
    b.json = requestBean.json
    b.url = DataConstant.UPDATE_USER_INFO_URL

    return getOssInfo2()
            .flatMap({
                val infoBean = UpdateUserInfoBean()
                if ("ok" == it.status) {
                    execute(b, UpdateUserInfoResponseBean::class.java)
                            .map { bean ->
                                trans2UpdateUserInfoBean2(bean, infoBean)

                                val dbBean = UserInfoDbBean()
                                dbBean.is_current = 1//目前没什么卵用
                                dbBean.screen_name = infoBean.screen_name
                                dbBean.screen_photo = infoBean.screen_photo
                                dbBean.user_id = infoBean.user_id
                                dbBean.auth_token = AYPrefUtils.getAuthToken()
                                DbRepository.updateProfile(dbBean)
                                infoBean
                            }
                } else {

                    infoBean.code = it.error?.code ?: DataConstant.NET_UNKNOWN_ERROR
                    infoBean.message = it.error?.message ?: ""
                    Observable.just(infoBean)
                }
            })

}

private fun trans2UpdateUserInfoBean2(bean: UpdateUserInfoResponseBean?, infoBean: UpdateUserInfoBean) {

    infoBean.isSuccess = bean != null && "ok" == bean.status

    bean?.result?.profile?.apply {
        infoBean.screen_name = screen_name
        infoBean.description = description
        infoBean.has_auth_phone = has_auth_phone
        infoBean.owner_name = owner_name
        infoBean.is_service_provider = is_service_provider
        infoBean.user_id = user_id
        infoBean.company = company
        infoBean.screen_photo = screen_photo
        infoBean.date = date
        infoBean.token = token
        infoBean.address = address
        infoBean.contact_no = contact_no
        infoBean.social_id = social_id
    }

    infoBean.code = bean?.error?.code?:DataConstant.NET_UNKNOWN_ERROR
    infoBean.message = bean?.error?.message?:""
}