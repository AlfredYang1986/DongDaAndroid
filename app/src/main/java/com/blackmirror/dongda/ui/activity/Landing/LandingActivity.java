package com.blackmirror.dongda.ui.activity.Landing;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.di.component.DaggerLandingComponent;
import com.blackmirror.dongda.domain.model.WeChatLoginBean;
import com.blackmirror.dongda.presenter.WeChatLoginPresenter;
import com.blackmirror.dongda.ui.WeChatLoginContract;
import com.blackmirror.dongda.ui.activity.HomeActivity.AYHomeActivity;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.blackmirror.dongda.utils.AppConstant;
import com.blackmirror.dongda.utils.DeviceUtils;
import com.blackmirror.dongda.utils.DongdaApplication;
import com.blackmirror.dongda.utils.LogUtils;
import com.blackmirror.dongda.utils.SnackbarUtils;
import com.blackmirror.dongda.utils.ToastUtils;

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

public class LandingActivity extends BaseActivity implements WeChatLoginContract.View{

    private TextView tv_phone_login;
    private TextView tv_wechat_login;
    private Disposable errorDb;
    private Disposable cancelDb;
    private WeChatLoginPresenter presenter;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_landing;
    }

    @Override
    protected void init() {
        DongdaApplication.addActivity(this);
    }

    @Override
    protected void initInject() {
        presenter = DaggerLandingComponent.builder()
                .activity(this)
                .view(this)
                .build()
                .getWeChatLoginPresenter();
    }

    @Override
    protected void initView() {
        tv_phone_login = findViewById(R.id.tv_phone_login);
        tv_wechat_login = findViewById(R.id.tv_wechat_login);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {

        tv_phone_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingActivity.this, PhoneInputActivity.class);
                startActivity(intent);
            }
        });

        tv_wechat_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weChatLogin();
            }
        });

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
        showProcessDialog(getString(R.string.logining_process), true);
        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.setPlatformActionListener(new MyPlatformActionListener());
        wechat.SSOSetting(false);
        authorize(wechat, 1);

    }

    class MyPlatformActionListener implements PlatformActionListener{

        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> map) {
            platform.setPlatformActionListener(null);
            String userId = platform.getDb().getUserId();//获取用户账号
            String userName = platform.getDb().getUserName();//获取用户名字
            String userIcon = platform.getDb().getUserIcon();//获取用户头像
            String userGender = platform.getDb().getUserGender(); //获取用户性别，m = 男, f = 女，如果微信没有设置性别,默认返回null
            String token = platform.getDb().getToken();


            final Map<String, String> m = new HashMap<>();

            m.put("provide_uid", userId);
            m.put("provide_token", token);
            m.put("provide_screen_name", userName);
            m.put("provide_name", "wechat");
            m.put("provide_screen_photo", userIcon);

            presenter.weChatLogin(userId,token,userName,"wechat",userIcon);

        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            platform.setPlatformActionListener(null);
            errorDb = Observable.just(throwable.getMessage())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            if (!isViewValid()) {
                                return;
                            }
                            closeProcessDialog();
                            if (!TextUtils.isEmpty(s))
                                ToastUtils.showShortToast(s);
                        }
                    });
        }

        @Override
        public void onCancel(Platform platform, int i) {
            platform.setPlatformActionListener(null);
            cancelDb = Observable.just("")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            if (!isViewValid()) {
                                return;
                            }
                            closeProcessDialog();
                            ToastUtils.showShortToast(getString(R.string.wechat_auth_cancel));
                        }
                    });
        }
    }


    //授权
    private void authorize(Platform plat, int type) {
        if (!plat.isClientValid()){
            ToastUtils.showShortToast("您还未安装微信!");
            return;
        }
        if (plat.isAuthValid()) { //如果授权就删除授权资料
            plat.removeAccount(true);
        }
        plat.showUser(null);//授权并获取用户信息
    }

    /**
     * 服务器成功回调
     * @param bean
     */
    @Override
    public void weChatLoginSuccess(WeChatLoginBean bean) {
        closeProcessDialog();

        Intent intent = new Intent(LandingActivity.this, AYHomeActivity.class);
        intent.putExtra("img_uuid",bean.screen_photo);
        startActivity(intent);
        DongdaApplication.finishAllActivity();
    }

    /**
     * 服务器失败回调
     * @param bean
     */
    @Override
    public void weChatLoginError(WeChatLoginBean bean) {
        closeProcessDialog();
        LogUtils.d("LandingActivity wechat failed ");
        if (bean.code==AppConstant.NET_WORK_UNAVAILABLE){
            SnackbarUtils.show(tv_phone_login,bean.message);
        }else {
            ToastUtils.showShortToast(bean.message+"("+bean.code+")");
        }
    }

    @Override
    protected void setStatusBarColor() {
        DeviceUtils.initSystemBarColor(this);
    }

    private void unSubscribe() {

        if (errorDb != null && !errorDb.isDisposed()) {
            errorDb.dispose();
            errorDb = null;
        }

        if (cancelDb != null && !cancelDb.isDisposed()) {
            cancelDb.dispose();
            cancelDb = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unSubscribe();
    }

}
