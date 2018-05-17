package com.blackmirror.dongda.ui.activity.apply;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.ui.activity.UserInfoActivity;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.blackmirror.dongda.utils.DensityUtils;
import com.blackmirror.dongda.utils.DeviceUtils;

public class ApplySuccessActivity extends BaseActivity {

    private ConstraintLayout cl_mp_root;
    private ImageView iv_back;

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
        iv_back = findViewById(R.id.iv_back);
    }

    @Override
    protected void initData() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            cl_mp_root.setElevation(DensityUtils.dp2px(6));
        }
    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ApplySuccessActivity.this, UserInfoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void setStatusBarColor() {
        DeviceUtils.initSystemBarColor(this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, UserInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        super.onBackPressed();
    }
}
