package com.blackmirror.dongda.ui.activity.enrol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.ui.base.BaseActivity;

public class EnrolPayTypeActivity extends BaseActivity implements View.OnClickListener{

    private ImageView iv_back;
    private TextView tv_next;
    private RadioButton rb_time_pay;
    private RadioButton rb_mb_pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_enrol_pay_type;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_next = findViewById(R.id.tv_next);
        rb_time_pay = findViewById(R.id.rb_time_pay);
        rb_mb_pay = findViewById(R.id.rb_mb_pay);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_next:
                break;
            case R.id.rb_time_pay:
                startActivity(new Intent(EnrolPayTypeActivity.this,EnrolTimePayActivity.class));
                break;
            case R.id.rb_mb_pay:
                startActivity(new Intent(EnrolPayTypeActivity.this,EnrolMbPayActivity.class));
                break;
        }
    }
}
