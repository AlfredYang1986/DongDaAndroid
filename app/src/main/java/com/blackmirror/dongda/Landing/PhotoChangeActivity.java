package com.blackmirror.dongda.Landing;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.blackmirror.dongda.AY.AYIntentRequestCode;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.facade.DongdaCommonFacade.SQLiteProxy.DAO.AYDaoUserProfile;

import java.io.File;
import java.io.IOException;

public class PhotoChangeActivity extends AYActivity {

    final static String TAG = "Photo Change Activity";

    private AYDaoUserProfile p = null;
    private ImageView iv = null;
    private String path = null;
    private Bitmap bm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_change);

        p = (AYDaoUserProfile) getIntent().getSerializableExtra("current_user");

        Button btn = (Button) findViewById(R.id.landing_enter_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "enter button clicked");
            }
        });

        iv = (ImageView) findViewById(R.id.landing_screen_photo_imgview);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "start album");
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");//相片类型
                startActivityForResult(intent, AYIntentRequestCode.AY_INTENT_PICK_IMAGE_FROM_ALBUM);
            }
        });

        Button album_btn = (Button) findViewById(R.id.landing_enter_album);
        album_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "start album");
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");//相片类型
                startActivityForResult(intent, AYIntentRequestCode.AY_INTENT_PICK_IMAGE_FROM_ALBUM);
            }
        });

        Button camera_btn = (Button) findViewById(R.id.landing_enter_camera);
        camera_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i(TAG, "start camera");
                path  = "/mnt/sdcard/DCIM/dongda_" + System.currentTimeMillis() + ".jpg";
                Uri uri = Uri.fromFile(new File(path));
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //调用照相机
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, AYIntentRequestCode.AY_INTENT_IMAGE_FROM_CAMERA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if (resultCode != RESULT_OK) {        //此处的 RESULT_OK 是系统自定义得一个常量
            Log.e(TAG,"ActivityResult resultCode error");
            return;
        }

        //外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
        ContentResolver resolver = getContentResolver();
        //此处的用于判断接收的Activity是不是你想要的那个
        if (requestCode == AYIntentRequestCode.AY_INTENT_PICK_IMAGE_FROM_ALBUM) {
            try {
                Uri originalUri = data.getData();        //获得图片的uri
                bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);        //显得到bitmap图片
                iv.setImageBitmap(bm);
                /**
                 * 这里开始的第二部分，获取图片的路径
                 */
//                String[] proj = {MediaStore.Images.Media.DATA};
//                Cursor cursor = managedQuery(originalUri, proj, null, null, null);
//                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                cursor.moveToFirst();
//                String path = cursor.getString(column_index);
//                Log.i(TAG, "select photo is " + path);

            }catch (IOException e) {
                Log.e(TAG, e.toString());
            }
        } else if (requestCode == AYIntentRequestCode.AY_INTENT_IMAGE_FROM_CAMERA) {
            try {
                Uri originalUri = Uri.fromFile(new File(path));
                bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);        //显得到bitmap图片
                iv.setImageBitmap(bm);
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            }
        }
    }

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected void bindingFragments() {

    }
}
