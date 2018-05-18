package com.blackmirror.dongda.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.base.AYApplication;
import com.blackmirror.dongda.di.component.DaggerEditUserInfoComponent;
import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.domain.model.UpdateUserInfoBean;
import com.blackmirror.dongda.domain.model.UpdateUserInfoDomainBean;
import com.blackmirror.dongda.presenter.UserInfoPresenter;
import com.blackmirror.dongda.ui.Contract;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.blackmirror.dongda.utils.AYPrefUtils;
import com.blackmirror.dongda.utils.AppConstant;
import com.blackmirror.dongda.utils.CalUtils;
import com.blackmirror.dongda.utils.DensityUtils;
import com.blackmirror.dongda.utils.DeviceUtils;
import com.blackmirror.dongda.utils.LogUtils;
import com.blackmirror.dongda.utils.OSSUtils;
import com.blackmirror.dongda.utils.SnackbarUtils;
import com.blackmirror.dongda.utils.ToastUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.File;
import java.io.IOException;

public class EditUserInfoActivity extends BaseActivity implements View.OnClickListener, Contract.NameInputView {

    private CoordinatorLayout ctl_edit_root;
    private ImageView iv_edit_user_back;
    private TextView tv_save_user_info;
    private ImageView iv_take_photo;
    private SimpleDraweeView sv_user_photo;
    private TextView tv_user_about_name;
    private TextView tv_user_about_me;
    private Uri imageUri;
    private Uri outputUri;
    private Bitmap bitmap;
    private boolean isChangeScreenPhoto;
    private String imagePath;
    private String screen_photo;
    private String screen_name;
    private String description;
    private FrameLayout tl_service_name;
    private FrameLayout tl_service_dec;
    private TextInputEditText tet_user_name;
    private TextInputEditText tet_user_dec;
    private String input_name;
    private String input_dec;
    private Uri scaleUri;
    private boolean needsRefresh;
    private String img_url;
    private UserInfoPresenter presenter;


    @Override
    protected void init() {
        AYApplication.addActivity(this);
        screen_photo = getIntent().getStringExtra("screen_photo");
        screen_name = getIntent().getStringExtra("screen_name");
        description = getIntent().getStringExtra("description");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_edit_user_info;
    }

    @Override
    protected void initInject() {
        presenter = DaggerEditUserInfoComponent.builder()
                .activity(this)
                .build()
                .getUserInfoPresenter();
        presenter.setNameInputView(this);
    }

    protected void initView() {
        ctl_edit_root = findViewById(R.id.ctl_edit_root);
        iv_edit_user_back = findViewById(R.id.iv_edit_user_back);
        tv_save_user_info = findViewById(R.id.tv_save_user_info);
        iv_take_photo = findViewById(R.id.iv_take_photo);
        sv_user_photo = findViewById(R.id.sv_user_photo);
        tv_user_about_name = findViewById(R.id.tv_user_about_name);
        tv_user_about_me = findViewById(R.id.tv_user_about_me);
        tl_service_name = findViewById(R.id.tl_service_name);
        tl_service_dec = findViewById(R.id.tl_service_dec);
        tet_user_name = findViewById(R.id.tet_user_name);
        tet_user_dec = findViewById(R.id.tet_user_dec);
    }

    protected void initData() {
        sv_user_photo.setImageURI(OSSUtils.getSignedUrl(screen_photo));
        tet_user_name.setText(screen_name);
        tet_user_dec.setText(description);
    }

    protected void initListener() {
        iv_edit_user_back.setOnClickListener(this);
        tv_save_user_info.setOnClickListener(this);
        iv_take_photo.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_edit_user_back:
                setResult(needsRefresh ? RESULT_OK : RESULT_CANCELED, getIntent().putExtra("img_url", img_url));
                AYApplication.finishActivity(this);
                break;
            case R.id.tv_save_user_info:
                updateUserInfo();
                break;
            case R.id.iv_take_photo:
                checkCameraPermission();
                break;
        }
    }

    private void updateUserInfo() {
        input_name = tet_user_name.getText().toString().trim();
        input_dec = tet_user_dec.getText().toString().trim();
        if (TextUtils.isEmpty(input_name)) {
            ToastUtils.showShortToast(getString(R.string.input_nickname_error));
            return;
        }
        if (TextUtils.isEmpty(input_dec)) {
            ToastUtils.showShortToast(getString(R.string.input_about_me_error));
            return;
        }
        showProcessDialog(getString(R.string.update_user_info_processing));
        UpdateUserInfoDomainBean bean = new UpdateUserInfoDomainBean();
        String json;
        if (isChangeScreenPhoto) {
            String img_uuid = CalUtils.getUUID32();

            json = "{\"token\":\"" + AYPrefUtils.getAuthToken() + "\",\"condition\":{\"user_id\":\"" + AYPrefUtils.getUserId() + "\"},\"profile\":{\"screen_name\":\"" + input_name + "\",\"screen_photo\":\"" + img_uuid + "\",\"description\":\"" + input_dec + "\"}}";

            bean.json = json;
            bean.imgUUID = img_uuid;
            presenter.updateUserInfo(bean);
        } else {
            json = "{\"token\":\"" + AYPrefUtils.getAuthToken() + "\",\"condition\":{\"user_id\":\"" + AYPrefUtils.getUserId() + "\"},\"profile\":{\"screen_name\":\"" + input_name + "\",\"description\":\"" + input_dec + "\"}}";
            bean.json = json;
            presenter.updateUserInfo(bean);
        }
    }

    @Override
    public void onUpdateUserInfoSuccess(UpdateUserInfoBean bean) {
        closeProcessDialog();
        needsRefresh = true;
        ToastUtils.showShortToast(R.string.update_user_info_success);
        img_url = bean.screen_photo;
        setResult(needsRefresh ? RESULT_OK : RESULT_CANCELED, getIntent().putExtra("img_url", img_url));
        AYApplication.finishActivity(this);

    }

    @Override
    public void onError(BaseDataBean bean) {
        closeProcessDialog();
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(ctl_edit_root, bean.message);
        } else {
            ToastUtils.showShortToast(bean.message + "(" + bean.code + ")");
        }
    }

    private void checkCameraPermission() {

        //如果是6.0以下的手机，ActivityCompat.checkSelfPermission()会始终等于PERMISSION_GRANTED，
        // 但是，如果用户关闭了你申请的权限，ActivityCompat.checkSelfPermission(),会导致程序崩溃(java.lang
        // .RuntimeException: Unknown exception code: 1 msg null)，
        // 你可以使用try{}catch(){},处理异常，也可以判断系统版本，低于23就不申请权限，直接做你想做的。
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        /**
         * shouldShowRequestPermissionRationale()。如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
         * 注：如果用户在过去拒绝了权限请求，并在权限请求系统对话框中选择了 Don't ask again 选项，
         * 此方法将返回 false。如果设备规范禁止应用具有该权限，此方法也会返回 false。
         */
        if (ContextCompat.checkSelfPermission(EditUserInfoActivity.this, Manifest.permission
                .CAMERA) == PackageManager.PERMISSION_GRANTED) {
            getPicFromCamera();

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(EditUserInfoActivity.this,
                    Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(EditUserInfoActivity.this, new String[]{Manifest
                        .permission.CAMERA}, AppConstant.PERMISSION_REQUEST);

            } else {
                //提示用户打开设置授权
                showGoSettingDialog();
            }
        }

    }

    private void showGoSettingDialog() {

        AlertDialog dialog = new AlertDialog.Builder(EditUserInfoActivity.this)
                .setCancelable(false)
                .setTitle(R.string.permission_denied)
                .setMessage(R.string.open_camera_permission_setting)
                .setPositiveButton(getString(R.string.go_permission_setting), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        DeviceUtils.gotoPermissionSetting(EditUserInfoActivity.this);
                    }
                })
                .setNegativeButton(getString(R.string.dlg_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case AppConstant.PERMISSION_REQUEST:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        getPicFromCamera();
                        LogUtils.d("xcx", permissions[i] + " granted");
                    } else {
                        LogUtils.d("xcx", permissions[i] + " denied");

                    }
                }
                break;
        }
    }


    /**
     * 进入相机拍照获取图片
     */
    private void getPicFromCamera() {
        // 创建File对象，用于存储拍照后的图片
        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            imageUri = Uri.fromFile(outputImage);
        } else {
            //Android 7.0系统开始 使用本地真实的Uri路径不安全,使用FileProvider封装共享Uri
            imageUri = FileProvider.getUriForFile(EditUserInfoActivity.this, "com.blackmirror" +
                    ".dongda.fileprovider", outputImage);
        }
        // 启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, AppConstant.TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case AppConstant.CHOOSE_PIC://从相册选择图片
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                    break;
                case AppConstant.PICTURE_CUT:
                    //                    sv_user_photo.setImageURI(outputUri);
                    displayImage(outputUri, sv_user_photo);
                    try {
                        bitmap = BitmapFactory.decodeFile(getExternalCacheDir() + "/crop_image.jpg");
                        /*bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream
                                (outputUri));*/
                        if (bitmap != null) {
                            isChangeScreenPhoto = true;
                            LogUtils.d("xcx", "压缩前图片的大小" + (bitmap.getByteCount() / 1024)
                                    + "k宽度为" + bitmap.getWidth() + "高度为" + bitmap.getHeight());
                            //                        scaleBitmap(bitmap,outputUri);

                        }
                    } catch (Exception e) {
                    }
                    break;
                case AppConstant.TAKE_PHOTO:
                    cropPhoto(imageUri);//裁剪图片
                    break;
            }
        } else {
            //            ToastUtils.showShortToast("设置图片出错!");
        }
    }

    // 4.4及以上系统使用这个方法处理图片 相册图片返回的不再是真实的Uri,而是分装过的Uri
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        imagePath = null;
        Uri uri = data.getData();
        LogUtils.d("xcx", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse
                        ("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        cropPhoto(uri);
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        imagePath = getImagePath(uri, null);
        cropPhoto(uri);
    }

    /**
     * 裁剪图片
     */
    private void cropPhoto(Uri uri) {
        // 创建File对象，用于存储裁剪后的图片，避免更改原图
        File file = new File(getExternalCacheDir(), "crop_image.jpg");
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        outputUri = Uri.fromFile(file);
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");
        //裁剪图片的宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("crop", "true");//可裁剪
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 800);
        intent.putExtra("outputY", 800);
        intent.putExtra("scale", true);//支持缩放
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());//输出图片格式
        intent.putExtra("noFaceDetection", true);//取消人脸识别
        startActivityForResult(intent, AppConstant.PICTURE_CUT);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    public void displayImage(Uri uri, SimpleDraweeView draweeView) {

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(DensityUtils.getScreenWidthPx(), DensityUtils.dp2px(250)))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.getController())
                .build();
        draweeView.setController(controller);
    }

    public static void startActivityForResult(AppCompatActivity activity, String screen_photo, String screen_name, String description, int requestCode) {
        Intent intent = new Intent(activity, EditUserInfoActivity.class);
        intent.putExtra("screen_photo", screen_photo);
        intent.putExtra("screen_name", screen_name);
        intent.putExtra("description", description);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onBackPressed() {
        setResult(needsRefresh ? RESULT_OK : RESULT_CANCELED, getIntent().putExtra("img_url", img_url));
        AYApplication.removeActivity(this);
        finish();
        super.onBackPressed();
    }

}
