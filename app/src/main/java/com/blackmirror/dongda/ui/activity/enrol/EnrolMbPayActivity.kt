package com.blackmirror.dongda.ui.activity.enrol

import android.app.Activity
import android.graphics.Color
import android.support.design.widget.TextInputEditText
import android.text.*
import android.text.style.AbsoluteSizeSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.ToastUtils
import com.blackmirror.dongda.utils.getDoubleValue

class EnrolMbPayActivity : BaseActivity() {

    private lateinit var iv_back: ImageView
    private lateinit var tv_head_title: TextView
    private lateinit var tv_save: TextView
    private lateinit var tet_mb_price: TextInputEditText
    private lateinit var tet_valid_time: TextInputEditText
    private lateinit var tet_class_time_minute: TextInputEditText

    private var can_save: Boolean = false

    override val layoutResId: Int
        get() = R.layout.activity_enrol_mb_pay

    override fun initInject() {

    }

    override fun initView() {
        iv_back = findViewById(R.id.iv_back)
        tv_head_title = findViewById(R.id.tv_head_title)
        tv_save = findViewById(R.id.tv_save)
        tet_mb_price = findViewById(R.id.tet_mb_price)
        tet_valid_time = findViewById(R.id.tet_valid_time)
        tet_class_time_minute = findViewById(R.id.tet_class_time_minute)
    }

    override fun initData() {
        tv_head_title.text = "会员制付费设置"
        val s1 = SpannableString("请填写")//定义hint的值
        val as1 = AbsoluteSizeSpan(15, true)//设置字体大小 true表示单位是sp
        s1.setSpan(as1, 0, s1.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tet_mb_price.hint = SpannedString(s1)

        val s2 = SpannableString("请填写")//定义hint的值
        val as2 = AbsoluteSizeSpan(15, true)//设置字体大小 true表示单位是sp
        s2.setSpan(as2, 0, s2.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tet_valid_time.hint = SpannedString(s2)

        val s3 = SpannableString("请填写")//定义hint的值
        val as3 = AbsoluteSizeSpan(15, true)//设置字体大小 true表示单位是sp
        s3.setSpan(as3, 0, s3.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tet_class_time_minute.hint = SpannedString(s3)
    }

    override fun initListener() {
        iv_back.setOnClickListener { finish() }

        tv_save.setOnClickListener(View.OnClickListener {
            val mb_price = tet_mb_price.text.toString()
            val valid_time = tet_valid_time.text.toString()
            val time = tet_class_time_minute.text.toString()

            if (TextUtils.isEmpty(mb_price)) {
                ToastUtils.showShortToast("请输入会员价格!")
                return@OnClickListener
            }
            if (TextUtils.isEmpty(valid_time)) {
                ToastUtils.showShortToast("请输入有效单位时间!")
                return@OnClickListener
            }
            if (TextUtils.isEmpty(time)) {
                ToastUtils.showShortToast("请输入单次授课时长!")
                return@OnClickListener
            }

            val d = (mb_price.getDoubleValue() * 100).toLong()

            intent.putExtra("mb_price", d)
            intent.putExtra("valid_time", valid_time)
            intent.putExtra("time", time)

            setResult(Activity.RESULT_OK, intent)
            finish()
        })

        tet_mb_price.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().length != 0 && !can_save) {
                    can_save = true
                    tv_save.setTextColor(Color.parseColor("#FF59D5C7"))
                }
                if (s.toString().length == 0 && can_save) {
                    can_save = false
                    tv_save.setTextColor(Color.parseColor("#FFD9D9D9"))
                }
            }

            override fun afterTextChanged(s: Editable) {
                val temp = s.toString()
                val posDot = temp.indexOf(".")
                if (posDot <= 0) return
                if (temp.length - posDot - 1 > 2) {
                    s.delete(posDot + 3, posDot + 4)
                }

            }
        })

        tet_valid_time.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().length != 0 && !can_save) {
                    can_save = true
                    tv_save.setTextColor(Color.parseColor("#FF59D5C7"))
                }
                if (s.toString().length == 0 && can_save) {
                    can_save = false
                    tv_save.setTextColor(Color.parseColor("#FFD9D9D9"))
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        tet_class_time_minute.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().length != 0 && !can_save) {
                    can_save = true
                    tv_save.setTextColor(Color.parseColor("#FF59D5C7"))
                }
                if (s.toString().length == 0 && can_save) {
                    can_save = false
                    tv_save.setTextColor(Color.parseColor("#FFD9D9D9"))
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }
}
