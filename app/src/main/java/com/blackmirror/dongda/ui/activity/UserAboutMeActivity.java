package com.blackmirror.dongda.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blackmirror.dongda.ui.activity.Landing.LandingActivity;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.utils.AYApplication;
import com.blackmirror.dongda.utils.AYPrefUtils;
import com.blackmirror.dongda.utils.AppConstant;
import com.blackmirror.dongda.utils.OSSUtils;
import com.blackmirror.dongda.utils.OtherUtils;
import com.blackmirror.dongda.utils.SnackbarUtils;
import com.blackmirror.dongda.utils.ToastUtils;
import com.blackmirror.dongda.model.serverbean.ErrorInfoServerBean;
import com.blackmirror.dongda.model.serverbean.QueryUserInfoServerBean;
import com.blackmirror.dongda.model.uibean.ErrorInfoUiBean;
import com.blackmirror.dongda.model.uibean.QueryUserInfoUiBean;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONException;
import org.json.JSONObject;

public class UserAboutMeActivity extends AYActivity implements View.OnClickListener {

    private ImageView iv_service_back;
    private TextView tv_user_logout;
    private SimpleDraweeView sv_user_about_photo;
    private ImageView iv_about_edit;
    private TextView tv_user_about_name;
    private TextView tv_user_about_dec;
    private QueryUserInfoUiBean uiBean;
    private boolean needsRefresh;
    private String img_url;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_about_me);
        AYApplication.addActivity(this);
        OtherUtils.initSystemBarColor(this);
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
        getUserInfo();
    }

    private void initListener() {
        iv_service_back.setOnClickListener(this);
        tv_user_logout.setOnClickListener(this);
        iv_about_edit.setOnClickListener(this);
    }

    private void getUserInfo() {
        showProcessDialog();
        try {
            String json = "{\"token\":\"" + AYPrefUtils.getAuthToken() + "\",\"condition\":{\"user_id\":\"" + AYPrefUtils.getUserId() + "\"}}";
            JSONObject object = new JSONObject(json);
            facades.get("UserFacade").execute("AYQueryUserInfoCmd", object);
        } catch (JSONException e) {
            closeProcessDialog();
        }
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
                EditUserInfoActivity.startActivityForResult(UserAboutMeActivity.this, uiBean.screen_photo, uiBean.screen_name, uiBean.description, AppConstant.EDIT_USER_REQUEST_CODE);
                break;
        }
    }

    private void showLogOutDialog() {
        dialog = new AlertDialog.Builder(UserAboutMeActivity.this)
                .setCancelable(false)
                .setTitle("提示")
                .setMessage("确定退出登录吗?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        closeDialog();
                        Intent intent = new Intent(UserAboutMeActivity.this, LandingActivity.class);
                        startActivity(intent);
                        AYApplication.finishAllActivity();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        closeDialog();
                    }
                }).create();
        dialog.show();
    }

    public void AYQueryUserInfoCmdSuccess(JSONObject args) {
        closeProcessDialog();
        QueryUserInfoServerBean serverBean = JSON.parseObject(args.toString(), QueryUserInfoServerBean.class);
        uiBean = new QueryUserInfoUiBean(serverBean);
        if (uiBean.isSuccess) {
            String url = OSSUtils.getSignedUrl(uiBean.screen_photo);
            sv_user_about_photo.setImageURI(url);
            tv_user_about_name.setText(uiBean.screen_name);
            if (TextUtils.isEmpty(uiBean.description)) {
                tv_user_about_dec.setText("一句话很短，高调的夸一夸自己");
            } else {
                tv_user_about_dec.setText(uiBean.description);
            }
        } else {
            ToastUtils.showShortToast(uiBean.message + "(" + uiBean.code + ")");
        }
    }


    public void AYQueryUserInfoCmdFailed(JSONObject args) {
        closeProcessDialog();
        ErrorInfoServerBean serverBean = JSON.parseObject(args.toString(), ErrorInfoServerBean.class);
        ErrorInfoUiBean uiBean = new ErrorInfoUiBean(serverBean);
        if (uiBean.code == 10010) {
            SnackbarUtils.show(iv_about_edit, uiBean.message);
        } else {
            ToastUtils.showShortToast(uiBean.message + "(" + uiBean.code + ")");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            needsRefresh = true;
            getUserInfo();
            img_url = data.getStringExtra("img_url");
        }
    }

    @Override
    protected void bindingFragments() {

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

    }
}
