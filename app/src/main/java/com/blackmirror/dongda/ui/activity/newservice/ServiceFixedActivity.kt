package com.blackmirror.dongda.ui.activity.newservice

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
import com.blackmirror.dongda.utils.getDoubleValue
import com.blackmirror.dongda.utils.showToast

class ServiceFixedActivity : BaseActivity() {

    private lateinit var iv_back: ImageView
    private lateinit var tv_head_title: TextView
    private lateinit var tv_save: TextView
    private lateinit var tet_all_month_price: TextInputEditText
    private lateinit var tet_mid_month_price: TextInputEditText

    private var can_save: Boolean = false

    override val layoutResId: Int
        get() = R.layout.activity_service_fixed

    override fun initInject() {

    }

    override fun initView() {
        iv_back = findViewById(R.id.iv_back)
        tv_head_title = findViewById(R.id.tv_head_title)
        tv_save = findViewById(R.id.tv_save)
        tet_all_month_price = findViewById(R.id.tet_all_month_price)
        tet_mid_month_price = findViewById(R.id.tet_mid_month_price)
    }

    override fun initData() {
        tv_head_title.text = "固定付费设置"

        val s1 = SpannableString("请填写")//定义hint的值
        val as1 = AbsoluteSizeSpan(15, true)//设置字体大小 true表示单位是sp
        s1.setSpan(as1, 0, s1.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tet_all_month_price.hint = SpannedString(s1)

        val s2 = SpannableString("请填写")//定义hint的值
        val as2 = AbsoluteSizeSpan(15, true)//设置字体大小 true表示单位是sp
        s2.setSpan(as2, 0, s2.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tet_mid_month_price.hint = SpannedString(s2)
    }

    override fun initListener() {
        iv_back.setOnClickListener { finish() }

        tv_save.setOnClickListener(View.OnClickListener {
            val all_month_price = tet_all_month_price.text.toString()
            val mid_month_price = tet_mid_month_price.text.toString()

            if (all_month_price.isNullOrEmpty()) {
                showToast("请输入全日托单月价格!")
                return@OnClickListener
            }
            if (mid_month_price.isNullOrEmpty()) {
                showToast("请输入半日托单月价格!")
                return@OnClickListener
            }

            val all = (all_month_price.getDoubleValue() * 100).toLong()
            val mid = (mid_month_price.getDoubleValue() * 100).toLong()

            intent.putExtra("all_month_price", all)
            intent.putExtra("mid_month_price", mid)
            setResult(Activity.RESULT_OK, intent)
            finish()
        })

        tet_all_month_price.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty() && !can_save) {
                    can_save = true
                    tv_save.setTextColor(Color.parseColor("#FF59D5C7"))
                }
                if (s.toString().isEmpty() && can_save) {
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

        tet_mid_month_price.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty() && !can_save) {
                    can_save = true
                    tv_save.setTextColor(Color.parseColor("#FF59D5C7"))
                }
                if (s.toString().isEmpty() && can_save) {
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
    }
}
