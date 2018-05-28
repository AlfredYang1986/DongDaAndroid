package com.blackmirror.dongda.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.base.AYApplication;
import com.blackmirror.dongda.di.component.DaggerUserAboutMeComponent;
import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.domain.model.UserInfoDomainBean;
import com.blackmirror.dongda.presenter.UserInfoPresenter;
import com.blackmirror.dongda.ui.activity.Landing.LandingActivity;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.blackmirror.dongda.utils.AYPrefUtils;
import com.blackmirror.dongda.utils.AppConstant;
import com.blackmirror.dongda.utils.DeviceUtils;
import com.blackmirror.dongda.utils.LogUtils;
import com.blackmirror.dongda.utils.OSSUtils;
import com.blackmirror.dongda.utils.SnackbarUtils;
import com.blackmirror.dongda.utils.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mob.MobSDK;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

public class UserAboutMeActivity extends BaseActivity implements View.OnClickListener, UserInfoContract.View {

    private ImageView iv_service_back;
    private TextView tv_user_logout;
    private SimpleDraweeView sv_user_about_photo;
    private ImageView iv_about_edit;
    private TextView tv_user_about_name;
    private TextView tv_user_about_dec;
    private boolean needsRefresh;
    private String img_url;
    private AlertDialog dialog;
    private UserInfoPresenter presenter;
    private UserInfoDomainBean bean;

    @Override
    protected void init() {
        AYApplication.addActivity(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_user_about_me;
    }

    @Override
    protected void initInject() {
        presenter = DaggerUserAboutMeComponent.builder()
                .activity(this)
                .build()
                .getUserInfoPresenter();
        presenter.setUserInfoView(this);
    }

    @Override
    protected void initView() {
        iv_service_back = findViewById(R.id.iv_service_back);
        tv_user_logout = findViewById(R.id.tv_user_logout);
        sv_user_about_photo = findViewById(R.id.sv_user_about_photo);
        iv_about_edit = findViewById(R.id.iv_about_edit);
        tv_user_about_name = findViewById(R.id.tv_user_about_name);
        tv_user_about_dec = findViewById(R.id.tv_user_about_dec);

    }

    @Override
    protected void initData() {
        getUserInfo();
    }

    @Override
    protected void initListener() {
        iv_service_back.setOnClickListener(this);
        tv_user_logout.setOnClickListener(this);
        iv_about_edit.setOnClickListener(this);
    }


    private void getUserInfo() {
        showProcessDialog();
        presenter.queryUserInfo();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_service_back:
                setResult(needsRefresh ? RESULT_OK : RESULT_CANCELED, getIntent().putExtra("img_url", img_url));
                AYApplication.finishActivity(this);
                break;
            case R.id.tv_user_logout:
                showLogOutDialog();
                break;
            case R.id.iv_about_edit:
                EditUserInfoActivity.startActivityForResult(UserAboutMeActivity.this, bean.screen_photo, bean.screen_name, bean.description, AppConstant.EDIT_USER_REQUEST_CODE);
                break;
        }
    }

    @Override
    public void onQueryUserInfoSuccess(UserInfoDomainBean dbean) {
        bean = dbean;
        closeProcessDialog();
        String url = OSSUtils.getSignedUrl(bean.screen_photo);
        sv_user_about_photo.setImageURI(url);
        tv_user_about_name.setText(bean.screen_name);
        if (TextUtils.isEmpty(bean.description)) {
            tv_user_about_dec.setText(R.string.default_about_me);
        } else {
            tv_user_about_dec.setText(bean.description);
        }
    }

    @Override
    public void onGetDataError(BaseDataBean bean) {
        closeProcessDialog();
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(iv_about_edit, bean.message);
        } else {
            ToastUtils.showShortToast(bean.message + "(" + bean.code + ")");
        }
    }

    private void showLogOutDialog() {
        dialog = new AlertDialog.Builder(UserAboutMeActivity.this)
                .setCancelable(false)
                .setTitle(R.string.dlg_tips)
                .setMessage(R.string.confirm_logout)
                .setPositiveButton(getString(R.string.dlg_confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        closeDialog();
                        Platform weChat = ShareSDK.getPlatform(Wechat.NAME);
                        weChat.getDb().removeAccount();
                        weChat.removeAccount(true);
                        ShareSDK.deleteCache();
                        MobSDK.clearUser();
                        AYPrefUtils.setUserId("");
                        AYPrefUtils.setAuthToken("");
                        Intent intent = new Intent(UserAboutMeActivity.this, LandingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            needsRefresh = true;
            getUserInfo();
            img_url = data.getStringExtra("img_url");
            LogUtils.d("UserAboutMeActivity img_url "+img_url);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(needsRefresh ? RESULT_OK : RESULT_CANCELED, getIntent().putExtra("img_url", img_url));
        AYApplication.removeActivity(this);
        super.onBackPressed();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDialog();
    }

    private void closeDialog(){
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }


    @Override
    protected void setStatusBarColor() {
        DeviceUtils.initSystemBarColor(this);
    }

}
