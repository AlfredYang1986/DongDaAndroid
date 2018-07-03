package com.blackmirror.dongda.ui.activity.enrol

import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.di.component.DaggerEnrolConfirmComponent
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.kdomain.model.BrandAllLocDomainBean
import com.blackmirror.dongda.kdomain.model.EnrolDomainBean
import com.blackmirror.dongda.kdomain.model.LocAllServiceDomainBean
import com.blackmirror.dongda.presenter.EnrolPresenter
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.*
import com.facebook.drawee.view.SimpleDraweeView

class EnrolConfirmActivity : BaseActivity(), View.OnClickListener, EnrolContract.View {

    private lateinit var iv_back: ImageView
    private lateinit var tv_save: TextView
    private lateinit var sv_service_photo: SimpleDraweeView
    private lateinit var tv_confirm_location: TextView
    private lateinit var tv_confirm_brand: TextView
    private lateinit var tv_child_age: TextView
    private lateinit var tv_to_set_date: TextView
    private lateinit var btn_confirm_enrol: Button
    private lateinit var tv_service_about_dec: TextView

    private var presenter: EnrolPresenter? = null

    override val layoutResId: Int
        get() = R.layout.activity_enrol_confirm

    override fun initInject() {
        presenter = DaggerEnrolConfirmComponent.builder()
                .activity(this)
                .view(this)
                .build()
                .enrolPresenter
    }

    override fun initView() {
        iv_back = findViewById(R.id.iv_back)
        tv_save = findViewById(R.id.tv_save)
        sv_service_photo = findViewById(R.id.sv_service_photo)
        tv_confirm_location = findViewById(R.id.tv_confirm_location)
        tv_confirm_brand = findViewById(R.id.tv_confirm_brand)
        tv_child_age = findViewById(R.id.tv_child_age)
        tv_to_set_date = findViewById(R.id.tv_to_set_date)
        btn_confirm_enrol = findViewById(R.id.btn_confirm_enrol)
        tv_service_about_dec = findViewById(R.id.tv_service_about_dec)
    }

    override fun initData() {
        val service_image = intent.getStringExtra("service_image")
        sv_service_photo.setImageURI(OSSUtils.getSignedUrl(service_image))
        tv_confirm_brand.text = intent.getStringExtra("service_leaf")
        val min_age = java.lang.Double.parseDouble(intent.getStringExtra("min_age"))
        val max_age = java.lang.Double.parseDouble(intent.getStringExtra("max_age"))
//        tv_child_age.text = min_age.formatNumber() + "-" + max_age.formatNumber() + "岁"
        tv_child_age.text = "${min_age.formatNumber()}-${max_age.formatNumber()}岁"
    }

    override fun initListener() {
        iv_back.setOnClickListener(this)
        tv_save.setOnClickListener(this)
        tv_to_set_date.setOnClickListener(this)
        btn_confirm_enrol.setOnClickListener(this)
        tv_service_about_dec.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> finish()
            R.id.tv_save -> {
            }
            R.id.tv_to_set_date -> startActivity(Intent(this, EnrolUnOpenDayActivity::class.java))
            R.id.btn_confirm_enrol -> enrol()
            R.id.tv_service_about_dec -> {
            }
        }
    }

    private fun enrol() {
        showProcessDialog()
        presenter?.enrol(intent.getStringExtra("json"))
    }

    override fun onGetBrandAllLocationSuccess(bean: BrandAllLocDomainBean) {

    }

    override fun onGetLocAllServiceSuccess(bean: LocAllServiceDomainBean) {

    }

    override fun onEnrolSuccess(bean: EnrolDomainBean) {
        closeProcessDialog()
        startActivity(Intent(this, EnrolSuccessActivity::class.java))
    }

    override fun onError(bean: BaseDataBean) {
        closeProcessDialog()
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(tv_child_age, bean.message)
        } else {
            ToastUtils.showShortToast("${bean.message}(${bean.code})")
        }
    }

    override fun setStatusBarColor() {
        DeviceUtils.setStatusBarColor(this, Color.parseColor("#33ADBADE"))
    }
}
