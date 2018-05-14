package com.blackmirror.dongda.ui.activity.enrol;

import android.content.Intent;
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

import java.util.ArrayList;

public class EnrolAgeActivity extends BaseActivity implements View.OnClickListener {

    private ArrayList<String> food = new ArrayList<>();
    private ArrayList<String> clothes = new ArrayList<>();
    private ArrayList<String> computer = new ArrayList<>();
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

        for (int i = 0; i < 10; i++) {
            food.add("food:" + i);
        }

        for (int i = 0; i < 10; i++) {
            clothes.add("clothes:" + i);
        }

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
                String f = food.get(options1);
                String c = clothes.get(options2);

                LogUtils.d("food: " + f + "  clothes: " + c + " options3=" + options3);
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

        pvCustomOptions.setNPicker(food, clothes, null);//添加数据
    }

    @Override
    protected void initListener() {
        cl_choose_low_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvCustomOptions.setSelectOptions(0, 0);
                pvCustomOptions.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_next:
                Intent intent = new Intent(EnrolAgeActivity.this, EnrolClassNumActivity.class);
                startActivity(intent);
                break;
            case R.id.cl_choose_low_age:
                break;
            case R.id.cl_choose_large_age:
                break;
        }
    }
}
