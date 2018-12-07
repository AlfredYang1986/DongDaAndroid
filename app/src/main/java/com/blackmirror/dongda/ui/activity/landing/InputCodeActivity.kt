package com.blackmirror.dongda.ui.activity.landing

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.logD

class InputCodeActivity : BaseActivity(), View.OnClickListener {


    lateinit var iv_back: ImageView
    lateinit var tv_phone_no: TextView
    lateinit var tv_code_1: TextView
    lateinit var tv_code_2: TextView
    lateinit var tv_code_3: TextView
    lateinit var tv_code_4: TextView
    lateinit var et_support_num: EditText
    lateinit var btn_resend_code: Button

    lateinit var text_list: ArrayList<TextView>

    override val layoutResId: Int
        get() = R.layout.activity_input_code


    override fun initInject() {
    }

    override fun initView() {
        iv_back = findViewById(R.id.iv_back)
        tv_phone_no = findViewById(R.id.tv_phone_no)
        tv_code_1 = findViewById(R.id.tv_code_1)
        tv_code_2 = findViewById(R.id.tv_code_2)
        tv_code_3 = findViewById(R.id.tv_code_3)
        tv_code_4 = findViewById(R.id.tv_code_4)
        et_support_num = findViewById(R.id.et_support_num)
        btn_resend_code = findViewById(R.id.btn_resend_code)
        text_list = arrayListOf(tv_code_1, tv_code_2, tv_code_3, tv_code_4)
    }

    override fun initData() {
    }

    override fun initListener() {
        iv_back.setOnClickListener(this)
        tv_code_1.setOnClickListener(this)
        tv_code_2.setOnClickListener(this)
        tv_code_3.setOnClickListener(this)
        tv_code_4.setOnClickListener(this)
        btn_resend_code.setOnClickListener(this)

        et_support_num.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                logD("afterTextChanged = $s")
                if (s.isEmpty()) {
                    clearText()
                    return
                }

                setCode()

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }


        })
    }

    private fun setCode() {
        val s = et_support_num.text.toString().toCharArray()
        clearText()
        for ((k,v) in s.withIndex()) {
            text_list[k].text=v.toString()
        }
    }

    private fun clearText() {
        tv_code_1.text = ""
        tv_code_2.text = ""
        tv_code_3.text = ""
        tv_code_4.text = ""
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> {
                finish()
            }
            R.id.tv_code_1, R.id.tv_code_2, R.id.tv_code_3, R.id.tv_code_4 -> {
                showInput()
            }
            R.id.btn_resend_code -> {

            }
        }
    }

    private fun showInput() {
        et_support_num.isFocusable = true
        et_support_num.isFocusableInTouchMode = true
        et_support_num.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }

}
