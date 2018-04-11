package com.blackmirror.dongda.Landing;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.blackmirror.dongda.Home.HomeActivity.AYHomeActivity;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.AppConstant;
import com.blackmirror.dongda.Tools.BasePrefUtils;
import com.blackmirror.dongda.Tools.LogUtils;
import com.blackmirror.dongda.Tools.PermissionUtils;
import com.blackmirror.dongda.Tools.ToastUtils;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.model.BaseBean;
import com.blackmirror.dongda.model.ErrorInfoBean;
import com.blackmirror.dongda.model.WeChatLoginServerBean;
import com.blackmirror.dongda.model.WeChatUserInfoBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class LandingActivity extends AYActivity implements PlatformActionListener {

    final static String TAG = "Landing Activity";
    private android.widget.RelativeLayout rl_phone_login;
    private android.widget.RelativeLayout rl_wechat_login;
    private static final PublishSubject<? extends BaseBean> pb = PublishSubject.create();
    private Disposable disposable;

    public static PublishSubject getWeChatInfo() {
        return pb;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        //在setContentView之后调用
        initSystemBarColor();
        initView();
        //        requestPermissions();
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
                Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE};

        PermissionUtils.requestMulitPermissions(LandingActivity.this, permissions);

    }

    private void initListener() {

        disposable = pb.subscribe(new Consumer<BaseBean>() {
            @Override
            public void accept(BaseBean bean) throws Exception {
                if (bean != null && bean instanceof WeChatUserInfoBean) {
                    WeChatUserInfoBean infoBean = (WeChatUserInfoBean) bean;
                    LogUtils.d(infoBean.toString());
                }
            }
        });

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
                weChatLogin();
                //                startActivity(new Intent(LandingActivity.this, AYHomeActivity.class));
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

    /**
     * 服务器成功回调
     *
     * @param arg
     */
    public void AYWeChatLoginCmdSuccess(JSONObject arg) {
        LogUtils.d("LandingActivity " + Thread.currentThread().getName());

        LogUtils.d("LandingActivity wechat login " + arg.toString());
        ToastUtils.showShortToast("登陆成功!");
        WeChatLoginServerBean bean = JSON.parseObject(arg.toString(), WeChatLoginServerBean.class);
        if (bean != null && "ok".equals(bean.status) && bean.result != null) {
            if (bean.result.user != null) {

            }
            BasePrefUtils.setAuthToken(bean.result.auth_token);
            startActivity(new Intent(LandingActivity.this, AYHomeActivity.class));
            finish();
        } else if (bean != null && bean.error != null) {
            ToastUtils.showShortToast(bean.error.message);
        }

    }

    /**
     * 服务器失败回调
     *
     * @param arg
     */
    public void AYWeChatLoginCmdFailed(JSONObject arg) {
        LogUtils.d("LandingActivity wechat failed " + arg.toString());
        //        Toast.makeText(this, sms_result.getErrorMessage(), LENGTH_LONG).show();
        //        sms_result = new SendSMSCodeResult(arg);
        ErrorInfoBean bean = JSON.parseObject(arg.toString(), ErrorInfoBean.class);
        if (bean != null && bean.error != null) {
            ToastUtils.showShortToast(bean.error.message);
        }

    }

    private void weChatLogin() {
       /* if (!AYApplication.weChatApi.isWXAppInstalled()) {
            Toast.makeText(getApplicationContext(), "您还未安装微信!", Toast.LENGTH_SHORT).show();
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "dongda_wx_login";
        AYApplication.weChatApi.sendReq(req);*/
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.setPlatformActionListener(this);
        wechat.SSOSetting(false);
        authorize(wechat, 1);

    }

    //授权
    private void authorize(Platform plat, int type) {
        if (plat.isAuthValid()) { //如果授权就删除授权资料
            plat.removeAccount(true);
        }
        plat.showUser(null);//授权并获取用户信息
    }

    private void unSubscribe() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unSubscribe();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case AppConstant.PERMISSION_REQUEST:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        LogUtils.d("xcx", permissions[i] + " granted");
                    } else {
                        LogUtils.d("xcx", permissions[i] + " denied");

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

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> map) {
        LogUtils.d("onComplete " + Thread.currentThread().getName());
        /*WeChatInfoBean bean = new WeChatInfoBean();
        //获取用户资料
        bean.userId = platform.getDb().getUserId();//获取用户账号
        bean.userName = platform.getDb().getUserName();//获取用户名字
        bean.userIcon = platform.getDb().getUserIcon();//获取用户头像
        bean.userGender = platform.getDb().getUserGender(); //获取用户性别，m = 男, f = 女，如果微信没有设置性别,默认返回null
        bean.token = platform.getDb().getToken();*/

        String userId = platform.getDb().getUserId();//获取用户账号
        String userName = platform.getDb().getUserName();//获取用户名字
        String userIcon = platform.getDb().getUserIcon();//获取用户头像
        String userGender = platform.getDb().getUserGender(); //获取用户性别，m = 男, f = 女，如果微信没有设置性别,默认返回null
        String token = platform.getDb().getToken();


        AYFacade facade = facades.get("LoginFacade");

        try {

            Map<String, String> m = new HashMap<>();

            m.put("provide_uid", userId);
            m.put("provide_token", token);
            m.put("provide_screen_name", userName);
            m.put("provide_name", "wechat");
            m.put("provide_screen_photo", userIcon);

            //            object.put("third", m);
            JSONObject o = new JSONObject(m);
            JSONObject object = new JSONObject();
            object.put("third", o);

            LogUtils.d("wechat " + object.toString());
            facade.execute("LoginWithWeChat", object);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Observable.just(throwable.getMessage())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (!isViewValid()) {
                            return;
                        }
                        if (!TextUtils.isEmpty(s))
                            ToastUtils.showShortToast(s);
                    }
                });
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Observable.just("")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (!isViewValid()) {
                            return;
                        }
                        ToastUtils.showShortToast("授权已取消");
                    }
                });
    }
}
