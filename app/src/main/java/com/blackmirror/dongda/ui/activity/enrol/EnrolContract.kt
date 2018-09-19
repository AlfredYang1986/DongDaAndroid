package com.blackmirror.dongda.ui.activity.enrol


import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.kdomain.model.BrandAllLocDomainBean
import com.blackmirror.dongda.kdomain.model.EnrolDomainBean
import com.blackmirror.dongda.kdomain.model.LocAllServiceDomainBean
import com.blackmirror.dongda.ui.BasePresenter

class EnrolContract {

    interface View {

        fun onGetBrandAllLocationSuccess(bean: BrandAllLocDomainBean)

        fun onGetLocAllServiceSuccess(bean: LocAllServiceDomainBean)

        fun onEnrolSuccess(bean: EnrolDomainBean)

        fun onError(bean: BaseDataBean)

    }

    interface Presenter : BasePresenter<View> {
        fun getBrandAllLocation(brand_id: String)
        fun getLocAllService(json: String, locations: String)
        fun enrol(json: String)
    }
}
