package com.blackmirror.dongda.ui.activity.apply;

import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.blackmirror.dongda.utils.DensityUtils;
import com.blackmirror.dongda.utils.DeviceUtils;

public class ApplySuccessActivity extends BaseActivity {

    private ConstraintLayout cl_mp_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_apply_success;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        cl_mp_root = findViewById(R.id.cl_mp_root);
    }

    @Override
    protected void initData() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            cl_mp_root.setElevation(DensityUtils.dp2px(6));
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {
        DeviceUtils.initSystemBarColor(this);
    }
}
