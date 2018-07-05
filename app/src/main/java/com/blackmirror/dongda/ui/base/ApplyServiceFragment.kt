package com.blackmirror.dongda.ui.base


import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.base.AYApplication
import com.blackmirror.dongda.ui.activity.SettingActivity
import com.blackmirror.dongda.ui.activity.UserAboutMeActivity
import com.blackmirror.dongda.ui.activity.apply.ApplyActivity
import com.blackmirror.dongda.utils.AppConstant
import com.blackmirror.dongda.utils.LogUtils
import com.blackmirror.dongda.utils.OSSUtils
import com.facebook.drawee.view.SimpleDraweeView


/**
 * A simple [Fragment] subclass.
 *
 */
class ApplyServiceFragment : BaseFragment(), View.OnClickListener {

    private var needsRefresh: Boolean = false
    private var img_url: String? = null

    //基础不变控件
    private lateinit var iv_home_head_back: ImageView
    private lateinit var sv_user_photo: SimpleDraweeView
    private lateinit var tv_user_name: TextView

    //申请成为服务者
    private lateinit var tv_join_service: TextView
    private lateinit var tv_apply_setting: TextView

    override val layoutResId: Int
        get() = R.layout.fragment_apply_service

    override fun initView(view: View) {
        //基础不变控件
        iv_home_head_back = view.findViewById(R.id.iv_home_head_back)
        sv_user_photo = view.findViewById(R.id.sv_user_photo)
        tv_user_name = view.findViewById(R.id.tv_user_name)

        //申请成为服务者
        tv_join_service = view.findViewById(R.id.tv_join_service)
        tv_apply_setting = view.findViewById(R.id.tv_apply_setting)
    }

    override fun initInject() {
    }

    override fun initListener() {
        iv_home_head_back.setOnClickListener(this)
        sv_user_photo.setOnClickListener(this)

        tv_join_service.setOnClickListener(this)
        tv_apply_setting.setOnClickListener(this)


    }

    override fun initData() {
    }

    override fun onClick(v: View) {
        val i = Intent(activity, SettingActivity::class.java)
        when (v.id) {
            R.id.iv_home_head_back -> {
                activity.setResult(if (needsRefresh) Activity.RESULT_OK else Activity.RESULT_CANCELED, activity.intent.putExtra("img_url", img_url))
                AYApplication.removeActivity(activity as AppCompatActivity)
                activity.finish()
            }
            R.id.sv_user_photo -> {
                AYApplication.addActivity(activity as AppCompatActivity)
                startActivityForResult(Intent(activity, UserAboutMeActivity::class.java), AppConstant.EDIT_USER_INFO_CODE)
            }
        //申请成为服务者
            R.id.tv_join_service -> startActivity(Intent(activity, ApplyActivity::class.java))
            R.id.tv_apply_setting -> {
                i.putExtra("flag", 0)//不是服务者
                startActivity(i)
                AYApplication.addActivity(activity as AppCompatActivity)
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        handleResult(requestCode, resultCode, data)
    }

    private fun handleResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            AppConstant.SHOW_ORDER_CODE -> {
                AYApplication.removeActivity(activity as AppCompatActivity)
                activity.finish()
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
}
