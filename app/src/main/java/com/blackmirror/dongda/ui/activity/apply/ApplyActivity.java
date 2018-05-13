package com.blackmirror.dongda.ui.activity.apply;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.ui.base.BaseActivity;

public class ApplyActivity extends BaseActivity{

    private TextView tv_apply_account;

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
        tv_apply_account = findViewById(R.id.tv_apply_account);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        tv_apply_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ApplyActivity.this,ApplyNameActivity.class));
            }
        });
    }
}
