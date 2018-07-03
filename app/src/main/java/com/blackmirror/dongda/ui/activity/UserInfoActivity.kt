package com.blackmirror.dongda.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.base.AYApplication
import com.blackmirror.dongda.di.component.DaggerUserInfoComponent
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.kdomain.model.UserInfoDomainBean
import com.blackmirror.dongda.presenter.UserInfoPresenter
import com.blackmirror.dongda.ui.activity.apply.ApplyActivity
import com.blackmirror.dongda.ui.activity.enrol.ChooseEnrolLocActivity
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.*
import com.facebook.drawee.view.SimpleDraweeView

class UserInfoActivity : BaseActivity(), View.OnClickListener, UserInfoContract.View {

    //基础不变控件
    private lateinit var iv_home_head_back: ImageView
    private lateinit var sv_user_photo: SimpleDraweeView
    private lateinit var tv_user_name: TextView

    //申请成为服务者
    private lateinit var cl_apply_service: ConstraintLayout
    private lateinit var tv_join_service: TextView
    private lateinit var tv_apply_setting: TextView

    //发布招生
    private lateinit var cl_enrol_class: ConstraintLayout
    private lateinit var tv_enrol_class: TextView
    private lateinit var tv_my_brand: TextView
    private lateinit var tv_enrol_setting: TextView

    //切换为服务模式
    private lateinit var cl_change_service: ConstraintLayout
    private lateinit var tv_change_to_service: TextView
    private lateinit var tv_change_service_setting: TextView

    private var presenter: UserInfoPresenter? = null
    private var needsRefresh: Boolean = false
    private var img_url: String? = null

    override val layoutResId: Int
        get() = R.layout.activity_user_info

    override fun initInject() {
        presenter = DaggerUserInfoComponent.builder()
                .activity(this)
                .build()
                .userInfoPresenter
    }

    override fun initView() {
        //基础不变控件
        iv_home_head_back = findViewById(R.id.iv_home_head_back)
        sv_user_photo = findViewById(R.id.sv_user_photo)
        tv_user_name = findViewById(R.id.tv_user_name)

        //申请成为服务者
        cl_apply_service = findViewById(R.id.cl_apply_service)
        tv_join_service = findViewById(R.id.tv_join_service)
        tv_apply_setting = findViewById(R.id.tv_apply_setting)

        //发布招生
        cl_enrol_class = findViewById(R.id.cl_enrol_class)
        tv_enrol_class = findViewById(R.id.tv_enrol_class)
        tv_my_brand = findViewById(R.id.tv_my_brand)
        tv_enrol_setting = findViewById(R.id.tv_enrol_setting)

        //切换为服务模式
        cl_change_service = findViewById(R.id.cl_change_service)
        tv_change_to_service = findViewById(R.id.tv_change_to_service)
        tv_change_service_setting = findViewById(R.id.tv_change_service_setting)
    }

    override fun initData() {
        showProcessDialog()
        presenter?.userView = this
        presenter?.queryUserInfo()
    }

    override fun initListener() {
        iv_home_head_back.setOnClickListener(this)
        sv_user_photo.setOnClickListener(this)

        tv_join_service.setOnClickListener(this)
        tv_apply_setting.setOnClickListener(this)

        tv_enrol_class.setOnClickListener(this)
        tv_my_brand.setOnClickListener(this)
        tv_enrol_setting.setOnClickListener(this)

        tv_change_to_service.setOnClickListener(this)
        tv_change_service_setting.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val i = Intent(this@UserInfoActivity, SettingActivity::class.java)
        when (v.id) {
            R.id.iv_home_head_back -> {
                setResult(if (needsRefresh) Activity.RESULT_OK else Activity.RESULT_CANCELED, intent.putExtra("img_url", img_url))
                AYApplication.removeActivity(this)
                finish()
            }
            R.id.sv_user_photo -> {
                AYApplication.addActivity(this)
                startActivityForResult(Intent(this, UserAboutMeActivity::class.java), AppConstant.EDIT_USER_INFO_CODE)
            }
        //申请成为服务者
            R.id.tv_join_service -> startActivity(Intent(this@UserInfoActivity, ApplyActivity::class.java))
            R.id.tv_apply_setting -> {
                i.putExtra("flag", 0)//不是服务者
                startActivity(i)
                AYApplication.addActivity(this)
            }
        //发布招生
            R.id.tv_enrol_class -> {
                val intent = Intent(this, ChooseEnrolLocActivity::class.java)
                intent.putExtra("brand_id", "")
                startActivity(intent)
            }
            R.id.tv_my_brand -> startActivity(Intent(this, MyBrandActivity::class.java))
            R.id.tv_enrol_setting -> {
                i.putExtra("flag", 1)//服务者 需要显示切换预定模式
                startActivityForResult(i, AppConstant.SHOW_ORDER_CODE)
                AYApplication.addActivity(this)
            }
        //切换为服务模式
            R.id.tv_change_to_service -> {
                AYPrefUtils.setSettingFlag("3")//3招生 2 预定模式
                cl_apply_service.visibility = View.GONE
                cl_enrol_class.visibility = View.VISIBLE
                cl_change_service.visibility = View.GONE
            }
            R.id.tv_change_service_setting -> {
                i.putExtra("flag", 3)//不是服务者
                startActivity(i)
                AYApplication.addActivity(this)
            }
        }
    }

    override fun onQueryUserInfoSuccess(bean: UserInfoDomainBean) {
        closeProcessDialog()
        tv_user_name.text = bean.screen_name
        sv_user_photo.setImageURI(OSSUtils.getSignedUrl(bean.screen_photo))
        if (bean.is_service_provider == 0) {
            cl_apply_service.visibility = View.VISIBLE
            cl_enrol_class.visibility = View.GONE
            cl_change_service.visibility = View.GONE
        } else if (bean.is_service_provider == 1) {
            if (AYPrefUtils.getSettingFlag() == "3") {
                cl_apply_service.visibility = View.GONE
                cl_enrol_class.visibility = View.VISIBLE
                cl_change_service.visibility = View.GONE
            } else if (AYPrefUtils.getSettingFlag() == "2") {
                cl_apply_service.visibility = View.GONE
                cl_enrol_class.visibility = View.GONE
                cl_change_service.visibility = View.VISIBLE
            }
        }
    }

    override fun onGetDataError(bean: BaseDataBean) {
        closeProcessDialog()
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(sv_user_photo, bean.message)
        } else {
            ToastUtils.showShortToast("${bean.message}(${bean.code})")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        handleResult(requestCode, resultCode, data)
    }

    private fun handleResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            AppConstant.SHOW_ORDER_CODE -> {
                AYApplication.removeActivity(this)
                finish()
            }
            AppConstant.EDIT_USER_INFO_CODE -> if (resultCode == Activity.RESULT_OK) {
                needsRefresh = true

                img_url = data.getStringExtra("img_url")
                LogUtils.d("img_url userinfo " + data.getStringExtra("img_url"))
                sv_user_photo.setImageURI(OSSUtils.getSignedUrl(img_url))
            } else {
                needsRefresh = false
            }
        }
    }

    override fun onBackPressed() {
        setResult(if (needsRefresh) Activity.RESULT_OK else Activity.RESULT_CANCELED, intent.putExtra("img_url", img_url))
        AYApplication.removeActivity(this)
        super.onBackPressed()
    }

    override fun setStatusBarColor() {
        DeviceUtils.setStatusBarColor(this, Color.parseColor("#FFF7F9FA"))
    }
}
