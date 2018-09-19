package com.blackmirror.dongda.ui.activity.apply


import com.blackmirror.dongda.kdomain.model.ApplyServiceDomainBean
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.ui.BasePresenter

class ApplyContract {
    interface View {
        fun onApplySuccess(bean: ApplyServiceDomainBean)

        fun onError(bean: BaseDataBean)

    }

    interface Presenter : BasePresenter<View> {
        fun apply(brand_name: String, name: String, category: String, phone: String, city: String)
    }
}
