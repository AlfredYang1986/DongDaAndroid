package com.blackmirror.dongda.data.net

import android.text.TextUtils
import com.blackmirror.dongda.data.DataConstant
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
fun updateUserInfo2(bean: UpDateBean): Observable<UpdateUserInfoResponseBean> {
    if (!TextUtils.isEmpty(bean.imgUUID)) {//需要上传图片
        return updateUserInfoWithPhoto2(bean)
    } else {//不需要
        return updateUserInfoWithOutPhoto2(bean)
    }


}

fun <T, R> T.updateUserInfo3(block: (T) -> R): R {
    return block(this)
}

fun xcx(bean: UpDateBean) {
    bean.updateUserInfo3 {
        if (!TextUtils.isEmpty(bean.imgUUID)) {//需要上传图片
            updateUserInfoWithPhoto2(bean)
        } else {//不需要
            updateUserInfoWithOutPhoto2(bean)
        }
    }
}


fun updateUserInfoWithPhoto2(requestBean: UpDateBean): Observable<UpdateUserInfoResponseBean> {
    val bean = UploadImageRequestBean()
    bean.json = requestBean.json
    bean.imgUUID = requestBean.imgUUID
    bean.url = DataConstant.UPDATE_USER_INFO_URL
    return getOssInfo2().map {
        val infoBean = UpLoadImgResponseBean()
        if ("ok" == it.status) {
            executeUpload3(bean)
        } else {
            infoBean.error = BaseResponseBean.ErrorBean()
            infoBean.error?.code = it.error?.code ?: DataConstant.NET_UNKNOWN_ERROR
            infoBean.error?.message = it.error?.message ?: ""
            infoBean
        }
    }.flatMap {
        val infoBean = UpdateUserInfoResponseBean()
        //修改用户信息
        if ("ok" == it.status) {
            val b = UpdateUserInfoRequestBean()
            b.json = requestBean.json
            b.imgUUID = requestBean.imgUUID
            b.url = DataConstant.UPDATE_USER_INFO_URL
            execute(b, UpdateUserInfoResponseBean::class.java)

        } else {
            infoBean.error = BaseResponseBean.ErrorBean()
            infoBean.error?.code = it.error?.code ?: DataConstant.NET_UNKNOWN_ERROR
            infoBean.error?.message = it.error?.message ?: ""

            Observable.just(infoBean)
        }
    }
}

fun updateUserInfoWithOutPhoto2(requestBean: UpDateBean): Observable<UpdateUserInfoResponseBean> {
    val b = UpdateUserInfoRequestBean()
    b.json = requestBean.json
    b.url = DataConstant.UPDATE_USER_INFO_URL

    return getOssInfo2()
            .flatMap({
                val infoBean = UpdateUserInfoResponseBean()
                if ("ok" == it.status) {
                    execute(b, UpdateUserInfoResponseBean::class.java)
                } else {
                    infoBean.error = BaseResponseBean.ErrorBean()
                    infoBean.error?.code = it.error?.code ?: DataConstant.NET_UNKNOWN_ERROR
                    infoBean.error?.message = it.error?.message ?: ""
                    Observable.just(infoBean)
                }
            })

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
