package com.blackmirror.dongda.ui.activity.enrol

import android.content.Intent
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.widget.ImageView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.adapter.LocAllServiceAdapter
import com.blackmirror.dongda.di.component.DaggerLocAllServiceComponent
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.kdomain.model.BrandAllLocDomainBean
import com.blackmirror.dongda.kdomain.model.EnrolDomainBean
import com.blackmirror.dongda.kdomain.model.LocAllServiceDomainBean
import com.blackmirror.dongda.presenter.EnrolPresenter
import com.blackmirror.dongda.ui.activity.newservice.ServiceAgeActivity
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.*

class LocAllServiceActivity : BaseActivity(), EnrolContract.View {


    private lateinit var iv_back: ImageView
    private lateinit var rv_service: RecyclerView
    private lateinit var cl_add_service: ConstraintLayout

    private var presenter: EnrolPresenter? = null
    private var locations: String? = null

    override val layoutResId: Int
        get() = R.layout.activity_loc_all_service

    override fun initInject() {
        presenter = DaggerLocAllServiceComponent.builder()
                .activity(this)
                .view(this)
                .build()
                .enrolPresenter
    }

    override fun initView() {
        iv_back = findViewById(R.id.iv_back)
        rv_service = findViewById(R.id.rv_service)
        cl_add_service = findViewById(R.id.cl_add_service)
    }

    override fun initData() {
        locations = intent.getStringExtra("locations")
        presenter?.getLocAllService("", locations!!)
    }

    override fun initListener() {
        iv_back.setOnClickListener { finish() }

        cl_add_service.setOnClickListener { }
    }

    override fun onGetBrandAllLocationSuccess(bean: BrandAllLocDomainBean) {

    }

    override fun onGetLocAllServiceSuccess(bean: LocAllServiceDomainBean) {
        LogUtils.d(bean.toString())
        closeProcessDialog()
        val adapter = LocAllServiceAdapter(this, bean)
        rv_service.layoutManager = LinearLayoutManager(this)
        rv_service.adapter = adapter
        adapter.setOnItemClickListener { view, position, bean ->
            val intent = Intent()
            val sb = StringBuilder()
            if (bean.service_tags != null && bean.service_tags!!.size != 0 && !TextUtils.isEmpty(bean.service_tags!![0])) {
                sb.append(bean.service_tags!![0])
                        .append("的")
            }
            sb.append(bean.service_leaf)
            intent.putExtra("locations", locations)
            intent.putExtra("service_leaf", sb.toString())
            intent.putExtra("service_image", bean.service_image)
            intent.putExtra("service_id", bean.service_id)
            intent.putExtra("address", getIntent().getStringExtra("address"))

            if (bean.service_type!!.contains("看顾")) {
                intent.setClass(this@LocAllServiceActivity, ServiceAgeActivity::class.java)
            } else {
                intent.setClass(this@LocAllServiceActivity, EnrolAgeActivity::class.java)
            }

            startActivity(intent)
        }
    }

    override fun onEnrolSuccess(bean: EnrolDomainBean) {

    }

    override fun onError(bean: BaseDataBean) {
        closeProcessDialog()
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(iv_back, bean.message)
        } else {
            ToastUtils.showShortToast("${bean.message}(${bean.code})")
        }
    }

    override fun setStatusBarColor() {
        DeviceUtils.setStatusBarColor(this, Color.parseColor("#FFF7F9FA"))
    }
}
