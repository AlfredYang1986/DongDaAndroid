package com.blackmirror.dongda.kdomain.interactor

import com.blackmirror.dongda.data.NEAR_SERVICE_URL
import com.blackmirror.dongda.data.NET_UNKNOWN_ERROR
import com.blackmirror.dongda.data.SERVICE_DETAIL_URL
import com.blackmirror.dongda.data.SERVICE_MORE_URL
import com.blackmirror.dongda.data.model.request.DetailInfoRequestBean
import com.blackmirror.dongda.data.model.request.NearServiceRequestBean
import com.blackmirror.dongda.data.model.request.SearchServiceRequestBean
import com.blackmirror.dongda.data.model.response.BaseResponseBean
import com.blackmirror.dongda.data.model.response.CareMoreResponseBean
import com.blackmirror.dongda.data.model.response.DetailInfoResponseBean
import com.blackmirror.dongda.data.model.response.NearServiceResponseBean
import com.blackmirror.dongda.data.net.execute
import com.blackmirror.dongda.data.net.getOssInfo
import com.blackmirror.dongda.data.repository.getDetailInfo
import com.blackmirror.dongda.data.repository.getNearService
import com.blackmirror.dongda.data.repository.getServiceMoreData
import com.blackmirror.dongda.kdomain.model.CareMoreDomainBean
import com.blackmirror.dongda.kdomain.model.DetailInfoDomainBean
import com.blackmirror.dongda.kdomain.model.NearServiceDomainBean
import com.blackmirror.dongda.utils.getAuthToken
import com.blackmirror.dongda.utils.getUserId
import io.reactivex.Observable

/**
 * Create By Ruge at 2018-06-12
 */

/**
 * 详细信息
 * @return
 */
fun getDetailInfoImpl(service_id: String): Observable<DetailInfoDomainBean> {
    return getDetailInfo(service_id, di).map {
        val domainBean = DetailInfoDomainBean()
        tran2DetailInfoBean(it, domainBean)
        domainBean
    }
}

fun getServiceMoreDataImpl(skipCount: Int, takeCount: Int, serviceType: String): Observable<CareMoreDomainBean> {
    return getServiceMoreData(skipCount, takeCount, serviceType, md).map {
        val domainBean = CareMoreDomainBean()
        tran2CareMoreDomainBean(it, domainBean)
        domainBean
    }
}

/**
 * 获取附近的服务
 * @return
 */
fun getNearServiceImpl(latitude: Double, longitude: Double): Observable<NearServiceDomainBean> {
    return getNearService(latitude, longitude, ns).map {
        val domainBean = NearServiceDomainBean()
        tran2NearServiceDomainBean(it, domainBean)
        domainBean
    }
}

val ns = fun(latitude: Double, longitude: Double): Observable<NearServiceResponseBean> {
    val bean = NearServiceRequestBean()

    bean.json = "{\"token\":\"${getAuthToken()}\",\"condition\":{\"user_id\":\"${getUserId()}\",\"pin\":{\"latitude\":$latitude,\"longitude\":$longitude}}}"


    bean.url = NEAR_SERVICE_URL

    return getOssInfo().flatMap {
        if ("ok" == it.status) {
            return@flatMap execute(bean, NearServiceResponseBean::class.java)
        }
        val sb = NearServiceResponseBean()
        sb.error = BaseResponseBean.ErrorBean()
        sb.error?.code = it.error?.code ?: NET_UNKNOWN_ERROR
        sb.error?.message = it.error?.message ?: ""
        Observable.just(sb)
    }


}


val md = fun(skipCount: Int, takeCount: Int, serviceType: String): Observable<CareMoreResponseBean> {
    val bean = SearchServiceRequestBean()
    bean.json = "{\"skip\" : $skipCount ,\"take\" : $takeCount ,\"token\": \"${getAuthToken()}\",\"condition\": {\"user_id\":\"${getUserId()}\",\"service_type\": \"$serviceType\"}}"

    bean.url = SERVICE_MORE_URL

    return getOssInfo().flatMap {
        if ("ok" == it.status) {
            return@flatMap execute(bean, CareMoreResponseBean::class.java)
        }
        val sb = CareMoreResponseBean()
        sb.error = BaseResponseBean.ErrorBean()
        sb.error?.code = it.error?.code ?: NET_UNKNOWN_ERROR
        sb.error?.message = it.error?.message ?: ""
        Observable.just(sb)
    }
}


val di = fun(service_id: String): Observable<DetailInfoResponseBean> {
    val bean = DetailInfoRequestBean()

    bean.json = "{\"token\":\"${getAuthToken()}\",\"condition\":{\"service_id\":\"$service_id\"}}"

    bean.url = SERVICE_DETAIL_URL

    return getOssInfo().flatMap {
        if ("ok" == it.status) {
            return@flatMap execute(bean, DetailInfoResponseBean::class.java)
        }
        val sb = DetailInfoResponseBean()
        sb.error = BaseResponseBean.ErrorBean()
        sb.error?.code = it.error?.code ?: NET_UNKNOWN_ERROR
        sb.error?.message = it.error?.message ?: ""
        Observable.just(sb)
    }

}

private fun tran2DetailInfoBean(bean: DetailInfoResponseBean, domainBean: DetailInfoDomainBean) {
    if (bean == null) {
        return
    }

    if (bean.status.isNullOrEmpty() || "ok" != bean.status) {
        if (bean.error == null) {
            return
        }
        domainBean.code = bean.error!!.code
        domainBean.message = bean.error!!.message
        return
    }


    if (bean.result == null || bean.result!!.service == null) {
        return
    }

    domainBean.isSuccess = true

    val service = bean.result!!.service
    domainBean.class_max_stu = service!!.class_max_stu
    if (service.location != null) {
        domainBean.location_id = service.location!!.location_id
        domainBean.address = service.location!!.address
        domainBean.friendliness = if (service.location!!.friendliness == null) mutableListOf() else service.location!!.friendliness
    }

    if (service.location != null && service.location!!.pin != null) {
        domainBean.latitude = service.location!!.pin!!.latitude
        domainBean.longitude = service.location!!.pin!!.longitude
    }

    domainBean.location_images = mutableListOf()

    if (service.location != null && service.location!!.location_images != null) {
        for (i in 0 until service.location!!.location_images!!.size) {
            val dlb = DetailInfoDomainBean.LocationImagesBean()
            val lb = service.location!!.location_images!![i]
            dlb.tag = lb.tag
            dlb.image = lb.image
            domainBean.location_images!!.add(dlb)
        }
    }
    domainBean.is_collected = service.is_collected
    domainBean.description = service.description
    domainBean.min_age = service.min_age
    domainBean.punchline = service.punchline
    domainBean.teacher_num = service.teacher_num
    domainBean.service_leaf = service.service_leaf

    if (service.brand != null) {

        domainBean.brand_id = service.brand!!.brand_id
        domainBean.date = service.brand!!.date
        domainBean.brand_name = service.brand!!.brand_name
        domainBean.brand_tag = service.brand!!.brand_tag
        domainBean.about_brand = service.brand!!.about_brand
    }

    domainBean.max_age = service.max_age
    domainBean.service_type = service.service_type
    domainBean.category = service.category
    domainBean.album = service.album
    domainBean.service_id = service.service_id
    domainBean.service_tags = if (service.service_tags == null) mutableListOf() else service.service_tags
    domainBean.operation = if (service.operation == null) mutableListOf() else service.operation

    domainBean.service_images = mutableListOf()
    if (service.service_images != null) {
        for (i in service.service_images!!.indices) {
            val dsb = DetailInfoDomainBean.ServiceImagesBean()
            val sb = service.service_images!![i]
            dsb.tag = sb.tag
            dsb.image = sb.image
            domainBean.service_images!!.add(dsb)
        }
    }


}

private fun tran2CareMoreDomainBean(bean: CareMoreResponseBean?, domainBean: CareMoreDomainBean) {
    if (bean == null) {
        return
    }

    if (bean.status.isNullOrEmpty() || "ok" != bean.status) {
        if (bean.error == null) {
            return
        }
        domainBean.code = bean.error!!.code
        domainBean.message = bean.error!!.message
        return

    }

    domainBean.isSuccess = true

    domainBean.services = mutableListOf()
    if (bean.result == null || bean.result!!.services == null) {
        return
    }

    for (i in bean.result!!.services!!.indices) {
        val sb = bean.result!!.services!![i]
        val b = CareMoreDomainBean.ServicesBean()

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
        val pin = CareMoreDomainBean.ServicesBean.PinBean()
        if (sb.pin != null) {
            pin.latitude = sb.pin!!.latitude
            pin.longitude = sb.pin!!.longitude
            b.pin = pin
        } else {
            b.pin = pin
        }

        b.service_id = sb.service_id

        b.service_tags = if (sb.service_tags != null) sb.service_tags else mutableListOf()
        b.operation = if (sb.operation != null) sb.operation else mutableListOf()

        domainBean.services!!.add(b)

    }

}

private fun tran2NearServiceDomainBean(bean: NearServiceResponseBean?, domainBean: NearServiceDomainBean) {
    if (bean == null) {
        return
    }

    if (bean.status.isNullOrEmpty() || "ok" != bean.status) {
        if (bean.error == null) {
            return
        }
        domainBean.code = bean.error!!.code
        domainBean.message = bean.error!!.message
        return

    }

    domainBean.isSuccess = true

    domainBean.services = mutableListOf()
    if (bean.result == null || bean.result!!.services == null) {
        return
    }

    for (i in bean.result!!.services!!.indices) {
        val sb = bean.result!!.services!![i]
        val b = NearServiceDomainBean.ServicesBean()

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
        val pin = NearServiceDomainBean.ServicesBean.PinBean()
        if (sb.pin != null) {
            pin.latitude = sb.pin!!.latitude
            pin.longitude = sb.pin!!.longitude
            b.pin = pin
        } else {
            b.pin = pin
        }

        b.service_id = sb.service_id

        b.service_tags = if (sb.service_tags != null) sb.service_tags else mutableListOf()
        b.operation = if (sb.operation != null) sb.operation else mutableListOf()

//        (domainBean.services as ArrayList<NearServiceDomainBean.ServicesBean>).add(b)
        domainBean.services!!.add(b)

    }

}


