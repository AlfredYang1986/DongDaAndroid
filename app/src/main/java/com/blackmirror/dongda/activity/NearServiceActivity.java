package com.blackmirror.dongda.activity;

import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.AppConstant;
import com.blackmirror.dongda.Tools.AYPrefUtils;
import com.blackmirror.dongda.Tools.DeviceUtils;
import com.blackmirror.dongda.Tools.LogUtils;
import com.blackmirror.dongda.Tools.OSSUtils;
import com.blackmirror.dongda.Tools.SnackbarUtils;
import com.blackmirror.dongda.Tools.ToastUtils;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.model.serverbean.ErrorInfoServerBean;
import com.blackmirror.dongda.model.serverbean.NearServiceServerBean;
import com.blackmirror.dongda.model.uibean.ErrorInfoUiBean;
import com.blackmirror.dongda.model.uibean.NearServiceUiBean;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class NearServiceActivity extends AYActivity {
    private MapView mv_near_map;
    private AMap aMap;
    private MarkerOptions markerOption;
    //声明AMapLocationClient类对象
    public AMapLocationClient locationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption locationOption = null;
    private ImageView iv_current_location;
    private ImageView iv_near_back;
    private SimpleDraweeView sv_near_photo;
    private TextView tv_near_title;
    private TextView tv_near_dec;
    private TextView tv_near_location;
    private Map<String, Marker> markers;
    private NearServiceUiBean uiBean;
    private PopupWindow popupWindow;
    private TranslateAnimation animation;
    private View view;
    private String lastClickMarker;
    private AlertDialog dialog;
    private String locMarkerId;

    //设置定位回调监听
//mLocationClient.setLocationListener(mLocationListener);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_service);
        initView(savedInstanceState);

        initLocation();
        initAMap();
        initListener();
    }

    private void initView(Bundle savedInstanceState) {
        iv_current_location = findViewById(R.id.iv_current_location);
        iv_near_back = findViewById(R.id.iv_near_back);
        //获取地图控件引用
        mv_near_map = findViewById(R.id.mv_near_map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mv_near_map.onCreate(savedInstanceState);
        markers = new HashMap<>();
    }


    private void initLocation() {
        showProcessDialog("正在定位...");
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

    private void initAMap() {
        if (aMap == null) {
            aMap = mv_near_map.getMap();
        }

    }

    private void initListener() {

        iv_near_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locationClient!=null){
                    stopLocation();
                    destroyLocation();
                    finish();
                }
            }
        });

        iv_current_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeProcessDialog();
                markers.clear();
                aMap.clear();
                showProcessDialog("正在定位...");
                closePopUpWindow();
                stopLocation();
                if (locationClient != null) {
                    locationClient.startLocation();
                }
            }
        });

        // 定义 Marker 点击事件监听
        // 绑定 Marker 被点击事件
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            // marker 对象被点击时回调的接口
            // 返回 true 则表示接口已响应事件，否则返回false
            @Override
            public boolean onMarkerClick(Marker marker) {
                LogUtils.d("onclick "+marker.getId());
                if (!marker.getId().equals(locMarkerId)) {
                    refreshPopUpWindow(marker);
                }
                return true;
            }
        });
    }

    private void closePopUpWindow() {
        if (popupWindow!=null && popupWindow.isShowing()){
            popupWindow.dismiss();
        }
    }

    private void refreshPopUpWindow(Marker marker) {


//        LogUtils.d("is_select"+markers.get(marker.getId()).is_select);

        //重复点击
        if (marker.getId().equals(lastClickMarker)){
            return;
        }

        boolean b1 = marker == markers.get(marker.getId());

        LogUtils.d("marker equals "+marker.equals(markers.get(marker.getId())));
        LogUtils.d("marker == "+b1);

        view.clearAnimation();
        closePopUpWindow();
        showPopUpWindow();

        NearServiceServerBean.ResultBean.ServicesBean b= (NearServiceServerBean.ResultBean.ServicesBean) marker.getObject();

        marker.setIcon(BitmapDescriptorFactory.fromResource(getImageResId(b.service_type,true)));
        sv_near_photo.setImageURI(OSSUtils.getSignedUrl(b.service_image));
        tv_near_title.setText(b.service_leaf);
        StringBuilder s = new StringBuilder();
        if (b.service_leaf.contains("看顾")){
            s.append(b.brand_name)
                    .append("的")
                    .append(b.service_leaf);
        }else {
            s.append(b.brand_name)
                    .append("的")
                    .append(b.service_leaf)
                    .append(b.category);
        }
        tv_near_dec.setText(s.toString());
        tv_near_location.setText(b.address);
        NearServiceServerBean.ResultBean.ServicesBean l= (NearServiceServerBean.ResultBean.ServicesBean) markers.get(lastClickMarker).getObject();
        markers.get(lastClickMarker).setIcon(BitmapDescriptorFactory.fromResource(getImageResId(l.service_type,false)));

        lastClickMarker=marker.getId();

    }

    private int getImageResId(String serviceType, boolean isSelect) {
        if (serviceType.equals("看顾")){
            return isSelect ? R.drawable.map_icon_day_care_select : R.drawable.map_icon_day_care_normal;
        }
        if (serviceType.equals("艺术")){
            return isSelect ? R.drawable.map_icon_art_select : R.drawable.map_icon_art_normal;
        }
        if (serviceType.equals("运动")){
            return isSelect ? R.drawable.map_icon_sport_select : R.drawable.map_icon_sport_normal;
        }
        if (serviceType.equals("科学")){
            return isSelect ? R.drawable.map_icon_science_select : R.drawable.map_icon_science_normal;
        }
        return R.drawable.map_icon_day_care_normal;
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            closeProcessDialog();

            //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
            if(location.getErrorCode() == 0){

                //定位类型  location.getLocationType()
                //经    度  location.getLongitude()
                //纬    度  location.getLatitude()

                double latitude=location.getLatitude();
                double longitude=location.getLongitude();

                aMap.getUiSettings().setZoomControlsEnabled(false);
                //设置默认缩放比例 3-19
                aMap.moveCamera(CameraUpdateFactory.zoomTo(13));
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(latitude,longitude)));
                markerOption = new MarkerOptions();
                markerOption.position(new LatLng(latitude,longitude));
                markerOption.icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.location_icon));
                markerOption.anchor(0.5f,0.5f);
                Marker marker = aMap.addMarker(markerOption);
                locMarkerId = marker.getId();
                try {
                    String json="{\"token\":\""+ AYPrefUtils.getAuthToken()+"\",\"condition\":{\"user_id\":\""+ AYPrefUtils.getUserId()+"\"," +
                            "\"pin\":{\"latitude\":"+latitude+",\"longitude\":"+longitude+"}}}";

                    JSONObject object = new JSONObject(json);
                    facades.get("AYMapCommonFacade").execute("AYGetNearServiceCmd",object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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

    public void AYGetNearServiceCmdSuccess(JSONObject args) {
        closeProcessDialog();
        NearServiceServerBean serverBean = JSON.parseObject(args.toString(), NearServiceServerBean.class);

        uiBean = new NearServiceUiBean(serverBean);
        if (uiBean.isSuccess){
            addMarkers(uiBean);
        }else {
            ToastUtils.showShortToast(uiBean.message+"("+ uiBean.code+")");
        }
    }

    public void AYGetNearServiceCmdFailed(JSONObject args) {
        closeProcessDialog();
        ErrorInfoServerBean serverBean = JSON.parseObject(args.toString(), ErrorInfoServerBean.class);
        ErrorInfoUiBean uiBean = new ErrorInfoUiBean(serverBean);
        if (uiBean.code==10010){
            SnackbarUtils.show(iv_current_location,uiBean.message);
        }else {
            ToastUtils.showShortToast(uiBean.message+"("+uiBean.code+")");
        }
    }

    private void addMarkers(NearServiceUiBean uiBean) {

        showPopUpWindow();
        for (int i = 0; i < uiBean.services.size(); i++) {

            markerOption = new MarkerOptions();
            markerOption.position(new LatLng(uiBean.services.get(i).pin.latitude,uiBean.services.get(i).pin.longitude));
            markerOption.anchor(0.5f,0.5f);//必须加这个

            Marker marker = aMap.addMarker(markerOption);
            marker.setObject(uiBean.services.get(i));
//            LogUtils.d("xcx","markerId== "+marker.getId());
            if (i==0){//展示第一个
                lastClickMarker=marker.getId();
                NearServiceServerBean.ResultBean.ServicesBean sb = uiBean.services.get(0);
                marker.setIcon(BitmapDescriptorFactory.fromResource(getImageResId(sb.service_type,true)));
                sv_near_photo.setImageURI(OSSUtils.getSignedUrl(sb.service_image));
                tv_near_title.setText(sb.service_leaf);
                StringBuilder s = new StringBuilder();
                if (sb.service_leaf.contains("看顾")){
                    s.append(sb.brand_name)
                            .append("的")
                            .append(sb.service_leaf);
                }else {
                    s.append(sb.brand_name)
                            .append("的")
                            .append(sb.service_leaf)
                            .append(sb.category);
                }
                tv_near_dec.setText(s.toString());
                tv_near_location.setText(sb.address);
            }else {
                marker.setIcon(BitmapDescriptorFactory
                        .fromResource(getImageResId(uiBean.services.get(i).service_type,false)));
            }
            markers.put(marker.getId(),marker);
        }

    }

    private void showPopUpWindow() {
        if (view == null) {
            view = View.inflate(NearServiceActivity.this, R.layout.dialog_near_service, null);
            sv_near_photo = view.findViewById(R.id.sv_near_photo);
            tv_near_title = view.findViewById(R.id.tv_near_title);
            tv_near_dec = view.findViewById(R.id.tv_near_dec);
            tv_near_location = view.findViewById(R.id.tv_near_location);
        }
        if (popupWindow == null){
            popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setFocusable(true);// 取得焦点
            //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            //点击外部消失
            popupWindow.setOutsideTouchable(false);
            //设置可以点击
            popupWindow.setTouchable(false);
            animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                    Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_PARENT, 0);
            animation.setInterpolator(new AccelerateDecelerateInterpolator());
            animation.setDuration(300);
        }

        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        view.startAnimation(animation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mv_near_map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mv_near_map.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mv_near_map.onDestroy();
        stopLocation();
        destroyLocation();
    }

    /**
     * 停止定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void stopLocation(){
        // 停止定位
        if (locationClient != null) {
            locationClient.stopLocation();
        }
    }

    /**
     * 销毁定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void destroyLocation(){
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
    protected void bindingFragments() {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mv_near_map.onSaveInstanceState(outState);
    }

    public  String formatUTC(long l, String strPattern) {
        SimpleDateFormat sdf = null;
        if (TextUtils.isEmpty(strPattern)) {
            strPattern = "yyyy-MM-dd HH:mm:ss";
        }
        if (sdf == null) {
            try {
                sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
            } catch (Throwable e) {
            }
        } else {
            sdf.applyPattern(strPattern);
        }
        return sdf == null ? "NULL" : sdf.format(l);
    }

    /**
     * 获取GPS状态的字符串
     * @param statusCode GPS状态码
     * @return
     */
    private void showLocationDialog(int statusCode){
        String str = "";
        switch (statusCode){
            case AMapLocationQualityReport.GPS_STATUS_OK:
                str = "GPS状态正常";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER:
                str = "手机中没有GPS Provider，无法进行GPS定位";
                ToastUtils.showShortToast(str);
                break;
            case AMapLocationQualityReport.GPS_STATUS_OFF:
                str = "GPS关闭，建议开启GPS，提高定位质量";
                ToastUtils.showShortToast(str);
                break;
            case AMapLocationQualityReport.GPS_STATUS_MODE_SAVING:
                str = "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量";
                ToastUtils.showShortToast(str);
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION:
            case AppConstant.NO_GPS_PERMISSION:
                str = "没有GPS定位权限，建议开启gps定位权限";
                showGoSettingDialog();
                break;
        }
        LogUtils.d(str);
    }

    private void showGoSettingDialog() {

        dialog = new AlertDialog.Builder(NearServiceActivity.this)
                .setCancelable(false)
                .setTitle("权限拒绝")
                .setMessage("请在设置->应用管理->咚哒->权限管理打开定位权限.")
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        DeviceUtils.gotoPermissionSetting(NearServiceActivity.this);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();

    }
}
