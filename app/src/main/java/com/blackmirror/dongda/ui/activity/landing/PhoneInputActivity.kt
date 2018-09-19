package com.blackmirror.dongda.ui.activity.landing

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.blackmirror.dongda.DongdaApplication
import com.blackmirror.dongda.R
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
        DongdaApplication.addActivity(this)
        DeviceUtils.setStatusBarColor(this, resources.getColor(R.color.colorPrimary))
    }

    override fun initInject() {
        presenter = DaggerPhoneInputComponent.builder()
                .activity(this)
                .view(this)
                .build()
                .phoneLoginPresenter
    }

    override fun initView() {
        et_phone = findViewById(R.id.phone_edit_text)
        et_code = findViewById(R.id.code_edit_text)
        sms_code = findViewById(R.id.request_sms_code)
        next_step = findViewById(R.id.landing_phone_input_next_step)
    }

    override fun initData() {

    }

    override fun initListener() {

        sms_code.setOnClickListener {
            LogUtils.d("request SMS code from server")

            val input_phone = et_phone.text.toString()

            if (input_phone.isNullOrEmpty()) {
                ToastUtils.showShortToast(getString(R.string.phone_not_empty))
                return@setOnClickListener
            }

            if (input_phone.trim { it <= ' ' }.length != 11) {
                ToastUtils.showShortToast(getString(R.string.input_phone_error))
                return@setOnClickListener
            }

            val bean = SendSmsRequestBean()
            bean.phone_number = input_phone
            presenter?.sendSms(bean)
            countDownSmsTime()
        }

        next_step.setOnClickListener { loginWithPhoneAndCode() }
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
        LogUtils.d("login with phone code")
        val phone = et_phone.text.toString().trim { it <= ' ' }
        val code = et_code.text.toString().trim { it <= ' ' }
        if (phone.isNullOrEmpty() || phone.length != 11) {
            ToastUtils.showShortToast(R.string.input_phone_error)
            return
        }
        if (code.isNullOrEmpty()) {
            ToastUtils.showShortToast(getString(R.string.input_code_error))
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
            ToastUtils.showShortToast(R.string.phone_input_next_step_error)
        }
    }

    private fun gotoActivity(bean: PhoneLoginBean) {
        if (!bean.isSuccess) {
            ToastUtils.showShortToast(getString(R.string.login_failare))
            return
        }

        ToastUtils.showShortToast(R.string.login_success)

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
            AYPrefUtils.setImgUuid(bean.screen_photo)
            AYPrefUtils.setIsPhoneLogin("phone")
            startActivity(intent)
            DongdaApplication.finishAllActivity()
        }
    }

    override fun loginSuccess(bean: PhoneLoginBean) {
        closeProcessDialog()
        gotoActivity(bean)
    }

    override fun sendSmsSuccess(bean: SendSmsKdBean) {
        ToastUtils.showShortToast(getString(R.string.send_sms_code_success))
        closeProcessDialog()
        this.bean = bean ?: SendSmsKdBean()
    }

    override fun onError(bean: BaseDataBean) {
        closeProcessDialog()
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(sms_code, bean.message)
        } else {
            ToastUtils.showShortToast("${bean.message}(${bean.code})")
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
        DongdaApplication.removeActivity(this)
        super.onBackPressed()
    }
}
