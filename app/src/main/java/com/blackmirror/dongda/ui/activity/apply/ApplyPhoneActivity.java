package com.blackmirror.dongda.ui.activity.apply;

import android.content.Intent;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApplyPhoneActivity extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_next;
    private TextInputEditText tet_phone_no;
    private TextView tv_name_dec;
    private String user_name;
    private String city_name;
    private boolean can_next;

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
        user_name = getIntent().getStringExtra("user_name");
        city_name = getIntent().getStringExtra("city_name");
        tv_name_dec.setText("非常好，来自"+city_name+"的"+user_name+"\n我们怎样可以联系到你？");
        SpannableString ss = new SpannableString(getString(R.string.apply_input_phone_hint));//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(15,true);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tet_phone_no.setHint(new SpannedString(ss));
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
                String phone_no = replaceBlank(tet_phone_no.getText().toString().trim());
                if (TextUtils.isEmpty(phone_no)){
                    ToastUtils.showShortToast("手机号不能为空!");
                    return;
                }

                if (phone_no.length()!=11){
                    ToastUtils.showShortToast("请输入正确的手机号!");
                    return;
                }
                Intent intent = new Intent(ApplyPhoneActivity.this, ApplyServiceActivity.class);
                intent.putExtra("user_name",user_name);
                intent.putExtra("brand_name",getIntent().getStringExtra("brand_name"));
                intent.putExtra("city_name",city_name);
                intent.putExtra("phone_no",replaceBlank(phone_no));
                startActivity(intent);
            }
        });

        tet_phone_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null || s.length() == 0) return;
                if (s.toString().length()!=0 && !can_next){
                    can_next = true;
                    tv_next.setTextColor(Color.parseColor("#FF59D5C7"));
                }
                if (s.toString().length()==0 && can_next){
                    can_next = false;
                    tv_next.setTextColor(Color.parseColor("#FFD9D9D9"));
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    if (i != 3 && i != 8 && s.charAt(i) == ' ') {
                        continue;
                    } else {
                        sb.append(s.charAt(i));
                        if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
                            sb.insert(sb.length() - 1, ' ');
                        }
                    }
                }
                if (!sb.toString().equals(s.toString())) {
                    int index = start + 1;
                    if (sb.charAt(start) == ' ') {
                        if (before == 0) {
                            index++;
                        } else {
                            index--;
                        }
                    } else {
                        if (before == 1) {
                            index--;
                        }
                    }
                    tet_phone_no.setText(sb.toString());
                    tet_phone_no.setSelection(index);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 获取电话号码
     * @return
     */
    public String getPhoneText() {
        String str = tet_phone_no.getText().toString();
        return replaceBlank(str);
    }

    /**
     * 去除字符串中的空格、回车、换行符、制表符
     *
     * @param str
     *
     * @return
     */
    private String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}
