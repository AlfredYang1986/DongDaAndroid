package com.blackmirror.dongda.kdomain.interactor

import android.text.TextUtils
import com.blackmirror.dongda.data.DataConstant
import com.blackmirror.dongda.data.model.request.LikePopRequestBean
import com.blackmirror.dongda.data.model.request.LikePushRequestBean
import com.blackmirror.dongda.data.model.request.LikeRequestBean
import com.blackmirror.dongda.data.model.response.BaseResponseBean
import com.blackmirror.dongda.data.model.response.LikePopResponseBean
import com.blackmirror.dongda.data.model.response.LikePushResponseBean
import com.blackmirror.dongda.data.model.response.LikeResponseBean
import com.blackmirror.dongda.data.net.execute
import com.blackmirror.dongda.data.net.getOssInfo
import com.blackmirror.dongda.data.repository.getMyLikeData
import com.blackmirror.dongda.data.repository.likePop2Server
import com.blackmirror.dongda.data.repository.likePush2Server
import com.blackmirror.dongda.kdomain.model.LikeDomainBean
import com.blackmirror.dongda.kdomain.model.LikePopDomainBean
import com.blackmirror.dongda.kdomain.model.LikePushDomainBean
import com.blackmirror.dongda.utils.AYPrefUtils
import io.reactivex.Observable
import java.util.*

/**
 * Create By Ruge at 2018-06-12
 */
fun likePushImpl(service_id: String): Observable<LikePushDomainBean> {
    return likePush2Server(service_id, lp).map {
        val b = LikePushDomainBean()
        if (it.result == null) {
            return@map b
        }
        b.isSuccess = "ok" == it.status

        b.code = it.error?.code ?: DataConstant.NET_UNKNOWN_ERROR
        b.message = it.error?.message ?: ""

        b
    }
}

fun likePopImpl(service_id: String): Observable<LikePopDomainBean> {
    return likePop2Server(service_id, pop).map {
        val b = LikePopDomainBean()
        if (it.result == null) {
            return@map b
        }
        b.isSuccess = "ok" == it.status

        b.code = it.error?.code ?: DataConstant.NET_UNKNOWN_ERROR
        b.message = it.error?.message ?: ""

        b
    }
}

fun getMyLikeDataImpl(): Observable<LikeDomainBean> {
    return getMyLikeData(like).map {
        val domainBean = LikeDomainBean()
        tran2LikeDomainBean(it, domainBean)
        domainBean
    }
}

val like = fun(): Observable<LikeResponseBean> {
    val bean = LikeRequestBean()
    bean.json = "{\"token\":\"${AYPrefUtils.getAuthToken()}\",\"condition\":{\"user_id\":\"${AYPrefUtils.getUserId()}\"}}"

    bean.url = DataConstant.LIKE_QUERY_LIST_URL

    return getOssInfo().flatMap {
        if ("ok" == it.status) {
            return@flatMap execute(bean, LikeResponseBean::class.java)
        }
        val sb = LikeResponseBean()
        sb.error = BaseResponseBean.ErrorBean()
        sb.error?.code = it.error?.code ?: DataConstant.NET_UNKNOWN_ERROR
        sb.error?.message = it.error?.message ?: ""
        Observable.just(sb)
    }
}

val pop = fun(service_id: String): Observable<LikePopResponseBean> {
    val bean = LikePopRequestBean()
    bean.json = "{\"token\":\"${AYPrefUtils.getAuthToken()}\",\"condition\": {\"user_id\":\"${AYPrefUtils.getUserId()}\",\"service_id\":\"$service_id\"},\"collections\":{\"user_id\": \"${AYPrefUtils.getUserId()}\",\"service_id\":\"$service_id\"}}"
    bean.url = DataConstant.LIKE_POP_URL

    return getOssInfo().flatMap {
        if ("ok" == it.status) {
            return@flatMap execute(bean, LikePopResponseBean::class.java)
        }
        val sb = LikePopResponseBean()
        sb.error = BaseResponseBean.ErrorBean()
        sb.error?.code = it.error?.code ?: DataConstant.NET_UNKNOWN_ERROR
        sb.error?.message = it.error?.message ?: ""
        Observable.just(sb)
    }
}

val lp = fun(service_id: String): Observable<LikePushResponseBean> {
    val bean = LikePushRequestBean()
    bean.json = "{\"token\":\"${AYPrefUtils.getAuthToken()}\",\"condition\": {\"user_id\":\"${AYPrefUtils.getUserId()}\",\"service_id\":\"$service_id\"},\"collections\":{\"user_id\": \"${AYPrefUtils.getUserId()}\",\"service_id\":\"$service_id \"}}"
    bean.url = DataConstant.LIKE_PUSH_URL

    return getOssInfo().flatMap {
        if ("ok" == it.status) {
            return@flatMap execute(bean, LikePushResponseBean::class.java)
        }
        val sb = LikePushResponseBean()
        sb.error = BaseResponseBean.ErrorBean()

        sb.error?.code = it.error?.code ?: DataConstant.NET_UNKNOWN_ERROR
        sb.error?.message = it.error?.message ?: ""

        Observable.just(sb)
    }
}


private fun tran2LikeDomainBean(bean: LikeResponseBean, domainBean: LikeDomainBean) {
    if (bean == null) {
        return
    }

    if (TextUtils.isEmpty(bean.status) || "ok" != bean.status) {
        if (bean.error == null) {
            return
        }
        domainBean.code = bean.error!!.code
        domainBean.message = bean.error!!.message
        return

    }

    domainBean.isSuccess = true

    domainBean.services = ArrayList()
    if (bean.result == null || bean.result!!.services == null) {
        return
    }

    for (i in bean.result!!.services!!.indices) {
        val sb = bean.result!!.services!![i]
        val b = LikeDomainBean.ServicesBean()

        b.is_collected = sb.is_collected
        b.punchline = sb.punchline
        b.service_leaf = sb.service_leaf
        b.brand_id = sb.brand_id
        b.location_id = sb.location_id
        b.service_image = sb.service_image
        b.brand_name = sb.brand_name
        b.service_type = sb.service_type
        b.address = sb.address
        b.category = sb.category
        val pin = LikeDomainBean.ServicesBean.PinBean()
        if (sb.pin != null) {
            pin.latitude = sb.pin!!.latitude
            pin.longitude = sb.pin!!.longitude
            b.pin = pin
        } else {
            b.pin = pin
        }

        b.service_id = sb.service_id

        b.service_tags = if (sb.service_tags != null) sb.service_tags else ArrayList()
        b.operation = if (sb.operation != null) sb.operation else ArrayList()

        (domainBean.services as ArrayList<LikeDomainBean.ServicesBean>).add(b)

    }

}