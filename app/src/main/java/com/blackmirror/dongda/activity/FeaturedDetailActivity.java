package com.blackmirror.dongda.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.OtherUtils;
import com.blackmirror.dongda.adapter.FeaturedDetailAdapter;

import java.util.ArrayList;
import java.util.List;

public class FeaturedDetailActivity extends AppCompatActivity {

    final static String TAG = "FeaturedDetailActivity";
    private ImageView iv_featured_detail_back;
    private TextView tv_featured_detail_title;
    private RecyclerView rv_featured_detail;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured_detail);
        OtherUtils.setStatusBarColor(FeaturedDetailActivity.this);
        initView();
        initData();
        initListener();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int top = toolbar.getPaddingTop();
        int height = OtherUtils.getStatusBarHeight(FeaturedDetailActivity.this);
//        toolbar.setPadding(toolbar.getPaddingLeft(),height/2,toolbar.getPaddingRight(),toolbar.getPaddingBottom());
    }

    private void initView() {
        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        iv_featured_detail_back = findViewById(R.id.iv_featured_detail_back);
        tv_featured_detail_title = findViewById(R.id.tv_featured_detail_title);
        rv_featured_detail = findViewById(R.id.rv_featured_detail);
    }

    private void initData() {
        List<Integer> list= new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        FeaturedDetailAdapter adapter = new FeaturedDetailAdapter(FeaturedDetailActivity.this,list);
        LinearLayoutManager manager = new LinearLayoutManager(FeaturedDetailActivity.this);
        rv_featured_detail.setLayoutManager(manager);
        rv_featured_detail.setAdapter(adapter);
    }

    private void initListener() {
        iv_featured_detail_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

   /* @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected void bindingFragments() {

    }*/
}
