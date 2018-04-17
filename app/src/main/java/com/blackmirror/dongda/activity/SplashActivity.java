package com.blackmirror.dongda.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.blackmirror.dongda.Landing.LandingActivity;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.AppConstant;
import com.blackmirror.dongda.Tools.LogUtils;
import com.blackmirror.dongda.Tools.PermissionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_launch);

//        init();
        requestPermissions();
    }

    /**
     * 动态申请权限
     */
    private void requestPermissions() {

        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE};

        List<String> list = PermissionUtils.checkPermissionAndNeedGranted(SplashActivity.this,
                permissions);

        if (list.size()!=0){
            PermissionUtils.requestMulitPermissions(SplashActivity.this, list);
        }else {
            init();
        }


    }

    private void init() {
        disposable = Observable.timer(1500, TimeUnit.MILLISECONDS, Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        unSubscribe();
//                        startActivity(new Intent(SplashActivity.this, LandingActivity.class));
                        startActivity(new Intent(SplashActivity.this, LandingActivity.class));
                        finish();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unSubscribe();
    }

    private void unSubscribe() {
        if (disposable != null && !disposable.isDisposed()){
            disposable.dispose();
            disposable = null;
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


}
