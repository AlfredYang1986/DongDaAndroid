package com.blackmirror.dongda.ui.activity.apply;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.ui.base.BaseActivity;

public class ApplyServiceActivity extends BaseActivity {

    private RadioButton rb_care;
    private RadioButton rb_class;
    private RadioButton rb_experience;
    private int check_status;//1 看顾 2 课程 3 体验

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_service);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_apply_service;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        rb_care = findViewById(R.id.rb_care);
        rb_class = findViewById(R.id.rb_class);
        rb_experience = findViewById(R.id.rb_experience);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        rb_care.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                check_status = isChecked ? 1 : check_status;
            }
        });

        rb_class.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                check_status = isChecked ? 2 : check_status;
            }
        });

        rb_experience.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                check_status = isChecked ? 3 : check_status;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch(check_status){
            case 1:
                if (!rb_care.isChecked()){
                    rb_care.setChecked(true);
                }
                break;
            case 2:
                if (!rb_class.isChecked()){
                    rb_class.setChecked(true);
                }
                break;
            case 3:
                if (!rb_experience.isChecked()){
                    rb_experience.setChecked(true);
                }
                break;
        }
    }
}
