package com.blackmirror.dongda.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.AppConstant;
import com.blackmirror.dongda.Tools.LogUtils;
import com.blackmirror.dongda.Tools.OtherUtils;

public class ShowMapActivity extends AppCompatActivity {
    private MapView mMapView ;
    private AMap aMap;
    private MarkerOptions markerOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);
        //获取地图控件引用
        mMapView = findViewById(R.id.mv_map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        OtherUtils.setStatusBarColor(ShowMapActivity.this);
        initAMap();
    }

    private void initAMap() {
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        aMap.getUiSettings().setZoomControlsEnabled(false);
        //设置默认缩放比例 3-19
        aMap.moveCamera(CameraUpdateFactory.zoomTo(13));
        addMarkers();
        initListener();
    }

    private void initListener() {
        // 定义 Marker 点击事件监听
        AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {
            // marker 对象被点击时回调的接口
            // 返回 true 则表示接口已响应事件，否则返回false
            @Override
            public boolean onMarkerClick(Marker marker) {
                LogUtils.d("onclick "+marker.getId());
                return true;
            }
        };
        // 绑定 Marker 被点击事件
        aMap.setOnMarkerClickListener(markerClickListener);
    }

    private void addMarkers() {
        markerOption = new MarkerOptions();
        markerOption.position(AppConstant.BEIJING);
        markerOption.icon(BitmapDescriptorFactory
                .fromResource(R.drawable.home_btn_nearyou));
        Marker marker = aMap.addMarker(markerOption);
        LogUtils.d("xcx","markerId== "+marker.getId());


        markerOption = new MarkerOptions();
        markerOption.position(AppConstant.ZHONGGUANCUN);
        markerOption.icon(BitmapDescriptorFactory
                .fromResource(R.drawable.home_btn_nearyou));
        Marker marker2 = aMap.addMarker(markerOption);
        LogUtils.d("xcx","markerId== "+marker2.getId());
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}
