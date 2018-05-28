package com.blackmirror.dongda.ui.activity.enrol;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.adapter.BrandAllLocAdapter;
import com.blackmirror.dongda.di.component.DaggerChooseEnrolLocComponent;
import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.domain.model.BrandAllLocDomainBean;
import com.blackmirror.dongda.domain.model.EnrolDomainBean;
import com.blackmirror.dongda.domain.model.LocAllServiceDomainBean;
import com.blackmirror.dongda.presenter.EnrolPresenter;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.blackmirror.dongda.utils.AppConstant;
import com.blackmirror.dongda.utils.DeviceUtils;
import com.blackmirror.dongda.utils.LogUtils;
import com.blackmirror.dongda.utils.SnackbarUtils;
import com.blackmirror.dongda.utils.ToastUtils;

public class ChooseEnrolLocActivity extends BaseActivity implements EnrolContract.View{


    private AMapLocationClient locationClient;
    private AMapLocationClientOption locationOption;
    private AlertDialog dialog;
    private ImageView iv_back;
    private TextView tv_cur_loc;
    private EnrolPresenter presenter;
    private RecyclerView rv_brand_loc_list;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_choose_enrol_loc;
    }

    @Override
    protected void initInject() {
        presenter = DaggerChooseEnrolLocComponent.builder()
                .activity(this)
                .view(this)
                .build()
                .getEnrolPresenter();
    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_cur_loc = findViewById(R.id.tv_cur_loc);
        rv_brand_loc_list = findViewById(R.id.rv_brand_loc_list);
    }

    @Override
    protected void initData() {
//        presenter.getBrandAllLocation("5a66fdea59a6270918508f25");
        presenter.getBrandAllLocation("");
        initLocation();
    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initLocation() {
        showProcessDialog(getString(R.string.location_processing));
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = new AMapLocationClientOption();

        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        locationOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        locationOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        locationOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        locationOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        locationOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
        locationOption.setOnceLocationLatest(true);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        locationOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        locationOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        locationOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true

        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            closeProcessDialog();

            //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
            if (location.getErrorCode() == 0) {

                //定位类型  location.getLocationType()
                //经    度  location.getLongitude()
                //纬    度  location.getLatitude()

                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                StringBuilder sb = new StringBuilder();


                sb.append("定位成功" + "\n");
                sb.append("定位类型: " + location.getLocationType() + "\n");
                sb.append("经    度    : " + location.getLongitude() + "\n");
                sb.append("纬    度    : " + location.getLatitude() + "\n");
                sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                sb.append("提供者    : " + location.getProvider() + "\n");

                sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                sb.append("角    度    : " + location.getBearing() + "\n");
                // 获取当前提供定位服务的卫星个数
                sb.append("星    数    : " + location.getSatellites() + "\n");
                sb.append("国    家    : " + location.getCountry() + "\n");
                sb.append("省            : " + location.getProvince() + "\n");
                sb.append("市            : " + location.getCity() + "\n");
                sb.append("城市编码 : " + location.getCityCode() + "\n");
                sb.append("区            : " + location.getDistrict() + "\n");
                sb.append("区域 码   : " + location.getAdCode() + "\n");
                sb.append("地    址    : " + location.getAddress() + "\n");
                sb.append("兴趣点    : " + location.getPoiName() + "\n");
                //定位完成的时间

                String address = location.getAddress();
                if (address.contains("靠近")){
                    address = address.substring(0,address.indexOf("靠近"));
                }

                LogUtils.d(sb.toString());
                LogUtils.d("address "+address);
                tv_cur_loc.setText(address);

            } else {
                StringBuilder sb = new StringBuilder();
                //定位失败
                sb.append("定位失败" + "\n");
                sb.append("错误码:" + location.getErrorCode() + "\n");
                sb.append("错误信息:" + location.getErrorInfo() + "\n");
                sb.append("错误描述:" + location.getLocationDetail() + "\n");

                LogUtils.d(sb.toString());

                showLocationDialog(location.getErrorCode());

            }
        }
    };

    /**
     * 获取GPS状态的字符串
     *
     * @param statusCode GPS状态码
     * @return
     */
    private void showLocationDialog(int statusCode) {
        String str = "";
        switch (statusCode) {
            case AMapLocationQualityReport.GPS_STATUS_OK:
                str = "GPS状态正常";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER:
                str = getString(R.string.gps_status_nogpsprovider);
                ToastUtils.showShortToast(str);
                break;
            case AMapLocationQualityReport.GPS_STATUS_OFF:
                str = getString(R.string.gps_status_off);
                ToastUtils.showShortToast(str);
                break;
            case AMapLocationQualityReport.GPS_STATUS_MODE_SAVING:
                str = getString(R.string.gps_status_mode_saving);
                ToastUtils.showShortToast(str);
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION:
            case AppConstant.NO_GPS_PERMISSION:
                str = getString(R.string.gps_status_nogpspermission);
                showGoSettingDialog();
                break;
        }
        LogUtils.d(str);
    }

    private void showGoSettingDialog() {

        dialog = new AlertDialog.Builder(ChooseEnrolLocActivity.this)
                .setCancelable(false)
                .setTitle(R.string.permission_denied)
                .setMessage(R.string.go_location_permission_setting)
                .setPositiveButton(getString(R.string.go_permission_setting), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        DeviceUtils.gotoPermissionSetting(ChooseEnrolLocActivity.this);
                    }
                })
                .setNegativeButton(getString(R.string.dlg_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
        destroyLocation();
    }

    /**
     * 销毁定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    @Override
    protected void setStatusBarColor() {
        DeviceUtils.setStatusBarColor(this, Color.parseColor("#FFF7F9FA"));
    }

    @Override
    public void onGetBrandAllLocationSuccess(BrandAllLocDomainBean bean) {
        initBrandAllLoc(bean);
    }

    @Override
    public void onGetLocAllServiceSuccess(LocAllServiceDomainBean bean) {

    }

    @Override
    public void onEnrolSuccess(EnrolDomainBean bean) {

    }

    private void initBrandAllLoc(BrandAllLocDomainBean bean) {
        LogUtils.d("initBrandAllLoc");
        BrandAllLocAdapter adapter = new BrandAllLocAdapter(this, bean);
        rv_brand_loc_list.setLayoutManager(new LinearLayoutManager(this));
        rv_brand_loc_list.setAdapter(adapter);
        adapter.setOnItemClickListener(new BrandAllLocAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, BrandAllLocDomainBean.LocationsBean bean) {
                Intent intent = new Intent(ChooseEnrolLocActivity.this, LocAllServiceActivity.class);
                intent.putExtra("locations",bean.location_id);
                intent.putExtra("address",bean.address);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onError(BaseDataBean bean) {
        closeProcessDialog();
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(tv_cur_loc, bean.message);
        } else {
            ToastUtils.showShortToast(bean.message + "(" + bean.code + ")");
        }
    }
}
