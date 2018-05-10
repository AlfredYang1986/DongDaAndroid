package com.blackmirror.dongda.ui.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.utils.DeviceUtils;

/**
 * Created by Ruge on 2018-05-04 下午6:41
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected ProgressDialog pb;
    private boolean isViewValid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        isViewValid = true;
        setStatusBarColor();
        initInject();
        initView();
        initData();
        initListener();
    }

    protected void setStatusBarColor() {
        DeviceUtils.setStatusBarColor(this);
    }

    protected abstract int getLayoutResId();
    protected abstract void initInject();
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void initListener();

    @Override
    protected void onDestroy() {
        closeProcessDialog();
        pb = null;
        isViewValid = false;
        super.onDestroy();
    }

    protected void showProcessDialog() {
        showProcessDialog(getString(R.string.dlg_tips), getString(R.string.dlg_processing));
    }

    protected void showProcessDialog(String message) {
        showProcessDialog(getString(R.string.dlg_tips), message);
    }

    protected void showProcessDialog(String message, boolean cancelable) {
        showProcessDialog(getString(R.string.dlg_tips), message, cancelable);
    }

    protected void showProcessDialog(String title, String message) {
        showProcessDialog(title, message, false);
    }

    protected void showProcessDialog(String title, String message,boolean cancelable) {
        if (!isViewValid()) {
            return;
        }
        if (pb == null) {
            pb = new ProgressDialog(this);
        }
        pb.setCanceledOnTouchOutside(cancelable);//设置在点击Dialog外是否取消Dialog进度条
        pb.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
        pb.setCancelable(true);// 设置是否可以通过点击Back键取消
        //        pb.setCanceledOnTouchOutside(false);//
        pb.setTitle(title);
        pb.setMessage(message);
        pb.onBackPressed();
        pb.show();
    }

    protected void closeProcessDialog() {
        if (pb != null && pb.isShowing()) {
            pb.dismiss();
            pb = null;
        }
    }

    protected boolean isViewValid() {
        return isViewValid;
    }

}
