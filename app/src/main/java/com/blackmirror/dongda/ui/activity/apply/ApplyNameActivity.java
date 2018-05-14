package com.blackmirror.dongda.ui.activity.apply;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.blackmirror.dongda.utils.ToastUtils;

public class ApplyNameActivity extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_next;
    private TextInputEditText tet_user_name;
    private TextInputEditText tet_service_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_apply_name;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_next = findViewById(R.id.tv_next);
        tet_user_name = findViewById(R.id.tet_user_name);
        tet_service_name = findViewById(R.id.tet_service_name);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = tet_user_name.getEditableText().toString();
                String brand_name = tet_service_name.getEditableText().toString();
                if (TextUtils.isEmpty(user_name)){
                    ToastUtils.showShortToast("用户名不能为空!");
                    return;
                }
                Intent intent = new Intent(ApplyNameActivity.this, ApplyCityActivity.class);
                intent.putExtra("user_name",user_name);
                intent.putExtra("brand_name",brand_name);
                startActivity(intent);
            }
        });
    }
}
