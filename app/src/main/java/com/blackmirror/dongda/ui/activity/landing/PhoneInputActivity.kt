package com.blackmirror.dongda.ui.activity.landing

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.base.AYApplication
import com.blackmirror.dongda.data.model.request.PhoneLoginRequestBean
import com.blackmirror.dongda.data.model.request.SendSmsRequestBean
import com.blackmirror.dongda.di.component.DaggerPhoneInputComponent
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.kdomain.model.PhoneLoginBean
import com.blackmirror.dongda.kdomain.model.SendSmsKdBean
import com.blackmirror.dongda.presenter.PhoneLoginPresenter
import com.blackmirror.dongda.ui.PhoneLoginContract
import com.blackmirror.dongda.ui.activity.homeActivity.AYHomeActivity
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.*
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit


class PhoneInputActivity : BaseActivity(), PhoneLoginContract.View {

    private lateinit var iv_back: ImageView
    private lateinit var tet_phone_no: TextInputEditText
    private lateinit var tv_login_next: TextView

    //delete
    private lateinit var et_phone: EditText
    private lateinit var et_code: EditText
    private lateinit var sms_code: Button
    private lateinit var next_step: Button

    private var mSms_disposable: Disposable? = null
    private var bean: SendSmsKdBean? = null
    private var presenter: PhoneLoginPresenter? = null

    override val layoutResId: Int
        get() = R.layout.activity_phone_input

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AYApplication.addActivity(this)
        setStatusBarColor(this, resources.getColor(R.color.colorPrimary))
    }

    override fun initInject() {
        presenter = DaggerPhoneInputComponent.builder()
                .activity(this)
                .view(this)
                .build()
                .phoneLoginPresenter
    }

    override fun initView() {
        iv_back = findViewById(R.id.iv_back)
        tet_phone_no = findViewById(R.id.tet_phone_no)
        tv_login_next = findViewById(R.id.tv_login_next)
    }

    override fun initData() {

    }

    override fun initListener() {

        tv_login_next.setOnClickListener {
            logD("request SMS code from server")

            val input_phone = tet_phone_no.text.toString()

            if (input_phone.isNullOrEmpty()) {
                showToast(getString(R.string.phone_not_empty))
                return@setOnClickListener
            }

            if (input_phone.trim { it <= ' ' }.length != 11) {
                showToast(getString(R.string.input_phone_error))
                return@setOnClickListener
            }

            val bean = SendSmsRequestBean()
            bean.phone_number = input_phone
            presenter?.sendSms(bean)
            countDownSmsTime()
        }

//        next_step.setOnClickListener { loginWithPhoneAndCode() }
    }

    private fun countDownSmsTime() {
        sms_code.isEnabled = false
        Observable.intervalRange(0, 30, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Long> {
                    override fun onSubscribe(d: Disposable) {
                        mSms_disposable = d
                    }

                    override fun onNext(aLong: Long) {
                        sms_code.text = String.format(getString(R.string.sms_count_down), (30 - aLong).toString())
                    }

                    override fun onError(e: Throwable) {
                        sms_code.isEnabled = true
                    }

                    override fun onComplete() {
                        sms_code.isEnabled = true
                        sms_code.text = getString(R.string.phone_input_btn_action_query_code)
                    }
                })

    }

    private fun loginWithPhoneAndCode() {
        logD("login with phone code")
        val phone = et_phone.text.toString().trim { it <= ' ' }
        val code = et_code.text.toString().trim { it <= ' ' }
        if (phone.isNullOrEmpty() || phone.length != 11) {
            showToast(getString(R.string.input_phone_error))
            return
        }
        if (code.isNullOrEmpty()) {
            showToast(getString(R.string.input_code_error))
            return
        }


        if (bean != null && bean!!.isSuccess) {
            val requestBean = PhoneLoginRequestBean()
            requestBean.reg_token = bean!!.reg_token
            requestBean.phone_number = bean!!.phone
            requestBean.code = code
            showProcessDialog(getString(R.string.logining_process))
            presenter?.login(requestBean)

        } else {
            showToast(getString(R.string.phone_input_next_step_error))
        }
    }

    private fun gotoActivity(bean: PhoneLoginBean) {
        if (!bean.isSuccess) {
            showToast(getString(R.string.login_failare))
            return
        }

        showToast(getString(R.string.login_success))

        if (bean.screen_name.isNullOrEmpty()) {

            val intent = Intent(this@PhoneInputActivity, NameInputActivity::class.java)
            intent.putExtra("has_photo", !TextUtils.isEmpty(bean.screen_photo))
            startActivity(intent)

        } else if (bean.screen_photo.isNullOrEmpty()) {

            val intent = Intent(this@PhoneInputActivity, PhotoChangeActivity::class.java)
            intent.putExtra("from", AppConstant.FROM_PHONE_INPUT)
            intent.putExtra("name", bean.screen_name)
            startActivity(intent)
        } else {
            val intent = Intent(this@PhoneInputActivity, AYHomeActivity::class.java)
            intent.putExtra("img_uuid", bean.screen_photo)
            setImgUuid(bean.screen_photo)
            setIsPhoneLogin("phone")
            startActivity(intent)
            AYApplication.finishAllActivity()
        }
    }

    override fun loginSuccess(bean: PhoneLoginBean) {
        closeProcessDialog()
        gotoActivity(bean)
    }

    override fun sendSmsSuccess(bean: SendSmsKdBean) {
        showToast(getString(R.string.send_sms_code_success))
        closeProcessDialog()
        this.bean = bean ?: SendSmsKdBean()
    }

    override fun onError(bean: BaseDataBean) {
        closeProcessDialog()
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            showSnackbar(sms_code, bean.message ?: "Server Error")
        } else {
            showToast("${bean.message}(${bean.code})")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mSms_disposable != null && !mSms_disposable!!.isDisposed) {
            mSms_disposable!!.dispose()
            mSms_disposable = null
        }
    }

    override fun onBackPressed() {
        AYApplication.removeActivity(this)
        super.onBackPressed()
    }
}
