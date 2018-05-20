package com.blackmirror.dongda.ui.activity.newservice;

import android.graphics.Color;
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
import com.blackmirror.dongda.utils.StringUtils;
import com.blackmirror.dongda.utils.ToastUtils;

public class ServiceFixedActivity extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_head_title;
    private TextView tv_save;
    private TextInputEditText tet_all_month_price;
    private TextInputEditText tet_mid_month_price;
    private boolean can_save;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_service_fixed;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_head_title = findViewById(R.id.tv_head_title);
        tv_save = findViewById(R.id.tv_save);
        tet_all_month_price = findViewById(R.id.tet_all_month_price);
        tet_mid_month_price = findViewById(R.id.tet_mid_month_price);
    }

    @Override
    protected void initData() {
        tv_head_title.setText("固定付费设置");

        SpannableString s1 = new SpannableString("请填写");//定义hint的值
        AbsoluteSizeSpan as1 = new AbsoluteSizeSpan(15,true);//设置字体大小 true表示单位是sp
        s1.setSpan(as1, 0, s1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tet_all_month_price.setHint(new SpannedString(s1));

        SpannableString s2 = new SpannableString("请填写");//定义hint的值
        AbsoluteSizeSpan as2 = new AbsoluteSizeSpan(15,true);//设置字体大小 true表示单位是sp
        s2.setSpan(as2, 0, s2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tet_mid_month_price.setHint(new SpannedString(s2));
    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String all_month_price = tet_all_month_price.getText().toString();
                String mid_month_price = tet_mid_month_price.getText().toString();

                if (TextUtils.isEmpty(all_month_price)){
                    ToastUtils.showShortToast("请输入全日托单月价格!");
                    return;
                }
                if (TextUtils.isEmpty(mid_month_price)){
                    ToastUtils.showShortToast("请输入半日托单月价格!");
                    return;
                }

                long all = (long) (StringUtils.getDoubleValue(all_month_price)*100);
                long mid = (long) (StringUtils.getDoubleValue(mid_month_price)*100);

                getIntent().putExtra("all_month_price",all);
                getIntent().putExtra("mid_month_price",mid);
                setResult(RESULT_OK,getIntent());
                finish();
            }
        });

        tet_all_month_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length()!=0 && !can_save){
                    can_save = true;
                    tv_save.setTextColor(Color.parseColor("#FF59D5C7"));
                }
                if (s.toString().length()==0 && can_save){
                    can_save = false;
                    tv_save.setTextColor(Color.parseColor("#FFD9D9D9"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String temp = s.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 2) {
                    s.delete(posDot + 3, posDot + 4);
                }

            }
        });

        tet_mid_month_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length()!=0 && !can_save){
                    can_save = true;
                    tv_save.setTextColor(Color.parseColor("#FF59D5C7"));
                }
                if (s.toString().length()==0 && can_save){
                    can_save = false;
                    tv_save.setTextColor(Color.parseColor("#FFD9D9D9"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String temp = s.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 2) {
                    s.delete(posDot + 3, posDot + 4);
                }

            }
        });
    }
}
