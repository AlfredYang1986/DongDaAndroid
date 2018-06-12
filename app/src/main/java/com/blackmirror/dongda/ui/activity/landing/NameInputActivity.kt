package com.blackmirror.dongda.ui.activity.landing

import android.content.Intent
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.blackmirror.dongda.R
import com.blackmirror.dongda.base.AYApplication
import com.blackmirror.dongda.di.component.DaggerNameInputComponent
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.kdomain.model.UpdateUserInfoBean
import com.blackmirror.dongda.kdomain.model.UpdateUserInfoDomainBean
import com.blackmirror.dongda.presenter.UserInfoPresenter
import com.blackmirror.dongda.ui.Contract
import com.blackmirror.dongda.ui.activity.homeActivity.AYHomeActivity
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.*

class NameInputActivity : BaseActivity(), Contract.NameInputView {
    private lateinit var btn_next: Button
    private lateinit var et_input_name: EditText
    private var has_photo: Boolean = false
    private var name: String? = null
    private var presenter: UserInfoPresenter? = null

    override val layoutResId: Int
        get() = R.layout.activity_name_input

    override fun init() {
        AYApplication.addActivity(this)
        has_photo = intent.getBooleanExtra("has_photo", true)
    }

    override fun initInject() {
        presenter = DaggerNameInputComponent.builder()
                .activity(this)
                .build()
                .userInfoPresenter
        presenter?.nameInputView = this
    }

    override fun initView() {
        btn_next = findViewById(R.id.btn_next)
        et_input_name = findViewById(R.id.et_input_name)
    }

    override fun initData() {

    }

    override fun initListener() {
        btn_next.setOnClickListener{
            name = et_input_name.text.toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(name)) {
                ToastUtils.showShortToast(getString(R.string.input_name_error))
                return@setOnClickListener
            }
            if (has_photo) {
                changeName()
            } else {
                AYApplication.addActivity(this@NameInputActivity)
                enterPhotoChangeActivity()
            }
        }
    }

    private fun changeName() {
        showProcessDialog()
        val bean = UpdateUserInfoDomainBean()
        bean.json = "{\"token\":\"" + AYPrefUtils.getAuthToken() + "\",\"condition\":{\"user_id\":\"" + AYPrefUtils.getUserId() + "\"},\"profile\":{\"screen_name\":\"" + name + "\"}}"
        presenter?.updateUserInfo(bean)
    }

    private fun enterPhotoChangeActivity() {

        val intent = Intent(this@NameInputActivity, PhotoChangeActivity::class.java)
        intent.putExtra("from", AppConstant.FROM_NAME_INPUT)
        intent.putExtra("name", name)
        startActivity(intent)
    }

    override fun onBackPressed() {
        AYApplication.removeActivity(this)
        super.onBackPressed()
    }

    override fun onUpdateUserInfoSuccess(bean: UpdateUserInfoBean) {
        closeProcessDialog()
        ToastUtils.showShortToast(getString(R.string.update_user_info_success))
        val intent = Intent(this@NameInputActivity, AYHomeActivity::class.java)
        intent.putExtra("img_uuid", bean.screen_photo)
        startActivity(intent)
        AYApplication.finishAllActivity()
    }

    override fun onError(bean: BaseDataBean) {
        closeProcessDialog()
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(btn_next, bean.message)
        } else {
            ToastUtils.showShortToast("${bean.message}(${bean.code})")
        }
    }

    override fun setStatusBarColor() {
        DeviceUtils.setStatusBarColor(this, resources.getColor(R.color.colorPrimary))
    }

    companion object {

        internal val TAG = "Name Input Activity"
    }
}
