package com.blackmirror.dongda.ui.activity.enrol;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.ui.base.BaseActivity;

public class EnrolSuccessActivity extends BaseActivity {

    private ImageView iv_back;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_enrol_success;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jump2LocAllActivity();
            }
        });
    }

    private void jump2LocAllActivity() {
        Intent intent = new Intent(this, LocAllServiceActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LocAllServiceActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        super.onBackPressed();
    }
}
