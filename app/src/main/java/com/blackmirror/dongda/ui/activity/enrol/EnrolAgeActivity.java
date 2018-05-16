package com.blackmirror.dongda.ui.activity.enrol;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.blackmirror.dongda.utils.LogUtils;
import com.blackmirror.dongda.utils.StringUtils;
import com.blackmirror.dongda.utils.ToastUtils;

import java.util.ArrayList;

public class EnrolAgeActivity extends BaseActivity implements View.OnClickListener {

    private ArrayList<String> ageMin = new ArrayList<>();
    private ArrayList<String> ageMax = new ArrayList<>();
    private OptionsPickerView pvCustomOptions;
    private ConstraintLayout cl_choose_low_age;
    private ImageView iv_back;
    private TextView tv_next;
    private TextView tv_choose_low_age;
    private ConstraintLayout cl_choose_large_age;
    private TextView tv_choose_large_age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_enrol_age;
    }

    @Override
    protected void initInject() {

    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_next = findViewById(R.id.tv_next);
        cl_choose_low_age = findViewById(R.id.cl_choose_low_age);
        tv_choose_low_age = findViewById(R.id.tv_choose_low_age);
        cl_choose_large_age = findViewById(R.id.cl_choose_large_age);
        tv_choose_large_age = findViewById(R.id.tv_choose_large_age);
    }

    @Override
    protected void initData() {

        ageMin.add("0");
        ageMin.add("0.5");
        ageMin.add("1");
        ageMin.add("1.5");
        ageMin.add("2");
        ageMin.add("2.5");
        ageMin.add("3");
        ageMin.add("3.5");
        ageMin.add("4");
        ageMin.add("4.5");
        ageMin.add("5");
        ageMin.add("5.5");
        ageMin.add("6");
        ageMin.add("6.5");
        ageMin.add("7");
        ageMin.add("7.5");
        ageMin.add("8");
        ageMin.add("8.5");
        ageMin.add("9");
        ageMin.add("9.5");
        ageMin.add("10");
        ageMin.add("10.5");
        ageMin.add("11");
        ageMin.add("11.5");
        ageMin.add("12");

        ageMax.addAll(ageMin);


        /**
         * @description
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        pvCustomOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                if (options2<=options1){
                    options2 = options1;
                }
                String min = ageMin.get(options1);
                String max = ageMax.get(options2);

                LogUtils.d("ageMin: " + min + "  ageMax: " + max + " options3=" + options3);
                tv_choose_low_age.setText(min);
                tv_choose_low_age.setTextColor(Color.parseColor("#FF404040"));
                tv_choose_low_age.setTextSize(22);

                tv_choose_large_age.setText(max);
                tv_choose_large_age.setTextColor(Color.parseColor("#FF404040"));
                tv_choose_large_age.setTextSize(22);

                tv_next.setTextColor(Color.parseColor("#FF59D5C7"));
            }
        })
                .setLayoutRes(R.layout.wheelview_pick_age, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tv_save_pick = v.findViewById(R.id.tv_save_pick);
                        tv_save_pick.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData();
                                pvCustomOptions.dismiss();
                            }
                        });


                    }
                })
                .setSelectOptions(0, 0)
                .setContentTextSize(23)
                .build();

        pvCustomOptions.setNPicker(ageMin, ageMax, null);//添加数据
    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        cl_choose_low_age.setOnClickListener(this);
        cl_choose_large_age.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_next:
                double min = StringUtils.getDoubleValue(tv_choose_low_age.getText().toString());
                double max = StringUtils.getDoubleValue(tv_choose_large_age.getText().toString());

                if (min < 0 || max < 0) {
                    ToastUtils.showShortToast("请选择年龄!");
                    return;
                }

                if (min > max) {
                    ToastUtils.showShortToast("最大年龄选择错误!");
                    return;
                }

                Intent intent = new Intent(EnrolAgeActivity.this, EnrolClassNumActivity.class);
                intent.putExtra("service_id",getIntent().getStringExtra("service_id"));
                intent.putExtra("min_age", min+"");
                intent.putExtra("max_age", max+"");
                intent.putExtra("locations",getIntent().getStringExtra("locations"));
                intent.putExtra("service_leaf",getIntent().getStringExtra("service_leaf"));
                intent.putExtra("service_image",getIntent().getStringExtra("service_image"));
                intent.putExtra("address",getIntent().getStringExtra("address"));
                startActivity(intent);
                break;
            case R.id.cl_choose_low_age:
                pvCustomOptions.setSelectOptions(0, 0);
                pvCustomOptions.show();
                break;
            case R.id.cl_choose_large_age:
                pvCustomOptions.setSelectOptions(0, 0);
                pvCustomOptions.show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pvCustomOptions != null) {
            pvCustomOptions.dismiss();
            pvCustomOptions = null;
        }
    }
}
