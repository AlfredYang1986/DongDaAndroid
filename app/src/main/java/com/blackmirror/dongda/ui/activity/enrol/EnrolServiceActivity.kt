package com.blackmirror.dongda.ui.activity.enrol

import android.content.Intent
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.DeviceUtils
import com.blackmirror.dongda.utils.OSSUtils
import com.facebook.drawee.view.SimpleDraweeView

class EnrolServiceActivity : BaseActivity() {

    private lateinit var iv_back: ImageView
    private lateinit var iv_service_location: TextView
    private lateinit var sv_service_photo: SimpleDraweeView
    private lateinit var tv_service_brand: TextView
    private lateinit var btn_start_enrol: Button

    private var service_id: String? = null

    override val layoutResId: Int
        get() = R.layout.activity_enrol_service


    override fun initInject() {

    }

    override fun initView() {
        iv_back = findViewById(R.id.iv_back)
        iv_service_location = findViewById(R.id.tv_service_location)
        sv_service_photo = findViewById(R.id.sv_service_photo)
        tv_service_brand = findViewById(R.id.tv_service_brand)
        btn_start_enrol = findViewById(R.id.btn_start_enrol)
    }

    override fun initData() {

        val locations = intent.getStringExtra("locations")
        val address = intent.getStringExtra("address")
        val service_leaf = intent.getStringExtra("service_leaf")
        val service_image = intent.getStringExtra("service_image")
        service_id = intent.getStringExtra("service_id")
        iv_service_location.text = address
        sv_service_photo.setImageURI(OSSUtils.getSignedUrl(service_image))
        tv_service_brand.text = service_leaf
    }

    override fun initListener() {

        iv_back.setOnClickListener { finish() }

        btn_start_enrol.setOnClickListener {
            val intent = Intent(this@EnrolServiceActivity, EnrolAgeActivity::class.java)
            intent.putExtra("service_id", service_id)
            intent.putExtra("locations", getIntent().getStringExtra("locations"))
            intent.putExtra("service_leaf", getIntent().getStringExtra("service_leaf"))
            intent.putExtra("service_image", getIntent().getStringExtra("service_image"))
            intent.putExtra("address", getIntent().getStringExtra("address"))
            startActivity(intent)
        }
    }

    override fun setStatusBarColor() {
        DeviceUtils.setStatusBarColor(this, resources.getColor(R.color.enrol_bg))
    }
}
