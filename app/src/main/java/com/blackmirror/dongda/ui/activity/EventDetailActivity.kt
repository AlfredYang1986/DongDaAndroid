package com.blackmirror.dongda.ui.activity

import android.support.constraint.ConstraintLayout
import android.support.design.widget.AppBarLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.CardView
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import com.amap.api.maps2d.MapView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.DeviceUtils
import com.facebook.drawee.view.SimpleDraweeView

class EventDetailActivity : BaseActivity() {

    lateinit var abl_root: AppBarLayout
    lateinit var vp_event_photo: ViewPager
    lateinit var tv_event_photo_page: TextView
    lateinit var tb_toolbar: Toolbar
    lateinit var iv_back: ImageView
    lateinit var iv_event_share: ImageView
    lateinit var iv_event_love: ImageView
    lateinit var tv_event_dec: TextView
    lateinit var tv_event_name: TextView
    lateinit var tv_event_exp_age: TextView
    lateinit var tv_event_exp_date: TextView
    lateinit var tv_event_exp_loc: TextView
    lateinit var tv_event_exp_dur: TextView
    lateinit var cv_event_exp_brand: CardView
    lateinit var sv_brand_logo: SimpleDraweeView
    lateinit var tv_brand_name: TextView
    lateinit var tv_brand_dec: TextView
    lateinit var iv_enter: ImageView
    lateinit var tv_exp_loc_num: TextView
    lateinit var tv_enter_loc_detail: TextView
    lateinit var iv_enter_loc_detail: ImageView
    lateinit var mv_event_exp_loc: MapView
    lateinit var tv_event_exp_loc_detail: TextView
    lateinit var tv_event_exp_loc_qu: TextView
    lateinit var tv_exp_money_free_dec: TextView
    lateinit var tv_event_exp_num: TextView
    lateinit var cl_join_root: ConstraintLayout

    override fun initView() {
        abl_root = findViewById(R.id.abl_root)
        vp_event_photo = findViewById(R.id.vp_event_photo)
        tv_event_photo_page = findViewById(R.id.tv_event_photo_page)
        tb_toolbar = findViewById(R.id.tb_toolbar)
        iv_back = findViewById(R.id.iv_back)
        iv_event_share = findViewById(R.id.iv_event_share)
        iv_event_love = findViewById(R.id.iv_event_love)
        tv_event_dec = findViewById(R.id.tv_event_dec)
        tv_event_name = findViewById(R.id.tv_event_name)
        tv_event_exp_age = findViewById(R.id.tv_event_exp_age)
        tv_event_exp_date = findViewById(R.id.tv_event_exp_date)
        tv_event_exp_loc = findViewById(R.id.tv_event_exp_loc)
        tv_event_exp_dur = findViewById(R.id.tv_event_exp_dur)
        cv_event_exp_brand = findViewById(R.id.cv_event_exp_brand)
        sv_brand_logo = findViewById(R.id.sv_brand_logo)
        tv_brand_name = findViewById(R.id.tv_brand_name)
        tv_brand_dec = findViewById(R.id.tv_brand_dec)
        iv_enter = findViewById(R.id.iv_enter)
        tv_exp_loc_num = findViewById(R.id.tv_exp_loc_num)
        tv_enter_loc_detail = findViewById(R.id.tv_enter_loc_detail)
        iv_enter_loc_detail = findViewById(R.id.iv_enter_loc_detail)
        mv_event_exp_loc = findViewById(R.id.mv_event_exp_loc)
        tv_event_exp_loc_detail = findViewById(R.id.tv_event_exp_loc_detail)
        tv_event_exp_loc_qu = findViewById(R.id.tv_event_exp_loc_qu)
        tv_exp_money_free_dec = findViewById(R.id.tv_exp_money_free_dec)
        tv_event_exp_num = findViewById(R.id.tv_event_exp_num)
        cl_join_root = findViewById(R.id.cl_join_root)
    }

    override val layoutResId: Int
        get() = R.layout.activity_event_detail

    override fun initInject() {
    }

    override fun initData() {
    }

    override fun initListener() {
    }

    override fun setStatusBarColor() {
        DeviceUtils.initSystemBarColor(this)
    }

}
