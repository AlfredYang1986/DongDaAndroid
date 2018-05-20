package com.blackmirror.dongda.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.base.AYApplication;
import com.blackmirror.dongda.ui.activity.Landing.LandingActivity;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.blackmirror.dongda.utils.AYPrefUtils;
import com.blackmirror.dongda.utils.FileUtils;
import com.blackmirror.dongda.utils.LogUtils;
import com.blackmirror.dongda.utils.ToastUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private ImageView iv_back;
    private TextView tv_change_order_mode;
    private TextView tv_clear_cache;
    private TextView tv_cache_size;
    private TextView tv_logout;
    private int flag;
    private Disposable disposable;
    private AlertDialog dialog;
    private AnimationDrawable animationDrawable;
    private ImageView iv_anim;
    private ConstraintLayout cl_content;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_change_order_mode = findViewById(R.id.tv_change_order_mode);
        tv_clear_cache = findViewById(R.id.tv_clear_cache);
        tv_cache_size = findViewById(R.id.tv_cache_size);
        tv_logout = findViewById(R.id.tv_logout);
        iv_anim = findViewById(R.id.iv_anim);
        cl_content = findViewById(R.id.cl_content);
    }

    @Override
    protected void initData() {
        String path = AYApplication.getAppContext().getExternalCacheDir().getAbsolutePath();
        double size = FileUtils.getFileOrFilesSize(path);
        flag = getIntent().getIntExtra("flag", 0);
        if (flag == 0 || flag == 3){//不是服务提供者 或者服务者已切换到预定模式
            tv_change_order_mode.setVisibility(View.GONE);
        }else if (flag == 1){
            tv_change_order_mode.setVisibility(View.VISIBLE);
        }
        tv_cache_size.setText(size+"MB");
    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
        tv_change_order_mode.setOnClickListener(this);
        tv_clear_cache.setOnClickListener(this);
        tv_logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_change_order_mode:
                orderMode();
                break;
            case R.id.tv_clear_cache:
                clearAllCache();
                break;
            case R.id.tv_logout:
                showLogOutDialog();
                break;
        }
    }

    private void orderMode() {

        // 通过逐帧动画的资源文件获得AnimationDrawable示例
        animationDrawable = (AnimationDrawable) getResources().getDrawable(
                R.drawable.frame_anim);
        animationDrawable.start();
        // 通过逐帧动画的资源文件获得AnimationDrawable示例
        animationDrawable = (AnimationDrawable) getResources().getDrawable(
                R.drawable.frame_anim);
        iv_anim.setBackground(animationDrawable);
        animationDrawable.start();
        iv_anim.setVisibility(View.VISIBLE);
        cl_content.setVisibility(View.GONE);
        Observable.timer(1000,TimeUnit.MILLISECONDS,Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (animationDrawable != null && animationDrawable.isRunning()){
                            animationDrawable.stop();
                            iv_anim.clearAnimation();
                            iv_anim.setVisibility(View.GONE);
                            AYPrefUtils.setSettingFlag("2");
                            setResult(RESULT_OK);
                            finish();
                        }
                    }
                });

    }

    private void clearAllCache() {
        showProcessDialog();
        disposable = Observable.timer(500, TimeUnit.MILLISECONDS, Schedulers.io())
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        FileUtils.deleteDir(AYApplication.getAppContext().getExternalCacheDir());
                        return aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long o) throws Exception {
                        if (!isViewValid()){
                            return;
                        }
                        closeProcessDialog();
                        ToastUtils.showShortToast("清除成功!");
                        String path = AYApplication.getAppContext().getExternalCacheDir().getPath();
                        double size = FileUtils.getFileOrFilesSize(path);
                        tv_cache_size.setText(size+"MB");

                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable!=null && !disposable.isDisposed()){
            disposable.dispose();
            disposable=null;
        }
        closeDialog();
    }

    private void showLogOutDialog() {
        dialog = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(R.string.dlg_tips)
                .setMessage(R.string.confirm_logout)
                .setPositiveButton(getString(R.string.dlg_confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        closeDialog();
                        Intent intent = new Intent(SettingActivity.this, LandingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        LogUtils.d("xcx hahaha" );
                        AYApplication.addActivity(SettingActivity.this);
                        AYApplication.finishAllActivity();
                    }
                })
                .setNegativeButton(getString(R.string.dlg_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        closeDialog();
                    }
                }).create();
        dialog.show();
    }

    private void closeDialog(){
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
