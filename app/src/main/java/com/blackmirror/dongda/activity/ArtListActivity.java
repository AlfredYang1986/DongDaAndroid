package com.blackmirror.dongda.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.OtherUtils;
import com.blackmirror.dongda.Tools.ToastUtils;
import com.blackmirror.dongda.adapter.ArtListAdapter;
import com.blackmirror.dongda.adapter.ArtListTestAdapter;
import com.blackmirror.dongda.adapter.itemdecoration.GridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ArtListActivity extends AppCompatActivity {

    private ImageView iv_home_head_back;
    private TextView tv_home_head_title;
    private RecyclerView rv_art_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art_list);
        initView();
        initData();
        //        initTestData();
        initListener();
        OtherUtils.setStatusBarColor(this);
    }

    private void initTestData() {
        tv_home_head_title.setText("艺术");
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.home_cover_00);
        list.add(R.drawable.home_cover_01);
        list.add(R.drawable.home_cover_02);
        list.add(R.drawable.home_cover_03);
        list.add(R.drawable.home_cover_04);
        list.add(R.drawable.home_cover_00);
        list.add(R.drawable.home_cover_01);
        list.add(R.drawable.home_cover_02);
        list.add(R.drawable.home_cover_03);
        list.add(R.drawable.home_cover_04);
        ArtListTestAdapter adapter = new ArtListTestAdapter(ArtListActivity.this, list);
        rv_art_list.setLayoutManager(new GridLayoutManager(ArtListActivity.this, 2));
        rv_art_list.setAdapter(adapter);
        rv_art_list.addItemDecoration(new GridItemDecoration(26, 16, 15, 48, 44, 2));
        adapter.setOnArtListClickListener(new ArtListTestAdapter.OnArtListClickListener() {
            @Override
            public void onItemArtListClick(View view, int position) {
                ToastUtils.showShortToast("点击了 " + position);
            }
        });
    }

    private void initView() {
        iv_home_head_back = findViewById(R.id.iv_home_head_back);
        tv_home_head_title = findViewById(R.id.tv_home_head_title);
        rv_art_list = findViewById(R.id.rv_art_list);
    }

    private void initData() {
        tv_home_head_title.setText("艺术");
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.home_cover_00);
        list.add(R.drawable.home_cover_01);
        list.add(R.drawable.coverlist_bg_theme);
        list.add(R.drawable.coverlist_bg_theme);
        list.add(R.drawable.coverlist_bg_theme);
        list.add(R.drawable.coverlist_bg_theme);
        list.add(R.drawable.coverlist_bg_theme);
        list.add(R.drawable.coverlist_bg_theme);
        list.add(R.drawable.coverlist_bg_theme);
        list.add(R.drawable.coverlist_bg_theme);
        ArtListAdapter adapter = new ArtListAdapter(ArtListActivity.this, list);
        rv_art_list.setLayoutManager(new GridLayoutManager(ArtListActivity.this, 2));
        rv_art_list.setAdapter(adapter);
        rv_art_list.addItemDecoration(new GridItemDecoration(16, 16, 15, 48, 44, 2));
        adapter.setOnArtListClickListener(new ArtListAdapter.OnArtListClickListener() {
            @Override
            public void onItemArtListClick(View view, int position) {
                ToastUtils.showShortToast("点击了 " + position);
            }
        });
    }

    private void initListener() {
        iv_home_head_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
