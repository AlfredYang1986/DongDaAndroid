package com.blackmirror.dongda.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.model.ServiceProfileBean
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.DeviceUtils
import com.blackmirror.dongda.utils.OtherUtils
import com.facebook.drawee.view.SimpleDraweeView

class ServiceProfileActivity : BaseActivity() {

    private lateinit var iv_service_back: ImageView
    private lateinit var sv_service_photo: SimpleDraweeView
    private lateinit var tv_service_tag: TextView
    private lateinit var tv_service_name: TextView
    private lateinit var tv_service_teacher_name: TextView
    private lateinit var tv_service_about_me: TextView
    private lateinit var tv_service_dec: TextView

    private var bean: ServiceProfileBean? = null

    override val layoutResId: Int
        get() = R.layout.activity_service_profile

    override fun initInject() {

    }

    override fun initView() {
        iv_service_back = findViewById(R.id.iv_service_back)
        sv_service_photo = findViewById(R.id.sv_service_photo)
        tv_service_tag = findViewById(R.id.tv_service_tag)
        tv_service_name = findViewById(R.id.tv_service_name)
        tv_service_teacher_name = findViewById(R.id.tv_service_teacher_name)
        tv_service_about_me = findViewById(R.id.tv_service_about_me)
        tv_service_dec = findViewById(R.id.tv_service_dec)
    }

    override fun initData() {
        bean = intent.getParcelableExtra("bean")
        if (bean != null) {
            sv_service_photo.setImageURI(OtherUtils.resourceIdToUri(this, bean!!.res_id))
            tv_service_tag.text = bean!!.brand_tag
            tv_service_name.text = bean!!.brand_name
            tv_service_teacher_name.text = String.format(getString(R.string.str_teacher), bean!!.brand_name)
            tv_service_dec.text = bean!!.about_brand
        }
    }

    override fun initListener() {
        iv_service_back.setOnClickListener { finish() }
    }

    override fun setStatusBarColor() {
        DeviceUtils.initSystemBarColor(this)
    }

    companion object {

        fun startActivity(activity: AppCompatActivity, tv_brand_tag: String, bg_res_id: Int) {
            val intent = Intent(activity, ServiceProfileActivity::class.java)
            intent.putExtra("tv_brand_tag", tv_brand_tag)
            intent.putExtra("bg_res_id", bg_res_id)
            intent.putExtra("tv_brand_tag", tv_brand_tag)
        }
    }
}
