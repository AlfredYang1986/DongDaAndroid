package com.blackmirror.dongda.ui.activity.live

import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.ui.base.BaseActivity

class LiveListActivity : BaseActivity() {

    lateinit var iv_back: ImageView
    lateinit var rv_live_list: RecyclerView

    override val layoutResId: Int
        get() = R.layout.activity_live_list

    override fun initInject() {
    }

    override fun initView() {
        iv_back=findViewById(R.id.iv_back)
        rv_live_list=findViewById(R.id.rv_live_list)
    }

    override fun initData() {
    }

    override fun initListener() {
        iv_back.setOnClickListener {
            finish()
        }
    }

}
