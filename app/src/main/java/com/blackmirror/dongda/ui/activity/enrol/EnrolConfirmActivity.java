package com.blackmirror.dongda.ui.activity.enrol;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.blackmirror.dongda.utils.OSSUtils;
import com.facebook.drawee.view.SimpleDraweeView;

public class EnrolConfirmActivity extends BaseActivity implements View.OnClickListener{

    private ImageView iv_back;
    private TextView tv_save;
    private SimpleDraweeView sv_service_photo;
    private TextView tv_confirm_location;
    private TextView tv_confirm_brand;
    private TextView tv_child_age;
    private TextView tv_to_set_date;
    private Button btn_confirm_enrol;
    private TextView tv_service_about_dec;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_enrol_confirm;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_save = findViewById(R.id.tv_save);
        sv_service_photo = findViewById(R.id.sv_service_photo);
        tv_confirm_location = findViewById(R.id.tv_confirm_location);
        tv_confirm_brand = findViewById(R.id.tv_confirm_brand);
        tv_child_age = findViewById(R.id.tv_child_age);
        tv_to_set_date = findViewById(R.id.tv_to_set_date);
        btn_confirm_enrol = findViewById(R.id.btn_confirm_enrol);
        tv_service_about_dec = findViewById(R.id.tv_service_about_dec);
    }

    @Override
    protected void initData() {
        String service_image = getIntent().getStringExtra("service_image");
        sv_service_photo.setImageURI(OSSUtils.getSignedUrl(service_image));
        tv_confirm_brand.setText(getIntent().getStringExtra("service_leaf"));
        String min_age = getIntent().getStringExtra("min_age");
        String max_age = getIntent().getStringExtra("max_age");
        tv_child_age.setText(min_age+"-"+max_age+"岁");
    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        tv_to_set_date.setOnClickListener(this);
        btn_confirm_enrol.setOnClickListener(this);
        tv_service_about_dec.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_save:
                break;
            case R.id.tv_to_set_date:
                startActivity(new Intent(this,EnrolUnOpenDayActivity.class));
                break;
            case R.id.btn_confirm_enrol:
                enrol();
                break;
            case R.id.tv_service_about_dec:
                break;
        }
    }

    private void enrol() {

    }
}
