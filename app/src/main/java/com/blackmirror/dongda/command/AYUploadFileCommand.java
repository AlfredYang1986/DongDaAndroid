package com.blackmirror.dongda.command;

import android.os.AsyncTask;
import com.blackmirror.dongda.AY.AYSysNotificationHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by alfredyang on 29/06/2017.
 */
public class AYUploadFileCommand extends AYCommand {

    final String TAG = "upload file";
    final String path = "/mnt/sdcard/DCIM/";

    final String destination = "/mnt/sdcard/dongda/";
    final String endpoint = "http://localhost:9000/post/uploadFile";
//    final String endpoint = "http://www.altlys.com:9000/post/uploadFile";

    final String failed_notify_func_name = "uploadFailed";
    final String success_notify_func_name = "uploadSuccess";

    public String file_name = null;
    public AYSysNotificationHandler handler = null;
    public int resource_id = 0;
    public int resource_index = 0;

    @Override
    public String getClassTag() {
        return TAG;
    }

    public <Args, Result> Result excute(Args ... args) {
        try {
            if (args.length == 2) {
                JSONObject a = (JSONObject)args[0];
                file_name = a.getString("file");
                resource_id = a.getInt("resource_id");
                resource_index = a.getInt("resource_index");

                handler = (AYSysNotificationHandler) args[1];

                String origin_file = path + file_name;



            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void excuteImpl(String ... args) {
        AYAsyncTask tk = new AYAsyncTask();
        tk.execute(args);
    }

    protected class AYAsyncTask extends AsyncTask<String, Integer, JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
        }
    }
}
