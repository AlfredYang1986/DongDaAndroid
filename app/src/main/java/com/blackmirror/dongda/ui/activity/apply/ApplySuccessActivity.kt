package com.blackmirror.dongda.ui.activity.apply

import android.content.Intent
import com.blackmirror.dongda.R
import com.blackmirror.dongda.ui.activity.UserInfoActivity
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.initSystemBarColor

class ApplySuccessActivity : BaseActivity() {



    override val layoutResId: Int
        get() = R.layout.activity_apply_success


    override fun initInject() {

    }

    override fun initView() {

    }

    override fun initData() {
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cl_mp_root.elevation = DensityUtils.dp2px(6).toFloat()
        }*/
    }

    override fun initListener() {

    }

    override fun setStatusBarColor() {
        initSystemBarColor(this)
    }

    override fun onBackPressed() {
        val intent = Intent(this, UserInfoActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        super.onBackPressed()
    }
}
