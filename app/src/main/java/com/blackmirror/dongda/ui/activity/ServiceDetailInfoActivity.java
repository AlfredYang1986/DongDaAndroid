package com.blackmirror.dongda.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.adapter.AddrDecInfoAdapter;
import com.blackmirror.dongda.adapter.PhotoDetailAdapter;
import com.blackmirror.dongda.di.component.DaggerServiceDetailInfoComponent;
import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.domain.model.DetailInfoDomainBean;
import com.blackmirror.dongda.domain.model.LikePopDomainBean;
import com.blackmirror.dongda.domain.model.LikePushDomainBean;
import com.blackmirror.dongda.model.DetailInfoServiceImagesBean;
import com.blackmirror.dongda.model.ServiceDetailPhotoBean;
import com.blackmirror.dongda.model.ServiceProfileBean;
import com.blackmirror.dongda.model.SafeUiBean;
import com.blackmirror.dongda.presenter.DetailInfoPresenter;
import com.blackmirror.dongda.ui.Contract;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.blackmirror.dongda.ui.view.SlidingTabLayout;
import com.blackmirror.dongda.utils.AppConstant;
import com.blackmirror.dongda.utils.CalUtils;
import com.blackmirror.dongda.utils.DeviceUtils;
import com.blackmirror.dongda.utils.OtherUtils;
import com.blackmirror.dongda.utils.SnackbarUtils;
import com.blackmirror.dongda.utils.StringUtils;
import com.blackmirror.dongda.utils.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ServiceDetailInfoActivity extends BaseActivity implements View.OnClickListener, Contract.DetailInfoView{

    private ViewPager vp_detail_photo;
    private SlidingTabLayout tl_detail_tab;
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
    private ImageView iv_detail_like;
    private ImageView iv_detail_class;
    private ImageView iv_detail_teacher;
    private TextView tv_detail_class_dec;
    private TextView tv_detail_teacher_dec;
    private MapView mv_detail_location;
    private AMap aMap;
    private MarkerOptions markerOption;
    private boolean isNeedRefresh;
    private CoordinatorLayout ctl_root;
    private Toolbar tb_toolbar;
    private AppBarLayout abl_root;
    private int status;//1 展开 2 关闭 3 中间状态
    private ImageView iv_detail_tb_back;
    private ImageView iv_detail_tb_like;
    private ConstraintLayout cl_tb_content;
    private SimpleDraweeView sv_teacher_bg;
    private TextView tv_addr_safe;
    private View view_line_4;
    private int randomInt;
    private DetailInfoPresenter presenter;
    private DetailInfoDomainBean bean;

    @Override
    protected void init(Bundle savedInstanceState) {
        service_id = getIntent().getStringExtra("service_id");
        initView(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_detail_info;
    }

    @Override
    protected void initInject() {
        presenter = DaggerServiceDetailInfoComponent.builder()
                .activity(this)
                .view(this)
                .build()
                .getDetailInfoPresenter();
    }

    @Override
    protected void initView() {

    }

    private void initView(Bundle savedInstanceState) {
        setTitle("");
        ctl_root = findViewById(R.id.ctl_root);
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
        iv_detail_like = findViewById(R.id.iv_detail_like);
        iv_detail_class = findViewById(R.id.iv_detail_class);
        iv_detail_teacher = findViewById(R.id.iv_detail_teacher);
        tv_detail_class_dec = findViewById(R.id.tv_detail_class_dec);
        tv_detail_teacher_dec = findViewById(R.id.tv_detail_teacher_dec);
        mv_detail_location = findViewById(R.id.mv_detail_location);
        tb_toolbar = findViewById(R.id.tb_toolbar);
        abl_root = findViewById(R.id.abl_root);
        iv_detail_tb_back = findViewById(R.id.iv_detail_tb_back);
        iv_detail_tb_like = findViewById(R.id.iv_detail_tb_like);
        cl_tb_content = findViewById(R.id.cl_tb_content);
        sv_teacher_bg = findViewById(R.id.sv_teacher_bg);
        tv_addr_safe = findViewById(R.id.tv_addr_safe);
        view_line_4 = findViewById(R.id.view_line_4);
        setSupportActionBar(tb_toolbar);
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

    @Override
    protected void initData() {
        presenter.getDetailInfo(service_id);
    }

    @Override
    protected void initListener() {
        iv_detail_back.setOnClickListener(this);
        iv_detail_tb_back.setOnClickListener(this);
        tv_dec_more.setOnClickListener(this);
        iv_detail_like.setOnClickListener(this);
        iv_detail_tb_like.setOnClickListener(this);
        sv_teacher_bg.setOnClickListener(this);
        abl_root.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {//展开状态
                    if (status != AppConstant.OPEN_STATUS) {
                        status = AppConstant.OPEN_STATUS;
                        hideTb();
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {//折叠状态
                    if (status != AppConstant.CLOSE_STATUS) {
                        status = AppConstant.CLOSE_STATUS;
                        showTb();
                    }
                } else {//其他状态
                    if (status != AppConstant.OTHER_STATUS) {
                        status = AppConstant.OTHER_STATUS;
                        hideTb();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_detail_tb_back:
            case R.id.iv_detail_back:
                setResult(isNeedRefresh ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
                finish();
                break;
            case R.id.tv_dec_more:
                if (tv_dec_more.getText().equals(getString(R.string.open_dec_more))) {
                    tv_dec_more.setText(R.string.close_dec_more);
                    tv_service_dec_content.setMaxLines(100);
                    tv_service_dec_content.setText(bean.description);
                } else {
                    tv_dec_more.setText(getString(R.string.open_dec_more));
                    tv_service_dec_content.setMaxLines(4);
                    tv_service_dec_content.setText(bean.description);
                }

                break;
            case R.id.iv_detail_tb_like:
            case R.id.iv_detail_like:
                if (bean != null && bean.isSuccess) {
                    sendLikeData();
                }
                break;
            case R.id.sv_teacher_bg:
                ServiceProfileBean sb = new ServiceProfileBean();
                Intent intent = new Intent(ServiceDetailInfoActivity.this, ServiceProfileActivity
                        .class);
                if (bean != null && bean.isSuccess) {

                    sb.res_id = randomInt;
                    sb.brand_tag = bean.brand_tag;
                    sb.brand_name = bean.brand_name;
                    sb.about_brand = bean.about_brand;

                }
                intent.putExtra("bean",bean);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onGetDetailInfoSuccess(DetailInfoDomainBean dbean) {
        closeProcessDialog();
        bean = dbean;
        setData(dbean);
    }


    @Override
    public void onLikePushSuccess(LikePushDomainBean bean) {
        isNeedRefresh = true;
        closeProcessDialog();
        this.bean.is_collected = true;
        iv_detail_like.setImageResource(R.drawable.like_selected);
    }

    @Override
    public void onLikePopSuccess(LikePopDomainBean bean) {
        isNeedRefresh = true;
        closeProcessDialog();
        this.bean.is_collected = false;
        iv_detail_like.setImageResource(R.drawable.home_art_like);
    }

    @Override
    public void onGetDataError(BaseDataBean bean) {
        closeProcessDialog();
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(ctl_root, bean.message);
        } else {
            ToastUtils.showShortToast(bean.message + "(" + bean.code + ")");
        }
    }

    private void showTb() {
        DeviceUtils.setStatusBarColor(this);
        tb_toolbar.setVisibility(View.VISIBLE);
        tb_toolbar.setBackgroundColor(getResources().getColor(R.color.sys_bar_white));
        cl_tb_content.setVisibility(View.VISIBLE);
    }

    private void hideTb() {
        DeviceUtils.initSystemBarColor(this);
        tb_toolbar.setVisibility(View.GONE);
        tb_toolbar.setBackgroundColor(Color.TRANSPARENT);
        cl_tb_content.setVisibility(View.GONE);
    }


    private void addMarkers(double latitude, double longitude) {

        //设置默认缩放比例 3-19
        LatLng latLng = new LatLng(latitude, longitude);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(14));
        markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.icon(BitmapDescriptorFactory
                .fromResource(R.drawable.map_icon_art_normal));
        markerOption.anchor(0.5f, 0.5f);
        aMap.addMarker(markerOption);
    }

    private void setData(DetailInfoDomainBean bean) {

        if (bean.is_collected) {
            iv_detail_like.setImageResource(R.drawable.like_selected);
        } else {
            iv_detail_like.setImageResource(R.drawable.home_art_like);
        }

        List<ServiceDetailPhotoBean> tabList = new ArrayList<>();


        for (DetailInfoDomainBean.LocationImagesBean image : bean.location_images) {
            tabList.add(new ServiceDetailPhotoBean(image.tag, image.image));
            tl_detail_tab.addTab(tl_detail_tab.newTab().setText(image.tag));
        }

        List<DetailInfoServiceImagesBean> array = new ArrayList<>();

        for (DetailInfoDomainBean.ServiceImagesBean image : bean.service_images) {

            if (StringUtils.isNumber(image.tag)) {
                DetailInfoServiceImagesBean sb = new DetailInfoServiceImagesBean();
                sb.image = image.image;
                sb.tag = image.tag;
                array.add(sb);
            } else {
                tabList.add(new ServiceDetailPhotoBean(image.tag, image.image));
                tl_detail_tab.addTab(tl_detail_tab.newTab().setText(image.tag));
            }
        }

        Collections.sort(array);

        for (int i = 0; i < array.size(); i++) {
            tabList.add(new ServiceDetailPhotoBean(array.get(i).tag, array.get(i).image));
            tl_detail_tab.addTab(tl_detail_tab.newTab().setText(array.get(i).tag));
        }

        PhotoDetailAdapter adapter = new PhotoDetailAdapter(tabList);

        vp_detail_photo.setAdapter(adapter);
        //        vp_detail_photo.setOffscreenPageLimit(bean.service.location.location_images.size());
        //下面的方法可以解决tab字体丢失的问题,不要用setupWithViewPager()方法
        //        tl_detail_tab.setupWithViewPager(vp_detail_photo);
        vp_detail_photo.addOnPageChangeListener(new SlidingTabLayout.MyTabLayoutOnPageChangeListener(tl_detail_tab));
        tl_detail_tab.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vp_detail_photo));

        tv_detail_content.setText("\"" + bean.punchline + "\"");//第一个描述

        StringBuilder sb = new StringBuilder();

        if (bean.service_type.contains(getString(R.string.str_care))) {
            sb.append(bean.service_leaf);
            tv_detail_type.setVisibility(View.VISIBLE);
        } else {
            sb.append(bean.service_type)
                    .append("·")
                    .append(bean.service_leaf)
                    .append(bean.category);
            tv_detail_type.setVisibility(View.GONE);
        }

        tv_detail_course.setText(sb.toString());//课程
        tv_detail_type.setText(bean.service_tags.get(0));//名校团队

        StringBuilder range = new StringBuilder();
        range.append(bean.min_age)
                .append("-")
                .append(bean.max_age)
                .append(getString(R.string.str_age));
        tv_age_range.setText(range.toString());//年龄范围

        checkClassMaxStu(bean.class_max_stu);

        tv_class_mb_no.setText(bean.class_max_stu + "");//班级人数

        int m = CalUtils.getGongyue(bean.class_max_stu, bean.teacher_num);
        StringBuilder radio = new StringBuilder();
        radio.append(bean.teacher_num / m)
                .append(":")
                .append(bean.class_max_stu);
        tv_t_s_ratio.setText(radio.toString());


        tv_course_name.setText(bean.brand_name);//课程名称
        tv_teacher_name.setText(String.format(getString(R.string.str_teacher),bean.brand_name));//

        Random random = new Random();
        randomInt = random.nextInt(10);
        randomInt = randomInt > 9 || randomInt < 0 ? 0 : randomInt;
        sv_teacher_bg.setImageURI(OtherUtils.resourceIdToUri(ServiceDetailInfoActivity.this, AppConstant.teacher_bg_res_id[randomInt]));

        tv_brand_tag.setText(bean.brand_tag);//

        tv_service_dec_content.setText(bean.description);

        tv_service_dec_content_more.setText(bean.about_brand);

        StringBuilder type = new StringBuilder();
        for (int i = 0; i < bean.operation.size(); i++) {
            type.append("#")
                    .append(bean.operation.get(i))
                    .append("# ");
        }
        tv_service_type.setText(type.toString());//

        List<SafeUiBean> list = new ArrayList<>();
        for (int i = 0; i < bean.friendliness.size(); i++) {
            String s = bean.friendliness.get(i);
            SafeUiBean b = new SafeUiBean();
            b.dec = s;
            if (s.equals(getString(R.string.new_wind_sys))) {
                b.res_id = R.drawable.new_wind_sys;
            } else if (s.equals(getString(R.string.air_clear_sys))) {
                b.res_id = R.drawable.air_clear_sys;
            } else if (s.equals(getString(R.string.safe_power))) {
                b.res_id = R.drawable.safe_power;
            } else if (s.equals(getString(R.string.real_time_protect))) {
                b.res_id = R.drawable.real_time_protect;
            } else if (s.equals(getString(R.string.has_wifi))) {
                b.res_id = R.drawable.wifi;
            } else if (s.equals(getString(R.string.protect_floor))) {
                b.res_id = R.drawable.floor;
            } else if (s.equals(getString(R.string.air_humid))) {
                b.res_id = R.drawable.air_humid;
            } else if (s.equals(getString(R.string.safe_guard))) {
                b.res_id = R.drawable.guard;
            } else if (s.equals(getString(R.string.kit_package))) {
                b.res_id = R.drawable.kit;
            } else if (s.equals(getString(R.string.safe_table))) {
                b.res_id = R.drawable.safe_table;
            }
            if (!s.equals("")) {
                list.add(b);
            }
        }

        if (list.isEmpty()) {
            tv_addr_safe.setVisibility(View.GONE);
            view_line_4.setVisibility(View.GONE);
            rv_addr_safe.setVisibility(View.GONE);
        } else {
            tv_addr_safe.setVisibility(View.VISIBLE);
            view_line_4.setVisibility(View.VISIBLE);
            rv_addr_safe.setVisibility(View.VISIBLE);
            AddrDecInfoAdapter decInfoAdapter = new AddrDecInfoAdapter(ServiceDetailInfoActivity.this, list);
            LinearLayoutManager manager = new LinearLayoutManager(ServiceDetailInfoActivity.this);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rv_addr_safe.setLayoutManager(manager);
            rv_addr_safe.setAdapter(decInfoAdapter);
        }

        tv_service_location.setText(bean.address);

        addMarkers(bean.latitude,bean.longitude);
    }

    /**
     * 检查人数是否正确 服务器返回了-1
     *
     * @param max_stu
     */
    private void checkClassMaxStu(int max_stu) {
        if (max_stu <= -1) {//隐藏
            iv_detail_class.setVisibility(View.GONE);
            tv_detail_class_dec.setVisibility(View.GONE);
            tv_class_mb_no.setVisibility(View.GONE);
            iv_detail_teacher.setVisibility(View.GONE);
            tv_detail_teacher_dec.setVisibility(View.GONE);
            tv_t_s_ratio.setVisibility(View.GONE);
        } else {
            iv_detail_class.setVisibility(View.VISIBLE);
            tv_detail_class_dec.setVisibility(View.VISIBLE);
            tv_class_mb_no.setVisibility(View.VISIBLE);
            iv_detail_teacher.setVisibility(View.VISIBLE);
            tv_detail_teacher_dec.setVisibility(View.VISIBLE);
            tv_t_s_ratio.setVisibility(View.VISIBLE);
        }
    }

    private void sendLikeData() {
        showProcessDialog();
        if (bean.is_collected) {//已收藏 点击取消
            presenter.likePop(bean.service_id);
        } else {
            presenter.likePush(bean.service_id);
        }
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


    @Override
    public void onBackPressed() {
        setResult(isNeedRefresh ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        super.onBackPressed();

    }

    @Override
    protected void setStatusBarColor() {
    }

}
