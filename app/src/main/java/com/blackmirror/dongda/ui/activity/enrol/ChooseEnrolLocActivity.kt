package com.blackmirror.dongda.ui.activity.enrol

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.location.AMapLocationQualityReport
import com.blackmirror.dongda.R
import com.blackmirror.dongda.adapter.BrandAllLocAdapter
import com.blackmirror.dongda.di.component.DaggerChooseEnrolLocComponent
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.kdomain.model.BrandAllLocDomainBean
import com.blackmirror.dongda.kdomain.model.EnrolDomainBean
import com.blackmirror.dongda.kdomain.model.LocAllServiceDomainBean
import com.blackmirror.dongda.presenter.EnrolPresenter
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.*

class ChooseEnrolLocActivity : BaseActivity(), EnrolContract.View {


    private lateinit var iv_back: ImageView
    private lateinit var tv_cur_loc: TextView
    private lateinit var rv_brand_loc_list: RecyclerView

    private var locationClient: AMapLocationClient? = null
    private var locationOption: AMapLocationClientOption? = null
    private var dialog: AlertDialog? = null
    private var presenter: EnrolPresenter? = null

    override val layoutResId: Int
        get() = R.layout.activity_choose_enrol_loc

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

            val sb = StringBuilder()


            sb.append("定位成功" + "\n")
            sb.append("定位类型: " + location.locationType + "\n")
            sb.append("经    度    : " + location.longitude + "\n")
            sb.append("纬    度    : " + location.latitude + "\n")
            sb.append("精    度    : " + location.accuracy + "米" + "\n")
            sb.append("提供者    : " + location.provider + "\n")

            sb.append("速    度    : " + location.speed + "米/秒" + "\n")
            sb.append("角    度    : " + location.bearing + "\n")
            // 获取当前提供定位服务的卫星个数
            sb.append("星    数    : " + location.satellites + "\n")
            sb.append("国    家    : " + location.country + "\n")
            sb.append("省            : " + location.province + "\n")
            sb.append("市            : " + location.city + "\n")
            sb.append("城市编码 : " + location.cityCode + "\n")
            sb.append("区            : " + location.district + "\n")
            sb.append("区域 码   : " + location.adCode + "\n")
            sb.append("地    址    : " + location.address + "\n")
            sb.append("兴趣点    : " + location.poiName + "\n")
            //定位完成的时间

            var address = location.address
            if (address.contains("靠近")) {
                address = address.substring(0, address.indexOf("靠近"))
            }

            logD(sb.toString())
            logD("address $address")
            tv_cur_loc.text = address

        } else {
            val sb = StringBuilder()
            //定位失败
            sb.append("定位失败" + "\n")
            sb.append("错误码:" + location.errorCode + "\n")
            sb.append("错误信息:" + location.errorInfo + "\n")
            sb.append("错误描述:" + location.locationDetail + "\n")

            logD(sb.toString())

            showLocationDialog(location.errorCode)

        }
    }

    override fun initInject() {
        presenter = DaggerChooseEnrolLocComponent.builder()
                .activity(this)
                .view(this)
                .build()
                .enrolPresenter
    }

    override fun initView() {
        iv_back = findViewById(R.id.iv_back)
        tv_cur_loc = findViewById(R.id.tv_cur_loc)
        rv_brand_loc_list = findViewById(R.id.rv_brand_loc_list)
    }

    override fun initData() {
        //        presenter.getBrandAllLocation("5a66fdea59a6270918508f25");
        presenter?.getBrandAllLocation("")
        initLocation()
    }

    override fun initListener() {
        iv_back.setOnClickListener { finish() }
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
                showToast(str)
            }
            AMapLocationQualityReport.GPS_STATUS_OFF -> {
                str = getString(R.string.gps_status_off)
                showToast(str)
            }
            AMapLocationQualityReport.GPS_STATUS_MODE_SAVING -> {
                str = getString(R.string.gps_status_mode_saving)
                showToast(str)
            }
            AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION, AppConstant.NO_GPS_PERMISSION -> {
                str = getString(R.string.gps_status_nogpspermission)
                showGoSettingDialog()
            }
        }
        logD(str)
    }

    private fun showGoSettingDialog() {

        dialog = AlertDialog.Builder(this@ChooseEnrolLocActivity)
                .setCancelable(false)
                .setTitle(R.string.permission_denied)
                .setMessage(R.string.go_location_permission_setting)
                .setPositiveButton(getString(R.string.go_permission_setting)) { dialog, which ->
                    dialog.dismiss()
                    gotoPermissionSetting(this@ChooseEnrolLocActivity)
                }
                .setNegativeButton(getString(R.string.dlg_cancel)) { dialog, which -> dialog.dismiss() }.create()
        dialog!!.show()

    }

    override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
            dialog = null
        }
        destroyLocation()
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

    override fun setStatusBarColor() {
        setStatusBarColor(this, Color.parseColor("#FFF7F9FA"))
    }

    override fun onGetBrandAllLocationSuccess(bean: BrandAllLocDomainBean) {
        initBrandAllLoc(bean)
    }

    override fun onGetLocAllServiceSuccess(bean: LocAllServiceDomainBean) {

    }

    override fun onEnrolSuccess(bean: EnrolDomainBean) {

    }

    private fun initBrandAllLoc(bean: BrandAllLocDomainBean) {
        logD("initBrandAllLoc")
        val adapter = BrandAllLocAdapter(this, bean)
        rv_brand_loc_list.layoutManager = LinearLayoutManager(this)
        rv_brand_loc_list.adapter = adapter

        adapter.setOnItemClickListener{ view, position, lb ->
            val intent = Intent(this@ChooseEnrolLocActivity, LocAllServiceActivity::class.java)
            intent.putExtra("locations", lb.location_id)
            intent.putExtra("address", lb.address)
            startActivity(intent)
        }
    }

    override fun onError(bean: BaseDataBean) {
        closeProcessDialog()
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            showSnackbar(tv_cur_loc, bean.message ?: "Server Error")
        } else {
            showToast("${bean.message}(${bean.code})")
        }
    }
}
