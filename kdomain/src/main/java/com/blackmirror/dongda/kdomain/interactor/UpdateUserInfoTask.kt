package com.blackmirror.dongda.kdomain.interactor

import android.text.TextUtils
import com.blackmirror.dongda.data.NET_UNKNOWN_ERROR
import com.blackmirror.dongda.data.model.db.UserInfoDbBean
import com.blackmirror.dongda.data.model.request.UpDateBean
import com.blackmirror.dongda.data.model.response.UpdateUserInfoResponseBean
import com.blackmirror.dongda.data.net.updateUserInfoWithOutPhoto
import com.blackmirror.dongda.data.net.updateUserInfoWithPhoto
import com.blackmirror.dongda.data.repository.DbRepository
import com.blackmirror.dongda.data.repository.updateUserInfo
import com.blackmirror.dongda.kdomain.model.UpdateUserInfoBean
import com.blackmirror.dongda.kdomain.model.UpdateUserInfoDomainBean
import com.blackmirror.dongda.utils.AYPrefUtils
import io.reactivex.Observable

/**
 * Create By Ruge at 2018-06-12
 */
fun updateUserInfoImpl(bean: UpdateUserInfoDomainBean): Observable<UpdateUserInfoBean> {
    val ub=UpDateBean()
    ub.imgUUID = bean.imgUUID
    ub.json = bean.json
    return updateUserInfo(ub, update).map {
        val infoBean = UpdateUserInfoBean()
        trans2UpdateUserInfoBean(it, infoBean)

        val dbBean = UserInfoDbBean()
        dbBean.is_current = 1//目前没什么卵用
        dbBean.screen_name = infoBean.screen_name
        dbBean.screen_photo = infoBean.screen_photo
        dbBean.user_id = infoBean.user_id
        dbBean.auth_token = AYPrefUtils.getAuthToken()
        DbRepository.updateProfile(dbBean)
         infoBean
    }
}

val update=fun(bean:UpDateBean): Observable<UpdateUserInfoResponseBean> {
    if (!TextUtils.isEmpty(bean.imgUUID)) {//需要上传图片
        return updateUserInfoWithPhoto(bean)
    } else {//不需要
        return updateUserInfoWithOutPhoto(bean)
    }
}

private fun trans2UpdateUserInfoBean(bean: UpdateUserInfoResponseBean?, infoBean: UpdateUserInfoBean) {
    if (bean == null){
        return
    }
    infoBean.isSuccess = "ok"==bean.status

    bean.result?.profile?.apply {
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

    infoBean.code = bean.error?.code?:NET_UNKNOWN_ERROR
    infoBean.message = bean.error?.message?:""

}
