package com.blackmirror.dongda.Landing;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
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
import com.blackmirror.dongda.Home.HomeActivity.AYHomeActivity;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.AYApplication;
import com.blackmirror.dongda.Tools.AppConstant;
import com.blackmirror.dongda.Tools.BasePrefUtils;
import com.blackmirror.dongda.Tools.CalUtils;
import com.blackmirror.dongda.Tools.DeviceUtils;
import com.blackmirror.dongda.Tools.LogUtils;
import com.blackmirror.dongda.Tools.OSSUtils;
import com.blackmirror.dongda.Tools.OtherUtils;
import com.blackmirror.dongda.Tools.ToastUtils;
import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.facade.DongdaCommonFacade.SQLiteProxy.DAO.AYDaoUserProfile;
import com.blackmirror.dongda.factory.AYFactoryManager;
import com.blackmirror.dongda.model.serverbean.ErrorInfoServerBean;
import com.blackmirror.dongda.model.serverbean.ImgTokenServerBean;
import com.blackmirror.dongda.model.serverbean.UpdateUserInfoServerBean;
import com.blackmirror.dongda.model.uibean.ImgTokenUiBean;
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
    private Bitmap bm;
    private AYDaoUserProfile p = null;
    private Boolean isChangeScreenPhoto = false;
    private String path = null;
    private String post_upload_uuid = null;
    private String imagePath;//打开相册选择照片的路径
    private Uri outputUri;//裁剪万照片保存地址
    private Uri imageUri;//相机拍照图片保存地址
    private Button btn_enter_home;
    private Button btn_enter_cancel;
    private boolean isFromNameInput;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_change);
        AYApplication.addActivity(this);
        OtherUtils.setStatusBarColor(this,getResources().getColor(R.color.colorPrimary));
        p = (AYDaoUserProfile) getIntent().getSerializableExtra("current_user");
        name = getIntent().getStringExtra("name");
        isFromNameInput = getIntent().getIntExtra("from", AppConstant.FROM_PHONE_INPUT) == AppConstant.FROM_PHONE_INPUT;
        initView();
        initData();
        initListener();
        AYFacade facade = facades.get("LoginFacade");
        try {
            JSONObject object = new JSONObject();
            object.put("token",BasePrefUtils.getAuthToken());
            facade.execute("getImgToken",object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

                if (isChangeScreenPhoto){
                    showProcessDialog("正在上传头像...");
                    facades.get("LoginFacade").execute("AYUploadFileBySDKCommand",new JSONObject());
                }else {
                    ToastUtils.showShortToast("请选择头像!");
                }

                break;
            case R.id.btn_enter_cancel:
                AYApplication.finishAllActivity();
                break;
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
                .setTitle("权限拒绝")
                .setMessage("请在设置->应用管理->咚哒->权限管理打开相机权限.")
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        DeviceUtils.gotoPermissionSetting(PhotoChangeActivity.this);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
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


    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected void bindingFragments() {

    }

    /**
     * 获取图片token 用于生成url签名
     * @param args
     */
    public void AYGetImgTokenCommandSuccess(JSONObject args){

        ImgTokenServerBean serverBean = JSON.parseObject(args.toString(), ImgTokenServerBean.class);
        ImgTokenUiBean bean = new ImgTokenUiBean(serverBean);
        if (bean.isSuccess){
            BasePrefUtils.setAccesskeyId(bean.accessKeyId);
            BasePrefUtils.setSecurityToken(bean.SecurityToken);
            BasePrefUtils.setAccesskeySecret(bean.accessKeySecret);
            BasePrefUtils.setExpiration(bean.Expiration);
            OSSUtils.initOSS(this,bean.accessKeyId,bean.accessKeySecret,bean.SecurityToken);
        }
    }

    public void AYGetImgTokenCommandFailed(JSONObject args) {
        ErrorInfoServerBean bean = JSON.parseObject(args.toString(), ErrorInfoServerBean.class);
        if (bean != null && bean.error != null) {
            ToastUtils.showShortToast(bean.error.message);
        }
    }

    /**
     * 上传文件回调
     * @param args
     */
    public void AYUploadFileBySDKCommandSuccess(JSONObject args){
//        ToastUtils.showShortToast("上传成功!");
        senDataToServer();
    }



    public void AYUploadFileBySDKCommandFailed(JSONObject args) {
        closeProcessDialog();
        ToastUtils.showShortToast("上传失败!");
    }

    /**
     * 修改用户信息回调
     * @param args
     * @return
     */
    public void AYUpdateProfileCommandSuccess(JSONObject args) {
        closeProcessDialog();
        UpdateUserInfoServerBean serverBean = JSON.parseObject(args.toString(), UpdateUserInfoServerBean.class);
        UpdateUserInfoUiBean uiBean = new UpdateUserInfoUiBean(serverBean);

        if (uiBean.isSuccess){
            AYDaoUserProfile profile = new AYDaoUserProfile();
            profile.auth_token = uiBean.token;
            profile.user_id = uiBean.user_id;
            profile.screen_name = uiBean.screen_name;
            profile.screen_photo = uiBean.screen_photo;
            profile.is_current=1;

            AYCommand cmd = facades.get("LoginFacade").cmds.get("UpdateLocalProfile");
            long result = cmd.excute(profile);
            if (result>0){

                ToastUtils.showShortToast("修改成功!");
                startActivity(new Intent(PhotoChangeActivity.this, AYHomeActivity.class));
                AYApplication.finishAllActivity();
            }else {
                ToastUtils.showShortToast("系统异常(SQL)");
            }
            closeProcessDialog();
        }
    }

    public void AYUpdateProfileCommandFailed(JSONObject args) {
        closeProcessDialog();
        ToastUtils.showShortToast("修改失败!");
    }

    private void senDataToServer() {

        String json;
        try {
            if (isFromNameInput){
                json="{\"token\":\""+BasePrefUtils.getAuthToken()+"\",\"condition\":{\"user_id\":\""+BasePrefUtils.getUserId()+"\"},\"profile\":{\"screen_name\":\""+name+"\",\"screen_photo\":\""+ CalUtils.md5(BasePrefUtils.getUserId())+"\"}}";
            }else {
                json="{\"token\":\"" + BasePrefUtils.getAuthToken() + "\",\"condition\":{\"user_id\":\"" + BasePrefUtils.getUserId() + "\"},\"profile\":{\"screen_photo\":\"" + CalUtils.md5(BasePrefUtils.getUserId()) + "\"}}";
            }
            JSONObject object = new JSONObject(json);
            facades.get("LoginFacade").execute("UpdateProfile",object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Boolean downloadSuccess(JSONObject arg) {

        Log.i(TAG, "send sms code result is " + arg.toString());

        try {
            int rid = arg.getInt("resource_id");
            ImageView tmp = (ImageView) findViewById(rid);
            String file_path = arg.getString("path");
            Uri originalUri = Uri.fromFile(new File(file_path));
            ContentResolver resolver = getContentResolver();
            bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);        //显得到bitmap图片
            tmp.setImageBitmap(bm);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public Boolean downloadFailed(JSONObject arg) {
        Log.i(TAG, "send sms code error is " + arg.toString());
        return true;
    }

    protected void setOriginScreenPhoto() {
        path = null;
        bm = null;
        isChangeScreenPhoto = false;
        post_upload_uuid = null;

        String screen_photo = p.getScreen_photo();

        AYCommand cmd = (AYCommand) AYFactoryManager.
                getInstance(this.getApplicationContext()).
                queryInstance("command", "DownloadFile");

        Map<String, Object> m = new HashMap<>();
        m.put("file", screen_photo);
        m.put("resource_id", R.id.iv_head_photo);
        m.put("resource_index", 0);
        JSONObject args = new JSONObject(m);
        cmd.excute(args, this);
    }

    protected void setOriginScreenName() {
        String screen_name = p.getScreen_name();

        TextView tv = (TextView) findViewById(R.id.tv_screen_name);
        tv.setText(screen_name);
    }


    protected void uploadUserScreenPhoto(String file_name) {
        AYCommand cmd = (AYCommand) AYFactoryManager.
                getInstance(null).
                queryInstance("command", "uploadFile");

        Map<String, Object> m = new HashMap<>();
        m.put("file", file_name);
        m.put("resource_id", R.id.iv_head_photo);
        m.put("resource_index", 0);
        JSONObject args = new JSONObject(m);
        cmd.excute(args, this);
    }

    public Boolean uploadSuccess(JSONObject arg) {

        Log.i(TAG, "send sms code result is " + arg.toString());

        try {
            post_upload_uuid = arg.getString("uuid");
            Log.i(TAG, "upload uuid is " + post_upload_uuid);

            /**
             * 2. 如果修改照片修改用户Profile
             */
            String ori_photo = p.getScreen_photo();

            if (!post_upload_uuid.equals(ori_photo)) {
                AYFacade facade = this.facades.get("LoginFacade");
                AYCommand cmd = facade.cmds.get("UpdateProfile");

                Map<String, Object> m = new HashMap<>();
                m.put("user_id", p.getUser_id());
                m.put("auth_token", p.getAuth_token());
                m.put("screen_photo", post_upload_uuid);
                JSONObject args = new JSONObject(m);
                cmd.excute(args);
            } else {
                loginSuccess();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return true;
    }

    public Boolean uploadFailed(JSONObject arg) {
        Log.i(TAG, "send sms code error is " + arg.toString());
        return true;
    }

    protected void loginSuccess() {
        /**
         * 3. 登陆成功
         */
        Intent intent = new Intent(this, AYHomeActivity.class);
        startActivity(intent);
    }

    /**
     * 进入相机拍照获取图片
     */
    private void getPicFromCamera() {
        // 创建File对象，用于存储拍照后的图片
        LogUtils.d(getExternalCacheDir() + "/takePic");
        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT < 24) {
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
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                    break;
                case AppConstant.PICTURE_CUT:
                    Bitmap bitmap;
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream
                                (outputUri));
                        if (bitmap!=null) {
                            isChangeScreenPhoto = true;
                            LogUtils.d("xcx", "压缩前图片的大小" + (bitmap.getByteCount() / 1024)
                                    + "k宽度为" + bitmap.getWidth() + "高度为" + bitmap.getHeight());
                            //                        scaleBitmap(bitmap,outputUri);
                            iv_head_photo.setImageBitmap(bitmap);
                        }
                    } catch (FileNotFoundException e) {
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

        LogUtils.d("xcx", "Matrix 压缩后图片的大小" + (bm2.getByteCount() / 1024 )
                + "k 宽度为 " + bm2.getWidth() + ",高度为 " + bm2.getHeight());



        bm1 = Bitmap.createBitmap(bm2.getWidth(),bm2.getHeight(), Bitmap.Config.RGB_565);


        LogUtils.d("xcx", "rgb 565 压缩后图片的大小" + (bm1.getByteCount() / 1024 )
                + "k 宽度为 " + bm1.getWidth() + ",高度为 " + bm1.getHeight());



        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bm3 = null;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream
                (outputUri),null,options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 300, 300);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        bm3=BitmapFactory.decodeStream(getContentResolver().openInputStream
                (outputUri),null,options);

        LogUtils.d("xcx", "inSampleSize 压缩后图片的大小" + (bm3.getByteCount() / 1024)
                + "k 宽度为 " + bm3.getWidth() + ",高度为 " + bm3.getHeight());

        iv_head_photo.setImageBitmap(bm3);



    }

    public  int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
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
        LogUtils.d("inSampleSize "+inSampleSize);
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
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
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
}
