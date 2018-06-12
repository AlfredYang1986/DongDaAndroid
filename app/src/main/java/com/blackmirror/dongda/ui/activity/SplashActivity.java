package com.blackmirror.dongda.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.di.component.DaggerSplashComponent;
import com.blackmirror.dongda.kdomain.model.BaseDataBean;
import com.blackmirror.dongda.kdomain.model.UpLoadWeChatIconDomainBean;
import com.blackmirror.dongda.kdomain.model.WeChatLoginBean;
import com.blackmirror.dongda.presenter.WeChatLoginPresenter;
import com.blackmirror.dongda.ui.WeChatLoginContract;
import com.blackmirror.dongda.ui.activity.HomeActivity.AYHomeActivity;
import com.blackmirror.dongda.ui.activity.Landing.LandingActivity;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.blackmirror.dongda.utils.AYPrefUtils;
import com.blackmirror.dongda.utils.AppConstant;
import com.blackmirror.dongda.utils.LogUtils;
import com.blackmirror.dongda.utils.PermissionUtils;
import com.blackmirror.dongda.utils.SnackbarUtils;
import com.blackmirror.dongda.utils.ToastUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity implements WeChatLoginContract.View {

    private Disposable disposable;
    private WeChatLoginPresenter presenter;
    private ImageView iv_icon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initInject() {
        presenter = DaggerSplashComponent.builder()
                .activity(this)
                .view(this)
                .build()
                .getWeChatLoginPresenter();
    }

    @Override
    protected void initView() {
        iv_icon = findViewById(R.id.iv_icon);
    }

    @Override
    protected void initData() {
        requestPermissions();
    }

    @Override
    protected void initListener() {

    }

    /**
     * 动态申请权限
     */
    private void requestPermissions() {

        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE};

        List<String> list = PermissionUtils.checkPermissionWithNoGranted(SplashActivity.this,
                permissions);

        if (list.size() != 0) {
            PermissionUtils.requestMulitPermissions(SplashActivity.this, list);
        } else {
            init2Landing();
        }
    }

    private void init2Landing() {
        Platform weChat = ShareSDK.getPlatform(Wechat.NAME);
        if (TextUtils.isEmpty(AYPrefUtils.getAuthToken())) {
            disposable = Observable.timer(1500, TimeUnit.MILLISECONDS, Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            unSubscribe();
                            //                        startActivityForResult(new Intent(SplashActivity.this, LandingActivity.class));
                            startActivity(new Intent(SplashActivity.this, LandingActivity.class));
                            finish();
                        }
                    });
        } else {
            if (weChat.isAuthValid()) {
                showProcessDialog();
                String userId = weChat.getDb().getUserId();//获取用户账号
                String userName = weChat.getDb().getUserName();//获取用户名字
                String userIcon = weChat.getDb().getUserIcon();//获取用户头像
                String userGender = weChat.getDb().getUserGender(); //获取用户性别，m = 男, f = 女，如果微信没有设置性别,默认返回null
                String token = weChat.getDb().getToken();

                presenter.weChatLogin(userId, token, userName, "wechat", userIcon);
            }
        }

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
                startActivity(new Intent(SplashActivity.this, LandingActivity.class));
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unSubscribe();
    }

    private void unSubscribe() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
        }
    }


    @Override
    public void weChatLoginSuccess(WeChatLoginBean bean) {
        closeProcessDialog();

        Intent intent = new Intent(SplashActivity.this, AYHomeActivity.class);
        intent.putExtra("img_uuid", bean.getScreen_photo());
        startActivity(intent);
        finish();
    }

    @Override
    public void onUpLoadWeChatIconSuccess(UpLoadWeChatIconDomainBean bean) {

    }

    @Override
    public void onError(BaseDataBean bean) {
        closeProcessDialog();
        if (bean.getCode() == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(iv_icon, bean.getMessage());
        } else {
            ToastUtils.showShortToast(bean.getMessage() + "(" + bean.getCode() + ")");
        }
    }
}
