package com.blackmirror.dongda.ui.activity.landing

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.base.AYApplication
import com.blackmirror.dongda.di.component.DaggerPhotoChangeComponent
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.kdomain.model.UpdateUserInfoBean
import com.blackmirror.dongda.kdomain.model.UpdateUserInfoDomainBean
import com.blackmirror.dongda.presenter.UserInfoPresenter
import com.blackmirror.dongda.ui.Contract
import com.blackmirror.dongda.ui.activity.homeActivity.AYHomeActivity
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.*
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class PhotoChangeActivity : BaseActivity(), View.OnClickListener, Contract.NameInputView {

    private lateinit var iv_head_photo: ImageView
    private lateinit var btn_enter_album: Button
    private lateinit var btn_open_camera: Button
    private lateinit var tv_screen_name: TextView
    private lateinit var btn_enter_home: Button
    private lateinit var btn_enter_cancel: Button
    private var isFromNameInput: Boolean = false
    private var isChangeScreenPhoto: Boolean? = false
    private var imagePath: String? = null//打开相册选择照片的路径
    private var outputUri: Uri? = null//裁剪万照片保存地址
    private var imageUri: Uri? = null//相机拍照图片保存地址
    private var name: String? = null
    private var bitmap: Bitmap? = null
    private var presenter: UserInfoPresenter? = null

    override val layoutResId: Int
        get() = R.layout.activity_photo_change


    override fun init() {
        AYApplication.addActivity(this)
        name = intent.getStringExtra("name")
        isFromNameInput = intent.getIntExtra("from", AppConstant.FROM_PHONE_INPUT) == AppConstant.FROM_NAME_INPUT
    }

    override fun initInject() {
        presenter = DaggerPhotoChangeComponent.builder()
                .activity(this)
                .build()
                .userInfoPresenter
        presenter?.nameInputView = this
    }

    override fun initView() {
        btn_enter_home = findViewById(R.id.btn_enter_home)
        iv_head_photo = findViewById(R.id.iv_head_photo)
        btn_enter_album = findViewById(R.id.btn_enter_album)
        btn_open_camera = findViewById(R.id.btn_open_camera)
        btn_enter_cancel = findViewById(R.id.btn_enter_cancel)
        tv_screen_name = findViewById(R.id.tv_screen_name)
    }

    override fun initData() {
        tv_screen_name.text = name
    }

    override fun initListener() {
        btn_enter_home.setOnClickListener(this)
        btn_enter_album.setOnClickListener(this)
        btn_open_camera.setOnClickListener(this)
        btn_enter_cancel.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_enter_album, R.id.iv_head_photo -> getPicFromAlbum()
            R.id.btn_open_camera -> checkCameraPermission()
            R.id.btn_enter_home -> if (isChangeScreenPhoto!!) {
                showProcessDialog(getString(R.string.uploading_head_photo))
                val bean = UpdateUserInfoDomainBean()
                val json: String
                val img_uuid = CalUtils.getUUID32()
                if (isFromNameInput) {
                    json = "{\"token\":\"" + AYPrefUtils.getAuthToken() + "\",\"condition\":{\"user_id\":\"" + AYPrefUtils.getUserId() + "\"},\"profile\":{\"screen_name\":\"" + name + "\",\"screen_photo\":\"" + img_uuid + "\"}}"
                } else {
                    json = "{\"token\":\"" + AYPrefUtils.getAuthToken() + "\",\"condition\":{\"user_id\":\"" + AYPrefUtils.getUserId() + "\"},\"profile\":{\"screen_photo\":\"" + img_uuid + "\"}}"
                }
                bean.json = json
                bean.imgUUID = img_uuid
                presenter!!.updateUserInfo(bean)
            } else {
                ToastUtils.showShortToast(getString(R.string.choose_head_photo))
            }
            R.id.btn_enter_cancel -> {
                AYApplication.removeActivity(this)
                finish()
            }
        }
    }

    override fun onUpdateUserInfoSuccess(bean: UpdateUserInfoBean) {
        closeProcessDialog()
        ToastUtils.showShortToast(R.string.update_user_info_success)
        val intent = Intent(this@PhotoChangeActivity, AYHomeActivity::class.java)
        intent.putExtra("img_uuid", bean.screen_photo)
        startActivity(intent)
        AYApplication.finishAllActivity()
    }

    override fun onError(bean: BaseDataBean) {
        closeProcessDialog()
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(iv_head_photo, bean.message)
        } else {
            ToastUtils.showShortToast(bean.message + "(" + bean.code + ")")
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
        if (ContextCompat.checkSelfPermission(this@PhotoChangeActivity, Manifest.permission
                        .CAMERA) == PackageManager.PERMISSION_GRANTED) {
            getPicFromCamera()

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@PhotoChangeActivity,
                            Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this@PhotoChangeActivity, arrayOf(Manifest
                        .permission.CAMERA), AppConstant.PERMISSION_REQUEST)

            } else {
                //提示用户打开设置授权
                showGoSettingDialog()
            }
        }

    }

    private fun showGoSettingDialog() {

        val dialog = AlertDialog.Builder(this@PhotoChangeActivity)
                .setCancelable(false)
                .setTitle(R.string.permission_denied)
                .setMessage(R.string.open_camera_permission_setting)
                .setPositiveButton(getString(R.string.go_permission_setting)) { dialog, which ->
                    dialog.dismiss()
                    DeviceUtils.gotoPermissionSetting(this@PhotoChangeActivity)
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

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {//7.0 以下
            imageUri = Uri.fromFile(outputImage)
        } else {
            //Android 7.0系统开始 使用本地真实的Uri路径不安全,使用FileProvider封装共享Uri
            imageUri = FileProvider.getUriForFile(this@PhotoChangeActivity, "com.blackmirror" + ".dongda.fileprovider", outputImage)
        }
        // 启动相机程序
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, AppConstant.TAKE_PHOTO)
    }

    /**
     * 从相册获取图片
     */
    private fun getPicFromAlbum() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"//相片类型
        startActivityForResult(intent, AppConstant.CHOOSE_PIC)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
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
                AppConstant.PICTURE_CUT -> try {
                    bitmap = BitmapFactory.decodeFile(externalCacheDir!!.toString() + "/crop_image.jpg")
                    /*bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream
                                (outputUri));*/
                    if (bitmap != null) {
                        isChangeScreenPhoto = true
                        LogUtils.d("xcx", "压缩前图片的大小" + bitmap!!.byteCount / 1024
                                + "k宽度为" + bitmap!!.width + "高度为" + bitmap!!.height)
                        //                        scaleBitmap(bitmap,outputUri);
                        iv_head_photo!!.setImageBitmap(bitmap)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                AppConstant.TAKE_PHOTO -> cropPhoto(imageUri)//裁剪图片
            }
        } else {
            //            ToastUtils.showShortToast("设置图片出错!");
        }
    }

    @Throws(FileNotFoundException::class)
    private fun scaleBitmap(bitmap: Bitmap, outputUri: Uri) {

        val options2 = BitmapFactory.Options()
        options2.inPreferredConfig = Bitmap.Config.RGB_565
        var bm1: Bitmap? = null
        var bm2: Bitmap? = null

        val matrix = Matrix()
        matrix.setScale(0.5f, 0.5f)
        bm2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width,
                bitmap.height, matrix, true)

        LogUtils.d("xcx", "Matrix 压缩后图片的大小" + bm2!!.byteCount / 1024
                + "k 宽度为 " + bm2.width + ",高度为 " + bm2.height)


        bm1 = Bitmap.createBitmap(bm2.width, bm2.height, Bitmap.Config.RGB_565)


        LogUtils.d("xcx", "rgb 565 压缩后图片的大小" + bm1!!.byteCount / 1024
                + "k 宽度为 " + bm1.width + ",高度为 " + bm1.height)


        // First decode with inJustDecodeBounds=true to check dimensions
        val options = BitmapFactory.Options()
        var bm3: Bitmap? = null
        options.inJustDecodeBounds = true
        BitmapFactory.decodeStream(contentResolver.openInputStream(outputUri), null, options)

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 300, 300)

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false

        bm3 = BitmapFactory.decodeStream(contentResolver.openInputStream(outputUri), null, options)

        LogUtils.d("xcx", "inSampleSize 压缩后图片的大小" + bm3!!.byteCount / 1024
                + "k 宽度为 " + bm3.width + ",高度为 " + bm3.height)

        iv_head_photo!!.setImageBitmap(bm3)


    }

    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight = height / 2
            val halfWidth = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        LogUtils.d("inSampleSize $inSampleSize")
        return inSampleSize
    }

    // 4.4及以上系统使用这个方法处理图片 相册图片返回的不再是真实的Uri,而是分装过的Uri
    @TargetApi(19)
    private fun handleImageOnKitKat(data: Intent) {
        imagePath = null
        val uri = data.data
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

    private fun handleImageBeforeKitKat(data: Intent) {
        val uri = data.data
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

    override fun setStatusBarColor() {
        DeviceUtils.setStatusBarColor(this, resources.getColor(R.color.colorPrimary))
    }

    override fun onBackPressed() {
        AYApplication.removeActivity(this)
        super.onBackPressed()
    }

}
