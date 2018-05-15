package com.blackmirror.dongda.ui.activity.apply;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.blackmirror.dongda.utils.ToastUtils;

public class ApplyCityActivity extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_next;
    private TextInputEditText tet_city_name;
    private TextView tv_name_dec;
    private String user_name;
    private String brand_name;
    private boolean can_next;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_apply_city;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_next = findViewById(R.id.tv_next);
        tet_city_name = findViewById(R.id.tet_city_name);
        tv_name_dec = findViewById(R.id.tv_name_dec);
    }

    @Override
    protected void initData() {
        user_name = getIntent().getStringExtra("user_name");
        brand_name = getIntent().getStringExtra("brand_name");
        tv_name_dec.setText("你好，"+user_name+"\n你的服务所在的城市？");
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
                String city_name = tet_city_name.getEditableText().toString();
                if (TextUtils.isEmpty(city_name)){
                    ToastUtils.showShortToast("请输入城市!");
                    return;
                }
                Intent intent = new Intent(ApplyCityActivity.this, ApplyPhoneActivity.class);
                intent.putExtra("user_name",user_name);
                intent.putExtra("brand_name",brand_name);
                intent.putExtra("city_name",city_name);
                startActivity(intent);
            }
        });

        tet_city_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length()!=0 && !can_next){
                    can_next = true;
                    tv_next.setTextColor(Color.parseColor("#FF59D5C7"));
                }
                if (s.toString().length()==0 && can_next){
                    can_next = false;
                    tv_next.setTextColor(Color.parseColor("#FFD9D9D9"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
