package com.blackmirror.dongda.ui.activity.homeActivity

import android.os.Build
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.widget.ImageView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.ui.fragment.GrowthFragment
import com.blackmirror.dongda.ui.fragment.HomeFragment
import com.blackmirror.dongda.ui.fragment.UserFragment
import com.blackmirror.dongda.ui.view.MyFragmentTabHost
import com.blackmirror.dongda.utils.DensityUtils

/**
 * Create by Ruge at 2018-09-20
 */

class HomeActivity : BaseActivity() {

    lateinit var iv_home_like: ImageView
    lateinit var fth_content: MyFragmentTabHost

    override val layoutResId: Int
        get() = R.layout.activity_home

    override fun initInject() {
    }

    override fun initView() {
//        iv_home_like = findViewById(R.id.iv_home_like)
        fth_content = findViewById(R.id.fth_content)
    }

    override fun initData() {
        initFragmentTabHost()

    }

    override fun initListener() {
        /*iv_home_like.setOnClickListener {
            finish()
        }*/
    }

    private fun initFragmentTabHost() {

        //设置与FrameLayout关联
        fth_content.setup(this,supportFragmentManager,android.R.id.tabcontent)

        if (Build.VERSION.SDK_INT > 10) {
            fth_content.tabWidget.showDividers=0
        }

        //创建TabSpec
        val ts1=fth_content.newTabSpec("growth")
        val ts2=fth_content.newTabSpec("home")
        val ts3=fth_content.newTabSpec("user")


        //自定义指示器布局
        val v1= layoutInflater.inflate(R.layout.fth_item_content,null,false)
        val v2= layoutInflater.inflate(R.layout.fth_item_content,null,false)
        val v3= layoutInflater.inflate(R.layout.fth_item_content,null,false)

        val i1 = v1.findViewById<ImageView>(R.id.iv_photo)
        i1.setBackgroundResource(R.drawable.icon_tabbar_growth_normal)
        val l1 = ConstraintLayout.LayoutParams(DensityUtils.dp2px(27), DensityUtils.dp2px(27))
        l1.topToTop = R.id.item_root
        l1.bottomToBottom = R.id.item_root
        l1.leftToLeft = R.id.item_root
        i1.layoutParams=l1

        val i2 = v2.findViewById<ImageView>(R.id.iv_photo)
        i2.setBackgroundResource(R.drawable.icon_tabbar_home_normal)
        val l2 = ConstraintLayout.LayoutParams(DensityUtils.dp2px(27), DensityUtils.dp2px(27))
        l2.topToTop = R.id.item_root
        l2.bottomToBottom = R.id.item_root
        l2.leftToLeft = R.id.item_root
        l2.rightToRight = R.id.item_root
        i2.layoutParams=l2

        val i3 = v3.findViewById<ImageView>(R.id.iv_photo)
        i3.setBackgroundResource(R.drawable.icon_tabbar_user_normal)
        val l3 = ConstraintLayout.LayoutParams(DensityUtils.dp2px(27), DensityUtils.dp2px(27))
        l3.topToTop = R.id.item_root
        l3.bottomToBottom = R.id.item_root
        l3.rightToRight = R.id.item_root
        i3.layoutParams=l3


        //设置自定义指示器
        ts1.setIndicator(v1)
        ts2.setIndicator(v2)
        ts3.setIndicator(v3)

        //添加TabSpec
        val b1=Bundle()
        val b2=Bundle()
        val b3=Bundle()

        fth_content.addTab(ts1,GrowthFragment::class.java,b1)
        fth_content.addTab(ts2,HomeFragment::class.java,b2)
        fth_content.addTab(ts3,UserFragment::class.java,b3)

        fth_content.currentTab = 1

    }

}
