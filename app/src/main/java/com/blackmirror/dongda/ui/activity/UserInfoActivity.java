package com.blackmirror.dongda.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.base.AYApplication;
import com.blackmirror.dongda.di.component.DaggerUserInfoComponent;
import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.domain.model.UserInfoDomainBean;
import com.blackmirror.dongda.presenter.UserInfoPresenter;
import com.blackmirror.dongda.ui.activity.apply.ApplyActivity;
import com.blackmirror.dongda.ui.activity.enrol.ChooseEnrolLocActivity;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.blackmirror.dongda.utils.AYPrefUtils;
import com.blackmirror.dongda.utils.AppConstant;
import com.blackmirror.dongda.utils.DeviceUtils;
import com.blackmirror.dongda.utils.LogUtils;
import com.blackmirror.dongda.utils.OSSUtils;
import com.blackmirror.dongda.utils.SnackbarUtils;
import com.blackmirror.dongda.utils.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;

public class UserInfoActivity extends BaseActivity implements View.OnClickListener, UserInfoContract.View {

    //基础不变控件
    private ImageView iv_home_head_back;
    private SimpleDraweeView sv_user_photo;
    private TextView tv_user_name;

    //申请成为服务者
    private ConstraintLayout cl_apply_service;
    private TextView tv_join_service;
    private TextView tv_apply_setting;

    //发布招生
    private ConstraintLayout cl_enrol_class;
    private TextView tv_enrol_class;
    private TextView tv_my_brand;
    private TextView tv_enrol_setting;

    //切换为服务模式
    private ConstraintLayout cl_change_service;
    private TextView tv_change_to_service;
    private TextView tv_change_service_setting;

    private UserInfoPresenter presenter;
    private boolean needsRefresh;
    private String img_url;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initInject() {
        presenter = DaggerUserInfoComponent.builder()
                .activity(this)
                .view(this)
                .build()
                .getUserInfoPresenter();
    }

    @Override
    protected void initView() {
        //基础不变控件
        iv_home_head_back = findViewById(R.id.iv_home_head_back);
        sv_user_photo = findViewById(R.id.sv_user_photo);
        tv_user_name = findViewById(R.id.tv_user_name);

        //申请成为服务者
        cl_apply_service = findViewById(R.id.cl_apply_service);
        tv_join_service = findViewById(R.id.tv_join_service);
        tv_apply_setting = findViewById(R.id.tv_apply_setting);

        //发布招生
        cl_enrol_class = findViewById(R.id.cl_enrol_class);
        tv_enrol_class = findViewById(R.id.tv_enrol_class);
        tv_my_brand = findViewById(R.id.tv_my_brand);
        tv_enrol_setting = findViewById(R.id.tv_enrol_setting);

        //切换为服务模式
        cl_change_service = findViewById(R.id.cl_change_service);
        tv_change_to_service = findViewById(R.id.tv_change_to_service);
        tv_change_service_setting = findViewById(R.id.tv_change_service_setting);
    }

    @Override
    protected void initData() {
        showProcessDialog();
        presenter.queryUserInfo();
    }

    @Override
    protected void initListener() {
        iv_home_head_back.setOnClickListener(this);
        sv_user_photo.setOnClickListener(this);

        tv_join_service.setOnClickListener(this);
        tv_apply_setting.setOnClickListener(this);

        tv_enrol_class.setOnClickListener(this);
        tv_my_brand.setOnClickListener(this);
        tv_enrol_setting.setOnClickListener(this);

        tv_change_to_service.setOnClickListener(this);
        tv_change_service_setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(UserInfoActivity.this, SettingActivity.class);
        switch (v.getId()) {
            case R.id.iv_home_head_back:
                setResult(needsRefresh ? RESULT_OK : RESULT_CANCELED, getIntent().putExtra("img_url", img_url));
                AYApplication.removeActivity(this);
                finish();
                break;
            case R.id.sv_user_photo:
                AYApplication.addActivity(this);
                startActivityForResult(new Intent(this,UserAboutMeActivity.class),AppConstant.EDIT_USER_INFO_CODE);
                break;
            //申请成为服务者
            case R.id.tv_join_service:
                startActivity(new Intent(UserInfoActivity.this, ApplyActivity.class));
                break;
            case R.id.tv_apply_setting:
                i.putExtra("flag", 0);//不是服务者
                startActivity(i);
                AYApplication.addActivity(this);
                break;
            //发布招生
            case R.id.tv_enrol_class:
                Intent intent = new Intent(this, ChooseEnrolLocActivity.class);
                intent.putExtra("brand_id", "");
                startActivity(intent);
                break;
            case R.id.tv_my_brand:
                startActivity(new Intent(this, MyBrandActivity.class));
                break;
            case R.id.tv_enrol_setting:
                i.putExtra("flag", 1);//服务者 需要显示切换预定模式
                startActivityForResult(i,AppConstant.SHOW_ORDER_CODE);
                AYApplication.addActivity(this);
                break;
            //切换为服务模式
            case R.id.tv_change_to_service:
                AYPrefUtils.setSettingFlag("3");//3招生 2 预定模式
                cl_apply_service.setVisibility(View.GONE);
                cl_enrol_class.setVisibility(View.VISIBLE);
                cl_change_service.setVisibility(View.GONE);
                break;
            case R.id.tv_change_service_setting:
                i.putExtra("flag", 3);//不是服务者
                startActivity(i);
                AYApplication.addActivity(this);
                break;
        }
    }

    @Override
    public void onQueryUserInfoSuccess(UserInfoDomainBean bean) {
        closeProcessDialog();
        tv_user_name.setText(bean.screen_name);
        sv_user_photo.setImageURI(OSSUtils.getSignedUrl(bean.screen_photo));
        if (bean.is_service_provider == 0) {
            cl_apply_service.setVisibility(View.VISIBLE);
            cl_enrol_class.setVisibility(View.GONE);
            cl_change_service.setVisibility(View.GONE);
        }
        if (bean.is_service_provider == 1) {
            if (AYPrefUtils.getSettingFlag().equals("3")){
                cl_apply_service.setVisibility(View.GONE);
                cl_enrol_class.setVisibility(View.VISIBLE);
                cl_change_service.setVisibility(View.GONE);
            }else if (AYPrefUtils.getSettingFlag().equals("2")){
                cl_apply_service.setVisibility(View.GONE);
                cl_enrol_class.setVisibility(View.GONE);
                cl_change_service.setVisibility(View.VISIBLE);
            }
        }
        /*if (AYPrefUtils.getSettingFlag().equals("3")){
            cl_apply_service.setVisibility(View.GONE);
            cl_enrol_class.setVisibility(View.VISIBLE);
            cl_change_service.setVisibility(View.GONE);
        }else if (AYPrefUtils.getSettingFlag().equals("2")){
            cl_apply_service.setVisibility(View.GONE);
            cl_enrol_class.setVisibility(View.GONE);
            cl_change_service.setVisibility(View.VISIBLE);
        }*/
    }

    @Override
    public void onGetDataError(BaseDataBean bean) {
        closeProcessDialog();
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(sv_user_photo, bean.message);
        } else {
            ToastUtils.showShortToast(bean.message + "(" + bean.code + ")");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        handleResult(requestCode,resultCode,data);
    }

    private void handleResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case AppConstant.SHOW_ORDER_CODE:
                AYApplication.removeActivity(this);
                finish();
                break;
            case AppConstant.EDIT_USER_INFO_CODE:
                if (resultCode == RESULT_OK) {
                    needsRefresh = true;

                    img_url=data.getStringExtra("img_url");
                    LogUtils.d("img_url userinfo "+data.getStringExtra("img_url"));
                    sv_user_photo.setImageURI(OSSUtils.getSignedUrl(img_url));
                }else {
                    needsRefresh = false;
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        setResult(needsRefresh ? RESULT_OK : RESULT_CANCELED, getIntent().putExtra("img_url", img_url));
        AYApplication.removeActivity(this);
        super.onBackPressed();
    }

    @Override
    protected void setStatusBarColor() {
        DeviceUtils.setStatusBarColor(this,Color.parseColor("#FFF7F9FA"));
    }
}
