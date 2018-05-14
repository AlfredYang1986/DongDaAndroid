package com.blackmirror.dongda.ui.activity.newservice;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.ui.base.BaseActivity;

public class ServiceFixedActivity extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_head_title;
    private TextView tv_save;
    private TextInputEditText tet_all_month_price;
    private TextInputEditText tet_mid_month_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_service_fixed;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_head_title = findViewById(R.id.tv_head_title);
        tv_save = findViewById(R.id.tv_save);
        tet_all_month_price = findViewById(R.id.tet_all_month_price);
        tet_mid_month_price = findViewById(R.id.tet_mid_month_price);
    }

    @Override
    protected void initData() {
        tv_head_title.setText("固定付费设置");
    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
