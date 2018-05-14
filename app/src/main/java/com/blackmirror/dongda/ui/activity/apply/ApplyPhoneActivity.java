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

public class ApplyPhoneActivity extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_next;
    private TextInputEditText tet_phone_no;
    private TextView tv_name_dec;
    private String user_name;
    private String city_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user_name = getIntent().getStringExtra("user_name");
        city_name = getIntent().getStringExtra("city_name");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_apply_phone;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_next = findViewById(R.id.tv_next);
        tet_phone_no = findViewById(R.id.tet_phone_no);
        tv_name_dec = findViewById(R.id.tv_name_dec);
    }

    @Override
    protected void initData() {
        tv_name_dec.setText("非常好，来自"+city_name+"的"+user_name+"\\n我们怎样可以联系到你？");
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
                String phone_no = tet_phone_no.getEditableText().toString().trim();
                if (TextUtils.isEmpty(phone_no)){
                    ToastUtils.showShortToast("手机号不能为空!");
                    return;
                }
                Intent intent = new Intent(ApplyPhoneActivity.this, ApplyServiceActivity.class);
                intent.putExtra("user_name",user_name);
                intent.putExtra("brand_name",getIntent().getStringExtra("brand_name"));
                intent.putExtra("city_name",city_name);
                intent.putExtra("phone_no",phone_no);
                startActivity(intent);
            }
        });
    }
}
