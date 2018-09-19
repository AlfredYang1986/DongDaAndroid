package com.blackmirror.dongda.ui.activity.enrol

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView

import com.blackmirror.dongda.R
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.AYPrefUtils
import com.blackmirror.dongda.utils.AppConstant
import com.blackmirror.dongda.utils.ToastUtils

class EnrolPayTypeActivity : BaseActivity(), View.OnClickListener {

    private lateinit var iv_back: ImageView
    private lateinit var tv_next: TextView
    private lateinit var rb_time_pay: RadioButton
    private lateinit var rb_mb_pay: RadioButton

    private var data: Intent? = null

    override val layoutResId: Int
        get() = R.layout.activity_enrol_pay_type

    override fun initInject() {

    }

    override fun initView() {
        iv_back = findViewById(R.id.iv_back)
        tv_next = findViewById(R.id.tv_next)
        rb_time_pay = findViewById(R.id.rb_time_pay)
        rb_mb_pay = findViewById(R.id.rb_mb_pay)
    }

    override fun initData() {

    }

    override fun initListener() {
        iv_back.setOnClickListener(this)
        tv_next.setOnClickListener(this)
        rb_time_pay.setOnClickListener(this)
        rb_mb_pay.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> finish()
            R.id.tv_next -> {
                if (!rb_time_pay.isChecked && !rb_mb_pay.isChecked) {
                    ToastUtils.showShortToast("请选择支付方式!")
                    return
                }
                val intent = Intent(this, EnrolConfirmActivity::class.java)

                intent.putExtra("service_id", getIntent().getStringExtra("service_id"))
                intent.putExtra("min_age", getIntent().getStringExtra("min_age"))
                intent.putExtra("max_age", getIntent().getStringExtra("max_age"))
                intent.putExtra("min_num", getIntent().getStringExtra("min_num"))
                intent.putExtra("max_num", getIntent().getStringExtra("max_num"))
                intent.putExtra("locations", getIntent().getStringExtra("locations"))
                intent.putExtra("service_leaf", getIntent().getStringExtra("service_leaf"))
                intent.putExtra("service_image", getIntent().getStringExtra("service_image"))
                intent.putExtra("address", getIntent().getStringExtra("address"))

                val service_id = getIntent().getStringExtra("service_id")
                val min_age = java.lang.Double.parseDouble(getIntent().getStringExtra("min_age")) * 10
                val max_age = java.lang.Double.parseDouble(getIntent().getStringExtra("max_age")) * 10
                val min_num = java.lang.Long.parseLong(getIntent().getStringExtra("min_num"))
                val max_num = java.lang.Long.parseLong(getIntent().getStringExtra("max_num"))

                if (rb_time_pay.isChecked) {
                    val price = data!!.getLongExtra("price", 0)
                    val order = java.lang.Long.parseLong(data!!.getStringExtra("order"))
                    val class_time = java.lang.Long.parseLong(data!!.getStringExtra("time"))

                    intent.putExtra("price", price)
                    intent.putExtra("order", order)
                    intent.putExtra("time", class_time)

                    //                    price : "按次付费单价，INT，单位分"， == price
                    //                    length : "按次付费单次课程时长，INT，单位分", == time
                    //                    times : "按次付费最少预定次数, INT, 单位次" == order

                    val json = "{\"token\":\"${AYPrefUtils.getAuthToken()}\",\"recruit\":{\"service_id\":\"$service_id\",\"age_boundary\":{\"lbl\":$min_age,\"ubl\":$max_age},\"stud_boundary\":{\"min\":$min_num,\"max\":$max_num},\"payment_time\":{\"price\":$price,\"length\":$class_time,\"times\":$order}}}"
                    intent.putExtra("json", json)


                } else if (rb_mb_pay.isChecked) {
                    val mb_price = data!!.getLongExtra("mb_price", 0)
                    val valid_time = java.lang.Long.parseLong(data!!.getStringExtra("valid_time"))
                    val time = java.lang.Long.parseLong(data!!.getStringExtra("time"))

                    intent.putExtra("mb_price", mb_price)
                    intent.putExtra("valid_time", valid_time)
                    intent.putExtra("time", time)

                    //                    price : "会员制付费单价，INT， 单位分", == mb_price
                    //                    length : "会员制付费单次课程时长，INT, 单位分", == time
                    //                    period : "会员制付费一学期时长，INT，单位月" == valid_time

                    val json = "{\"token\":\"${AYPrefUtils.getAuthToken()}\",\"recruit\":{\"service_id\":\"$service_id\",\"age_boundary\":{\"lbl\":$min_age,\"ubl\":$max_age},\"stud_boundary\":{\"min\":$min_num,\"max\":$max_num},\"payment_membership\":{\"price\":$mb_price,\"length\":$time,\"period\":$valid_time}}}"
                    intent.putExtra("json", json)

                }

                startActivity(intent)
            }
            R.id.rb_time_pay -> startActivityForResult(Intent(this@EnrolPayTypeActivity, EnrolTimePayActivity::class.java), AppConstant.ENROL_TIME_PAY_CODE)
            R.id.rb_mb_pay -> startActivityForResult(Intent(this@EnrolPayTypeActivity, EnrolMbPayActivity::class.java), AppConstant.ENROL_MB_PAY_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        handleResult(requestCode, resultCode, data)
    }

    private fun handleResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            AppConstant.ENROL_TIME_PAY_CODE -> if (resultCode == Activity.RESULT_OK) {
                rb_time_pay.isChecked = true
                rb_mb_pay.isChecked = false
                tv_next.setTextColor(Color.parseColor("#FF59D5C7"))
                this.data = data
            } else {
                rb_mb_pay.isChecked = false
                rb_time_pay.isChecked = false
                tv_next.setTextColor(Color.parseColor("#FFD9D9D9"))
            }
            AppConstant.ENROL_MB_PAY_CODE -> if (resultCode == Activity.RESULT_OK) {
                rb_mb_pay.isChecked = true
                rb_time_pay.isChecked = false
                tv_next.setTextColor(Color.parseColor("#FF59D5C7"))
                this.data = data
            } else {
                rb_mb_pay.isChecked = false
                rb_time_pay.isChecked = false
                tv_next.setTextColor(Color.parseColor("#FFD9D9D9"))
            }
        }
    }
}
