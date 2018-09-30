package com.blackmirror.dongda.ui.activity

import android.support.v7.widget.LinearLayoutManager
import com.blackmirror.dongda.R
import com.blackmirror.dongda.adapter.WillStartAdapter
import com.blackmirror.dongda.kdomain.model.WillStartDomainBean
import com.blackmirror.dongda.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_will_start.*

class WillStartActivity : BaseActivity() {


    override val layoutResId: Int
        get() = R.layout.activity_will_start

    override fun initInject() {
    }

    override fun initView() {
    }

    override fun initData() {
        testData()
    }

    private fun testData() {
        rv_will_start.layoutManager=LinearLayoutManager(this)
        rv_will_start.adapter=WillStartAdapter(this, WillStartDomainBean())
    }

    override fun initListener() {
    }


}
