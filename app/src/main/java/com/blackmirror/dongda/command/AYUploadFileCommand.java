package com.blackmirror.dongda.command;

import android.os.AsyncTask;
import android.util.Log;

import com.blackmirror.dongda.AY.AYSysHelperFunc;
import com.blackmirror.dongda.AY.AYSysNotificationHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import okhttp3.MultipartBody;

/**
 * Created by alfredyang on 29/06/2017.
 */
public class AYUploadFileCommand extends AYCommand {

    final String TAG = "upload file";
    String path = null;

    final String destination = "/mnt/sdcard/dongda/";
//    final String endpoint = "http://192.168.100.12:9000/post/uploadFile";
    final String endpoint = kDONGDABASEURL + "post/uploadFile";

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
                path = a.getString("file");
                resource_id = a.getInt("resource_id");
                resource_index = a.getInt("resource_index");

                handler = (AYSysNotificationHandler) args[1];

                file_name = UUID.randomUUID().toString();

                String origin_file = path;
                String des_file = destination + file_name;

                if (AYSysHelperFunc.getInstance().copyFile(origin_file, des_file)) {
                    excuteImpl(des_file, file_name);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void excuteImpl(String ... args) {
        AYAsyncTask tk = new AYAsyncTask();
        tk.execute(args);
        executeRequest(args);
    }

    private void executeRequest(String[] args) {
        new MultipartBody.Builder("--AaB03x")
                .setType(MultipartBody.FORM)
                .build();

    }

    protected class AYAsyncTask extends AsyncTask<String, Integer, JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            Map<String, String> result = new HashMap<>();

            final String file_path = args[0];
            final String file_name = args[1];

            //分界线的标识符
            final String lineEnd = "\r\n";
            final String twoHyphens = "--";
            final String boundary = "AaB03x";

            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;

            // 读文件
            try {
                FileInputStream fileInputStream = new FileInputStream(new File(file_path));

                URL url = new URL(endpoint);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", file_name);

                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"upload\";filename=\"" + file_name + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                int serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i(TAG, "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200){
                    result.put("status", "ok");
                    result.put("uuid", file_name);
                } else {
                    result.put("status", "error");
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return new JSONObject(result);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            try {
                if (result.get("status").equals("ok")) {
                    handler.handleNotifications(success_notify_func_name, result);
                } else {
                    handler.handleNotifications(failed_notify_func_name, result);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
