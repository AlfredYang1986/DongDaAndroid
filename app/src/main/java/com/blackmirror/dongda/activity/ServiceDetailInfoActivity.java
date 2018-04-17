package com.blackmirror.dongda.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.BasePrefUtils;
import com.blackmirror.dongda.Tools.CalUtils;
import com.blackmirror.dongda.Tools.ToastUtils;
import com.blackmirror.dongda.adapter.AddrDecInfoAdapter;
import com.blackmirror.dongda.adapter.PhotoDetailAdapter;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.model.ErrorInfoBean;
import com.blackmirror.dongda.model.serverbean.ServiceDetailInfoServerBean;
import com.blackmirror.dongda.model.uibean.SafeUiBean;
import com.blackmirror.dongda.model.uibean.ServiceDetailInfoUiBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ServiceDetailInfoActivity extends AYActivity {

    private ViewPager vp_detail_photo;
    private TabLayout tl_detail_tab;
    private RecyclerView rv_addr_safe;
    private String service_id;
    private TextView tv_detail_content;
    private TextView tv_detail_course;
    private TextView tv_detail_type;
    private TextView tv_age_range;
    private TextView tv_class_mb_no;
    private TextView tv_t_s_ratio;
    private TextView tv_course_name;
    private TextView tv_teacher_name;
    private TextView tv_service_dec;
    private TextView tv_dec_more;
    private TextView tv_service_dec_content;
    private TextView tv_service_dec_content_more;
    private TextView tv_service_type;
    private TextView tv_service_location;
    private TextView tv_brand_tag;
    private ImageView iv_detail_back;
    private MapView mv_detail_location;
    private AMap aMap;
    private MarkerOptions markerOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);
        service_id=getIntent().getStringExtra("service_id");
//        OtherUtils.setStatusBarColor(this,0);
//        OtherUtils.fullScreen(this);
        initView(savedInstanceState);
        initData();
        initListener();
    }



    private void initView(Bundle savedInstanceState) {
        vp_detail_photo = findViewById(R.id.vp_detail_photo);
        tl_detail_tab = findViewById(R.id.tl_detail_tab);
        rv_addr_safe = findViewById(R.id.rv_addr_safe);
        tv_detail_content = findViewById(R.id.tv_detail_content);
        tv_detail_course = findViewById(R.id.tv_detail_course);
        tv_detail_type = findViewById(R.id.tv_detail_type);
        tv_age_range = findViewById(R.id.tv_age_range);
        tv_class_mb_no = findViewById(R.id.tv_class_mb_no);
        tv_t_s_ratio = findViewById(R.id.tv_t_s_ratio);
        tv_course_name = findViewById(R.id.tv_course_name);
        tv_teacher_name = findViewById(R.id.tv_teacher_name);
        tv_service_dec = findViewById(R.id.tv_service_dec);
        tv_dec_more = findViewById(R.id.tv_dec_more);
        tv_service_dec_content = findViewById(R.id.tv_service_dec_content);
        tv_service_dec_content_more = findViewById(R.id.tv_service_dec_content_more);
        tv_service_type = findViewById(R.id.tv_service_type);
        tv_service_location = findViewById(R.id.tv_service_location);
        tv_brand_tag = findViewById(R.id.tv_brand_tag);
        iv_detail_back = findViewById(R.id.iv_detail_back);
        mv_detail_location = findViewById(R.id.mv_detail_location);
        initMapView(savedInstanceState);
    }

    private void initMapView(Bundle savedInstanceState) {
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mv_detail_location.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mv_detail_location.getMap();
        }
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.getUiSettings().setAllGesturesEnabled(false);
       /* if (aMap == null) {
            aMap = mv_detail_location.getMap();
        }
//        aMap.getUiSettings().setZoomControlsEnabled(false);
//        aMap.getUiSettings().setAllGesturesEnabled(false);
        //设置默认缩放比例 3-19
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));*/
    }

    private void initData() {
        String json="{\"token\":\""+ BasePrefUtils.getAuthToken()+"\",\"condition\":{\"service_id\":\""+service_id+"\"}}";
        try {
            JSONObject object = new JSONObject(json);
            facades.get("AYDetailInfoFacade").execute("AYGetDetailInfoCmd",object);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initListener() {
        iv_detail_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_dec_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_service_dec_content_more.getVisibility()==View.GONE){
                    tv_dec_more.setText("收起");
                    tv_service_dec_content_more.setVisibility(View.VISIBLE);
                }else {
                    tv_service_dec_content_more.setVisibility(View.GONE);
                    tv_dec_more.setText("展开");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mv_detail_location.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mv_detail_location.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mv_detail_location.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mv_detail_location.onSaveInstanceState(outState);
    }

    private void addMarkers(ServiceDetailInfoServerBean.ResultBean.ServiceBean.LocationBean.PinBean pin) {

        //设置默认缩放比例 3-19
        LatLng latLng = new LatLng(pin.latitude, pin.longitude);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
                markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.icon(BitmapDescriptorFactory
                .fromResource(R.drawable.map_icon_art_normal));
        Marker marker = aMap.addMarker(markerOption);



        /*markerOption = new MarkerOptions();
        markerOption.position(AppConstant.ZHONGGUANCUN);
        markerOption.icon(BitmapDescriptorFactory
                .fromResource(R.drawable.home_btn_nearyou));
        Marker marker2 = aMap.addMarker(markerOption);
        LogUtils.d("xcx","markerId== "+marker2.getId());*/
    }

    private void setData(ServiceDetailInfoUiBean bean) {
        PhotoDetailAdapter adapter = new PhotoDetailAdapter(bean.service.location.location_images);
        for (int i = 0; i < bean.service.location.location_images.size(); i++) {
            tl_detail_tab.addTab(tl_detail_tab.newTab().setText(bean.service.location.location_images.get(i).tag));
        }

        vp_detail_photo.setAdapter(adapter);
        vp_detail_photo.setOffscreenPageLimit(bean.service.location.location_images.size());
        //下面的方法可以解决tab字体丢失的问题,不要用setupWithViewPager()方法
        //        tl_detail_tab.setupWithViewPager(vp_detail_photo);
        vp_detail_photo.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tl_detail_tab));
        tl_detail_tab.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vp_detail_photo));

        tv_detail_content.setText(bean.service.punchline);//第一个描述

        StringBuilder sb = new StringBuilder();
        sb.append(bean.service.service_type)
                .append(TextUtils.isEmpty(bean.service.service_leaf)?"":"·")
                .append(TextUtils.isEmpty(bean.service.service_leaf)?"":bean.service.service_leaf)
                .append(bean.service.category);
        tv_detail_course.setText(sb.toString());//课程
        tv_detail_type.setText(bean.service.service_tags.get(0));//名校团队

        StringBuilder range=new StringBuilder();
        range.append(bean.service.min_age)
                .append("-")
                .append(bean.service.max_age)
                .append("岁");
        tv_age_range.setText(range.toString());//年龄范围
        tv_class_mb_no.setText(bean.service.class_max_stu+"");//班级人数

        int m= CalUtils.getGongyue(bean.service.class_max_stu,bean.service.teacher_num);
        StringBuilder radio=new StringBuilder();
        radio.append(bean.service.teacher_num/m)
                .append(":")
                .append(bean.service.class_max_stu);
        tv_t_s_ratio.setText(radio.toString());


        tv_course_name.setText(bean.service.service_tags.get(0));//课程名称


        tv_teacher_name.setText(bean.service.brand.brand_name);//

        tv_brand_tag.setText(bean.service.brand.brand_tag);//

        tv_service_dec_content.setText(bean.service.description);

        tv_service_dec_content_more.setText(bean.service.brand.about_brand);

        StringBuilder type=new StringBuilder();
        for (int i = 0; i < bean.service.operation.size(); i++) {
            type.append("#")
                    .append(bean.service.operation.get(i))
                    .append("# ");
        }
        tv_service_type.setText(type.toString());//

        List<SafeUiBean> list=new ArrayList<>();
        for (int i = 0; i < bean.service.location.friendliness.size(); i++) {
            String s = bean.service.location.friendliness.get(i);
            SafeUiBean b = new SafeUiBean();
            b.dec=s;
            if (s.equals("新风系统")){
                b.res_id= R.drawable.new_wind_sys;
            }else if (s.equals("空气净化器")){
                b.res_id= R.drawable.air_clear_sys;
            }else if (s.equals("安全插座")){
                b.res_id= R.drawable.safe_power;
            }else if (s.equals("实时监控")){
                b.res_id= R.drawable.real_time_protect;
            }else if (s.equals("无线WI-FI")){
                b.res_id= R.drawable.wifi;
            }else if (s.equals("防摔地板")){
                b.res_id= R.drawable.floor;
            }else if (s.equals("加湿器")){
                b.res_id= R.drawable.air_humid;
            }else if (s.equals("安全护栏")){
                b.res_id= R.drawable.guard;
            }else if (s.equals("急救包")){
                b.res_id= R.drawable.kit;
            }else if (s.equals("")){
            }else {
                b.res_id= R.drawable.other;
            }
            if (!s.equals("")) {
                list.add(b);
            }
        }

        AddrDecInfoAdapter decInfoAdapter = new AddrDecInfoAdapter(ServiceDetailInfoActivity.this, list);
        LinearLayoutManager manager = new LinearLayoutManager(ServiceDetailInfoActivity.this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_addr_safe.setLayoutManager(manager);
        rv_addr_safe.setAdapter(decInfoAdapter);

        tv_service_location.setText(bean.service.location.address);

        addMarkers(bean.service.location.pin);
    }

    public void AYGetDetailInfoCmdSuccess(JSONObject args){
        ServiceDetailInfoServerBean serverBean = JSON.parseObject(args.toString(), ServiceDetailInfoServerBean.class);
        ServiceDetailInfoUiBean uiBean = new ServiceDetailInfoUiBean(serverBean);
        if (uiBean.isSuccess){
            setData(uiBean);
        }else {
            ToastUtils.showShortToast(uiBean.message+"("+uiBean.code+")");
        }
    }



    public void AYGetDetailInfoCmdFailed(JSONObject args) {
        ErrorInfoBean bean = JSON.parseObject(args.toString(), ErrorInfoBean.class);
        if (bean != null && bean.error != null) {
            ToastUtils.showShortToast(bean.error.message+"("+bean.error.code+")");
        }
    }


    @Override
    protected void bindingFragments() {

    }
}
