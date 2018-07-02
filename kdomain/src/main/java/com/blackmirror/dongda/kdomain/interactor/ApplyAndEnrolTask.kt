package com.blackmirror.dongda.kdomain.interactor

import com.blackmirror.dongda.data.*
import com.blackmirror.dongda.data.model.request.ApplyServiceRequestBean
import com.blackmirror.dongda.data.model.request.BrandAllLocRequestBean
import com.blackmirror.dongda.data.model.request.EnrolRequestBean
import com.blackmirror.dongda.data.model.request.LocAllServiceRequestBean
import com.blackmirror.dongda.data.model.response.*
import com.blackmirror.dongda.data.net.execute
import com.blackmirror.dongda.data.net.getOssInfo
import com.blackmirror.dongda.data.repository.applyService
import com.blackmirror.dongda.data.repository.enrolStudent
import com.blackmirror.dongda.data.repository.getBrandAllLocation
import com.blackmirror.dongda.data.repository.getLocAllService
import com.blackmirror.dongda.kdomain.model.ApplyServiceDomainBean
import com.blackmirror.dongda.kdomain.model.BrandAllLocDomainBean
import com.blackmirror.dongda.kdomain.model.EnrolDomainBean
import com.blackmirror.dongda.kdomain.model.LocAllServiceDomainBean
import com.blackmirror.dongda.utils.AYPrefUtils
import io.reactivex.Observable
import java.util.*

/**
 * Create By Ruge at 2018-06-12
 */
fun applyServiceImpl(brand_name: String, name: String, category: String, phone: String, city: String): Observable<ApplyServiceDomainBean> {
    return applyService(brand_name, name, category, phone, city, apply).map {
        val domainBean = ApplyServiceDomainBean()
        if (it == null) {
            return@map domainBean
        }
        if ("ok" == it.status) {
            domainBean.isSuccess = true
            domainBean.apply_id = if (it.result != null) it.result!!.apply_id else ""
        } else {
            domainBean.code = it.error?.code ?: NET_UNKNOWN_ERROR
            domainBean.message = it.error?.message ?: ""
        }
        domainBean
    }
}

/**
 * 遍历品牌下的所有位置
 * @return
 */
fun getBrandAllLocationImpl(brand_id: String): Observable<BrandAllLocDomainBean> {
    return getBrandAllLocation(brand_id, addr).map {
        val domainBean = BrandAllLocDomainBean()
        transLoc2DomainBean(it, domainBean)
        domainBean
    }
}

fun getLocAllServiceImpl(json: String, locations: String): Observable<LocAllServiceDomainBean> {
    return getLocAllService(json, locations, bl).map {
        val db = LocAllServiceDomainBean()
        if (it == null) {
            db
        }
        db.services = ArrayList()
        db.isSuccess = "ok" == it.status

        it.result?.services?.forEach {
            val dsb = LocAllServiceDomainBean.ServicesBean()
            val sb = it
            dsb.punchline = sb.punchline
            dsb.service_leaf = sb.service_leaf
            dsb.service_image = sb.service_image
            dsb.service_type = sb.service_type
            dsb.category = sb.category
            dsb.service_id = sb.service_id
            dsb.service_tags = if (sb.service_tags == null) mutableListOf() else sb.service_tags
            dsb.operation = if (sb.operation == null) mutableListOf() else sb.operation
            db.services!!.add(dsb)
        }

        db.code = it.error?.code ?: NET_UNKNOWN_ERROR
        db.message = it.error?.message ?: ""

        db
    }
}

fun enrolStudentImpl(json: String): Observable<EnrolDomainBean> {
    return enrolStudent(json, el).map {
        val db = EnrolDomainBean()
        if (it == null) {
            return@map db
        }
        if ("ok" == it.status) {
            db.isSuccess = true
            db.recruit_id = if (it.result != null) it.result!!.recruit_id else ""
        } else {
            db.code = it.error?.code ?: NET_UNKNOWN_ERROR
            db.message = it.error?.message ?: ""
        }
         db
    }
}

val el = fun(json: String): Observable<EnrolResponseBean> {
    val bean = EnrolRequestBean()
    bean.json = json

    bean.url = ENROL_URL

    return getOssInfo().flatMap {
        if ("ok" == it.status) {
            return@flatMap execute(bean, EnrolResponseBean::class.java)
        }
        val sb = EnrolResponseBean()
        sb.error = BaseResponseBean.ErrorBean()
        sb.error?.code = it.error?.code ?: NET_UNKNOWN_ERROR
        sb.error?.message = it.error?.message ?: ""
        Observable.just(sb)
    }

}


/**
 * 遍历品牌下的所有位置
 * @return
 */
val bl = fun(json: String, locations: String): Observable<LocAllServiceResponseBean> {
    val bean = LocAllServiceRequestBean()
    bean.json = "{\"token\":\"${AYPrefUtils.getAuthToken()}\",\"locations\":[\"$locations\"]}"

    bean.url = LOC_ALL_SERVICE_URL

    return getOssInfo().flatMap {
        if ("ok" == it.status) {
            return@flatMap execute(bean, LocAllServiceResponseBean::class.java)
        }
        val sb = LocAllServiceResponseBean()
        sb.error = BaseResponseBean.ErrorBean()
        sb.error?.code = it.error?.code ?: NET_UNKNOWN_ERROR
        sb.error?.message = it.error?.message ?: ""
        Observable.just(sb)
    }
}


val addr = fun(brand_id: String): Observable<BrandAllLocResponseBean> {
    val bean = BrandAllLocRequestBean()
    bean.json = "{\"token\":\"${AYPrefUtils.getAuthToken()}\",\"brand_id\":\"$brand_id\"}"

    bean.url = BRAND_ALL_LOC_URL

    return getOssInfo().flatMap {
        if ("ok" == it.status) {
            return@flatMap execute(bean, BrandAllLocResponseBean::class.java)
        }
        val sb = BrandAllLocResponseBean()
        sb.error = BaseResponseBean.ErrorBean()
        sb.error?.code = it.error?.code ?: NET_UNKNOWN_ERROR
        sb.error?.message = it.error?.message ?: ""
        Observable.just<BrandAllLocResponseBean>(sb)
    }

}

val apply = fun(brand_name: String, name: String, category: String, phone: String, city: String): Observable<ApplyServiceResponseBean> {
    val bean = ApplyServiceRequestBean()
    bean.json = "{\"token\":\"${AYPrefUtils.getAuthToken()}\",\"condition\":{\"user_id\":\"${AYPrefUtils.getUserId()}\"},\"apply\":{\"brand_name\":\"$brand_name\",\"name\":\"$name\",\"category\":\"$category\",\"phone\":\"$phone\",\"city\":\"$city\"}}"

    bean.url = APPLY_SERVICE_URL

    return getOssInfo().flatMap {
        if ("ok" == it.status) {
            return@flatMap execute(bean, ApplyServiceResponseBean::class.java)
        }
        val sb = ApplyServiceResponseBean()
        sb.error = BaseResponseBean.ErrorBean()
        sb.error?.code = it.error?.code ?: NET_UNKNOWN_ERROR
        sb.error?.message = it.error?.message ?: ""
        Observable.just(sb)
    }
}

private fun transLoc2DomainBean(bean: BrandAllLocResponseBean, domainBean: BrandAllLocDomainBean) {
    if (bean == null) {
        return
    }
    if ("ok" != bean.status) {
        domainBean.code = if (bean.error != null) bean.error!!.code else domainBean.code
        domainBean.message = if (bean.error != null) bean.error!!.message else domainBean.message
        return
    }
    domainBean.isSuccess = true
//    val locations = ArrayList<BrandAllLocDomainBean.LocationsBean>()
    domainBean.locations = mutableListOf()
    if (bean.result == null) {
        return
    }

    bean.result?.locations?.forEach out@{lb->
//        val lb = it
        val dlb = BrandAllLocDomainBean.LocationsBean()

        dlb.location_id = lb.location_id
        dlb.address = lb.address

        val pin = BrandAllLocDomainBean.LocationsBean.PinBean()
        if (lb.pin != null) {
            pin.latitude = lb.pin!!.latitude
            pin.longitude = lb.pin!!.longitude
        }
        dlb.pin = pin

        dlb.friendliness = if (lb.friendliness == null) mutableListOf() else lb.friendliness!!

        dlb.location_images = mutableListOf()
        if (lb.location_images == null) {
            domainBean.locations!!.add(dlb)
            return@out
        }

        lb.location_images?.forEach {ib->
//            val ib = lb.location_images[j]
            val dib = BrandAllLocDomainBean.LocationsBean.LocationImagesBean()
            dib.image = ib.image
            dib.tag = ib.tag
            dlb.location_images!!.add(dib)
        }


        domainBean.locations!!.add(dlb)

    }

    /*for (i in bean.result.locations.indices) {
        val lb = bean.result.locations[i]
        val dlb = BrandAllLocDomainBean.LocationsBean()

        dlb.location_id = lb.location_id
        dlb.address = lb.address

        val pin = BrandAllLocDomainBean.LocationsBean.PinBean()
        if (lb.pin != null) {
            pin.latitude = lb.pin.latitude
            pin.longitude = lb.pin.longitude
        }
        dlb.pin = pin

        dlb.friendliness = if (lb.friendliness == null) ArrayList() else lb.friendliness

        dlb.location_images = ArrayList()
        if (lb.location_images == null) {
            (domainBean.locations as ArrayList<BrandAllLocDomainBean.LocationsBean>).add(dlb)
            continue
        }

        for (j in lb.location_images.indices) {
            val ib = lb.location_images[j]
            val dib = BrandAllLocDomainBean.LocationsBean.LocationImagesBean()
            dib.image = ib.image
            dib.tag = ib.tag
            (dlb.location_images as ArrayList<BrandAllLocDomainBean.LocationsBean.LocationImagesBean>).add(dib)
        }

        (domainBean.locations as ArrayList<BrandAllLocDomainBean.LocationsBean>).add(dlb)

    }*/
}
