package com.blackmirror.dongda.ui.base

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.blackmirror.dongda.R
import com.blackmirror.dongda.utils.DeviceUtils

/**
 * Created by Ruge on 2018-05-04 下午6:41
 */
abstract class BaseActivity : AppCompatActivity() {

    protected var pb: ProgressDialog? = null
    protected var isViewValid: Boolean = false

    protected abstract val layoutResId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
        init(savedInstanceState)
        isViewValid = true
        setStatusBarColor()
        initInject()
        initView()
        initData()
        initListener()
    }

    protected open fun init(savedInstanceState: Bundle?) {
        init()
    }

    protected open fun init() {

    }

    protected fun initPreSetContentView() {

    }

    protected open fun setStatusBarColor() {
        DeviceUtils.setStatusBarColor(this)
    }

    protected abstract fun initInject()
    protected abstract fun initView()
    protected abstract fun initData()
    protected abstract fun initListener()

    override fun onDestroy() {
        closeProcessDialog()
        pb = null
        isViewValid = false
        super.onDestroy()
    }

    protected fun showProcessDialog(title: String = getString(R.string.dlg_tips), message: String = getString(R.string.dlg_processing), cancelable: Boolean = false) {
        if (!isViewValid) {
            return
        }
        if (pb == null) {
            pb = ProgressDialog(this)
        }
        pb?.setCanceledOnTouchOutside(cancelable)//设置在点击Dialog外是否取消Dialog进度条
        pb?.setProgressStyle(ProgressDialog.STYLE_SPINNER)// 设置进度条的形式为圆形转动的进度条
        pb?.setCancelable(true)// 设置是否可以通过点击Back键取消
        //        pb.setCanceledOnTouchOutside(false);//
        pb?.setTitle(title)
        pb?.setMessage(message)
        pb?.onBackPressed()
        pb?.show()
    }

    protected fun closeProcessDialog() {

        pb?.isShowing.let {
            pb?.dismiss()
            pb = null
        }
    }

}
