package com.blackmirror.dongda.ui.activity.newservice

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.CustomListener
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.LogUtils
import com.blackmirror.dongda.utils.StringUtils
import com.blackmirror.dongda.utils.ToastUtils

import java.util.ArrayList

class ServiceAgeActivity : BaseActivity(), View.OnClickListener {

    private lateinit var iv_back: ImageView
    private lateinit var tv_next: TextView
    private lateinit var cl_choose_min_age: ConstraintLayout
    private lateinit var tv_choose_min_age: TextView
    private lateinit var cl_choose_max_age: ConstraintLayout
    private lateinit var tv_choose_max_age: TextView

    private var ageMin: ArrayList<String>? = null
    private var ageMax: ArrayList<String>? = null
    private var pvCustomOptions: OptionsPickerView<*>? = null

    override val layoutResId: Int
        get() = R.layout.activity_service_age


    override fun initInject() {

    }

    override fun initView() {
        iv_back = findViewById(R.id.iv_back)
        tv_next = findViewById(R.id.tv_next)
        cl_choose_min_age = findViewById(R.id.cl_choose_min_age)
        tv_choose_min_age = findViewById(R.id.tv_choose_min_age)
        cl_choose_max_age = findViewById(R.id.cl_choose_max_age)
        tv_choose_max_age = findViewById(R.id.tv_choose_max_age)
    }

    override fun initData() {
        ageMin = ArrayList()
        ageMax = ArrayList()

        ageMin!!.add("0")
        ageMin!!.add("0.5")
        ageMin!!.add("1")
        ageMin!!.add("1.5")
        ageMin!!.add("2")
        ageMin!!.add("2.5")
        ageMin!!.add("3")
        ageMin!!.add("3.5")
        ageMin!!.add("4")
        ageMin!!.add("4.5")
        ageMin!!.add("5")
        ageMin!!.add("5.5")
        ageMin!!.add("6")
        ageMin!!.add("6.5")
        ageMin!!.add("7")
        ageMin!!.add("7.5")
        ageMin!!.add("8")
        ageMin!!.add("8.5")
        ageMin!!.add("9")
        ageMin!!.add("9.5")
        ageMin!!.add("10")
        ageMin!!.add("10.5")
        ageMin!!.add("11")
        ageMin!!.add("11.5")
        ageMin!!.add("12")

        ageMax!!.addAll(ageMin)

        /**
         * @description
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        pvCustomOptions = OptionsPickerBuilder(this, OnOptionsSelectListener { options1, options2, options3, v ->
            var options2 = options2
            //返回的分别是三个级别的选中位置
            if (options2 <= options1) {
                options2 = options1
            }
            val min = ageMin!![options1]
            val max = ageMax!![options2]

            LogUtils.d("ageMin: $min  ageMax: $max options3=$options3")
            tv_choose_min_age.text = min
            tv_choose_min_age.setTextColor(Color.parseColor("#FF404040"))
            tv_choose_min_age.textSize = 22f

            tv_choose_max_age.text = max
            tv_choose_max_age.setTextColor(Color.parseColor("#FF404040"))
            tv_choose_max_age.textSize = 22f

            tv_next.setTextColor(Color.parseColor("#FF59D5C7"))
        })
                .setLayoutRes(R.layout.wheelview_pick_age) { v ->
                    val tv_save_pick = v.findViewById<TextView>(R.id.tv_save_pick)
                    tv_save_pick.setOnClickListener {
                        pvCustomOptions!!.returnData()
                        pvCustomOptions!!.dismiss()
                    }
                }
                .setSelectOptions(0, 0)
                .setContentTextSize(23)
                .build()

        pvCustomOptions!!.setNPicker(ageMin, ageMax, null)//添加数据

    }

    override fun initListener() {
        iv_back.setOnClickListener(this)
        tv_next.setOnClickListener(this)
        cl_choose_min_age.setOnClickListener(this)
        cl_choose_max_age.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> finish()
            R.id.tv_next -> {
                val min = StringUtils.getDoubleValue(tv_choose_min_age.text.toString())
                val max = StringUtils.getDoubleValue(tv_choose_max_age.text.toString())

                if (min < 0 || max < 0) {
                    ToastUtils.showShortToast("请选择年龄!")
                    return
                }

                if (min > max) {
                    ToastUtils.showShortToast("最大年龄选择错误!")
                    return
                }

                val intent = Intent(this@ServiceAgeActivity, ServiceTeacherNumActivity::class.java)
                intent.putExtra("service_id", getIntent().getStringExtra("service_id"))
                intent.putExtra("min_age", min.toString() + "")
                intent.putExtra("max_age", max.toString() + "")
                intent.putExtra("locations", getIntent().getStringExtra("locations"))
                intent.putExtra("service_leaf", getIntent().getStringExtra("service_leaf"))
                intent.putExtra("service_image", getIntent().getStringExtra("service_image"))
                intent.putExtra("address", getIntent().getStringExtra("address"))
                startActivity(intent)
            }
            R.id.cl_choose_min_age -> {
                pvCustomOptions!!.setSelectOptions(0, 0)
                pvCustomOptions!!.show()
            }
            R.id.cl_choose_max_age -> {
                pvCustomOptions!!.setSelectOptions(0, 0)
                pvCustomOptions!!.show()
            }
        }
    }
}
