package com.blackmirror.dongda.ui.activity

import android.widget.ImageView
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.ui.base.BaseActivity
import com.facebook.drawee.view.SimpleDraweeView

class MyBrandActivity : BaseActivity() {

    private lateinit var iv_back: ImageView
    private lateinit var sv_brand_photo: SimpleDraweeView
    private lateinit var tv_brand_name: TextView
    private lateinit var tv_about_brand: TextView

    override val layoutResId: Int
        get() = R.layout.activity_my_brand

    override fun initInject() {

    }

    override fun initView() {
        iv_back = findViewById(R.id.iv_back)
        sv_brand_photo = findViewById(R.id.sv_brand_photo)
        tv_brand_name = findViewById(R.id.tv_brand_name)
        tv_about_brand = findViewById(R.id.tv_about_brand)
    }

    override fun initData() {

    }

    override fun initListener() {
        iv_back.setOnClickListener { finish() }
    }
}
