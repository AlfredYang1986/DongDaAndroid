package com.blackmirror.dongda.ui.activity.apply

import android.content.Intent
import android.widget.Button
import android.widget.ImageView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.DeviceUtils

class ApplyActivity : BaseActivity() {

    private lateinit var iv_back: ImageView
    private lateinit var btn_apply_account: Button

    override val layoutResId: Int
        get() = R.layout.activity_apply


    override fun initInject() {

    }

    override fun initView() {
        iv_back = findViewById(R.id.iv_back)
        btn_apply_account = findViewById(R.id.btn_apply_account)
    }

    override fun initData() {

    }

    override fun initListener() {
        iv_back.setOnClickListener { finish() }

        btn_apply_account.setOnClickListener { startActivity(Intent(this@ApplyActivity, ApplyNameActivity::class.java)) }
    }

    override fun setStatusBarColor() {
        DeviceUtils.initSystemBarColor(this)
    }
}
