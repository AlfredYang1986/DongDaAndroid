package com.blackmirror.dongda.ui.activity.newservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.ui.base.BaseActivity;

public class ServiceAgeActivity extends BaseActivity implements View.OnClickListener{

    private ImageView iv_back;
    private TextView tv_next;
    private ConstraintLayout cl_choose_min_age;
    private TextView tv_choose_min_age;
    private ConstraintLayout cl_choose_max_age;
    private TextView tv_choose_max_age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_service_age;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_next = findViewById(R.id.tv_next);
        cl_choose_min_age = findViewById(R.id.cl_choose_min_age);
        tv_choose_min_age = findViewById(R.id.tv_choose_min_age);
        cl_choose_max_age = findViewById(R.id.cl_choose_max_age);
        tv_choose_max_age = findViewById(R.id.tv_choose_max_age);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_next:
                Intent intent = new Intent(ServiceAgeActivity.this, ServiceTeacherNumActivity.class);
                startActivity(intent);
                break;
            case R.id.cl_choose_min_age:
                break;
            case R.id.cl_choose_max_age:
                break;
        }
    }
}
