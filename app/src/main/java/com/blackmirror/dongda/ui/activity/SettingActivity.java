package com.blackmirror.dongda.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.ui.base.BaseActivity;

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private ImageView iv_back;
    private TextView tv_change_order_mode;
    private TextView tv_clear_cache;
    private TextView tv_cache_size;
    private TextView tv_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_change_order_mode = findViewById(R.id.tv_change_order_mode);
        tv_clear_cache = findViewById(R.id.tv_clear_cache);
        tv_cache_size = findViewById(R.id.tv_cache_size);
        tv_logout = findViewById(R.id.tv_logout);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
        tv_change_order_mode.setOnClickListener(this);
        tv_clear_cache.setOnClickListener(this);
        tv_logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_change_order_mode:
                break;
            case R.id.tv_clear_cache:
                break;
            case R.id.tv_logout:
                break;
        }
    }
}
