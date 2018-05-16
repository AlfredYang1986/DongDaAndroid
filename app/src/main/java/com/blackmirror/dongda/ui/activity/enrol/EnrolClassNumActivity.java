package com.blackmirror.dongda.ui.activity.enrol;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.blackmirror.dongda.utils.ToastUtils;

public class EnrolClassNumActivity extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_next;
    private TextInputEditText tet_max_num;
    private boolean can_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_enrol_class_num;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_next = findViewById(R.id.tv_next);
        tet_max_num = findViewById(R.id.tet_max_num);
    }

    @Override
    protected void initData() {
        SpannableString s1 = new SpannableString("请填写");//定义hint的值
        AbsoluteSizeSpan as1 = new AbsoluteSizeSpan(15,true);//设置字体大小 true表示单位是sp
        s1.setSpan(as1, 0, s1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tet_max_num.setHint(new SpannedString(s1));
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
                String max_num = tet_max_num.getText().toString();
                if (TextUtils.isEmpty(max_num)){
                    ToastUtils.showShortToast("最大满班人数不能为空!");
                    return;
                }
                if (Integer.parseInt(max_num)<10){
                    ToastUtils.showShortToast("最大满班人数不能小于最少开班人数!");
                }
                Intent intent = new Intent(EnrolClassNumActivity.this, EnrolPayTypeActivity.class);
                intent.putExtra("service_id",getIntent().getStringExtra("service_id"));
                intent.putExtra("min_age",getIntent().getStringExtra("min_age"));
                intent.putExtra("max_age",getIntent().getStringExtra("max_age"));
                intent.putExtra("min_num",10+"");
                intent.putExtra("max_num",max_num);
                intent.putExtra("locations",getIntent().getStringExtra("locations"));
                intent.putExtra("service_leaf",getIntent().getStringExtra("service_leaf"));
                intent.putExtra("service_image",getIntent().getStringExtra("service_image"));
                intent.putExtra("address",getIntent().getStringExtra("address"));
                startActivity(intent);
            }
        });

        tet_max_num.addTextChangedListener(new TextWatcher() {
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
