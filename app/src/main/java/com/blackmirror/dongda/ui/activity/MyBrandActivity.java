package com.blackmirror.dongda.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.facebook.drawee.view.SimpleDraweeView;

public class MyBrandActivity extends BaseActivity {

    private ImageView iv_back;
    private SimpleDraweeView sv_brand_photo;
    private TextView tv_brand_name;
    private TextView tv_about_brand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_brand;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        sv_brand_photo = findViewById(R.id.sv_brand_photo);
        tv_brand_name = findViewById(R.id.tv_brand_name);
        tv_about_brand = findViewById(R.id.tv_about_brand);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
