package com.blackmirror.dongda.ui.activity

import android.graphics.Color
import android.view.View
import com.blackmirror.dongda.R
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.AppConstant
import com.blackmirror.dongda.utils.initSystemBarColor
import com.blackmirror.dongda.utils.setStatusBarColor
import kotlinx.android.synthetic.main.activity_event_detail.*

class EventDetailActivity : BaseActivity() {

    private var status: Int = 0//1 展开 2 关闭 3 中间状态


    override val layoutResId: Int
        get() = R.layout.activity_event_detail

    override fun initView() {
        title=""
        setSupportActionBar(tb_toolbar)
    }

    override fun initInject() {
    }

    override fun initData() {
    }

    override fun initListener() {
        abl_root.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset == 0) {//展开状态
                if (status != AppConstant.OPEN_STATUS) {
                    status = AppConstant.OPEN_STATUS
                    hideTb()
                }
            } else if (Math.abs(verticalOffset) >= appBarLayout.totalScrollRange) {//折叠状态
                if (status != AppConstant.CLOSE_STATUS) {
                    status = AppConstant.CLOSE_STATUS
                    showTb()
                }
            } else {//其他状态
                if (status != AppConstant.OTHER_STATUS) {
                    status = AppConstant.OTHER_STATUS
                    showTb()
                }
            }
        }
    }

    private fun showTb() {
        setStatusBarColor(this)
        tb_toolbar.visibility = View.VISIBLE
        tb_toolbar.setBackgroundColor(resources.getColor(R.color.sys_bar_white))
        cl_tb_content.visibility = View.VISIBLE
    }

    private fun hideTb() {
        initSystemBarColor(this)
        tb_toolbar.visibility = View.GONE
        tb_toolbar.setBackgroundColor(Color.TRANSPARENT)
        cl_tb_content.visibility = View.GONE
    }

    override fun setStatusBarColor() {
        initSystemBarColor(this)
    }

}
