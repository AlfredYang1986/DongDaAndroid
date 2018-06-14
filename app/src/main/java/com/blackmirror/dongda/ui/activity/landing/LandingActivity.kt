package com.blackmirror.dongda.ui.activity.landing

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.widget.TextView
import cn.sharesdk.framework.Platform
import cn.sharesdk.framework.PlatformActionListener
import cn.sharesdk.framework.ShareSDK
import cn.sharesdk.wechat.friends.Wechat
import com.blackmirror.dongda.R
import com.blackmirror.dongda.di.component.DaggerLandingComponent
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.kdomain.model.UpLoadWeChatIconDomainBean
import com.blackmirror.dongda.kdomain.model.WeChatLoginBean
import com.blackmirror.dongda.presenter.WeChatLoginPresenter
import com.blackmirror.dongda.ui.WeChatLoginContract
import com.blackmirror.dongda.ui.activity.homeActivity.AYHomeActivity
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import java.util.*

class LandingActivity : BaseActivity(), WeChatLoginContract.View {


    private lateinit var tv_phone_login: TextView
    private lateinit var tv_wechat_login: TextView
    private var errorDb: Disposable? = null
    private var cancelDb: Disposable? = null
    private var presenter: WeChatLoginPresenter? = null
    private var userIcon: String? = null


    override val layoutResId: Int
        get() = R.layout.activity_landing

    override fun init() {
        DongdaApplication.addActivity(this)
    }

    override fun initInject() {
        presenter = DaggerLandingComponent.builder()
                .activity(this)
                .view(this)
                .build()
                .weChatLoginPresenter
    }

    override fun initView() {
        tv_phone_login = findViewById(R.id.tv_phone_login)
        tv_wechat_login = findViewById(R.id.tv_wechat_login)
    }

    override fun initData() {}

    override fun initListener() {

        tv_phone_login.setOnClickListener {
            val intent = Intent(this@LandingActivity, PhoneInputActivity::class.java)
            startActivity(intent)
        }

        tv_wechat_login.setOnClickListener { weChatLogin() }

    }

    private fun weChatLogin() {
        showProcessDialog(message = getString(R.string.logining_process), cancelable = true)
        val wechat = ShareSDK.getPlatform(Wechat.NAME)
        wechat.platformActionListener = MyPlatformActionListener()
        wechat.SSOSetting(false)
        authorize(wechat, 1)

    }

    internal inner class MyPlatformActionListener : PlatformActionListener {

        override fun onComplete(platform: Platform, i: Int, map: HashMap<String, Any>) {
            platform.platformActionListener = null
            val userId = platform.db.userId//获取用户账号
            val userName = platform.db.userName//获取用户名字
            //获取用户头像
            userIcon = platform.db.userIcon
            val userGender = platform.db.userGender //获取用户性别，m = 男, f = 女，如果微信没有设置性别,默认返回null
            val token = platform.db.token

            presenter!!.weChatLogin(userId, token, userName, "wechat", userIcon!!)

        }

        override fun onError(platform: Platform, i: Int, throwable: Throwable) {
            platform.platformActionListener = null
            errorDb = Observable.just<String>(throwable.message)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(Consumer { s ->
                        if (!isViewValid) {
                            return@Consumer
                        }
                        closeProcessDialog()
                        if (!TextUtils.isEmpty(s))
                            ToastUtils.showShortToast(s)
                    })
        }

        override fun onCancel(platform: Platform, i: Int) {
            platform.platformActionListener = null
            cancelDb = Observable.just("")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(Consumer {
                        if (!isViewValid) {
                            return@Consumer
                        }
                        closeProcessDialog()
                        ToastUtils.showShortToast(getString(R.string.wechat_auth_cancel))
                    })
        }
    }


    //授权
    private fun authorize(plat: Platform, type: Int) {
        if (!plat.isClientValid) {
            ToastUtils.showShortToast("您还未安装微信!")
            return
        }
        plat.showUser(null)//授权并获取用户信息
    }

    /**
     * 服务器成功回调
     *
     * @param bean
     */
    override fun weChatLoginSuccess(bean: WeChatLoginBean) {

        val intent = Intent(this@LandingActivity, AYHomeActivity::class.java)
        if (!TextUtils.isEmpty(bean.screen_photo)) {//有头像 直接登录
            closeProcessDialog()
            intent.putExtra("img_uuid", bean.screen_photo)
            startActivity(intent)
            AYPrefUtils.setImgUuid(bean.screen_photo)
            AYPrefUtils.setIsPhoneLogin("weChat")
            DongdaApplication.finishAllActivity()
        } else {
            val img_uuid = CalUtils.getUUID32()
            if (userIcon!!.contains("132")) {
                userIcon = userIcon!!.substring(0, userIcon!!.lastIndexOf("132")) + 0
            }
            presenter!!.upLoadWeChatIcon(userIcon!!, img_uuid)
        }

    }

    override fun onUpLoadWeChatIconSuccess(bean: UpLoadWeChatIconDomainBean) {
        closeProcessDialog()

        val intent = Intent(this@LandingActivity, AYHomeActivity::class.java)
        intent.putExtra("img_uuid", bean.imgUUID)
        startActivity(intent)
        AYPrefUtils.setImgUuid(bean.imgUUID)
        AYPrefUtils.setIsPhoneLogin("weChat")
        DongdaApplication.finishAllActivity()
    }

    /**
     * 服务器失败回调
     *
     * @param bean
     */
    override fun onError(bean: BaseDataBean) {
        closeProcessDialog()
        LogUtils.d("LandingActivity wechat failed ")
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(tv_phone_login, bean.message)
            return
        }
        if (bean.code == AppConstant.UPLOAD_WECHAT_ERROR) {
            val intent = Intent(this@LandingActivity, AYHomeActivity::class.java)
            intent.putExtra("img_uuid", "")
            startActivity(intent)
            DongdaApplication.finishAllActivity()
            return
        }

        ToastUtils.showShortToast(bean.message + "(" + bean.code + ")")

    }

    override fun setStatusBarColor() {
        DeviceUtils.initSystemBarColor(this)
    }

    private fun unSubscribe() {

        if (errorDb != null && !errorDb!!.isDisposed) {
            errorDb!!.dispose()
            errorDb = null
        }

        if (cancelDb != null && !cancelDb!!.isDisposed) {
            cancelDb!!.dispose()
            cancelDb = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.d("landing onDestroy")
        unSubscribe()
    }

    override fun onBackPressed() {
        //杀死Application
        val packName = packageName
        val activityMgr = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityMgr.killBackgroundProcesses(packName)
        android.os.Process.killProcess(android.os.Process.myPid())
        super.onBackPressed()
    }
}
