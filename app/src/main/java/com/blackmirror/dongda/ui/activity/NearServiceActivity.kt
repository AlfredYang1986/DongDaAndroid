package com.blackmirror.dongda.ui.activity

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.location.AMapLocationQualityReport
import com.amap.api.maps2d.AMap
import com.amap.api.maps2d.CameraUpdateFactory
import com.amap.api.maps2d.MapView
import com.amap.api.maps2d.model.BitmapDescriptorFactory
import com.amap.api.maps2d.model.LatLng
import com.amap.api.maps2d.model.Marker
import com.amap.api.maps2d.model.MarkerOptions
import com.blackmirror.dongda.R
import com.blackmirror.dongda.di.component.DaggerNearServiceComponent
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.kdomain.model.NearServiceDomainBean
import com.blackmirror.dongda.presenter.NearServicePresenter
import com.blackmirror.dongda.ui.Contract
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.*
import com.facebook.drawee.view.SimpleDraweeView
import java.text.SimpleDateFormat
import java.util.*

class NearServiceActivity : BaseActivity(), Contract.NearServiceView {
    private lateinit var mv_near_map: MapView
    private lateinit var iv_current_location: ImageView
    private lateinit var iv_near_back: ImageView
    private lateinit var sv_near_photo: SimpleDraweeView
    private lateinit var tv_near_title: TextView
    private lateinit var tv_near_dec: TextView
    private lateinit var tv_near_location: TextView
    private lateinit var cl_root: ConstraintLayout

    private var animation: TranslateAnimation? = null
    private var popupWindow: PopupWindow? = null
    private var markers: MutableMap<String, Marker>? = null
    private var aMap: AMap? = null
    private var markerOption: MarkerOptions? = null
    private var view: View? = null
    private var lastClickMarker: String? = null
    private var dialog: AlertDialog? = null
    private var locMarkerId: String? = null
    private var presenter: NearServicePresenter? = null
    //声明AMapLocationClient类对象
    var locationClient: AMapLocationClient? = null
    //声明AMapLocationClientOption对象
    var locationOption: AMapLocationClientOption? = null

    //设置定位回调监听
    //mLocationClient.setLocationListener(mLocationListener);

    override val layoutResId: Int
        get() = R.layout.activity_near_service

    /**
     * 定位监听
     */
    internal var locationListener: AMapLocationListener = AMapLocationListener { location ->
        closeProcessDialog()

        //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
        if (location.errorCode == 0) {

            //定位类型  location.getLocationType()
            //经    度  location.getLongitude()
            //纬    度  location.getLatitude()

            val latitude = location.latitude
            val longitude = location.longitude

            aMap!!.uiSettings.isZoomControlsEnabled = false
            //设置默认缩放比例 3-19
            aMap!!.moveCamera(CameraUpdateFactory.zoomTo(13.2f))
            aMap!!.moveCamera(CameraUpdateFactory.changeLatLng(LatLng(latitude, longitude)))
            markerOption = MarkerOptions()
            markerOption!!.position(LatLng(latitude, longitude))
            markerOption!!.icon(BitmapDescriptorFactory
                    .fromResource(R.drawable.location_icon))
            markerOption!!.anchor(0.5f, 0.5f)
            val marker = aMap!!.addMarker(markerOption)
            locMarkerId = marker.id

            presenter?.getNearService(latitude, longitude)

        } else {
            val sb = StringBuilder()
            //定位失败
            sb.append("定位失败" + "\n")
            sb.append("错误码:" + location.errorCode + "\n")
            sb.append("错误信息:" + location.errorInfo + "\n")
            sb.append("错误描述:" + location.locationDetail + "\n")

            LogUtils.d(sb.toString())

            showLocationDialog(location.errorCode)

        }
    }

    override fun init(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
    }

    override fun initInject() {
        presenter = DaggerNearServiceComponent.builder()
                .activity(this)
                .view(this)
                .build()
                .nearServicePresenter

    }

    override fun initView() {

    }

    override fun initData() {
        initLocation()
        initAMap()
    }

    private fun initView(savedInstanceState: Bundle?) {
        iv_current_location = findViewById(R.id.iv_current_location)
        iv_near_back = findViewById(R.id.iv_near_back)
        //获取地图控件引用
        mv_near_map = findViewById(R.id.mv_near_map)
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mv_near_map.onCreate(savedInstanceState)
        markers = HashMap()
    }


    private fun initLocation() {
        showProcessDialog(getString(R.string.location_processing))
        //初始化client
        locationClient = AMapLocationClient(this.applicationContext)
        locationOption = AMapLocationClientOption()

        locationOption!!.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        locationOption!!.isGpsFirst = false//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        locationOption!!.httpTimeOut = 30000//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        locationOption!!.interval = 2000//可选，设置定位间隔。默认为2秒
        locationOption!!.isNeedAddress = true//可选，设置是否返回逆地理地址信息。默认是true
        locationOption!!.isOnceLocation = true//可选，设置是否单次定位。默认是false
        locationOption!!.isOnceLocationLatest = true//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP)//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        locationOption!!.isSensorEnable = false//可选，设置是否使用传感器。默认是false
        locationOption!!.isWifiScan = true //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        locationOption!!.isLocationCacheEnable = true //可选，设置是否使用缓存定位，默认为true

        //设置定位参数
        locationClient!!.setLocationOption(locationOption)
        // 设置定位监听
        locationClient!!.setLocationListener(locationListener)
        // 启动定位
        locationClient!!.startLocation()
    }

    private fun initAMap() {
        if (aMap == null) {
            aMap = mv_near_map.map
        }

    }

    override fun initListener() {

        iv_near_back.setOnClickListener {
            if (locationClient != null) {
                stopLocation()
                destroyLocation()
                finish()
            }
        }

        iv_current_location.setOnClickListener {
            closeProcessDialog()
            markers!!.clear()
            aMap!!.clear()
            showProcessDialog(getString(R.string.location_processing))
            closePopUpWindow()
            stopLocation()
            if (locationClient != null) {
                locationClient!!.startLocation()
            }
        }

        // 定义 Marker 点击事件监听
        // 绑定 Marker 被点击事件
        aMap!!.setOnMarkerClickListener { marker ->
            // marker 对象被点击时回调的接口
            // 返回 true 则表示接口已响应事件，否则返回false
            LogUtils.d("onclick " + marker.id)
            if (marker.id != locMarkerId) {
                refreshPopUpWindow(marker)
            }
            true
        }
    }

    private fun closePopUpWindow() {
        if (popupWindow != null && popupWindow!!.isShowing) {
            popupWindow!!.dismiss()
        }
    }

    private fun refreshPopUpWindow(marker: Marker) {


        //        LogUtils.d("is_select"+markers.get(marker.getId()).is_select);

        //重复点击
        if (marker.id == lastClickMarker) {
            return
        }

        val b1 = marker === markers!![marker.id]

        LogUtils.d("marker equals " + (marker == markers!![marker.id]))
        LogUtils.d("marker == $b1")

        view!!.clearAnimation()
        closePopUpWindow()
        showPopUpWindow()

        val b = marker.`object` as NearServiceDomainBean.ServicesBean

        marker.setIcon(BitmapDescriptorFactory.fromResource(getImageResId(b.service_type!!, true)))
        sv_near_photo.setImageURI(OSSUtils.getSignedUrl(b.service_image))
        tv_near_title.text = b.service_leaf
        val s = StringBuilder()
        if (b.service_leaf!!.contains(getString(R.string.str_care))) {
            s.append(b.brand_name)
                    .append(getString(R.string.str_de))
                    .append(b.service_leaf)
        } else {
            s.append(b.brand_name)
                    .append(getString(R.string.str_de))
                    .append(b.service_leaf)
                    .append(b.category)
        }
        tv_near_dec.text = s.toString()
        tv_near_location.text = b.address
        val l = markers!![lastClickMarker]?.getObject() as NearServiceDomainBean.ServicesBean
        markers!![lastClickMarker]?.setIcon(BitmapDescriptorFactory.fromResource(getImageResId(l.service_type!!, false)))

        lastClickMarker = marker.id

    }

    private fun getImageResId(serviceType: String, isSelect: Boolean): Int {
        if (serviceType == getString(R.string.str_care)) {
            return if (isSelect) R.drawable.map_icon_day_care_select else R.drawable.map_icon_day_care_normal
        }
        if (serviceType == getString(R.string.str_art)) {
            return if (isSelect) R.drawable.map_icon_art_select else R.drawable.map_icon_art_normal
        }
        if (serviceType == getString(R.string.str_sport)) {
            return if (isSelect) R.drawable.map_icon_sport_select else R.drawable.map_icon_sport_normal
        }
        return if (serviceType == getString(R.string.str_science)) {
            if (isSelect) R.drawable.map_icon_science_select else R.drawable.map_icon_science_normal
        } else R.drawable.map_icon_day_care_normal
    }

    override fun onGetNearServiceSuccess(bean: NearServiceDomainBean) {
        closeProcessDialog()
        addMarkers(bean)
    }

    override fun onGetDataError(bean: BaseDataBean) {
        closeProcessDialog()
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(iv_current_location, bean.message)
        } else {
            ToastUtils.showShortToast("${bean.message}(${bean.code})")
        }
    }

    private fun addMarkers(bean: NearServiceDomainBean) {

        showPopUpWindow()
        for (i in bean.services!!.indices) {

            markerOption = MarkerOptions()
            markerOption!!.position(LatLng(bean.services!![i].pin!!.latitude, bean.services!![i].pin!!.longitude))
            markerOption!!.anchor(0.5f, 0.5f)//必须加这个

            val marker = aMap!!.addMarker(markerOption)
            marker.`object` = bean.services!![i]
            //            LogUtils.d("xcx","markerId== "+marker.getId());
            if (i == 0) {//展示第一个
                lastClickMarker = marker.id
                val sb = bean.services!![0]
                marker.setIcon(BitmapDescriptorFactory.fromResource(getImageResId(sb.service_type!!, true)))
                sv_near_photo.setImageURI(OSSUtils.getSignedUrl(sb.service_image))
                tv_near_title.text = sb.service_leaf
                val s = StringBuilder()
                if (sb.service_leaf!!.contains("看顾")) {
                    s.append(sb.brand_name)
                            .append("的")
                            .append(sb.service_leaf)
                } else {
                    s.append(sb.brand_name)
                            .append("的")
                            .append(sb.service_leaf)
                            .append(sb.category)
                }
                tv_near_dec.text = s.toString()
                tv_near_location.text = sb.address
            } else {
                marker.setIcon(BitmapDescriptorFactory
                        .fromResource(getImageResId(bean.services!![i].service_type!!, false)))
            }
            markers!![marker.id] = marker
        }

    }

    private fun showPopUpWindow() {
        if (view == null) {
            view = View.inflate(this@NearServiceActivity, R.layout.dialog_near_service, null)
            cl_root = view!!.findViewById(R.id.cl_root)
            sv_near_photo = view!!.findViewById(R.id.sv_near_photo)
            tv_near_title = view!!.findViewById(R.id.tv_near_title)
            tv_near_dec = view!!.findViewById(R.id.tv_near_dec)
            tv_near_location = view!!.findViewById(R.id.tv_near_location)
        }
        if (popupWindow == null) {
            popupWindow = PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            // 取得焦点
            // true时，点击返回键先消失 PopupWindow
            // 但是设置为true时setOutsideTouchable，setTouchable方法就失效了（点击外部不消失，内容区域也不响应事件）
            // false时PopupWindow不处理返回键
            popupWindow!!.isFocusable = false
            //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
            popupWindow!!.setBackgroundDrawable(BitmapDrawable())
            //点击外部消失 setOutsideTouchable设置生效的前提是setTouchable(true)和setFocusable(false)
            popupWindow!!.isOutsideTouchable = false
            //设置可以点击 设置为true之后，PopupWindow内容区域 才可以响应点击事件
            popupWindow!!.isTouchable = true
            animation = TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0f,
                    Animation.RELATIVE_TO_PARENT, 1f, Animation.RELATIVE_TO_PARENT, 0f)
            animation!!.interpolator = AccelerateDecelerateInterpolator()
            animation!!.duration = 300
        }

        popupWindow!!.showAtLocation(view, Gravity.BOTTOM, 0, 0)
        view!!.startAnimation(animation)
    }

    override fun onResume() {
        super.onResume()
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mv_near_map.onResume()
    }

    override fun onPause() {
        super.onPause()
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mv_near_map.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mv_near_map.onDestroy()
        stopLocation()
        destroyLocation()
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
            dialog = null
        }
    }

    /**
     * 停止定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private fun stopLocation() {
        // 停止定位
        if (locationClient != null) {
            locationClient!!.stopLocation()
        }
    }

    /**
     * 销毁定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private fun destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient!!.onDestroy()
            locationClient = null
            locationOption = null
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mv_near_map.onSaveInstanceState(outState)
    }

    fun formatUTC(l: Long, strPattern: String): String {
        var strPattern = strPattern
        var sdf: SimpleDateFormat? = null
        if (TextUtils.isEmpty(strPattern)) {
            strPattern = "yyyy-MM-dd HH:mm:ss"
        }
        if (sdf == null) {
            try {
                sdf = SimpleDateFormat(strPattern, Locale.CHINA)
            } catch (e: Throwable) {
            }

        } else {
            sdf.applyPattern(strPattern)
        }
        return if (sdf == null) "NULL" else sdf.format(l)
    }

    /**
     * 获取GPS状态的字符串
     *
     * @param statusCode GPS状态码
     * @return
     */
    private fun showLocationDialog(statusCode: Int) {
        var str = ""
        when (statusCode) {
            AMapLocationQualityReport.GPS_STATUS_OK -> str = "GPS状态正常"
            AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER -> {
                str = getString(R.string.gps_status_nogpsprovider)
                ToastUtils.showShortToast(str)
            }
            AMapLocationQualityReport.GPS_STATUS_OFF -> {
                str = getString(R.string.gps_status_off)
                ToastUtils.showShortToast(str)
            }
            AMapLocationQualityReport.GPS_STATUS_MODE_SAVING -> {
                str = getString(R.string.gps_status_mode_saving)
                ToastUtils.showShortToast(str)
            }
            AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION, AppConstant.NO_GPS_PERMISSION -> {
                str = getString(R.string.gps_status_nogpspermission)
                showGoSettingDialog()
            }
        }
        LogUtils.d(str)
    }

    private fun showGoSettingDialog() {

        dialog = AlertDialog.Builder(this@NearServiceActivity)
                .setCancelable(false)
                .setTitle(R.string.permission_denied)
                .setMessage(R.string.go_location_permission_setting)
                .setPositiveButton(getString(R.string.go_permission_setting)) { dialog, which ->
                    dialog.dismiss()
                    DeviceUtils.gotoPermissionSetting(this@NearServiceActivity)
                }
                .setNegativeButton(getString(R.string.dlg_cancel)) { dialog, which -> dialog.dismiss() }.create()
        dialog?.show()

    }

    override fun setStatusBarColor() {
        DeviceUtils.initSystemBarColor(this)
    }
}
