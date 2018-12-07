package com.blackmirror.dongda.ui.activity.newservice

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

class ServiceTeacherNumActivity : BaseActivity() {

    private lateinit var iv_back: ImageView
    private lateinit var tv_next: TextView
    private lateinit var tet_teacher_num: TextInputEditText
    private lateinit var tet_child_num: TextInputEditText
    private lateinit var tet_location_num: TextInputEditText
    private var can_next: Boolean = false

    override val layoutResId: Int
        get() = R.layout.activity_service_teacher_num


    override fun initInject() {

    }

    override fun initView() {
        iv_back = findViewById(R.id.iv_back)
        tv_next = findViewById(R.id.tv_next)
        tet_teacher_num = findViewById(R.id.tet_teacher_num)
        tet_child_num = findViewById(R.id.tet_child_num)
        tet_location_num = findViewById(R.id.tet_location_num)
    }

    override fun initData() {
        val s1 = SpannableString("请填写")//定义hint的值
        val as1 = AbsoluteSizeSpan(15, true)//设置字体大小 true表示单位是sp
        s1.setSpan(as1, 0, s1.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tet_teacher_num.hint = SpannedString(s1)

        val s2 = SpannableString("请填写")//定义hint的值
        val as2 = AbsoluteSizeSpan(15, true)//设置字体大小 true表示单位是sp
        s2.setSpan(as2, 0, s2.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tet_child_num.hint = SpannedString(s2)

        val s3 = SpannableString("请填写")//定义hint的值
        val as3 = AbsoluteSizeSpan(15, true)//设置字体大小 true表示单位是sp
        s3.setSpan(as3, 0, s3.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tet_location_num.hint = SpannedString(s3)
    }

    override fun initListener() {
        iv_back.setOnClickListener { finish() }

        tv_next.setOnClickListener(View.OnClickListener {
            val teacher_num = tet_teacher_num.text.toString()
            val child_num = tet_child_num.text.toString()
            val location_num = tet_location_num.text.toString()

            if (teacher_num.isNullOrEmpty()) {
                showToast("请输入教师人数!")
                return@OnClickListener
            }
            if (child_num.isNullOrEmpty()) {
                showToast("请输入学生人数!")
                return@OnClickListener
            }
            if (location_num.isNullOrEmpty()) {
                showToast("请输入场地容纳人数!")
                return@OnClickListener
            }


            val intent = Intent(this@ServiceTeacherNumActivity, ServicePayTypeActivity::class.java)
            intent.putExtra("service_id", getIntent().getStringExtra("service_id"))
            intent.putExtra("min_age", getIntent().getStringExtra("min_age"))
            intent.putExtra("max_age", getIntent().getStringExtra("max_age"))
            intent.putExtra("teacher_num", teacher_num)
            intent.putExtra("child_num", child_num)
            intent.putExtra("location_num", location_num)
            intent.putExtra("locations", getIntent().getStringExtra("locations"))
            intent.putExtra("service_leaf", getIntent().getStringExtra("service_leaf"))
            intent.putExtra("service_image", getIntent().getStringExtra("service_image"))
            intent.putExtra("address", getIntent().getStringExtra("address"))
            startActivity(intent)
        })

        tet_teacher_num.addTextChangedListener(object : TextWatcher {
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

        tet_child_num.addTextChangedListener(object : TextWatcher {
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

        tet_location_num.addTextChangedListener(object : TextWatcher {
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
