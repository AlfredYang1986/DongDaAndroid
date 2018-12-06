package com.blackmirror.dongda.ui.activity

import com.blackmirror.dongda.R
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.DeviceUtils

class LocationDetailActivity : BaseActivity() {

    override val layoutResId: Int
        get() = R.layout.activity_location_detail

    override fun initInject() {
    }

    override fun initView() {
    }

    override fun initData() {
    }

    override fun initListener() {
    }

    override fun setStatusBarColor() {
        DeviceUtils.initSystemBarColor(this)
    }

}
