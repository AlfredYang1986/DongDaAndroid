package com.blackmirror.dongda.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.OtherUtils;
import com.blackmirror.dongda.Tools.ToastUtils;
import com.blackmirror.dongda.adapter.MyLikeListAdapter;
import com.blackmirror.dongda.adapter.itemdecoration.TopItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MyLikeActivity extends AppCompatActivity {

    private RecyclerView rv_my_like;
    private ImageView iv_home_head_back;
    private TextView tv_home_head_title;
    private MyLikeListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_like);
        OtherUtils.setStatusBarColor(MyLikeActivity.this);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        rv_my_like = findViewById(R.id.rv_my_like);
    }

    private void initData() {
        iv_home_head_back = findViewById(R.id.iv_home_head_back);
        tv_home_head_title = findViewById(R.id.tv_home_head_title);

        tv_home_head_title.setText("我的收藏");
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

        adapter = new MyLikeListAdapter(MyLikeActivity.this, list);
        rv_my_like.setLayoutManager(new LinearLayoutManager(MyLikeActivity.this));
        rv_my_like.setAdapter(adapter);
        rv_my_like.addItemDecoration(new TopItemDecoration(40,40));
        adapter.setOnLikeListClickListener(new MyLikeListAdapter.OnLikeListClickListener() {
            @Override
            public void onItemLikeListClick(View view, int position) {

            }

            @Override
            public void onItemLikeClick(View view, int position) {
                ToastUtils.showShortToast("点击了 "+position);
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
