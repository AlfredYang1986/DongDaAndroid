package com.blackmirror.dongda.ui.activity.apply

import android.widget.ImageView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.ui.view.AlignTextView

class ServiceHelpActivity : BaseActivity() {

    private lateinit var iv_back: ImageView

    override val layoutResId: Int
        get() = R.layout.activity_service_help

    override fun initInject() {

    }

    override fun initView() {
        iv_back = findViewById(R.id.iv_back)
        val tv = findViewById<AlignTextView>(R.id.tv_help_care_dec)
        tv.text = "发斯蒂芬斯蒂芬斯蒂芬是个十分松开安提供更多是否公平和梵蒂冈电饭锅"
    }

    override fun initData() {

    }

    override fun initListener() {
        iv_back.setOnClickListener { finish() }
    }
}
