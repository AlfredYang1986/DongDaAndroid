package com.blackmirror.dongda.ui.activity.apply;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.di.component.DaggerApplyServiceComponent;
import com.blackmirror.dongda.domain.model.ApplyServiceDomainBean;
import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.presenter.ApplyPresenter;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.blackmirror.dongda.utils.AppConstant;
import com.blackmirror.dongda.utils.SnackbarUtils;
import com.blackmirror.dongda.utils.ToastUtils;

public class ApplyServiceActivity extends BaseActivity implements View.OnClickListener,ApplyContract.View{

    private ImageView iv_back;
    private TextView tv_next;
    private RadioButton rb_care;
    private RadioButton rb_class;
    private RadioButton rb_experience;
    private TextView tv_service_about_dec;
    private int check_status;//1 看顾 2 课程 3 体验
    private boolean can_next;
    private String user_name;
    private String city_name;
    private String brand_name;
    private String phone_no;
    private ApplyPresenter presenter;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_apply_service;
    }

    @Override
    protected void initInject() {
        presenter = DaggerApplyServiceComponent.builder()
                .activity(this)
                .view(this)
                .build()
                .getApplyPresenter();
    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_next = findViewById(R.id.tv_next);
        rb_care = findViewById(R.id.rb_care);
        rb_class = findViewById(R.id.rb_class);
        rb_experience = findViewById(R.id.rb_experience);
        tv_service_about_dec = findViewById(R.id.tv_service_about_dec);
    }

    @Override
    protected void initData() {
        user_name = getIntent().getStringExtra("user_name");
        city_name = getIntent().getStringExtra("city_name");
        brand_name = getIntent().getStringExtra("brand_name");
        phone_no = getIntent().getStringExtra("phone_no");
    }

    @Override
    protected void initListener() {

        iv_back.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        tv_service_about_dec.setOnClickListener(this);


        rb_care.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                check_status = isChecked ? 1 : check_status;
                canNext();
            }
        });

        rb_class.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                check_status = isChecked ? 2 : check_status;
                canNext();
            }
        });

        rb_experience.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                check_status = isChecked ? 3 : check_status;
                canNext();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_next:
                apply();
                break;
            case R.id.tv_service_about_dec:
                startActivity(new Intent(ApplyServiceActivity.this,ServiceHelpActivity.class));
                break;
        }
    }

    private void apply() {
        showProcessDialog();
        presenter.apply(brand_name,user_name,"",phone_no,city_name);
    }

    private void canNext() {
        if (!can_next) {
            can_next = true;
            tv_next.setTextColor(Color.parseColor("#FF59D5C7"));
        } else {
            can_next = false;
            tv_next.setTextColor(Color.parseColor("#FFD9D9D9"));
        }
    }


    @Override
    public void onApplySuccess(ApplyServiceDomainBean bean) {
        closeProcessDialog();
        startActivity(new Intent(ApplyServiceActivity.this,ApplySuccessActivity.class));
    }

    @Override
    public void onError(BaseDataBean bean) {
        closeProcessDialog();
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(tv_next, bean.message);
        } else {
            ToastUtils.showShortToast(bean.message + "(" + bean.code + ")");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (check_status) {
            case 1:
                if (!rb_care.isChecked()) {
                    rb_care.setChecked(true);
                }
                break;
            case 2:
                if (!rb_class.isChecked()) {
                    rb_class.setChecked(true);
                }
                break;
            case 3:
                if (!rb_experience.isChecked()) {
                    rb_experience.setChecked(true);
                }
                break;
        }
    }
}
