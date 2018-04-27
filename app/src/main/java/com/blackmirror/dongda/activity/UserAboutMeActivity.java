package com.blackmirror.dongda.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.facebook.drawee.view.SimpleDraweeView;

public class UserAboutMeActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView iv_service_back;
    private TextView tv_user_logout;
    private SimpleDraweeView sv_user_about_photo;
    private ImageView iv_about_edit;
    private TextView tv_user_about_name;
    private TextView tv_user_about_dec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_about_me);
        initSystemBarColor();
        initView();
        initData();
        initListener();
    }

    private void initView() {
        iv_service_back = findViewById(R.id.iv_service_back);
        tv_user_logout = findViewById(R.id.tv_user_logout);
        sv_user_about_photo = findViewById(R.id.sv_user_about_photo);
        iv_about_edit = findViewById(R.id.iv_about_edit);
        tv_user_about_name = findViewById(R.id.tv_user_about_name);
        tv_user_about_dec = findViewById(R.id.tv_user_about_dec);

    }

    private void initData() {

    }

    private void initListener() {
        iv_service_back.setOnClickListener(this);
        tv_user_logout.setOnClickListener(this);
        iv_about_edit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.iv_service_back:
                finish();
                break;
            case R.id.tv_user_logout:
                break;
            case R.id.iv_about_edit:
                Intent intent = new Intent(UserAboutMeActivity.this, EditUserInfoActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initSystemBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup viewGroup = this.findViewById(Window.ID_ANDROID_CONTENT);
            View childView = viewGroup.getChildAt(0);
            if (null != childView) {
                //                childView.setFitsSystemWindows(false);
            }
        }
    }

}
