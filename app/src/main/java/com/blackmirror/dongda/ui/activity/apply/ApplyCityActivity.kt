package com.blackmirror.dongda.ui.activity.apply

import android.content.Intent
import android.graphics.Color
import android.support.design.widget.TextInputEditText
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.showToast

class ApplyCityActivity : BaseActivity() {

    private lateinit var iv_back: ImageView
    private lateinit var tv_next: TextView
    private lateinit var tet_city_name: TextInputEditText
    private lateinit var tv_name_dec: TextView

    private var user_name: String? = null
    private var brand_name: String? = null
    private var can_next: Boolean = false

    override val layoutResId: Int
        get() = R.layout.activity_apply_city

    override fun init() {

    }

    override fun initInject() {

    }

    override fun initView() {
        iv_back = findViewById(R.id.iv_back)
        tv_next = findViewById(R.id.tv_next)
        tet_city_name = findViewById(R.id.tet_city_name)
        tv_name_dec = findViewById(R.id.tv_name_dec)
    }

    override fun initData() {
        user_name = intent.getStringExtra("user_name")
        brand_name = intent.getStringExtra("brand_name")
        tv_name_dec.text = "你好，$user_name\n你的服务所在的城市？"
    }

    override fun initListener() {
        iv_back.setOnClickListener { finish() }

        tv_next.setOnClickListener(View.OnClickListener {
            val city_name = tet_city_name.editableText.toString()
            if (city_name.isNullOrEmpty()) {
                showToast(msg = "请输入城市!")
                return@OnClickListener
            }
            val intent = Intent(this@ApplyCityActivity, ApplyPhoneActivity::class.java)
            intent.putExtra("user_name", user_name)
            intent.putExtra("brand_name", brand_name)
            intent.putExtra("city_name", city_name)
            startActivity(intent)
        })

        tet_city_name.addTextChangedListener(object : TextWatcher {
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
