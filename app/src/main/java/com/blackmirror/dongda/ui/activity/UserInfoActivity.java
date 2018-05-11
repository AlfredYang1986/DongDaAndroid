package com.blackmirror.dongda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.ui.activity.apply.ApplyActivity;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.blackmirror.dongda.utils.DeviceUtils;
import com.facebook.drawee.view.SimpleDraweeView;

public class UserInfoActivity extends BaseActivity implements View.OnClickListener{

    private ImageView iv_home_head_back;
    private SimpleDraweeView sv_user_photo;
    private TextView tv_user_name;
    private TextView tv_join_service;
    private TextView tv_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        iv_home_head_back = findViewById(R.id.iv_home_head_back);
        sv_user_photo = findViewById(R.id.sv_user_photo);
        tv_user_name = findViewById(R.id.tv_user_name);
        tv_join_service = findViewById(R.id.tv_join_service);
        tv_setting = findViewById(R.id.tv_setting);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        iv_home_head_back.setOnClickListener(this);
        sv_user_photo.setOnClickListener(this);
        tv_join_service.setOnClickListener(this);
        tv_setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_home_head_back:
                finish();
                break;
            case R.id.sv_user_photo:
                break;
            case R.id.tv_join_service:
                startActivity(new Intent(UserInfoActivity.this,ApplyActivity.class));
                break;
            case R.id.tv_setting:
                break;
        }
    }

    @Override
    protected void setStatusBarColor() {
        DeviceUtils.initSystemBarColor(this);
    }


}
