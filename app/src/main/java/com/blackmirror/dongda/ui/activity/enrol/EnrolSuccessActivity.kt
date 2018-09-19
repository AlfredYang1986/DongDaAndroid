package com.blackmirror.dongda.ui.activity.enrol

import android.content.Intent
import android.widget.ImageView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.ui.base.BaseActivity

class EnrolSuccessActivity : BaseActivity() {

    private lateinit var iv_back: ImageView

    override val layoutResId: Int
        get() = R.layout.activity_enrol_success

    override fun initInject() {

    }

    override fun initView() {
        iv_back = findViewById(R.id.iv_back)
    }

    override fun initData() {

    }

    override fun initListener() {
        iv_back.setOnClickListener { jump2LocAllActivity() }
    }

    private fun jump2LocAllActivity() {
        val intent = Intent(this, LocAllServiceActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        val intent = Intent(this, LocAllServiceActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        super.onBackPressed()
    }
}
