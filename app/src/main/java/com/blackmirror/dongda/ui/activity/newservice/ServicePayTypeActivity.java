package com.blackmirror.dongda.ui.activity.newservice;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.ui.activity.enrol.EnrolConfirmActivity;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.blackmirror.dongda.utils.AYPrefUtils;
import com.blackmirror.dongda.utils.AppConstant;
import com.blackmirror.dongda.utils.LogUtils;
import com.blackmirror.dongda.utils.ToastUtils;

public class ServicePayTypeActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TextView tv_next;
    private RadioButton rb_flexible_pay;
    private RadioButton rb_fixed_pay;
    private Intent data;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_service_pay_type;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_next = findViewById(R.id.tv_next);
        rb_flexible_pay = findViewById(R.id.rb_flexible_pay);
        rb_fixed_pay = findViewById(R.id.rb_fixed_pay);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        rb_flexible_pay.setOnClickListener(this);
        rb_fixed_pay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_next:
                if (!rb_flexible_pay.isChecked() && !rb_fixed_pay.isChecked()){
                    ToastUtils.showShortToast("请选择支付方式!");
                    return;
                }
                Intent intent = new Intent(this, EnrolConfirmActivity.class);

                intent.putExtra("service_id",getIntent().getStringExtra("service_id"));
                intent.putExtra("min_age",getIntent().getStringExtra("min_age"));
                intent.putExtra("max_age",getIntent().getStringExtra("max_age"));
                intent.putExtra("teacher_num",getIntent().getStringExtra("teacher_num"));
                intent.putExtra("child_num",getIntent().getStringExtra("child_num"));
                intent.putExtra("location_num",getIntent().getStringExtra("location_num"));
                intent.putExtra("locations",getIntent().getStringExtra("locations"));
                intent.putExtra("service_leaf",getIntent().getStringExtra("service_leaf"));
                intent.putExtra("service_image",getIntent().getStringExtra("service_image"));
                intent.putExtra("address",getIntent().getStringExtra("address"));

                long teacher_num=Long.parseLong(getIntent().getStringExtra("teacher_num"));
                long child_num=Long.parseLong(getIntent().getStringExtra("child_num"));

                String service_id = getIntent().getStringExtra("service_id");
                double min_age = Double.parseDouble(getIntent().getStringExtra("min_age"))*10;
                double max_age = Double.parseDouble(getIntent().getStringExtra("max_age"))*10;

                if (rb_flexible_pay.isChecked()){
                    long hour_price = data.getLongExtra("hour_price",0);
                    long min_buy_hour = Long.parseLong(data.getStringExtra("min_buy_hour"));

                    intent.putExtra("hour_price",hour_price);
                    intent.putExtra("min_buy_hour",min_buy_hour);


                    String json="{\"token\":\""+AYPrefUtils.getAuthToken()+"\",\"recruit\":{\"service_id\":\""+service_id+"\",\"age_boundary\":{\"lbl\":"+min_age+",\"ubl\":"+max_age+"},\"stud_tech\":{\"stud\":"+child_num+",\"tech\":"+teacher_num+"},\"payment_daily\":{\"price\":"+hour_price+",\"length\":"+min_buy_hour+"}}}";
                    intent.putExtra("json",json);



                }else if (rb_fixed_pay.isChecked()){
                    long all_month_price = data.getLongExtra("all_month_price",0);
                    long mid_month_price = data.getLongExtra("mid_month_price",0);

                    intent.putExtra("all_month_price",all_month_price);
                    intent.putExtra("mid_month_price",mid_month_price);

                    String json="{\"token\":\""+AYPrefUtils.getAuthToken()+"\",\"recruit\":{\"service_id\":\""+service_id+"\",\"age_boundary\":{\"lbl\":"+min_age+",\"ubl\":"+max_age+"},\"stud_tech\":{\"stud\":"+child_num+",\"tech\":"+teacher_num+"},\"payment_monthly\":{\"full_time\":"+all_month_price+",\"half_time\":"+mid_month_price+"}}}";

                    intent.putExtra("json",json);

                }

                startActivity(intent);
                break;
            case R.id.rb_flexible_pay:
                startActivityForResult(new Intent(ServicePayTypeActivity.this, ServiceFlexibleActivity.class), AppConstant.FLEXIBLE_PAY_CODE);
                break;
            case R.id.rb_fixed_pay:
                startActivityForResult(new Intent(ServicePayTypeActivity.this, ServiceFixedActivity.class), AppConstant.FIXED_PAY_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.d("requestCode "+requestCode+" ;resultCode "+resultCode);
        handleResult(requestCode, resultCode, data);
    }

    private void handleResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case AppConstant.FLEXIBLE_PAY_CODE:
                if (resultCode == RESULT_OK) {
                    rb_flexible_pay.setChecked(true);
                    rb_fixed_pay.setChecked(false);
                    tv_next.setTextColor(Color.parseColor("#FF59D5C7"));
                    this.data = data;
                } else {
                    rb_flexible_pay.setChecked(false);
                    tv_next.setTextColor(Color.parseColor("#FFD9D9D9"));
                }
                break;
            case AppConstant.FIXED_PAY_CODE:
                if (resultCode == RESULT_OK) {
                    rb_fixed_pay.setChecked(true);
                    rb_flexible_pay.setChecked(false);
                    tv_next.setTextColor(Color.parseColor("#FF59D5C7"));
                    this.data = data;
                } else {
                    rb_fixed_pay.setChecked(false);
                    tv_next.setTextColor(Color.parseColor("#FFD9D9D9"));
                }
                break;
        }
    }
}
