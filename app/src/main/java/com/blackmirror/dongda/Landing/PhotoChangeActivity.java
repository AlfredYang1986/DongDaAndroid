package com.blackmirror.dongda.Landing;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.blackmirror.dongda.AY.AYIntentRequestCode;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.facade.DongdaCommonFacade.SQLiteProxy.DAO.AYDaoUserProfile;
import com.blackmirror.dongda.factory.AYFactoryManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;

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

        String screen_photo = p.getScreen_photo();
//        AYFacade facade = facades.get("FileFacade");
//        AYCommand cmd = facade.cmds.get("DownloadFile");

        AYCommand cmd = (AYCommand) AYFactoryManager.
                getInstance(this.getApplicationContext()).
                queryInstance("command", "DownloadFile");

        Map<String, Object> m = new HashMap<>();
        m.put("file", screen_photo);
        m.put("resource_id", R.id.landing_screen_photo_imgview);
        m.put("resource_index", 0);
        JSONObject args = new JSONObject(m);
        cmd.excute(args, this);

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

    protected void resetImageChoose() {
        path = null;
        bm = null;
    }

    protected Boolean downloadSuccess(JSONObject arg) {

        Log.i(TAG, "send sms code result is " + arg.toString());

        try {
            int rid = arg.getInt("resource_id");
            ImageView tmp= (ImageView) findViewById(rid);
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

    protected Boolean downloadFailed(JSONObject arg) {
        Log.i(TAG, "send sms code error is " + arg.toString());
        return true;
    }
}
