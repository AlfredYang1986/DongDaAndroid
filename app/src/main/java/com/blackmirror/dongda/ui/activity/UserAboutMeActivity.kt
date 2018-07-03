package com.blackmirror.dongda.ui.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import cn.sharesdk.framework.ShareSDK
import cn.sharesdk.wechat.friends.Wechat
import com.blackmirror.dongda.R
import com.blackmirror.dongda.base.AYApplication
import com.blackmirror.dongda.di.component.DaggerUserAboutMeComponent
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.kdomain.model.UserInfoDomainBean
import com.blackmirror.dongda.presenter.UserInfoPresenter
import com.blackmirror.dongda.ui.activity.landing.LandingActivity
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.*
import com.facebook.drawee.view.SimpleDraweeView
import com.mob.MobSDK

class UserAboutMeActivity : BaseActivity(), View.OnClickListener, UserInfoContract.View {

    private lateinit var iv_service_back: ImageView
    private lateinit var tv_user_logout: TextView
    private lateinit var sv_user_about_photo: SimpleDraweeView
    private lateinit var iv_about_edit: ImageView
    private lateinit var tv_user_about_name: TextView
    private lateinit var tv_user_about_dec: TextView
    private var needsRefresh: Boolean = false
    private var img_url: String? = null
    private var dialog: AlertDialog? = null
    private var presenter: UserInfoPresenter? = null
    private var bean: UserInfoDomainBean? = null

    override val layoutResId: Int
        get() = R.layout.activity_user_about_me

    override fun init() {
        AYApplication.addActivity(this)
    }

    override fun initInject() {
        presenter = DaggerUserAboutMeComponent.builder()
                .activity(this)
                .build()
                .userInfoPresenter
        presenter?.userView = this
    }

    override fun initView() {
        iv_service_back = findViewById(R.id.iv_service_back)
        tv_user_logout = findViewById(R.id.tv_user_logout)
        sv_user_about_photo = findViewById(R.id.sv_user_about_photo)
        iv_about_edit = findViewById(R.id.iv_about_edit)
        tv_user_about_name = findViewById(R.id.tv_user_about_name)
        tv_user_about_dec = findViewById(R.id.tv_user_about_dec)

    }

    override fun initData() {
        getUserInfo()
    }

    override fun initListener() {
        iv_service_back.setOnClickListener(this)
        tv_user_logout.setOnClickListener(this)
        iv_about_edit.setOnClickListener(this)
    }


    private fun getUserInfo() {
        showProcessDialog()
        presenter?.queryUserInfo()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.iv_service_back -> {
                setResult(if (needsRefresh) Activity.RESULT_OK else Activity.RESULT_CANCELED, intent.putExtra("img_url", img_url))
                AYApplication.finishActivity(this)
            }
            R.id.tv_user_logout -> showLogOutDialog()
            R.id.iv_about_edit -> EditUserInfoActivity.startActivityForResult(this@UserAboutMeActivity, bean!!.screen_photo!!, bean!!.screen_name!!, bean!!.description!!, AppConstant.EDIT_USER_REQUEST_CODE)
        }
    }

    override fun onQueryUserInfoSuccess(dbean: UserInfoDomainBean) {
        bean = dbean
        closeProcessDialog()
        val url = OSSUtils.getSignedUrl(bean!!.screen_photo)
        sv_user_about_photo.setImageURI(url)
        tv_user_about_name.text = bean!!.screen_name
        if (TextUtils.isEmpty(bean!!.description)) {
            tv_user_about_dec.setText(R.string.default_about_me)
        } else {
            tv_user_about_dec.text = bean!!.description
        }
    }

    override fun onGetDataError(bean: BaseDataBean) {
        closeProcessDialog()
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(iv_about_edit, bean.message)
        } else {
            ToastUtils.showShortToast("${bean.message}(${bean.code})")
        }
    }

    private fun showLogOutDialog() {
        dialog = AlertDialog.Builder(this@UserAboutMeActivity)
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
                    val intent = Intent(this@UserAboutMeActivity, LandingActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    AYApplication.finishAllActivity()
                }
                .setNegativeButton(getString(R.string.dlg_cancel)) { dialog, which -> closeDialog() }.create()
        dialog?.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            needsRefresh = true
            getUserInfo()
            img_url = data.getStringExtra("img_url")
            LogUtils.d("UserAboutMeActivity img_url " + img_url!!)
        }
    }

    override fun onBackPressed() {
        setResult(if (needsRefresh) Activity.RESULT_OK else Activity.RESULT_CANCELED, intent.putExtra("img_url", img_url))
        AYApplication.removeActivity(this)
        super.onBackPressed()
    }


    override fun onDestroy() {
        super.onDestroy()
        closeDialog()
    }

    private fun closeDialog() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
            dialog = null
        }
    }


    override fun setStatusBarColor() {
        DeviceUtils.initSystemBarColor(this)
    }

}
