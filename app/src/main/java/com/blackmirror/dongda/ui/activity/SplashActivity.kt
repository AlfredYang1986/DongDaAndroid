package com.blackmirror.dongda.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import cn.sharesdk.framework.ShareSDK
import cn.sharesdk.wechat.friends.Wechat
import com.blackmirror.dongda.R
import com.blackmirror.dongda.di.component.DaggerSplashComponent
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.kdomain.model.UpLoadWeChatIconDomainBean
import com.blackmirror.dongda.kdomain.model.WeChatLoginBean
import com.blackmirror.dongda.presenter.WeChatLoginPresenter
import com.blackmirror.dongda.ui.WeChatLoginContract
import com.blackmirror.dongda.ui.activity.homeActivity.AYHomeActivity
import com.blackmirror.dongda.ui.activity.landing.LandingActivity
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity(), WeChatLoginContract.View {

    private var disposable: Disposable? = null
    private var presenter: WeChatLoginPresenter? = null
    private var iv_icon: ImageView? = null

    override val layoutResId: Int
        get() = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
    }

    override fun initInject() {
        presenter = DaggerSplashComponent.builder()
                .activity(this)
                .view(this)
                .build()
                .weChatLoginPresenter
    }

    override fun initView() {
        iv_icon = findViewById(R.id.iv_icon)
    }

    override fun initData() {
        requestPermissions()
    }

    override fun initListener() {

    }

    /**
     * 动态申请权限
     */
    private fun requestPermissions() {

        val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE)

        val list = PermissionUtils.checkPermissionWithNoGranted(this@SplashActivity,
                permissions)

        if (list.size != 0) {
            PermissionUtils.requestMulitPermissions(this@SplashActivity, list)
        } else {
            init2Landing()
        }
    }

    private fun init2Landing() {
        val weChat = ShareSDK.getPlatform(Wechat.NAME)
        if (TextUtils.isEmpty(AYPrefUtils.getAuthToken())) {
            disposable = Observable.timer(1500, TimeUnit.MILLISECONDS, Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        unSubscribe()
                        //                        startActivityForResult(new Intent(SplashActivity.this, LandingActivity.class));
                        startActivity(Intent(this@SplashActivity, LandingActivity::class.java))
                        finish()
                    }
        } else {
            if (weChat.isAuthValid) {
                showProcessDialog()
                val userId = weChat.db.userId//获取用户账号
                val userName = weChat.db.userName//获取用户名字
                val userIcon = weChat.db.userIcon//获取用户头像
                val userGender = weChat.db.userGender //获取用户性别，m = 男, f = 女，如果微信没有设置性别,默认返回null
                val token = weChat.db.token

                presenter?.weChatLogin(userId, token, userName, "wechat", userIcon)
            }
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            AppConstant.PERMISSION_REQUEST -> {
                for (i in grantResults.indices) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        LogUtils.d("xcx", permissions[i] + " granted")
                    } else {
                        LogUtils.d("xcx", permissions[i] + " denied")

                    }
                }
                startActivity(Intent(this@SplashActivity, LandingActivity::class.java))
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unSubscribe()
    }

    private fun unSubscribe() {
        if (disposable != null && !disposable!!.isDisposed) {
            disposable!!.dispose()
            disposable = null
        }
    }


    override fun weChatLoginSuccess(bean: WeChatLoginBean) {
        closeProcessDialog()

        val intent = Intent(this@SplashActivity, AYHomeActivity::class.java)
        intent.putExtra("img_uuid", bean.screen_photo)
        startActivity(intent)
        finish()
    }

    override fun onUpLoadWeChatIconSuccess(bean: UpLoadWeChatIconDomainBean) {

    }

    override fun onError(bean: BaseDataBean) {
        closeProcessDialog()
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(iv_icon, bean.message)
        } else {
            ToastUtils.showShortToast(bean.message + "(" + bean.code + ")")
        }
    }
}
