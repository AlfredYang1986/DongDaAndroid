package com.blackmirror.dongda.ui.activity.apply;

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

public class ApplyNameActivity extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_next;
    private TextInputEditText tet_user_name;
    private TextInputEditText tet_service_name;
    private boolean can_next;

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
        SpannableString s1 = new SpannableString(getString(R.string.input_user_name_hint));//定义hint的值
        AbsoluteSizeSpan as1 = new AbsoluteSizeSpan(15,true);//设置字体大小 true表示单位是sp
        s1.setSpan(as1, 0, s1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tet_user_name.setHint(new SpannedString(s1));

        SpannableString s2 = new SpannableString(getString(R.string.input_user_name_hint));//定义hint的值
        AbsoluteSizeSpan as2 = new AbsoluteSizeSpan(15,true);//设置字体大小 true表示单位是sp
        s2.setSpan(as2, 0, s2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tet_service_name.setHint(new SpannedString(s2));
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

        tet_user_name.addTextChangedListener(new TextWatcher() {
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
