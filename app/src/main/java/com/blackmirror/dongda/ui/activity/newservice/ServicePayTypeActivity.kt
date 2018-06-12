package com.blackmirror.dongda.ui.activity.newservice

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView

import com.blackmirror.dongda.R
import com.blackmirror.dongda.ui.activity.enrol.EnrolConfirmActivity
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.AYPrefUtils
import com.blackmirror.dongda.utils.AppConstant
import com.blackmirror.dongda.utils.LogUtils
import com.blackmirror.dongda.utils.ToastUtils

class ServicePayTypeActivity : BaseActivity(), View.OnClickListener {

    private lateinit var iv_back: ImageView
    private lateinit var tv_next: TextView
    private lateinit var rb_flexible_pay: RadioButton
    private lateinit var rb_fixed_pay: RadioButton
    private var data: Intent? = null

    override val layoutResId: Int
        get() = R.layout.activity_service_pay_type

    override fun initInject() {

    }

    override fun initView() {
        iv_back = findViewById(R.id.iv_back)
        tv_next = findViewById(R.id.tv_next)
        rb_flexible_pay = findViewById(R.id.rb_flexible_pay)
        rb_fixed_pay = findViewById(R.id.rb_fixed_pay)
    }

    override fun initData() {

    }

    override fun initListener() {
        iv_back.setOnClickListener(this)
        tv_next.setOnClickListener(this)
        rb_flexible_pay.setOnClickListener(this)
        rb_fixed_pay.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> finish()
            R.id.tv_next -> {
                if (!rb_flexible_pay.isChecked && !rb_fixed_pay.isChecked) {
                    ToastUtils.showShortToast("请选择支付方式!")
                    return
                }
                val intent = Intent(this, EnrolConfirmActivity::class.java)

                intent.putExtra("service_id", getIntent().getStringExtra("service_id"))
                intent.putExtra("min_age", getIntent().getStringExtra("min_age"))
                intent.putExtra("max_age", getIntent().getStringExtra("max_age"))
                intent.putExtra("teacher_num", getIntent().getStringExtra("teacher_num"))
                intent.putExtra("child_num", getIntent().getStringExtra("child_num"))
                intent.putExtra("location_num", getIntent().getStringExtra("location_num"))
                intent.putExtra("locations", getIntent().getStringExtra("locations"))
                intent.putExtra("service_leaf", getIntent().getStringExtra("service_leaf"))
                intent.putExtra("service_image", getIntent().getStringExtra("service_image"))
                intent.putExtra("address", getIntent().getStringExtra("address"))

                val teacher_num = java.lang.Long.parseLong(getIntent().getStringExtra("teacher_num"))
                val child_num = java.lang.Long.parseLong(getIntent().getStringExtra("child_num"))

                val service_id = getIntent().getStringExtra("service_id")
                val min_age = java.lang.Double.parseDouble(getIntent().getStringExtra("min_age")) * 10
                val max_age = java.lang.Double.parseDouble(getIntent().getStringExtra("max_age")) * 10

                if (rb_flexible_pay.isChecked) {
                    val hour_price = data!!.getLongExtra("hour_price", 0)
                    val min_buy_hour = java.lang.Long.parseLong(data!!.getStringExtra("min_buy_hour"))

                    intent.putExtra("hour_price", hour_price)
                    intent.putExtra("min_buy_hour", min_buy_hour)


                    val json = "{\"token\":\"" + AYPrefUtils.getAuthToken() + "\",\"recruit\":{\"service_id\":\"" + service_id + "\",\"age_boundary\":{\"lbl\":" + min_age + ",\"ubl\":" + max_age + "},\"stud_tech\":{\"stud\":" + child_num + ",\"tech\":" + teacher_num + "},\"payment_daily\":{\"price\":" + hour_price + ",\"length\":" + min_buy_hour + "}}}"
                    intent.putExtra("json", json)


                } else if (rb_fixed_pay.isChecked) {
                    val all_month_price = data!!.getLongExtra("all_month_price", 0)
                    val mid_month_price = data!!.getLongExtra("mid_month_price", 0)

                    intent.putExtra("all_month_price", all_month_price)
                    intent.putExtra("mid_month_price", mid_month_price)

                    val json = "{\"token\":\"" + AYPrefUtils.getAuthToken() + "\",\"recruit\":{\"service_id\":\"" + service_id + "\",\"age_boundary\":{\"lbl\":" + min_age + ",\"ubl\":" + max_age + "},\"stud_tech\":{\"stud\":" + child_num + ",\"tech\":" + teacher_num + "},\"payment_monthly\":{\"full_time\":" + all_month_price + ",\"half_time\":" + mid_month_price + "}}}"

                    intent.putExtra("json", json)

                }

                startActivity(intent)
            }
            R.id.rb_flexible_pay -> startActivityForResult(Intent(this@ServicePayTypeActivity, ServiceFlexibleActivity::class.java), AppConstant.FLEXIBLE_PAY_CODE)
            R.id.rb_fixed_pay -> startActivityForResult(Intent(this@ServicePayTypeActivity, ServiceFixedActivity::class.java), AppConstant.FIXED_PAY_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        LogUtils.d("requestCode $requestCode ;resultCode $resultCode")
        handleResult(requestCode, resultCode, data)
    }

    private fun handleResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            AppConstant.FLEXIBLE_PAY_CODE -> if (resultCode == Activity.RESULT_OK) {
                rb_flexible_pay.isChecked = true
                rb_fixed_pay.isChecked = false
                tv_next.setTextColor(Color.parseColor("#FF59D5C7"))
                this.data = data
            } else {
                rb_flexible_pay.isChecked = false
                tv_next.setTextColor(Color.parseColor("#FFD9D9D9"))
            }
            AppConstant.FIXED_PAY_CODE -> if (resultCode == Activity.RESULT_OK) {
                rb_fixed_pay.isChecked = true
                rb_flexible_pay.isChecked = false
                tv_next.setTextColor(Color.parseColor("#FF59D5C7"))
                this.data = data
            } else {
                rb_fixed_pay.isChecked = false
                tv_next.setTextColor(Color.parseColor("#FFD9D9D9"))
            }
        }
    }
}
