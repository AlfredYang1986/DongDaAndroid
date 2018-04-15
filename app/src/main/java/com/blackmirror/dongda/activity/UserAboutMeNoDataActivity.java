package com.blackmirror.dongda.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.OtherUtils;

public class UserAboutMeNoDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_about_me_no_data);
        OtherUtils.setStatusBarColor(UserAboutMeNoDataActivity.this);
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
