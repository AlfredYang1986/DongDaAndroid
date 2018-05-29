package com.blackmirror.dongda.ui.activity.newservice;

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

public class ServiceTeacherNumActivity extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_next;
    private TextInputEditText tet_teacher_num;
    private TextInputEditText tet_child_num;
    private TextInputEditText tet_location_num;
    private boolean can_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_service_teacher_num;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_next = findViewById(R.id.tv_next);
        tet_teacher_num = findViewById(R.id.tet_teacher_num);
        tet_child_num = findViewById(R.id.tet_child_num);
        tet_location_num = findViewById(R.id.tet_location_num);
    }

    @Override
    protected void initData() {
        SpannableString s1 = new SpannableString("请填写");//定义hint的值
        AbsoluteSizeSpan as1 = new AbsoluteSizeSpan(15,true);//设置字体大小 true表示单位是sp
        s1.setSpan(as1, 0, s1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tet_teacher_num.setHint(new SpannedString(s1));

        SpannableString s2 = new SpannableString("请填写");//定义hint的值
        AbsoluteSizeSpan as2 = new AbsoluteSizeSpan(15,true);//设置字体大小 true表示单位是sp
        s2.setSpan(as2, 0, s2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tet_child_num.setHint(new SpannedString(s2));

        SpannableString s3 = new SpannableString("请填写");//定义hint的值
        AbsoluteSizeSpan as3 = new AbsoluteSizeSpan(15,true);//设置字体大小 true表示单位是sp
        s3.setSpan(as3, 0, s3.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tet_location_num.setHint(new SpannedString(s3));
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

                String teacher_num = tet_teacher_num.getText().toString();
                String child_num = tet_child_num.getText().toString();
                String location_num = tet_location_num.getText().toString();

                if (TextUtils.isEmpty(teacher_num)){
                    ToastUtils.showShortToast("请输入教师人数!");
                    return;
                }
                if (TextUtils.isEmpty(child_num)){
                    ToastUtils.showShortToast("请输入学生人数!");
                    return;
                }
                if (TextUtils.isEmpty(location_num)){
                    ToastUtils.showShortToast("请输入场地容纳人数!");
                    return;
                }


                Intent intent = new Intent(ServiceTeacherNumActivity.this, ServicePayTypeActivity.class);
                intent.putExtra("service_id",getIntent().getStringExtra("service_id"));
                intent.putExtra("min_age",getIntent().getStringExtra("min_age"));
                intent.putExtra("max_age",getIntent().getStringExtra("max_age"));
                intent.putExtra("teacher_num",teacher_num);
                intent.putExtra("child_num",child_num);
                intent.putExtra("location_num",location_num);
                intent.putExtra("locations",getIntent().getStringExtra("locations"));
                intent.putExtra("service_leaf",getIntent().getStringExtra("service_leaf"));
                intent.putExtra("service_image",getIntent().getStringExtra("service_image"));
                intent.putExtra("address",getIntent().getStringExtra("address"));
                startActivity(intent);
            }
        });

        tet_teacher_num.addTextChangedListener(new TextWatcher() {
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

        tet_child_num.addTextChangedListener(new TextWatcher() {
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

        tet_location_num.addTextChangedListener(new TextWatcher() {
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
