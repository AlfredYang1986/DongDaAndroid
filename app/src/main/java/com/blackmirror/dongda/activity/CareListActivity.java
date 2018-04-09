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
import com.blackmirror.dongda.adapter.CareListAdapter;
import com.blackmirror.dongda.adapter.itemdecoration.TopItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class CareListActivity extends AppCompatActivity {

    private ImageView iv_home_head_back;
    private TextView tv_home_head_title;
    private RecyclerView rv_care_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_list);
        initView();
        initData();
        initListener();
        OtherUtils.setStatusBarColor(this);
    }

    private void initView() {
        iv_home_head_back = findViewById(R.id.iv_home_head_back);
        tv_home_head_title = findViewById(R.id.tv_home_head_title);
        rv_care_list = findViewById(R.id.rv_care_list);
    }

    private void initData() {
        tv_home_head_title.setText("看顾");
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
        CareListAdapter adapter = new CareListAdapter(CareListActivity.this, list);
        rv_care_list.setLayoutManager(new LinearLayoutManager(CareListActivity.this));
        rv_care_list.setAdapter(adapter);
        rv_care_list.addItemDecoration(new TopItemDecoration(40,40));
        adapter.setOnCareListClickListener(new CareListAdapter.OnCareListClickListener() {
            @Override
            public void onItemCareListClick(View view, int position) {

            }

            @Override
            public void onItemCareLikeClick(View view, int position) {
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
