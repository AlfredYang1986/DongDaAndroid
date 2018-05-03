package com.blackmirror.dongda.ui.activity.Landing;

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
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blackmirror.dongda.ui.activity.HomeActivity.AYHomeActivity;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.utils.AYApplication;
import com.blackmirror.dongda.utils.AYPrefUtils;
import com.blackmirror.dongda.utils.AppConstant;
import com.blackmirror.dongda.utils.DateUtils;
import com.blackmirror.dongda.utils.DeviceUtils;
import com.blackmirror.dongda.utils.LogUtils;
import com.blackmirror.dongda.utils.SnackbarUtils;
import com.blackmirror.dongda.utils.ToastUtils;
import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.ui.activity.AYActivity;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.facade.DongdaCommonFacade.SQLiteProxy.DAO.AYDaoUserProfile;
import com.blackmirror.dongda.model.serverbean.ErrorInfoServerBean;
import com.blackmirror.dongda.model.serverbean.ImgTokenServerBean;
import com.blackmirror.dongda.model.serverbean.UpLoadFileServerBean;
import com.blackmirror.dongda.model.serverbean.UpdateUserInfoServerBean;
import com.blackmirror.dongda.model.uibean.ErrorInfoUiBean;
import com.blackmirror.dongda.model.uibean.ImgTokenUiBean;
import com.blackmirror.dongda.model.uibean.UpLoadFileUiBean;
import com.blackmirror.dongda.model.uibean.UpdateUserInfoUiBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PhotoChangeActivity extends AYActivity implements View.OnClickListener {

    final static String TAG = "Photo Change Activity";

    private ImageView iv_head_photo;
    private Button btn_enter_album;
    private Button btn_open_camera;
    private TextView tv_screen_name;
    private AYDaoUserProfile p = null;
    private Boolean isChangeScreenPhoto = false;
    private String imagePath;//打开相册选择照片的路径
    private Uri outputUri;//裁剪万照片保存地址
    private Uri imageUri;//相机拍照图片保存地址
    private Button btn_enter_home;
    private Button btn_enter_cancel;
    private boolean isFromNameInput;
    private String name;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_change);
        AYApplication.addActivity(this);
        DeviceUtils.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary));
        p = (AYDaoUserProfile) getIntent().getSerializableExtra("current_user");
        name = getIntent().getStringExtra("name");
        isFromNameInput = getIntent().getIntExtra("from", AppConstant.FROM_PHONE_INPUT) == AppConstant.FROM_NAME_INPUT;
        initView();
        initData();
        initListener();
    }

    private void initView() {
        btn_enter_home = findViewById(R.id.btn_enter_home);
        iv_head_photo = findViewById(R.id.iv_head_photo);
        btn_enter_album = findViewById(R.id.btn_enter_album);
        btn_open_camera = findViewById(R.id.btn_open_camera);
        btn_enter_cancel = findViewById(R.id.btn_enter_cancel);
        tv_screen_name = findViewById(R.id.tv_screen_name);
    }

    private void initData() {
        tv_screen_name.setText(name);
    }

    private void initListener() {
        btn_enter_home.setOnClickListener(this);
        btn_enter_album.setOnClickListener(this);
        btn_open_camera.setOnClickListener(this);
        btn_enter_cancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_enter_album:
            case R.id.iv_head_photo:
                getPicFromAlbum();
                break;
            case R.id.btn_open_camera:
                checkCameraPermission();
                break;
            case R.id.btn_enter_home:
                if (isChangeScreenPhoto) {
                    showProcessDialog(getString(R.string.uploading_head_photo));
                    if (DateUtils.isNeedRefreshToken(AYPrefUtils.getExpiration())) {
                        getImageToken();
                    } else {
                        uploadImage();
                    }

                } else {
                    ToastUtils.showShortToast(getString(R.string.choose_head_photo));
                }
                break;
            case R.id.btn_enter_cancel:
                AYApplication.removeActivity(this);
                finish();
                break;
        }
    }

    private void getImageToken() {
        try {
            AYFacade facade = facades.get("LoginFacade");
            JSONObject object = new JSONObject();
            object.put("token", AYPrefUtils.getAuthToken());

            Map<String, Object> m1 = new HashMap<>();
            m1.put("login", "login");
            JSONObject login = new JSONObject(m1);

            facade.execute("getImgToken", object, login);
        } catch (Exception e) {
            closeProcessDialog();
        }
    }

    public void uploadImage(){
        facades.get("LoginFacade").execute("AYUploadFileBySDKCommand", new JSONObject());
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
        if (ContextCompat.checkSelfPermission(PhotoChangeActivity.this, Manifest.permission
                .CAMERA) == PackageManager.PERMISSION_GRANTED) {
            getPicFromCamera();

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(PhotoChangeActivity.this,
                    Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(PhotoChangeActivity.this, new String[]{Manifest
                        .permission.CAMERA}, AppConstant.PERMISSION_REQUEST);

            } else {
                //提示用户打开设置授权
                showGoSettingDialog();
            }
        }

    }

    private void showGoSettingDialog() {

        AlertDialog dialog = new AlertDialog.Builder(PhotoChangeActivity.this)
                .setCancelable(false)
                .setTitle(R.string.permission_denied)
                .setMessage(R.string.open_camera_permission_setting)
                .setPositiveButton(getString(R.string.go_permission_setting), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        DeviceUtils.gotoPermissionSetting(PhotoChangeActivity.this);
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
     * 获取图片token 用于生成url签名
     *
     * @param args
     */
    public void AYGetImgTokenCommandSuccess(JSONObject args) {

        LogUtils.d("PhotoChangeActivity AYGetImgTokenCommandSuccess");

        ImgTokenServerBean serverBean = JSON.parseObject(args.toString(), ImgTokenServerBean.class);
        ImgTokenUiBean bean = new ImgTokenUiBean(serverBean);
        if (bean.isSuccess) {
            AYPrefUtils.setAccesskeyId(bean.accessKeyId);
            AYPrefUtils.setSecurityToken(bean.SecurityToken);
            AYPrefUtils.setAccesskeySecret(bean.accessKeySecret);
            AYPrefUtils.setExpiration(bean.Expiration);
            uploadImage();
        } else {
            ToastUtils.showShortToast(String.format(getString(R.string.data_unknown_error),bean.message,String.valueOf(bean.code)));
        }
    }

    public void AYGetImgTokenCommandFailed(JSONObject args) {
        LogUtils.d("PhotoChangeActivity AYGetImgTokenCommandFailed");
        closeProcessDialog();
        ErrorInfoServerBean serverBean = JSON.parseObject(args.toString(), ErrorInfoServerBean.class);
        ErrorInfoUiBean uiBean = new ErrorInfoUiBean(serverBean);
        if (uiBean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(btn_enter_home, uiBean.message);
        } else {
            ToastUtils.showShortToast(String.format(getString(R.string.upload_unknown_error),uiBean.message,String.valueOf(uiBean.code)));
        }
    }

    /**
     * 上传文件回调
     *
     * @param args
     */
    public void AYUploadFileBySDKCommandSuccess(JSONObject args) {
        //        ToastUtils.showShortToast("上传成功!");
        LogUtils.d("PhotoChangeActivity AYUploadFileBySDKCommandSuccess");
        UpLoadFileServerBean serverBean = JSON.parseObject(args.toString(), UpLoadFileServerBean.class);
        UpLoadFileUiBean uiBean = new UpLoadFileUiBean(serverBean);
        if (uiBean.isSuccess){
            senDataToServer(uiBean);
        }else {
            ToastUtils.showShortToast(uiBean.message+"("+uiBean.code+")");
        }
    }


    public void AYUploadFileBySDKCommandFailed(JSONObject args) {
        LogUtils.d("PhotoChangeActivity AYUploadFileBySDKCommandFailed");

        closeProcessDialog();
        ErrorInfoServerBean serverBean = JSON.parseObject(args.toString(), ErrorInfoServerBean.class);
        ErrorInfoUiBean uiBean = new ErrorInfoUiBean(serverBean);
        if (uiBean.code==AppConstant.NET_WORK_UNAVAILABLE){
            SnackbarUtils.show(iv_head_photo,uiBean.message);
        }else {
            ToastUtils.showShortToast(uiBean.message+"("+uiBean.code+")");
        }

    }

    /**
     * 修改用户信息回调
     *
     * @param args
     * @return
     */
    public void AYUpdateProfileCommandSuccess(JSONObject args) {
        LogUtils.d("PhotoChangeActivity AYUpdateProfileCommandSuccess");

        UpdateUserInfoServerBean serverBean = JSON.parseObject(args.toString(), UpdateUserInfoServerBean.class);
        UpdateUserInfoUiBean uiBean = new UpdateUserInfoUiBean(serverBean);
        closeProcessDialog();
        if (uiBean.isSuccess) {
            AYDaoUserProfile profile = new AYDaoUserProfile();
            profile.auth_token = uiBean.token;
            profile.user_id = uiBean.user_id;
            profile.screen_name = uiBean.screen_name;
            profile.screen_photo = uiBean.screen_photo;
            profile.is_current = 1;

            AYCommand cmd = facades.get("LoginFacade").cmds.get("UpdateLocalProfile");
            long result = cmd.execute(profile);
            if (result > 0) {
                ToastUtils.showShortToast(R.string.update_user_info_success);
                Intent intent = new Intent(PhotoChangeActivity.this, AYHomeActivity.class);
                intent.putExtra("img_uuid",uiBean.screen_photo);
                startActivity(intent);
                AYApplication.finishAllActivity();
            } else {
                ToastUtils.showShortToast(R.string.update_sql_error);
            }
        }

    }

    public void AYUpdateProfileCommandFailed(JSONObject args) {
        LogUtils.d("PhotoChangeActivity AYUpdateProfileCommandFailed");

        closeProcessDialog();
        ErrorInfoServerBean serverBean = JSON.parseObject(args.toString(), ErrorInfoServerBean.class);
        ErrorInfoUiBean uiBean = new ErrorInfoUiBean(serverBean);
        if (uiBean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(iv_head_photo, uiBean.message);
        } else {
            ToastUtils.showShortToast(uiBean.message + "(" + uiBean.code + ")");
        }
    }

    private void senDataToServer(UpLoadFileUiBean uiBean) {
        LogUtils.d("PhotoChangeActivity senDataToServer");

        String json;
        try {
            if (isFromNameInput) {
                json = "{\"token\":\"" + AYPrefUtils.getAuthToken() + "\",\"condition\":{\"user_id\":\"" + AYPrefUtils.getUserId() + "\"},\"profile\":{\"screen_name\":\"" + name + "\",\"screen_photo\":\"" + uiBean.img_uuid + "\"}}";
            } else {
                json = "{\"token\":\"" + AYPrefUtils.getAuthToken() + "\",\"condition\":{\"user_id\":\"" + AYPrefUtils.getUserId() + "\"},\"profile\":{\"screen_photo\":\"" + uiBean.img_uuid + "\"}}";
            }
            JSONObject object = new JSONObject(json);
            Map<String, Object> m1 = new HashMap<>();
            m1.put("login", "login");
            JSONObject login = new JSONObject(m1);
            facades.get("LoginFacade").execute("UpdateProfile", object, login);
        } catch (JSONException e) {
            e.printStackTrace();
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
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {//7.0 以下
            imageUri = Uri.fromFile(outputImage);
        } else {
            //Android 7.0系统开始 使用本地真实的Uri路径不安全,使用FileProvider封装共享Uri
            imageUri = FileProvider.getUriForFile(PhotoChangeActivity.this, "com.blackmirror" +
                    ".dongda.fileprovider", outputImage);
        }
        // 启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, AppConstant.TAKE_PHOTO);
    }

    /**
     * 从相册获取图片
     */
    private void getPicFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, AppConstant.CHOOSE_PIC);
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
                    try {
                        bitmap = BitmapFactory.decodeFile(getExternalCacheDir() + "/crop_image.jpg");
                        /*bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream
                                (outputUri));*/
                        if (bitmap != null) {
                            isChangeScreenPhoto = true;
                            LogUtils.d("xcx", "压缩前图片的大小" + (bitmap.getByteCount() / 1024)
                                    + "k宽度为" + bitmap.getWidth() + "高度为" + bitmap.getHeight());
                            //                        scaleBitmap(bitmap,outputUri);
                            iv_head_photo.setImageBitmap(bitmap);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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

    private void scaleBitmap(Bitmap bitmap, Uri outputUri) throws FileNotFoundException {

        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bm1 = null;
        Bitmap bm2 = null;

        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);
        bm2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);

        LogUtils.d("xcx", "Matrix 压缩后图片的大小" + (bm2.getByteCount() / 1024)
                + "k 宽度为 " + bm2.getWidth() + ",高度为 " + bm2.getHeight());


        bm1 = Bitmap.createBitmap(bm2.getWidth(), bm2.getHeight(), Bitmap.Config.RGB_565);


        LogUtils.d("xcx", "rgb 565 压缩后图片的大小" + (bm1.getByteCount() / 1024)
                + "k 宽度为 " + bm1.getWidth() + ",高度为 " + bm1.getHeight());


        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bm3 = null;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream
                (outputUri), null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 300, 300);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        bm3 = BitmapFactory.decodeStream(getContentResolver().openInputStream
                (outputUri), null, options);

        LogUtils.d("xcx", "inSampleSize 压缩后图片的大小" + (bm3.getByteCount() / 1024)
                + "k 宽度为 " + bm3.getWidth() + ",高度为 " + bm3.getHeight());

        iv_head_photo.setImageBitmap(bm3);


    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        LogUtils.d("inSampleSize " + inSampleSize);
        return inSampleSize;
    }

    // 4.4及以上系统使用这个方法处理图片 相册图片返回的不再是真实的Uri,而是分装过的Uri
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
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

    @Override
    protected void setStatusBarColor() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        AYApplication.removeActivity(this);
        super.onBackPressed();
    }

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected void bindingFragments() {

    }
}
