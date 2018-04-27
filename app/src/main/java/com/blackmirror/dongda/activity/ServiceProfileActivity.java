package com.blackmirror.dongda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.OtherUtils;

public class ServiceProfileActivity extends AppCompatActivity {

    private ImageView iv_service_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_profile);
        OtherUtils.setStatusBarColor(ServiceProfileActivity.this);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        iv_service_back = findViewById(R.id.iv_service_back);
    }

    private void initData() {

    }

    private void initListener() {
        iv_service_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public static void startActivity(AppCompatActivity activity,String tv_brand_tag,int bg_res_id){
        Intent intent = new Intent(activity, ServiceProfileActivity.class);
        intent.putExtra("tv_brand_tag",tv_brand_tag);
        intent.putExtra("bg_res_id",bg_res_id);
        intent.putExtra("tv_brand_tag",tv_brand_tag);
    }
}
