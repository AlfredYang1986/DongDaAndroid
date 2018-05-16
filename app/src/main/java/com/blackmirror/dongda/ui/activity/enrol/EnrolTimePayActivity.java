package com.blackmirror.dongda.ui.activity.enrol;

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
import com.blackmirror.dongda.utils.ToastUtils;

public class EnrolTimePayActivity extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_head_title;
    private TextView tv_save;
    private TextInputEditText tet_time_price;
    private TextInputEditText tet_min_order;
    private TextInputEditText tet_class_time_minute;
    private boolean can_save;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_enrol_time_pay;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_head_title = findViewById(R.id.tv_head_title);
        tv_save = findViewById(R.id.tv_save);
        tet_time_price = findViewById(R.id.tet_time_price);
        tet_min_order = findViewById(R.id.tet_min_order);
        tet_class_time_minute = findViewById(R.id.tet_class_time_minute);
    }

    @Override
    protected void initData() {
        tv_head_title.setText("按次付费设置");
        SpannableString s1 = new SpannableString("请填写");//定义hint的值
        AbsoluteSizeSpan as1 = new AbsoluteSizeSpan(15,true);//设置字体大小 true表示单位是sp
        s1.setSpan(as1, 0, s1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tet_time_price.setHint(new SpannedString(s1));

        SpannableString s2 = new SpannableString("请填写");//定义hint的值
        AbsoluteSizeSpan as2 = new AbsoluteSizeSpan(15,true);//设置字体大小 true表示单位是sp
        s2.setSpan(as2, 0, s2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tet_min_order.setHint(new SpannedString(s2));

        SpannableString s3 = new SpannableString("请填写");//定义hint的值
        AbsoluteSizeSpan as3 = new AbsoluteSizeSpan(15,true);//设置字体大小 true表示单位是sp
        s3.setSpan(as3, 0, s3.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tet_class_time_minute.setHint(new SpannedString(s3));


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
                String price = tet_time_price.getText().toString();
                String order = tet_min_order.getText().toString();
                String time = tet_class_time_minute.getText().toString();

                if (TextUtils.isEmpty(price)){
                    ToastUtils.showShortToast("请输入单次课程价格!");
                    return;
                }
                if (TextUtils.isEmpty(order)){
                    ToastUtils.showShortToast("请输入单次最少预定次数!");
                    return;
                }
                if (TextUtils.isEmpty(time)){
                    ToastUtils.showShortToast("请输入单次授课时长!");
                    return;
                }

                getIntent().putExtra("price",price);
                getIntent().putExtra("order",order);
                getIntent().putExtra("time",time);

                setResult(RESULT_OK,getIntent());
                finish();
            }
        });

        tet_time_price.addTextChangedListener(new TextWatcher() {
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
                if (temp.length() - posDot - 1 > 2)
                {
                    s.delete(posDot + 3, posDot + 4);
                }

            }
        });

        tet_min_order.addTextChangedListener(new TextWatcher() {
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

            }
        });

        tet_class_time_minute.addTextChangedListener(new TextWatcher() {
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

            }
        });
    }
}
