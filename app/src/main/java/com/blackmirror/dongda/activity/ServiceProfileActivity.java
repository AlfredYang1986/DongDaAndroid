package com.blackmirror.dongda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.OtherUtils;
import com.blackmirror.dongda.model.ServiceProfileBean;
import com.facebook.drawee.view.SimpleDraweeView;

public class ServiceProfileActivity extends AppCompatActivity {

    private ImageView iv_service_back;
    private SimpleDraweeView sv_service_photo;
    private ServiceProfileBean bean;
    private TextView tv_service_tag;
    private TextView tv_service_name;
    private TextView tv_service_teacher_name;
    private TextView tv_service_about_me;
    private TextView tv_service_dec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_profile);
        OtherUtils.initSystemBarColor(this);
        bean = getIntent().getParcelableExtra("bean");
        initView();
        initData();
        initListener();
    }

    private void initView() {
        iv_service_back = findViewById(R.id.iv_service_back);
        sv_service_photo = findViewById(R.id.sv_service_photo);
        tv_service_tag = findViewById(R.id.tv_service_tag);
        tv_service_name = findViewById(R.id.tv_service_name);
        tv_service_teacher_name = findViewById(R.id.tv_service_teacher_name);
        tv_service_about_me = findViewById(R.id.tv_service_about_me);
        tv_service_dec = findViewById(R.id.tv_service_dec);
    }

    private void initData() {
        if (bean!=null){
            sv_service_photo.setImageURI(OtherUtils.resourceIdToUri(this,bean.res_id));
            tv_service_tag.setText(bean.brand_tag);
            tv_service_name.setText(bean.brand_name);
            tv_service_teacher_name.setText(bean.brand_name+"老师");
            tv_service_dec.setText(bean.about_brand);
        }
    }

    private void initListener() {
        iv_service_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public static void startActivity(AppCompatActivity activity,String tv_brand_tag,int bg_res_id){
        Intent intent = new Intent(activity, ServiceProfileActivity.class);
        intent.putExtra("tv_brand_tag",tv_brand_tag);
        intent.putExtra("bg_res_id",bg_res_id);
        intent.putExtra("tv_brand_tag",tv_brand_tag);
    }
}
