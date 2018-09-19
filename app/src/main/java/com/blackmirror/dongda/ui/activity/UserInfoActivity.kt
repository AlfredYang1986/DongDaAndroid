package com.blackmirror.dongda.ui.activity

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.support.constraint.ConstraintLayout
import android.support.v4.app.ActivityCompat
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
import com.blackmirror.dongda.ui.activity.live.LiveActivity
import com.blackmirror.dongda.ui.activity.live.LiveListActivity
import com.blackmirror.dongda.ui.activity.live.RecordActivity
import com.blackmirror.dongda.ui.activity.live.VideoListActivity
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.*
import com.facebook.drawee.view.SimpleDraweeView
import com.mabeijianxi.smallvideorecord2.JianXiCamera
import com.mabeijianxi.smallvideorecord2.Log
import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity
import com.mabeijianxi.smallvideorecord2.model.MediaRecorderConfig.Buidler
import java.io.File

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

    private lateinit var tv_start_push: TextView
    private lateinit var tv_record: TextView
    private lateinit var tv_video: TextView
    private lateinit var tv_live: TextView

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

        //直播
        tv_start_push = findViewById(R.id.tv_start_push)
        tv_record = findViewById(R.id.tv_record)
        tv_video = findViewById(R.id.tv_video)
        tv_live = findViewById(R.id.tv_live)
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

        tv_start_push.setOnClickListener(this)
        tv_record.setOnClickListener(this)
        tv_video.setOnClickListener(this)
        tv_live.setOnClickListener(this)
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
            R.id.tv_start_push->{
                checkLivePermissions()
            }
            R.id.tv_record->{
                checkRecordPermissions()
            }
            R.id.tv_video->{
                startActivity(Intent(this,VideoListActivity::class.java))
            }
            R.id.tv_live->{
                startActivity(Intent(this,LiveListActivity::class.java))
            }
        }
    }

    private fun checkRecordPermissions() {
        //如果是6.0以下的手机，ActivityCompat.checkSelfPermission()会始终等于PERMISSION_GRANTED，
        // 但是，如果用户关闭了你申请的权限，ActivityCompat.checkSelfPermission(),会导致程序崩溃(java.lang
        // .RuntimeException: Unknown exception code: 1 msg null)，
        // 你可以使用try{}catch(){},处理异常，也可以判断系统版本，低于23就不申请权限，直接做你想做的。
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            initRecord()
            return
        }

        val p = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
        )
        val needsGrand = PermissionUtils.checkPermissionWithNoGrantedForArray(this, p)

        if (needsGrand.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, needsGrand, AppConstant.PERMISSION_LIVE)
        } else {
            initRecord()
        }
    }

    private fun checkLivePermissions() {
        //如果是6.0以下的手机，ActivityCompat.checkSelfPermission()会始终等于PERMISSION_GRANTED，
        // 但是，如果用户关闭了你申请的权限，ActivityCompat.checkSelfPermission(),会导致程序崩溃(java.lang
        // .RuntimeException: Unknown exception code: 1 msg null)，
        // 你可以使用try{}catch(){},处理异常，也可以判断系统版本，低于23就不申请权限，直接做你想做的。
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            startActivity(Intent(this, LiveActivity::class.java))
            finish()
            return
        }

        val p = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
        )
        val needsGrand = PermissionUtils.checkPermissionWithNoGrantedForArray(this, p)

        if (needsGrand.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, needsGrand, AppConstant.PERMISSION_LIVE)
        } else {
            startActivity(Intent(this,LiveActivity::class.java))
            finish()
        }
    }

    private fun showGoSettingDialog() {

        val dialog = AlertDialog.Builder(this@UserInfoActivity)
                .setCancelable(false)
                .setTitle(R.string.permission_denied)
                .setMessage(R.string.permisson_denied)
                .setPositiveButton(getString(R.string.go_permission_setting)) { dialog, which ->
                    dialog.dismiss()
                    DeviceUtils.gotoPermissionSetting(this@UserInfoActivity)
                }
                .setNegativeButton(getString(R.string.dlg_cancel)) { dialog, which ->
                    dialog.dismiss()
                }.create()
        dialog.show()

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            AppConstant.PERMISSION_LIVE -> {
                var b = false
                for (i in grantResults.indices) {

                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        LogUtils.d("xcx", permissions[i] + " granted")
                        b = true
                    } else {
                        LogUtils.d("xcx", permissions[i] + " denied")
                        b = false
                        break
                    }
                }
                if (b) {
                    startActivity(Intent(this, LiveActivity::class.java))
                    finish()
                } else {
                    showGoSettingDialog()
                }
            }
            AppConstant.PERMISSION_RECORD->{
                initRecord()
            }
        }
    }

    private fun initRecord() {
//        ToastUtils.showShortToast(externalCacheDir.absolutePath)
        Log.d("xcx","externalCacheDir: ${externalCacheDir.absolutePath}")

        val path="${externalCacheDir.absolutePath}/video"

        val f=File(path)
        if (!f.exists()){
            f.mkdirs()
        }
        // 设置拍摄视频缓存路径
        JianXiCamera.setVideoCachePath("$path/")

        // 初始化拍摄
        JianXiCamera.initialize(false, null)

        // 录制
        val config = Buidler()
                .fullScreen(true)
                .smallVideoWidth(0)
                .smallVideoHeight(720)
                .recordTimeMax(6000)
                .recordTimeMin(6000)
                .maxFrameRate(20)
                .videoBitrate(6000000)
                .captureThumbnailsTime(1)
                .build()

        MediaRecorderActivity.goSmallVideoRecorder(this, RecordActivity::class.java.name,config)

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
