package com.blackmirror.dongda.kdomain.interactor

import android.text.TextUtils
import com.blackmirror.dongda.data.DataConstant
import com.blackmirror.dongda.data.model.request.SearchServiceRequestBean
import com.blackmirror.dongda.data.model.response.BaseResponseBean
import com.blackmirror.dongda.data.model.response.SearchServiceResponseBean
import com.blackmirror.dongda.data.net.execute
import com.blackmirror.dongda.data.net.getOssInfo
import com.blackmirror.dongda.data.repository.searchHomeData
import com.blackmirror.dongda.kdomain.model.HomepageDomainBean
import com.blackmirror.dongda.utils.AYPrefUtils
import io.reactivex.Observable
import java.util.*

/**
 * Create By Ruge at 2018-06-12
 */

fun searchServiceImpl(): Observable<HomepageDomainBean>{
    return searchHomeData(sh)
            .map {
                val domainBean = HomepageDomainBean()
                tran2DomainBean(it, domainBean)
                 domainBean
            }
}


var sh = fun(): Observable<SearchServiceResponseBean> {
    val bean = SearchServiceRequestBean()
    bean.json = "{ \"token\": \"${AYPrefUtils.getAuthToken()}\", \"condition\": { \"user_id\": \"${AYPrefUtils.getUserId()}\", \"service_type_list\": [{ \"service_type\": \"看顾\", \"count\": 6 }, { \"service_type\": \"艺术\", \"count\": 4 }, { \"service_type\": \"运动\", \"count\": 4 }, { \"service_type\": \"科学\", \"count\": 4 }]}}"
    bean.url = DataConstant.HOME_PAGE_URL

    return getOssInfo().flatMap {
        if ("ok" == it.status) {
            return@flatMap execute(bean, SearchServiceResponseBean::class.java)
        }
        val sb = SearchServiceResponseBean()
        sb.error = BaseResponseBean.ErrorBean()
        sb.error?.code = it.error?.code?:DataConstant.NET_UNKNOWN_ERROR
        sb.error?.message = it.error?.message?:""

        return@flatMap Observable.just(sb)
    }
}

private fun tran2DomainBean(bean: SearchServiceResponseBean, domainBean: HomepageDomainBean) {
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

    domainBean.homepage_services = ArrayList()
    if (bean.result == null || bean.result!!.homepage_services == null) {
        return
    }

    for (i in bean.result!!.homepage_services!!.indices) {
        val sb = bean.result!!.homepage_services!![i]
        val b = HomepageDomainBean.HomepageServicesBean()
        b.service_type = sb.service_type
        b.totalCount = sb.totalCount

        if (sb.services == null) {
            (domainBean.homepage_services as ArrayList<HomepageDomainBean.HomepageServicesBean>).add(b)
            continue
        }

        b.services = ArrayList()

        for (j in sb.services!!.indices) {
            val s = sb.services!![j]
            val d = HomepageDomainBean.HomepageServicesBean.ServicesBean()
            d.is_collected = s.is_collected
            d.service_leaf = s.service_leaf
            d.brand_id = s.brand_id
            d.location_id = s.location_id
            d.service_image = s.service_image
            d.brand_name = s.brand_name
            d.service_type = s.service_type
            d.address = s.address
            d.category = s.category
            d.service_id = s.service_id

            val pin = HomepageDomainBean.HomepageServicesBean.ServicesBean.PinBean()
            if (s.pin != null) {
                pin.latitude = s.pin!!.latitude
                pin.longitude = s.pin!!.longitude
                d.pin = pin
            } else {
                d.pin = pin
            }

            d.service_tags = if (s.service_tags != null) s.service_tags else mutableListOf()
            d.operation = if (s.operation != null) s.operation else mutableListOf()

            (b.services as ArrayList<HomepageDomainBean.HomepageServicesBean.ServicesBean>).add(d)

        }

        (domainBean.homepage_services as ArrayList<HomepageDomainBean.HomepageServicesBean>).add(b)
    }

}