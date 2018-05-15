package com.blackmirror.dongda.ui.activity.apply;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.blackmirror.dongda.utils.DeviceUtils;

public class ApplyActivity extends BaseActivity{

    private ImageView iv_back;
    private Button btn_apply_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_apply;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        btn_apply_account = findViewById(R.id.btn_apply_account);
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

        btn_apply_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ApplyActivity.this,ApplyNameActivity.class));
            }
        });
    }

    @Override
    protected void setStatusBarColor() {
        DeviceUtils.initSystemBarColor(this);
    }
}
