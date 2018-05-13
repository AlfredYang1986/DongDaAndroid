package com.blackmirror.dongda.ui.activity.apply;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.ui.base.BaseActivity;

public class ServiceHelpActivity extends BaseActivity {

    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_service_help;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
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
