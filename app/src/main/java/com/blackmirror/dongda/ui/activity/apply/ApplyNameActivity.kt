package com.blackmirror.dongda.ui.activity.apply

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
import com.blackmirror.dongda.utils.showToast

class ApplyNameActivity : BaseActivity() {

    private lateinit var iv_back: ImageView
    private lateinit var tv_next: TextView
    private lateinit var tet_user_name: TextInputEditText
    private lateinit var tet_service_name: TextInputEditText

    private var can_next: Boolean = false

    override val layoutResId: Int
        get() = R.layout.activity_apply_name


    override fun initInject() {

    }

    override fun initView() {
        iv_back = findViewById(R.id.iv_back)
        tv_next = findViewById(R.id.tv_next)
        tet_user_name = findViewById(R.id.tet_user_name)
        tet_service_name = findViewById(R.id.tet_service_name)
    }

    override fun initData() {
        val s1 = SpannableString(getString(R.string.input_user_name_hint))//定义hint的值
        val as1 = AbsoluteSizeSpan(15, true)//设置字体大小 true表示单位是sp
        s1.setSpan(as1, 0, s1.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tet_user_name.hint = SpannedString(s1)

        val s2 = SpannableString(getString(R.string.input_user_name_hint))//定义hint的值
        val as2 = AbsoluteSizeSpan(15, true)//设置字体大小 true表示单位是sp
        s2.setSpan(as2, 0, s2.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tet_service_name.hint = SpannedString(s2)
    }

    override fun initListener() {

        iv_back.setOnClickListener { finish() }

        tv_next.setOnClickListener(View.OnClickListener {
            val user_name = tet_user_name.editableText.toString()
            val brand_name = tet_service_name.editableText.toString()
            if (user_name.isNullOrEmpty()) {
                showToast("用户名不能为空!")
                return@OnClickListener
            }
            val intent = Intent(this@ApplyNameActivity, ApplyCityActivity::class.java)
            intent.putExtra("user_name", user_name)
            intent.putExtra("brand_name", brand_name)
            startActivity(intent)
        })

        tet_user_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty() && !can_next) {
                    can_next = true
                    tv_next.setTextColor(Color.parseColor("#FF59D5C7"))
                }
                if (s.toString().isEmpty() && can_next) {
                    can_next = false
                    tv_next.setTextColor(Color.parseColor("#FFD9D9D9"))
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }
}
