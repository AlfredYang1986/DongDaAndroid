package com.blackmirror.dongda.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.MapView
import com.amap.api.maps2d.model.BitmapDescriptorFactory
import com.amap.api.maps2d.model.LatLng
import com.amap.api.maps2d.model.MarkerOptions
import com.blackmirror.dongda.R
import com.blackmirror.dongda.adapter.AddrDecInfoAdapter
import com.blackmirror.dongda.adapter.PhotoDetailAdapter
import com.blackmirror.dongda.di.component.DaggerServiceDetailInfoComponent
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.kdomain.model.DetailInfoDomainBean
import com.blackmirror.dongda.kdomain.model.LikePopDomainBean
import com.blackmirror.dongda.kdomain.model.LikePushDomainBean
import com.blackmirror.dongda.model.DetailInfoServiceImagesBean
import com.blackmirror.dongda.model.SafeUiBean
import com.blackmirror.dongda.model.ServiceDetailPhotoBean
import com.blackmirror.dongda.model.ServiceProfileBean
import com.blackmirror.dongda.presenter.DetailInfoPresenter
import com.blackmirror.dongda.ui.Contract
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.ui.view.SlidingTabLayout
import com.blackmirror.dongda.utils.*
import com.facebook.drawee.view.SimpleDraweeView
import java.util.*

class ServiceDetailInfoActivity : BaseActivity(), View.OnClickListener, Contract.DetailInfoView {

    private lateinit var vp_detail_photo: ViewPager
    private lateinit var tl_detail_tab: SlidingTabLayout
    private lateinit var rv_addr_safe: RecyclerView
    private lateinit var tv_detail_content: TextView
    private lateinit var tv_detail_course: TextView
    private lateinit var tv_detail_type: TextView
    private lateinit var tv_age_range: TextView
    private lateinit var tv_class_mb_no: TextView
    private lateinit var tv_t_s_ratio: TextView
    private lateinit var tv_course_name: TextView
    private lateinit var tv_teacher_name: TextView
    private lateinit var tv_service_dec: TextView
    private lateinit var tv_dec_more: TextView
    private lateinit var tv_service_dec_content: TextView
    private lateinit var tv_service_dec_content_more: TextView
    private lateinit var tv_service_type: TextView
    private lateinit var tv_service_location: TextView
    private lateinit var tv_brand_tag: TextView
    private lateinit var iv_detail_back: ImageView
    private lateinit var iv_detail_like: ImageView
    private lateinit var iv_detail_class: ImageView
    private lateinit var iv_detail_teacher: ImageView
    private lateinit var tv_detail_class_dec: TextView
    private lateinit var tv_detail_teacher_dec: TextView
    private lateinit var mv_detail_location: MapView
    private lateinit var ctl_root: CoordinatorLayout
    private lateinit var tb_toolbar: Toolbar
    private lateinit var iv_detail_tb_back: ImageView
    private lateinit var iv_detail_tb_like: ImageView
    private lateinit var cl_tb_content: ConstraintLayout
    private lateinit var sv_teacher_bg: SimpleDraweeView
    private lateinit var view_line_4: View
    private lateinit var abl_root: AppBarLayout
    private lateinit var tv_addr_safe: TextView

    private var aMap: AMap? = null
    private var service_id: String? = null
    private var markerOption: MarkerOptions? = null
    private var isNeedRefresh: Boolean = false
    private var status: Int = 0//1 展开 2 关闭 3 中间状态
    private var randomInt: Int = 0
    private var presenter: DetailInfoPresenter? = null
    private var bean: DetailInfoDomainBean? = null
    private var isLike: Boolean = false

    override val layoutResId: Int
        get() = R.layout.activity_detail_info

    override fun init(savedInstanceState: Bundle?) {
        service_id = intent.getStringExtra("service_id")
        initView(savedInstanceState)
    }

    override fun initInject() {
        presenter = DaggerServiceDetailInfoComponent.builder()
                .activity(this)
                .view(this)
                .build()
                .detailInfoPresenter
    }

    override fun initView() {

    }

    private fun initView(savedInstanceState: Bundle?) {
        title = ""
        ctl_root = findViewById(R.id.ctl_root)
        vp_detail_photo = findViewById(R.id.vp_detail_photo)
        tl_detail_tab = findViewById(R.id.tl_detail_tab)
        rv_addr_safe = findViewById(R.id.rv_addr_safe)
        tv_detail_content = findViewById(R.id.tv_detail_content)
        tv_detail_course = findViewById(R.id.tv_detail_course)
        tv_detail_type = findViewById(R.id.tv_detail_type)
        tv_age_range = findViewById(R.id.tv_age_range)
        tv_class_mb_no = findViewById(R.id.tv_class_mb_no)
        tv_t_s_ratio = findViewById(R.id.tv_t_s_ratio)
        tv_course_name = findViewById(R.id.tv_course_name)
        tv_teacher_name = findViewById(R.id.tv_teacher_name)
        tv_service_dec = findViewById(R.id.tv_service_dec)
        tv_dec_more = findViewById(R.id.tv_dec_more)
        tv_service_dec_content = findViewById(R.id.tv_service_dec_content)
        tv_service_dec_content_more = findViewById(R.id.tv_service_dec_content_more)
        tv_service_type = findViewById(R.id.tv_service_type)
        tv_service_location = findViewById(R.id.tv_service_location)
        tv_brand_tag = findViewById(R.id.tv_brand_tag)
        iv_detail_back = findViewById(R.id.iv_detail_back)
        iv_detail_like = findViewById(R.id.iv_detail_like)
        iv_detail_class = findViewById(R.id.iv_detail_class)
        iv_detail_teacher = findViewById(R.id.iv_detail_teacher)
        tv_detail_class_dec = findViewById(R.id.tv_detail_class_dec)
        tv_detail_teacher_dec = findViewById(R.id.tv_detail_teacher_dec)
        mv_detail_location = findViewById(R.id.mv_detail_location)
        tb_toolbar = findViewById(R.id.tb_toolbar)
        abl_root = findViewById(R.id.abl_root)
        iv_detail_tb_back = findViewById(R.id.iv_detail_tb_back)
        iv_detail_tb_like = findViewById(R.id.iv_detail_tb_like)
        cl_tb_content = findViewById(R.id.cl_tb_content)
        sv_teacher_bg = findViewById(R.id.sv_teacher_bg)
        tv_addr_safe = findViewById(R.id.tv_addr_safe)
        view_line_4 = findViewById(R.id.view_line_4)
        setSupportActionBar(tb_toolbar)
        initMapView(savedInstanceState)
    }

    private fun initMapView(savedInstanceState: Bundle?) {
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mv_detail_location.onCreate(savedInstanceState)
        if (aMap == null) {
            aMap = mv_detail_location.map
        }
        aMap!!.uiSettings.isZoomControlsEnabled = false
        aMap!!.uiSettings.setAllGesturesEnabled(false)
        /* if (aMap == null) {
            aMap = mv_detail_location.getMap();
        }
//        aMap.getUiSettings().setZoomControlsEnabled(false);
//        aMap.getUiSettings().setAllGesturesEnabled(false);
        //设置默认缩放比例 3-19
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));*/
    }

    override fun initData() {
        presenter?.getDetailInfo(service_id!!)
    }

    override fun initListener() {
        iv_detail_back.setOnClickListener(this)
        iv_detail_tb_back.setOnClickListener(this)
        tv_dec_more.setOnClickListener(this)
        iv_detail_like.setOnClickListener(this)
        iv_detail_tb_like.setOnClickListener(this)
        sv_teacher_bg.setOnClickListener(this)
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
                    hideTb()
                }
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_detail_tb_back, R.id.iv_detail_back -> {
                setResult(if (isNeedRefresh) Activity.RESULT_OK else Activity.RESULT_CANCELED, intent.putExtra("is_like", isLike))
                finish()
            }
            R.id.tv_dec_more -> if (tv_dec_more.text == getString(R.string.open_dec_more)) {
                tv_dec_more.setText(R.string.close_dec_more)
                tv_service_dec_content.maxLines = 100
                tv_service_dec_content.text = bean!!.description
            } else {
                tv_dec_more.text = getString(R.string.open_dec_more)
                tv_service_dec_content.maxLines = 4
                tv_service_dec_content.text = bean!!.description
            }
            R.id.iv_detail_tb_like, R.id.iv_detail_like -> if (bean != null && bean!!.isSuccess) {
                sendLikeData()
            }
            R.id.sv_teacher_bg -> {
                val sb = ServiceProfileBean()
                val intent = Intent(this@ServiceDetailInfoActivity, ServiceProfileActivity::class.java)
                if (bean != null && bean!!.isSuccess) {

                    sb.res_id = randomInt
                    sb.brand_tag = bean!!.brand_tag
                    sb.brand_name = bean!!.brand_name
                    sb.about_brand = bean!!.about_brand

                }
                intent.putExtra("bean", sb)
                startActivity(intent)
            }
        }
    }

    override fun onGetDetailInfoSuccess(dbean: DetailInfoDomainBean) {
        closeProcessDialog()
        bean = dbean
        setData(dbean)
    }


    override fun onLikePushSuccess(bean: LikePushDomainBean) {
        isNeedRefresh = true
        closeProcessDialog()
        this.bean!!.is_collected = true
        isLike = true
        iv_detail_like.setImageResource(R.drawable.like_selected)
    }

    override fun onLikePopSuccess(bean: LikePopDomainBean) {
        isNeedRefresh = true
        closeProcessDialog()
        this.bean!!.is_collected = false
        isLike = false
        iv_detail_like.setImageResource(R.drawable.home_art_like)
    }

    override fun onGetDataError(bean: BaseDataBean) {
        closeProcessDialog()
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(ctl_root, bean.message)
        } else {
            ToastUtils.showShortToast(bean.message + "(" + bean.code + ")")
        }
    }

    private fun showTb() {
        DeviceUtils.setStatusBarColor(this)
        tb_toolbar.visibility = View.VISIBLE
        tb_toolbar.setBackgroundColor(resources.getColor(R.color.sys_bar_white))
        cl_tb_content.visibility = View.VISIBLE
    }

    private fun hideTb() {
        DeviceUtils.initSystemBarColor(this)
        tb_toolbar.visibility = View.GONE
        tb_toolbar.setBackgroundColor(Color.TRANSPARENT)
        cl_tb_content.visibility = View.GONE
    }


    private fun addMarkers(latitude: Double, longitude: Double) {

        //设置默认缩放比例 3-19
        val latLng = LatLng(latitude, longitude)
        aMap!!.moveCamera(CameraUpdateFactory.changeLatLng(latLng))
        aMap!!.moveCamera(CameraUpdateFactory.zoomTo(14f))
        markerOption = MarkerOptions()
        markerOption!!.position(latLng)
        markerOption!!.icon(BitmapDescriptorFactory
                .fromResource(R.drawable.map_icon_art_normal))
        markerOption!!.anchor(0.5f, 0.5f)
        aMap!!.addMarker(markerOption)
    }

    private fun setData(bean: DetailInfoDomainBean) {

        if (bean.is_collected) {
            iv_detail_like.setImageResource(R.drawable.like_selected)
        } else {
            iv_detail_like.setImageResource(R.drawable.home_art_like)
        }

        val tabList = ArrayList<ServiceDetailPhotoBean>()


        for (image in bean.location_images!!) {
            tabList.add(ServiceDetailPhotoBean(image.tag, image.image))
            tl_detail_tab.addTab(tl_detail_tab.newTab().setText(image.tag))
        }

        val array = ArrayList<DetailInfoServiceImagesBean>()

        for (image in bean.service_images!!) {

            if (StringUtils.isNumber(image.tag)) {
                val sb = DetailInfoServiceImagesBean()
                sb.image = image.image
                sb.tag = image.tag
                array.add(sb)
            } else {
                tabList.add(ServiceDetailPhotoBean(image.tag, image.image))
                tl_detail_tab.addTab(tl_detail_tab.newTab().setText(image.tag))
            }
        }

        Collections.sort(array)

        for (i in array.indices) {
            tabList.add(ServiceDetailPhotoBean(array[i].tag, array[i].image))
            tl_detail_tab.addTab(tl_detail_tab.newTab().setText(array[i].tag))
        }

        val adapter = PhotoDetailAdapter(tabList)

        vp_detail_photo.adapter = adapter
        //        vp_detail_photo.setOffscreenPageLimit(bean.service.location.location_images.size());
        //下面的方法可以解决tab字体丢失的问题,不要用setupWithViewPager()方法
        //        tl_detail_tab.setupWithViewPager(vp_detail_photo);
        vp_detail_photo.addOnPageChangeListener(SlidingTabLayout.MyTabLayoutOnPageChangeListener(tl_detail_tab))
        tl_detail_tab.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(vp_detail_photo))
        tl_detail_tab.initIcon()

        tv_detail_content.text = "\"" + bean.punchline + "\""//第一个描述

        val sb = StringBuilder()

        if (bean.service_type!!.contains(getString(R.string.str_care))) {
            sb.append(bean.service_leaf)
            tv_detail_type.visibility = View.VISIBLE
        } else {
            sb.append(bean.service_type)
                    .append("·")
                    .append(bean.service_leaf)
                    .append(bean.category)
            tv_detail_type.visibility = View.GONE
        }

        tv_detail_course.text = sb.toString()//课程
        tv_detail_type.text = bean.service_tags!![0]//名校团队

        val range = StringBuilder()
        range.append(bean.min_age)
                .append("-")
                .append(bean.max_age)
                .append(getString(R.string.str_age))
        tv_age_range.text = range.toString()//年龄范围

        checkClassMaxStu(bean.class_max_stu)

        tv_class_mb_no.text = bean.class_max_stu.toString() + ""//班级人数

        val m = CalUtils.getGongyue(bean.class_max_stu, bean.teacher_num)
        val radio = StringBuilder()
        radio.append(bean.teacher_num / m)
                .append(":")
                .append(bean.class_max_stu)
        tv_t_s_ratio.text = radio.toString()


        tv_course_name.text = bean.brand_name//课程名称
        tv_teacher_name.text = String.format(getString(R.string.str_teacher), bean.brand_name)//

        val random = Random()
        randomInt = random.nextInt(10)
        randomInt = if (randomInt > 9 || randomInt < 0) 0 else randomInt
        sv_teacher_bg.setImageURI(OtherUtils.resourceIdToUri(this@ServiceDetailInfoActivity, AppConstant.teacher_bg_res_id[randomInt]))

        tv_brand_tag.text = bean.brand_tag//

        tv_service_dec_content.text = bean.description

        tv_service_dec_content_more.text = bean.about_brand

        val type = StringBuilder()
        for (i in bean.operation!!.indices) {
            type.append("#")
                    .append(bean.operation!![i])
                    .append("# ")
        }
        tv_service_type.text = type.toString()//

        val list = ArrayList<SafeUiBean>()
        for (i in bean.friendliness!!.indices) {
            val s = bean.friendliness!![i]
            val b = SafeUiBean()
            b.dec = s
            if (s == getString(R.string.new_wind_sys)) {
                b.res_id = R.drawable.new_wind_sys
            } else if (s == getString(R.string.air_clear_sys)) {
                b.res_id = R.drawable.air_clear_sys
            } else if (s == getString(R.string.safe_power)) {
                b.res_id = R.drawable.safe_power
            } else if (s == getString(R.string.real_time_protect)) {
                b.res_id = R.drawable.real_time_protect
            } else if (s == getString(R.string.has_wifi)) {
                b.res_id = R.drawable.wifi
            } else if (s == getString(R.string.protect_floor)) {
                b.res_id = R.drawable.floor
            } else if (s == getString(R.string.air_humid)) {
                b.res_id = R.drawable.air_humid
            } else if (s == getString(R.string.safe_guard)) {
                b.res_id = R.drawable.guard
            } else if (s == getString(R.string.kit_package)) {
                b.res_id = R.drawable.kit
            } else if (s == getString(R.string.safe_table)) {
                b.res_id = R.drawable.safe_table
            }
            if (s != "") {
                list.add(b)
            }
        }

        if (list.isEmpty()) {
            tv_addr_safe.visibility = View.GONE
            view_line_4.visibility = View.GONE
            rv_addr_safe.visibility = View.GONE
        } else {
            tv_addr_safe.visibility = View.VISIBLE
            view_line_4.visibility = View.VISIBLE
            rv_addr_safe.visibility = View.VISIBLE
            val decInfoAdapter = AddrDecInfoAdapter(this@ServiceDetailInfoActivity, list)
            val manager = LinearLayoutManager(this@ServiceDetailInfoActivity)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            rv_addr_safe.layoutManager = manager
            rv_addr_safe.adapter = decInfoAdapter
        }

        tv_service_location.text = bean.address

        addMarkers(bean.latitude, bean.longitude)
    }

    /**
     * 检查人数是否正确 服务器返回了-1
     *
     * @param max_stu
     */
    private fun checkClassMaxStu(max_stu: Int) {
        if (max_stu <= -1) {//隐藏
            iv_detail_class.visibility = View.GONE
            tv_detail_class_dec.visibility = View.GONE
            tv_class_mb_no.visibility = View.GONE
            iv_detail_teacher.visibility = View.GONE
            tv_detail_teacher_dec.visibility = View.GONE
            tv_t_s_ratio.visibility = View.GONE
        } else {
            iv_detail_class.visibility = View.VISIBLE
            tv_detail_class_dec.visibility = View.VISIBLE
            tv_class_mb_no.visibility = View.VISIBLE
            iv_detail_teacher.visibility = View.VISIBLE
            tv_detail_teacher_dec.visibility = View.VISIBLE
            tv_t_s_ratio.visibility = View.VISIBLE
        }
    }

    private fun sendLikeData() {
        showProcessDialog()
        if (bean!!.is_collected) {//已收藏 点击取消
            presenter?.likePop(bean!!.service_id!!)
        } else {
            presenter?.likePush(bean!!.service_id!!)
        }
    }

    override fun onResume() {
        super.onResume()
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mv_detail_location.onResume()
    }

    override fun onPause() {
        super.onPause()
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mv_detail_location.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mv_detail_location.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mv_detail_location.onSaveInstanceState(outState)
    }


    override fun onBackPressed() {
        setResult(if (isNeedRefresh) Activity.RESULT_OK else Activity.RESULT_CANCELED, intent.putExtra("is_like", isLike))
        super.onBackPressed()

    }

    override fun setStatusBarColor() {
        DeviceUtils.initSystemBarColor(this)
    }

}
