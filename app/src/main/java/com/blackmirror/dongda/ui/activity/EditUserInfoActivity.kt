package com.blackmirror.dongda.ui.activity

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.TextInputEditText
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.base.AYApplication
import com.blackmirror.dongda.di.component.DaggerEditUserInfoComponent
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.kdomain.model.UpdateUserInfoBean
import com.blackmirror.dongda.kdomain.model.UpdateUserInfoDomainBean
import com.blackmirror.dongda.presenter.UserInfoPresenter
import com.blackmirror.dongda.ui.Contract
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.*
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import java.io.File
import java.io.IOException

class EditUserInfoActivity : BaseActivity(), View.OnClickListener, Contract.NameInputView {

    private lateinit var ctl_edit_root: CoordinatorLayout
    private lateinit var iv_edit_user_back: ImageView
    private lateinit var tv_save_user_info: TextView
    private lateinit var iv_take_photo: ImageView
    private lateinit var sv_user_photo: SimpleDraweeView
    private lateinit var tv_user_about_me: TextView
    private lateinit var tl_service_name: FrameLayout
    private lateinit var tl_service_dec: FrameLayout
    private lateinit var tet_user_name: TextInputEditText
    private lateinit var tet_user_dec: TextInputEditText

    private var input_name: String? = null
    private var input_dec: String? = null
    private val scaleUri: Uri? = null
    private var needsRefresh: Boolean = false
    private var img_url: String? = null
    private var presenter: UserInfoPresenter? = null
    private var imageUri: Uri? = null
    private var outputUri: Uri? = null
    private var bitmap: Bitmap? = null
    private var isChangeScreenPhoto: Boolean = false
    private var imagePath: String? = null
    private var screen_photo: String? = null
    private var screen_name: String? = null
    private var description: String? = null

    override val layoutResId: Int
        get() = R.layout.activity_edit_user_info


    override fun init() {
        AYApplication.addActivity(this)
        screen_photo = intent.getStringExtra("screen_photo")
        screen_name = intent.getStringExtra("screen_name")
        description = intent.getStringExtra("description")
    }

    override fun initInject() {
        presenter = DaggerEditUserInfoComponent.builder()
                .activity(this)
                .build()
                .userInfoPresenter
        presenter?.nameInputView = this
    }

    override fun initView() {
        ctl_edit_root = findViewById(R.id.ctl_edit_root)
        iv_edit_user_back = findViewById(R.id.iv_edit_user_back)
        tv_save_user_info = findViewById(R.id.tv_save_user_info)
        iv_take_photo = findViewById(R.id.iv_take_photo)
        sv_user_photo = findViewById(R.id.sv_user_photo)
        tv_user_about_me = findViewById(R.id.tv_user_about_me)
        tl_service_name = findViewById(R.id.tl_service_name)
        tl_service_dec = findViewById(R.id.tl_service_dec)
        tet_user_name = findViewById(R.id.tet_user_name)
        tet_user_dec = findViewById(R.id.tet_user_dec)
    }

    override fun initData() {
        sv_user_photo.setImageURI(OSSUtils.getSignedUrl(screen_photo))
        tet_user_name.setText(screen_name)
        tet_user_dec.setText(description)
    }

    override fun initListener() {
        iv_edit_user_back.setOnClickListener(this)
        tv_save_user_info.setOnClickListener(this)
        iv_take_photo.setOnClickListener(this)
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.iv_edit_user_back -> {
                setResult(if (needsRefresh) Activity.RESULT_OK else Activity.RESULT_CANCELED, intent.putExtra("img_url", img_url))
                AYApplication.finishActivity(this)
            }
            R.id.tv_save_user_info -> updateUserInfo()
            R.id.iv_take_photo -> checkCameraPermission()
        }
    }

    private fun updateUserInfo() {
        input_name = tet_user_name.text.toString().trim { it <= ' ' }
        input_dec = tet_user_dec.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(input_name)) {
            ToastUtils.showShortToast(getString(R.string.input_nickname_error))
            return
        }
        if (TextUtils.isEmpty(input_dec)) {
            ToastUtils.showShortToast(getString(R.string.input_about_me_error))
            return
        }
        showProcessDialog(getString(R.string.update_user_info_processing))
        val bean = UpdateUserInfoDomainBean()
        val json: String
        if (isChangeScreenPhoto) {
            val img_uuid = getUUID32()

            json = "{\"token\":\"${AYPrefUtils.getAuthToken()}\",\"condition\":{\"user_id\":\"${AYPrefUtils.getUserId()}\"},\"profile\":{\"screen_name\":\"$input_name\",\"screen_photo\":\"$img_uuid\",\"description\":\"$input_dec\"}}"

            bean.json = json
            bean.imgUUID = img_uuid
            presenter?.updateUserInfo(bean)
        } else {
            json = "{\"token\":\"${AYPrefUtils.getAuthToken()}\",\"condition\":{\"user_id\":\"${AYPrefUtils.getUserId()}\"},\"profile\":{\"screen_name\":\"$input_name\",\"description\":\"$input_dec\"}}"
            bean.json = json
            presenter?.updateUserInfo(bean)
        }
    }

    override fun onUpdateUserInfoSuccess(bean: UpdateUserInfoBean) {
        closeProcessDialog()
        needsRefresh = true
        ToastUtils.showShortToast(R.string.update_user_info_success)
        img_url = bean.screen_photo
        setResult(if (needsRefresh) Activity.RESULT_OK else Activity.RESULT_CANCELED, intent.putExtra("img_url", img_url))
        AYApplication.finishActivity(this)

    }

    override fun onError(bean: BaseDataBean) {
        closeProcessDialog()
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(ctl_edit_root, bean.message)
        } else {
            ToastUtils.showShortToast("${bean.message}(${bean.code})")
        }
    }

    private fun checkCameraPermission() {

        //如果是6.0以下的手机，ActivityCompat.checkSelfPermission()会始终等于PERMISSION_GRANTED，
        // 但是，如果用户关闭了你申请的权限，ActivityCompat.checkSelfPermission(),会导致程序崩溃(java.lang
        // .RuntimeException: Unknown exception code: 1 msg null)，
        // 你可以使用try{}catch(){},处理异常，也可以判断系统版本，低于23就不申请权限，直接做你想做的。
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }

        /**
         * shouldShowRequestPermissionRationale()。如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
         * 注：如果用户在过去拒绝了权限请求，并在权限请求系统对话框中选择了 Don't ask again 选项，
         * 此方法将返回 false。如果设备规范禁止应用具有该权限，此方法也会返回 false。
         */
        if (ContextCompat.checkSelfPermission(this@EditUserInfoActivity, Manifest.permission
                        .CAMERA) == PackageManager.PERMISSION_GRANTED) {
            getPicFromCamera()

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@EditUserInfoActivity,
                            Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this@EditUserInfoActivity, arrayOf(Manifest
                        .permission.CAMERA), AppConstant.PERMISSION_REQUEST)

            } else {
                //提示用户打开设置授权
                showGoSettingDialog()
            }
        }

    }

    private fun showGoSettingDialog() {

        val dialog = AlertDialog.Builder(this@EditUserInfoActivity)
                .setCancelable(false)
                .setTitle(R.string.permission_denied)
                .setMessage(R.string.open_camera_permission_setting)
                .setPositiveButton(getString(R.string.go_permission_setting)) { dialog, which ->
                    dialog.dismiss()
                    DeviceUtils.gotoPermissionSetting(this@EditUserInfoActivity)
                }
                .setNegativeButton(getString(R.string.dlg_cancel)) { dialog, which -> dialog.dismiss() }.create()
        dialog.show()

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            AppConstant.PERMISSION_REQUEST -> for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    getPicFromCamera()
                    LogUtils.d("xcx", permissions[i] + " granted")
                } else {
                    LogUtils.d("xcx", permissions[i] + " denied")

                }
            }
        }
    }


    /**
     * 进入相机拍照获取图片
     */
    private fun getPicFromCamera() {
        // 创建File对象，用于存储拍照后的图片
        val outputImage = File(externalCacheDir, "output_image.jpg")
        try {
            if (outputImage.exists()) {
                outputImage.delete()
            }
            outputImage.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            imageUri = Uri.fromFile(outputImage)
        } else {
            //Android 7.0系统开始 使用本地真实的Uri路径不安全,使用FileProvider封装共享Uri
            imageUri = FileProvider.getUriForFile(this@EditUserInfoActivity, "com.blackmirror" + ".dongda.fileprovider", outputImage)
        }
        // 启动相机程序
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, AppConstant.TAKE_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                AppConstant.CHOOSE_PIC//从相册选择图片
                ->
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data)
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data)
                    }
                AppConstant.PICTURE_CUT -> {
                    //                    sv_user_photo.setImageURI(outputUri);
                    displayImage(outputUri, sv_user_photo)
                    try {
                        bitmap = BitmapFactory.decodeFile(externalCacheDir!!.toString() + "/crop_image.jpg")
                        /*bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream
                                (outputUri));*/
                        if (bitmap != null) {
                            isChangeScreenPhoto = true
                            LogUtils.d("xcx", "压缩前图片的大小" + bitmap!!.byteCount / 1024
                                    + "k宽度为" + bitmap!!.width + "高度为" + bitmap!!.height)
                            //                        scaleBitmap(bitmap,outputUri);

                        }
                    } catch (e: Exception) {
                    }

                }
                AppConstant.TAKE_PHOTO -> cropPhoto(imageUri)//裁剪图片
            }
        } else {
            //            ToastUtils.showShortToast("设置图片出错!");
        }
    }

    // 4.4及以上系统使用这个方法处理图片 相册图片返回的不再是真实的Uri,而是分装过的Uri
    @TargetApi(19)
    private fun handleImageOnKitKat(data: Intent?) {
        imagePath = null
        val uri = data?.data
        LogUtils.d("xcx", "handleImageOnKitKat: uri is " + uri!!)
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri.authority) {
                val id = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1] // 解析出数字格式的id
                val selection = MediaStore.Images.Media._ID + "=" + id
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection)
            } else if ("com.android.providers.downloads.documents" == uri.authority) {
                val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(docId))
                imagePath = getImagePath(contentUri, null)
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.path
        }
        cropPhoto(uri)
    }

    private fun handleImageBeforeKitKat(data: Intent?) {
        val uri = data?.data
        imagePath = getImagePath(uri, null)
        cropPhoto(uri)
    }

    /**
     * 裁剪图片
     */
    private fun cropPhoto(uri: Uri?) {
        // 创建File对象，用于存储裁剪后的图片，避免更改原图
        val file = File(externalCacheDir, "crop_image.jpg")
        try {
            if (file.exists()) {
                file.delete()
            }
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        outputUri = Uri.fromFile(file)
        val intent = Intent("com.android.camera.action.CROP")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        intent.setDataAndType(uri, "image/*")
        //裁剪图片的宽高比例
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        intent.putExtra("crop", "true")//可裁剪
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 800)
        intent.putExtra("outputY", 800)
        intent.putExtra("scale", true)//支持缩放
        intent.putExtra("return-data", false)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri)
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())//输出图片格式
        intent.putExtra("noFaceDetection", true)//取消人脸识别
        startActivityForResult(intent, AppConstant.PICTURE_CUT)
    }

    private fun getImagePath(uri: Uri?, selection: String?): String? {
        var path: String? = null
        // 通过Uri和selection来获取真实的图片路径
        val cursor = contentResolver.query(uri!!, null, selection, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path
    }

    fun displayImage(uri: Uri?, draweeView: SimpleDraweeView) {

        Fresco.getImagePipeline().evictFromCache(uri)

        val request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(ResizeOptions(DensityUtils.getScreenWidthPx(), DensityUtils.dp2px(250)))
                .build()

        val controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.controller)
                .build()
        draweeView.controller = controller
    }

    override fun onBackPressed() {
        setResult(if (needsRefresh) Activity.RESULT_OK else Activity.RESULT_CANCELED, intent.putExtra("img_url", img_url))
        AYApplication.removeActivity(this)
        finish()
        super.onBackPressed()
    }

    companion object {

        fun startActivityForResult(activity: AppCompatActivity, screen_photo: String, screen_name: String, description: String, requestCode: Int) {
            val intent = Intent(activity, EditUserInfoActivity::class.java)
            intent.putExtra("screen_photo", screen_photo)
            intent.putExtra("screen_name", screen_name)
            intent.putExtra("description", description)
            activity.startActivityForResult(intent, requestCode)
        }
    }

}
