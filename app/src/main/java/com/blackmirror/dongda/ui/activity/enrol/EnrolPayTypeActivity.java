package com.blackmirror.dongda.ui.activity.enrol;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.blackmirror.dongda.utils.AppConstant;
import com.blackmirror.dongda.utils.ToastUtils;

public class EnrolPayTypeActivity extends BaseActivity implements View.OnClickListener{

    private ImageView iv_back;
    private TextView tv_next;
    private RadioButton rb_time_pay;
    private RadioButton rb_mb_pay;
    private Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_enrol_pay_type;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_next = findViewById(R.id.tv_next);
        rb_time_pay = findViewById(R.id.rb_time_pay);
        rb_mb_pay = findViewById(R.id.rb_mb_pay);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        rb_time_pay.setOnClickListener(this);
        rb_mb_pay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_next:
                if (!rb_time_pay.isChecked() && !rb_mb_pay.isChecked()){
                    ToastUtils.showShortToast("请选择支付方式!");
                    return;
                }
                Intent intent = new Intent(this, EnrolConfirmActivity.class);
                intent.putExtra("service_id",getIntent().getStringExtra("service_id"));
                intent.putExtra("min_age",getIntent().getStringExtra("min_age"));
                intent.putExtra("max_age",getIntent().getStringExtra("max_age"));
                intent.putExtra("min_num",getIntent().getStringExtra("min_num"));
                intent.putExtra("max_num",getIntent().getStringExtra("max_num"));
                intent.putExtra("locations",getIntent().getStringExtra("locations"));
                intent.putExtra("service_leaf",getIntent().getStringExtra("service_leaf"));
                intent.putExtra("service_image",getIntent().getStringExtra("service_image"));
                intent.putExtra("address",getIntent().getStringExtra("address"));

                if (rb_time_pay.isChecked()){
                    String price = data.getStringExtra("price");
                    String order = data.getStringExtra("order");
                    String time = data.getStringExtra("time");

                    intent.putExtra("price",price);
                    intent.putExtra("order",order);
                    intent.putExtra("time",time);

                }else if (rb_mb_pay.isChecked()){
                    String mb_price = data.getStringExtra("mb_price");
                    String valid_time = data.getStringExtra("valid_time");
                    String time = data.getStringExtra("time");

                    intent.putExtra("mb_price",mb_price);
                    intent.putExtra("valid_time",valid_time);
                    intent.putExtra("time",time);

                }

                startActivity(intent);

                break;
            case R.id.rb_time_pay:
                startActivityForResult(new Intent(EnrolPayTypeActivity.this,EnrolTimePayActivity.class), AppConstant.ENROL_TIME_PAY_CODE);
                break;
            case R.id.rb_mb_pay:
                startActivityForResult(new Intent(EnrolPayTypeActivity.this,EnrolMbPayActivity.class), AppConstant.ENROL_MB_PAY_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        handleResult(requestCode,resultCode,data);
    }

    private void handleResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case AppConstant.ENROL_TIME_PAY_CODE:
                if (resultCode == RESULT_OK){
                    rb_time_pay.setChecked(true);
                    rb_mb_pay.setChecked(false);
                    tv_next.setTextColor(Color.parseColor("#FF59D5C7"));
                    this.data = data;
                }else {
                    rb_time_pay.setChecked(false);
                    tv_next.setTextColor(Color.parseColor("#FFD9D9D9"));
                }
                break;
            case AppConstant.ENROL_MB_PAY_CODE:
                if (resultCode == RESULT_OK){
                    rb_mb_pay.setChecked(true);
                    rb_time_pay.setChecked(false);
                    tv_next.setTextColor(Color.parseColor("#FF59D5C7"));
                    this.data = data;
                }else {
                    rb_mb_pay.setChecked(false);
                    tv_next.setTextColor(Color.parseColor("#FFD9D9D9"));
                }
                break;
        }
    }
}
