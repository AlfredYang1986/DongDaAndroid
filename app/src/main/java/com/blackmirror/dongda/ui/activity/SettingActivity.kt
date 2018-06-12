package com.blackmirror.dongda.ui.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.support.constraint.ConstraintLayout
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import cn.sharesdk.framework.ShareSDK
import cn.sharesdk.wechat.friends.Wechat
import com.blackmirror.dongda.R
import com.blackmirror.dongda.base.AYApplication
import com.blackmirror.dongda.ui.activity.landing.LandingActivity
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.AYPrefUtils
import com.blackmirror.dongda.utils.FileUtils
import com.blackmirror.dongda.utils.ToastUtils
import com.mob.MobSDK
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SettingActivity : BaseActivity(), View.OnClickListener {

    private lateinit var iv_back: ImageView
    private lateinit var tv_change_order_mode: TextView
    private lateinit var tv_clear_cache: TextView
    private lateinit var tv_cache_size: TextView
    private lateinit var tv_logout: TextView
    private lateinit var iv_anim: ImageView
    private lateinit var cl_content: ConstraintLayout

    private var flag: Int = 0
    private var disposable: Disposable? = null
    private var dialog: AlertDialog? = null
    private var animationDrawable: AnimationDrawable? = null
    private var animDisposable: Disposable? = null

    override val layoutResId: Int
        get() = R.layout.activity_setting

    override fun initInject() {

    }

    override fun initView() {
        iv_back = findViewById(R.id.iv_back)
        tv_change_order_mode = findViewById(R.id.tv_change_order_mode)
        tv_clear_cache = findViewById(R.id.tv_clear_cache)
        tv_cache_size = findViewById(R.id.tv_cache_size)
        tv_logout = findViewById(R.id.tv_logout)
        iv_anim = findViewById(R.id.iv_anim)
        cl_content = findViewById(R.id.cl_content)
    }

    override fun initData() {
        val path = AYApplication.getAppContext().externalCacheDir!!.absolutePath
        val size = FileUtils.getFileOrFilesSize(path)
        flag = intent.getIntExtra("flag", 0)
        if (flag == 0 || flag == 3) {//不是服务提供者 或者服务者已切换到预定模式
            tv_change_order_mode.visibility = View.GONE
        } else if (flag == 1) {
            tv_change_order_mode.visibility = View.VISIBLE
        }
        tv_cache_size!!.text = size.toString() + "MB"
    }

    override fun initListener() {
        iv_back.setOnClickListener(this)
        tv_change_order_mode.setOnClickListener(this)
        tv_clear_cache.setOnClickListener(this)
        tv_logout.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> finish()
            R.id.tv_change_order_mode -> orderMode()
            R.id.tv_clear_cache -> clearAllCache()
            R.id.tv_logout -> showLogOutDialog()
        }
    }

    private fun orderMode() {

        // 通过逐帧动画的资源文件获得AnimationDrawable示例
        animationDrawable = resources.getDrawable(
                R.drawable.frame_anim) as AnimationDrawable
        animationDrawable!!.start()
        // 通过逐帧动画的资源文件获得AnimationDrawable示例
        animationDrawable = resources.getDrawable(
                R.drawable.frame_anim) as AnimationDrawable
        iv_anim.background = animationDrawable
        animationDrawable!!.start()
        iv_anim.visibility = View.VISIBLE
        cl_content.visibility = View.GONE
        animDisposable = Observable.timer(1000, TimeUnit.MILLISECONDS, Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (animDisposable != null && !animDisposable!!.isDisposed) {
                        animDisposable!!.dispose()
                    }
                    if (animationDrawable != null && animationDrawable!!.isRunning) {
                        animationDrawable!!.stop()
                        iv_anim.clearAnimation()
                        iv_anim.visibility = View.GONE
                        AYPrefUtils.setSettingFlag("2")
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                }

    }

    private fun clearAllCache() {
        showProcessDialog()
        disposable = Observable.timer(500, TimeUnit.MILLISECONDS, Schedulers.io())
                .map{
                        FileUtils.deleteDir(AYApplication.getAppContext().externalCacheDir)
                         it
                    }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer {
                    if (!isViewValid) {
                        return@Consumer
                    }
                    closeProcessDialog()
                    ToastUtils.showShortToast("清除成功!")
                    val path = AYApplication.getAppContext().externalCacheDir!!.path
                    val size = FileUtils.getFileOrFilesSize(path)
                    tv_cache_size.text = "${size.toString()}MB"
                })

    }

    override fun onDestroy() {
        super.onDestroy()
        if (disposable != null && !disposable!!.isDisposed) {
            disposable!!.dispose()
            disposable = null
        }
        if (animDisposable != null && !animDisposable!!.isDisposed) {
            animDisposable!!.dispose()
            animDisposable = null
        }
        closeDialog()
    }

    private fun showLogOutDialog() {
        dialog = AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(R.string.dlg_tips)
                .setMessage(R.string.confirm_logout)
                .setPositiveButton(getString(R.string.dlg_confirm)) { dialog, which ->
                    closeDialog()
                    val weChat = ShareSDK.getPlatform(Wechat.NAME)
                    weChat.db.removeAccount()
                    weChat.removeAccount(true)
                    ShareSDK.deleteCache()
                    MobSDK.clearUser()
                    AYPrefUtils.setUserId("")
                    AYPrefUtils.setAuthToken("")
                    val intent = Intent(this@SettingActivity, LandingActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                    AYApplication.finishAllActivity()
                }
                .setNegativeButton(getString(R.string.dlg_cancel)) { dialog, which -> closeDialog() }.create()
        dialog?.show()
    }

    private fun closeDialog() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
            dialog = null
        }
    }
}
