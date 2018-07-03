package com.blackmirror.dongda.data.net

import android.text.TextUtils
import com.blackmirror.dongda.data.NET_UNKNOWN_ERROR
import com.blackmirror.dongda.data.UPDATE_USER_INFO_URL
import com.blackmirror.dongda.data.model.request.UpDateBean
import com.blackmirror.dongda.data.model.request.UpdateUserInfoRequestBean
import com.blackmirror.dongda.data.model.request.UploadImageRequestBean
import com.blackmirror.dongda.data.model.response.BaseResponseBean
import com.blackmirror.dongda.data.model.response.UpLoadImgResponseBean
import com.blackmirror.dongda.data.model.response.UpdateUserInfoResponseBean
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
fun updateUserInfo(bean: UpDateBean): Observable<UpdateUserInfoResponseBean> {
    if (!TextUtils.isEmpty(bean.imgUUID)) {//需要上传图片
        return updateUserInfoWithPhoto(bean)
    } else {//不需要
        return updateUserInfoWithOutPhoto(bean)
    }


}

fun updateUserInfoWithPhoto(requestBean: UpDateBean): Observable<UpdateUserInfoResponseBean> {
    val bean = UploadImageRequestBean()
    bean.json = requestBean.json
    bean.imgUUID = requestBean.imgUUID
    bean.url = UPDATE_USER_INFO_URL
    return getOssInfo().map {
        val infoBean = UpLoadImgResponseBean()
        if ("ok" == it.status) {
            return@map executeUpload(bean)
        } else {
            infoBean.error = BaseResponseBean.ErrorBean()
            infoBean.error?.code = it.error?.code ?: NET_UNKNOWN_ERROR
            infoBean.error?.message = it.error?.message ?: ""
            return@map infoBean
        }
    }.flatMap {
        val infoBean = UpdateUserInfoResponseBean()
        //修改用户信息
        if ("ok" == it.status) {
            val b = UpdateUserInfoRequestBean()
            b.json = requestBean.json
            b.imgUUID = requestBean.imgUUID
            b.url = UPDATE_USER_INFO_URL
            return@flatMap execute(b, UpdateUserInfoResponseBean::class.java)

        } else {
            infoBean.error = BaseResponseBean.ErrorBean()
            infoBean.error?.code = it.error?.code ?: NET_UNKNOWN_ERROR
            infoBean.error?.message = it.error?.message ?: ""

            return@flatMap Observable.just(infoBean)
        }
    }
}

fun updateUserInfoWithOutPhoto(requestBean: UpDateBean): Observable<UpdateUserInfoResponseBean> {
    val b = UpdateUserInfoRequestBean()
    b.json = requestBean.json
    b.url = UPDATE_USER_INFO_URL

    return getOssInfo()
            .flatMap{
                val infoBean = UpdateUserInfoResponseBean()
                if ("ok" == it.status) {
                    return@flatMap execute(b, UpdateUserInfoResponseBean::class.java)
                } else {
                    infoBean.error = BaseResponseBean.ErrorBean()
                    infoBean.error?.code = it.error?.code ?: NET_UNKNOWN_ERROR
                    infoBean.error?.message = it.error?.message ?: ""
                    return@flatMap Observable.just(infoBean)
                }
            }

}

/*
fun trans2UpdateUserInfoBean2(bean: UpdateUserInfoResponseBean?, infoBean: UpdateUserInfoBean) {

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

    infoBean.code = bean?.error?.code ?: DataConstant.NET_UNKNOWN_ERROR
    infoBean.message = bean?.error?.message ?: ""
}*/
