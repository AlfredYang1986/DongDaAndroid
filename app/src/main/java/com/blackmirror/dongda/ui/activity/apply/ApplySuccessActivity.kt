package com.blackmirror.dongda.ui.activity.apply

import android.content.Intent
import android.os.Build
import android.support.constraint.ConstraintLayout
import android.widget.ImageView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.ui.activity.UserInfoActivity
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.DensityUtils
import com.blackmirror.dongda.utils.DeviceUtils

class ApplySuccessActivity : BaseActivity() {

    private lateinit var cl_mp_root: ConstraintLayout
    private lateinit var iv_back: ImageView

    override val layoutResId: Int
        get() = R.layout.activity_apply_success


    override fun initInject() {

    }

    override fun initView() {
        cl_mp_root = findViewById(R.id.cl_mp_root)
        iv_back = findViewById(R.id.iv_back)
    }

    override fun initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cl_mp_root.elevation = DensityUtils.dp2px(6).toFloat()
        }
    }

    override fun initListener() {
        iv_back.setOnClickListener {
            val intent = Intent(this@ApplySuccessActivity, UserInfoActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }

    override fun setStatusBarColor() {
        DeviceUtils.initSystemBarColor(this)
    }

    override fun onBackPressed() {
        val intent = Intent(this, UserInfoActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        super.onBackPressed()
    }
}
