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
import com.blackmirror.dongda.utils.ToastUtils
import java.util.regex.Pattern

class ApplyPhoneActivity : BaseActivity() {

    private lateinit var iv_back: ImageView
    private lateinit var tv_next: TextView
    private lateinit var tet_phone_no: TextInputEditText
    private lateinit var tv_name_dec: TextView

    private var user_name: String? = null
    private var city_name: String? = null
    private var can_next: Boolean = false

    override val layoutResId: Int
        get() = R.layout.activity_apply_phone

    /**
     * 获取电话号码
     * @return
     */
    val phoneText: String
        get() {
            val str = tet_phone_no.text.toString()
            return replaceBlank(str)
        }

    override fun initInject() {

    }

    override fun initView() {
        iv_back = findViewById(R.id.iv_back)
        tv_next = findViewById(R.id.tv_next)
        tet_phone_no = findViewById(R.id.tet_phone_no)
        tv_name_dec = findViewById(R.id.tv_name_dec)
    }

    override fun initData() {
        user_name = intent.getStringExtra("user_name")
        city_name = intent.getStringExtra("city_name")
        tv_name_dec.text = "非常好，来自${city_name}的${user_name}\n我们怎样可以联系到你？"
        val ss = SpannableString(getString(R.string.apply_input_phone_hint))//定义hint的值
        val ass = AbsoluteSizeSpan(15, true)//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tet_phone_no.hint = SpannedString(ss)
    }

    override fun initListener() {
        iv_back.setOnClickListener { finish() }

        tv_next.setOnClickListener(View.OnClickListener {
            val phone_no = replaceBlank(tet_phone_no.text.toString().trim { it <= ' ' })
            if (phone_no.isNullOrEmpty()) {
                ToastUtils.showShortToast("手机号不能为空!")
                return@OnClickListener
            }

            if (phone_no.length != 11) {
                ToastUtils.showShortToast("请输入正确的手机号!")
                return@OnClickListener
            }
            val intent = Intent(this@ApplyPhoneActivity, ApplyServiceActivity::class.java)
            intent.putExtra("user_name", user_name)
            intent.putExtra("brand_name", getIntent().getStringExtra("brand_name"))
            intent.putExtra("city_name", city_name)
            intent.putExtra("phone_no", replaceBlank(phone_no))
            startActivity(intent)
        })

        tet_phone_no.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    return
                }
                if (s.toString().isNotEmpty() && !can_next) {
                    can_next = true
                    tv_next.setTextColor(Color.parseColor("#FF59D5C7"))
                }
                if (s.toString().isEmpty() && can_next) {
                    can_next = false
                    tv_next.setTextColor(Color.parseColor("#FFD9D9D9"))
                }
                val sb = StringBuilder()
                for (i in 0 until s.length) {
                    if (i != 3 && i != 8 && s[i] == ' ') {
                        continue
                    } else {
                        sb.append(s[i])
                        if ((sb.length == 4 || sb.length == 9) && sb[sb.length - 1] != ' ') {
                            sb.insert(sb.length - 1, ' ')
                        }
                    }
                }
                if (sb.toString() != s.toString()) {
                    var index = start + 1
                    if (sb[start] == ' ') {
                        if (before == 0) {
                            index++
                        } else {
                            index--
                        }
                    } else {
                        if (before == 1) {
                            index--
                        }
                    }
                    tet_phone_no.setText(sb.toString())
                    tet_phone_no.setSelection(index)
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }

    /**
     * 去除字符串中的空格、回车、换行符、制表符
     *
     * @param str
     *
     * @return
     */
    private fun replaceBlank(str: String?): String {
        var dest = ""
        if (str != null) {
            val p = Pattern.compile("\\s*|\t|\r|\n")
            val m = p.matcher(str)
            dest = m.replaceAll("")
        }
        return dest
    }
}
