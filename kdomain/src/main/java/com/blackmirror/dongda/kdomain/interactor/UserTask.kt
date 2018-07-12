package com.blackmirror.dongda.kdomain.interactor

import com.blackmirror.dongda.data.NET_UNKNOWN_ERROR
import com.blackmirror.dongda.data.QUERY_USER_INFO_URL
import com.blackmirror.dongda.data.model.request.UserInfoRequestBean
import com.blackmirror.dongda.data.model.response.BaseResponseBean
import com.blackmirror.dongda.data.model.response.UserInfoResponseBean
import com.blackmirror.dongda.data.net.execute
import com.blackmirror.dongda.data.net.getOssInfo
import com.blackmirror.dongda.data.repository.queryUserInfo
import com.blackmirror.dongda.kdomain.model.UserInfoDomainBean
import com.blackmirror.dongda.utils.AYPrefUtils
import io.reactivex.Observable

/**
 * Create By Ruge at 2018-06-12
 */

fun queryUserInfoImpl(): Observable<UserInfoDomainBean> {
    return queryUserInfo(qu).map {bean->
        val ub = UserInfoDomainBean()
        if (bean == null) {
            return@map ub
        }
        ub.isSuccess ="ok" == bean.status
        bean.result?.profile?.apply {
            ub.screen_name = screen_name
            ub.description = description
            ub.has_auth_phone = has_auth_phone
            ub.owner_name = owner_name
            ub.is_service_provider = is_service_provider
            ub.user_id = user_id
            ub.company = company
            ub.screen_photo = screen_photo
            ub.date = date
            ub.address = address
            ub.contact_no = contact_no
            ub.social_id = social_id
        }

        ub.code = bean.error?.code?:NET_UNKNOWN_ERROR
        ub.message = bean.error?.message?:""

         ub
    }
}

val qu = fun(): Observable<UserInfoResponseBean> {
    val bean = UserInfoRequestBean()
    bean.json = "{\"token\":\"${AYPrefUtils.getAuthToken()}\",\"condition\":{\"user_id\":\"${AYPrefUtils.getUserId()}\"}}"

    bean.url = QUERY_USER_INFO_URL

    return getOssInfo().flatMap {
        if ("ok" == it.status) {
            return@flatMap execute(bean, UserInfoResponseBean::class.java)
        }
        val sb = UserInfoResponseBean()
        sb.error = BaseResponseBean.ErrorBean()
        sb.error?.code = it.error?.code ?: NET_UNKNOWN_ERROR
        sb.error?.message = it.error?.message ?: ""
        Observable.just(sb)
    }
}