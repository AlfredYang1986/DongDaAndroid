package com.blackmirror.dongda.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.OtherUtils;

public class ServiceProfileActivity extends AppCompatActivity {

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

    }

    private void initData() {

    }

    private void initListener() {

    }
}