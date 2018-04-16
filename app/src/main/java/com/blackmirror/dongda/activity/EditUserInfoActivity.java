package com.blackmirror.dongda.activity;

import android.os.Bundle;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.OtherUtils;
import com.blackmirror.dongda.controllers.AYActivity;

public class EditUserInfoActivity extends AYActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        OtherUtils.setStatusBarColor(EditUserInfoActivity.this);
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

    @Override
    protected void bindingFragments() {

    }
}
