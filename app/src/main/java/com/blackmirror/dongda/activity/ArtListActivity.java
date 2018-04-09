package com.blackmirror.dongda.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;

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
        initListener();
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
        list.add(R.drawable.home_cover_02);
        list.add(R.drawable.home_cover_03);
        list.add(R.drawable.home_cover_04);
        list.add(R.drawable.home_cover_00);
        list.add(R.drawable.home_cover_01);
        list.add(R.drawable.home_cover_02);
        list.add(R.drawable.home_cover_03);
        list.add(R.drawable.home_cover_04);
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
