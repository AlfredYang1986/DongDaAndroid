package com.blackmirror.dongda.ui.activity.enrol

import android.content.Intent
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

class EnrolClassNumActivity : BaseActivity() {

    private lateinit var iv_back: ImageView
    private lateinit var tv_next: TextView
    private lateinit var tet_max_num: TextInputEditText

    private var can_next: Boolean = false

    override val layoutResId: Int
        get() = R.layout.activity_enrol_class_num


    override fun initInject() {

    }

    override fun initView() {
        iv_back = findViewById(R.id.iv_back)
        tv_next = findViewById(R.id.tv_next)
        tet_max_num = findViewById(R.id.tet_max_num)
    }

    override fun initData() {
        val s1 = SpannableString("请填写")//定义hint的值
        val as1 = AbsoluteSizeSpan(15, true)//设置字体大小 true表示单位是sp
        s1.setSpan(as1, 0, s1.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tet_max_num.hint = SpannedString(s1)
    }

    override fun initListener() {
        iv_back.setOnClickListener { finish() }

        tv_next.setOnClickListener(View.OnClickListener {
            val max_num = tet_max_num.text.toString()
            if (TextUtils.isEmpty(max_num)) {
                ToastUtils.showShortToast("最大满班人数不能为空!")
                return@OnClickListener
            }
            if (Integer.parseInt(max_num) < 10) {
                ToastUtils.showShortToast("最大满班人数不能小于最少开班人数!")
                return@OnClickListener
            }
            val intent = Intent(this@EnrolClassNumActivity, EnrolPayTypeActivity::class.java)
            intent.putExtra("service_id", getIntent().getStringExtra("service_id"))
            intent.putExtra("min_age", getIntent().getStringExtra("min_age"))
            intent.putExtra("max_age", getIntent().getStringExtra("max_age"))
            intent.putExtra("min_num", 10.toString() + "")
            intent.putExtra("max_num", max_num)
            intent.putExtra("locations", getIntent().getStringExtra("locations"))
            intent.putExtra("service_leaf", getIntent().getStringExtra("service_leaf"))
            intent.putExtra("service_image", getIntent().getStringExtra("service_image"))
            intent.putExtra("address", getIntent().getStringExtra("address"))
            startActivity(intent)
        })

        tet_max_num.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().length != 0 && !can_next) {
                    can_next = true
                    tv_next.setTextColor(Color.parseColor("#FF59D5C7"))
                }
                if (s.toString().length == 0 && can_next) {
                    can_next = false
                    tv_next.setTextColor(Color.parseColor("#FFD9D9D9"))
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }
}
