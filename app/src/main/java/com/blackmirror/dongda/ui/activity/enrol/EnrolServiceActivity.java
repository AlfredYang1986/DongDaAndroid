package com.blackmirror.dongda.ui.activity.enrol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.blackmirror.dongda.utils.DeviceUtils;
import com.blackmirror.dongda.utils.OSSUtils;
import com.facebook.drawee.view.SimpleDraweeView;

public class EnrolServiceActivity extends BaseActivity {

    private ImageView iv_back;
    private TextView iv_service_location;
    private SimpleDraweeView sv_service_photo;
    private TextView tv_service_brand;
    private Button btn_start_enrol;
    private String service_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_enrol_service;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        iv_service_location = findViewById(R.id.tv_service_location);
        sv_service_photo = findViewById(R.id.sv_service_photo);
        tv_service_brand = findViewById(R.id.tv_service_brand);
        btn_start_enrol = findViewById(R.id.btn_start_enrol);
    }

    @Override
    protected void initData() {

        String locations = getIntent().getStringExtra("locations");
        String address = getIntent().getStringExtra("address");
        String service_leaf = getIntent().getStringExtra("service_leaf");
        String service_image = getIntent().getStringExtra("service_image");
        service_id = getIntent().getStringExtra("service_id");
        iv_service_location.setText(address);
        sv_service_photo.setImageURI(OSSUtils.getSignedUrl(service_image));
        tv_service_brand.setText(service_leaf);
    }

    @Override
    protected void initListener() {

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_start_enrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnrolServiceActivity.this, EnrolAgeActivity.class);
                intent.putExtra("service_id",service_id);
                intent.putExtra("locations",getIntent().getStringExtra("locations"));
                intent.putExtra("service_leaf",getIntent().getStringExtra("service_leaf"));
                intent.putExtra("service_image",getIntent().getStringExtra("service_image"));
                intent.putExtra("address",getIntent().getStringExtra("address"));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void setStatusBarColor() {
        DeviceUtils.setStatusBarColor(this,getResources().getColor(R.color.enrol_bg));
    }
}
