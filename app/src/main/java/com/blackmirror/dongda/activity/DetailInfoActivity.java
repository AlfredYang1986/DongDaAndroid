package com.blackmirror.dongda.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.OtherUtils;
import com.blackmirror.dongda.adapter.AddrDecInfoAdapter;
import com.blackmirror.dongda.adapter.PhotoDetailAdapter;

import java.util.ArrayList;
import java.util.List;

public class DetailInfoActivity extends AppCompatActivity {

    private ViewPager vp_detail_photo;
    private TabLayout tl_detail_tab;
    private RecyclerView rv_addr_safe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);
        OtherUtils.setStatusBarColor(DetailInfoActivity.this);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        vp_detail_photo = findViewById(R.id.vp_detail_photo);
        tl_detail_tab = findViewById(R.id.tl_detail_tab);
        rv_addr_safe = findViewById(R.id.rv_addr_safe);
    }

    private void initData() {
        List<Integer> list=new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(i);
        }
        PhotoDetailAdapter adapter = new PhotoDetailAdapter(list);
        tl_detail_tab.addTab(tl_detail_tab.newTab().setText("时刻"));
        tl_detail_tab.addTab(tl_detail_tab.newTab().setText("教学区"));
        tl_detail_tab.addTab(tl_detail_tab.newTab().setText("生活区"));
        tl_detail_tab.addTab(tl_detail_tab.newTab().setText("家长休息区"));
        vp_detail_photo.setAdapter(adapter);
        /**
         * 下面的方法可以解决tab字体丢失的问题,不要用setupWithViewPager()方法
         */
        //   activity.getTabLayout().setupWithViewPager(mVpPager);
        vp_detail_photo.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tl_detail_tab));
        tl_detail_tab.addOnTabSelectedListener(new TabLayout
                .ViewPagerOnTabSelectedListener(vp_detail_photo));
//        tl_detail_tab.setupWithViewPager(vp_detail_photo);

        List<Integer> l=new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            l.add(i);
        }
        AddrDecInfoAdapter decInfoAdapter = new AddrDecInfoAdapter(DetailInfoActivity.this, list);
        LinearLayoutManager manager = new LinearLayoutManager(DetailInfoActivity.this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_addr_safe.setLayoutManager(manager);
        rv_addr_safe.setAdapter(decInfoAdapter);

    }

    private void initListener() {

    }
}
