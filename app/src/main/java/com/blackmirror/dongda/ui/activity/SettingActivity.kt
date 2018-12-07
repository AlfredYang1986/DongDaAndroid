package com.blackmirror.dongda.ui.activity

import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.view.View
import cn.sharesdk.framework.ShareSDK
import cn.sharesdk.wechat.friends.Wechat
import com.blackmirror.dongda.R
import com.blackmirror.dongda.base.AYApplication
import com.blackmirror.dongda.ui.activity.landing.LandingActivity
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.*
import com.mob.MobSDK
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SettingActivity : BaseActivity(), View.OnClickListener {



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

    }

    override fun initData() {

    }

    override fun initListener() {

    }

    override fun onClick(v: View) {

    }

    /*private fun orderMode() {

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

    }*/

    private fun clearAllCache() {
        showProcessDialog()
        disposable = Observable.timer(500, TimeUnit.MILLISECONDS, Schedulers.io())
                .map{
                        deleteDir(AYApplication.appContext.externalCacheDir)
                         it
                    }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer {
                    if (!isViewValid) {
                        return@Consumer
                    }
                    closeProcessDialog()
                    showToast("清除成功!")
                    val path = AYApplication.appContext.externalCacheDir!!.path
                    val size = getFileOrFilesSize(path)
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
                    setUserId("")
                    setAuthToken("")
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
