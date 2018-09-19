package com.blackmirror.dongda.ui.activity.apply

import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.di.component.DaggerApplyServiceComponent
import com.blackmirror.dongda.kdomain.model.ApplyServiceDomainBean
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.presenter.ApplyPresenter
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.AppConstant
import com.blackmirror.dongda.utils.SnackbarUtils
import com.blackmirror.dongda.utils.ToastUtils

class ApplyServiceActivity : BaseActivity(), View.OnClickListener, ApplyContract.View {

    private lateinit var iv_back: ImageView
    private lateinit var tv_next: TextView
    private lateinit var rb_care: RadioButton
    private lateinit var rb_class: RadioButton
    private lateinit var rb_experience: RadioButton
    private lateinit var tv_service_about_dec: TextView

    private var check_status: Int = 0//1 看顾 2 课程 3 体验
    private var can_next: Boolean = false
    private var user_name: String? = null
    private var city_name: String? = null
    private var brand_name: String? = null
    private var phone_no: String? = null
    private var presenter: ApplyPresenter? = null


    override val layoutResId: Int
        get() = R.layout.activity_apply_service

    override fun initInject() {
        presenter = DaggerApplyServiceComponent.builder()
                .activity(this)
                .view(this)
                .build()
                .applyPresenter
    }

    override fun initView() {
        iv_back = findViewById(R.id.iv_back)
        tv_next = findViewById(R.id.tv_next)
        rb_care = findViewById(R.id.rb_care)
        rb_class = findViewById(R.id.rb_class)
        rb_experience = findViewById(R.id.rb_experience)
        tv_service_about_dec = findViewById(R.id.tv_service_about_dec)
    }

    override fun initData() {
        user_name = intent.getStringExtra("user_name")
        city_name = intent.getStringExtra("city_name")
        brand_name = intent.getStringExtra("brand_name")
        phone_no = intent.getStringExtra("phone_no")
    }

    override fun initListener() {

        iv_back.setOnClickListener(this)
        tv_next.setOnClickListener(this)
        tv_service_about_dec.setOnClickListener(this)


        rb_care.setOnCheckedChangeListener { buttonView, isChecked ->
            check_status = if (isChecked) 1 else check_status
            canNext()
        }

        rb_class.setOnCheckedChangeListener { buttonView, isChecked ->
            check_status = if (isChecked) 2 else check_status
            canNext()
        }

        rb_experience.setOnCheckedChangeListener { buttonView, isChecked ->
            check_status = if (isChecked) 3 else check_status
            canNext()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> finish()
            R.id.tv_next -> apply()
            R.id.tv_service_about_dec -> startActivity(Intent(this@ApplyServiceActivity, ServiceHelpActivity::class.java))
        }
    }

    private fun apply() {
        showProcessDialog()
        presenter?.apply(brand_name!!, user_name!!, "", phone_no!!, city_name!!)
    }

    private fun canNext() {
        if (!can_next) {
            can_next = true
            tv_next.setTextColor(Color.parseColor("#FF59D5C7"))
        } else {
            can_next = false
            tv_next.setTextColor(Color.parseColor("#FFD9D9D9"))
        }
    }


    override fun onApplySuccess(bean: ApplyServiceDomainBean) {
        closeProcessDialog()
        startActivity(Intent(this@ApplyServiceActivity, ApplySuccessActivity::class.java))
    }

    override fun onError(bean: BaseDataBean) {
        closeProcessDialog()
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(tv_next, bean.message)
        } else {
            ToastUtils.showShortToast("${bean.message}(${bean.code})")
        }
    }

    override fun onResume() {
        super.onResume()
        when (check_status) {
            1 -> if (!rb_care.isChecked) {
                rb_care.isChecked = true
            }
            2 -> if (!rb_class.isChecked) {
                rb_class.isChecked = true
            }
            3 -> if (!rb_experience.isChecked) {
                rb_experience.isChecked = true
            }
        }
    }
}
