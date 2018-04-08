package com.blackmirror.dongda.Landing;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.blackmirror.dongda.Home.HomeActivity.AYHomeActivity;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.AYApplication;
import com.blackmirror.dongda.Tools.AppConstant;
import com.blackmirror.dongda.Tools.LogUtils;
import com.blackmirror.dongda.Tools.PermissionUtils;
import com.blackmirror.dongda.controllers.AYActivity;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

public class LandingActivity extends AYActivity {

    final static String TAG = "Landing Activity";
    private android.widget.RelativeLayout rl_phone_login;
    private android.widget.RelativeLayout rl_wechat_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        //在setContentView之后调用
        initSystemBarColor();
        initView();
        requestPermissions();
        initData();
        initListener();

    }

    private void initView() {
        rl_phone_login = findViewById(R.id.rl_phone_login);
        rl_wechat_login = findViewById(R.id.rl_wechat_login);
    }

    private void initData() {

    }

    /**
     * 动态申请权限
     */
    private void requestPermissions() {

        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,Manifest.permission.READ_PHONE_STATE};

        PermissionUtils.requestMulitPermissions(LandingActivity.this, permissions);

    }

    private void initListener() {
        rl_phone_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingActivity.this, PhoneInputActivity.class);
                startActivity(intent);
            }
        });

        rl_wechat_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                weChatLogin();
                startActivity(new Intent(LandingActivity.this, AYHomeActivity.class));
            }
        });

        /*{
            Button btn = (Button) findViewById(R.id.phone_login_btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "login dongda with phone number");
                    Intent intent = new Intent(LandingActivity.this, PhoneInputActivity.class);
                    startActivity(intent);
                }
            });
        }

        {
            Button btn = (Button) findViewById(R.id.wechat_login_btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "login dongda with wechat");
                }
            });
        }

        {
            AYFacade facade = (AYFacade) AYFactoryManager.getInstance(this).queryInstance("facade", "DongdaCommanFacade");
            AYCommand cmd = facade.cmds.get("QueryCurrentLoginUser");
            Object o = cmd.excute();
            if (o != null) {

                *//**
         * 打印已登陆用户
         *//*
                Log.i(TAG, o.toString());
                Intent intent = new Intent(this, AYHomeActivity.class);
                startActivity(intent);
            }
        }*/
    }

    private void weChatLogin() {
        if (!AYApplication.weChatApi.isWXAppInstalled()) {
            Toast.makeText(getApplicationContext(), "您还未安装微信!", Toast.LENGTH_SHORT).show();
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "dongda_wx_login";
        AYApplication.weChatApi.sendReq(req);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case AppConstant.PERMISSION_REQUEST:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                        LogUtils.d("xcx",permissions[i]+" granted");
                    }else {
                        LogUtils.d("xcx",permissions[i]+" denied");

                    }
                }
                break;
        }
    }

    private void checkPermissionStatus() {

    }

    private void initSystemBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup viewGroup = this.findViewById(Window.ID_ANDROID_CONTENT);
            View childView = viewGroup.getChildAt(0);
            if (null != childView) {
                ViewCompat.setFitsSystemWindows(childView, false);
            }
        }
    }

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected void bindingFragments() {
        //        FragmentManager fm = getSupportFragmentManager();
        //        AYFragment f = this.fragments.get("frag_test");
        //        fm.beginTransaction().add(R.id.fragment_test, f).commit();
    }
}
