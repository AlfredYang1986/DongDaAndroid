package com.blackmirror.dongda.command;

import android.os.AsyncTask;
import com.blackmirror.dongda.AY.AYSysNotificationHandler;
import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.command.AYRemoteCommand;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
//import com.blackmirror.dongda.command.AYRemoteCommand;

/**
 * Created by alfredyang on 28/06/2017.
 */
public class AYDongloadFileCommand extends AYCommand {

    final String TAG = "download file";
    final String path = "/mnt/sdcard/dongda/";
    final String endpoint = "http://www.altlys.com:9000/query/downloadFile/";

    final String failed_notify_func_name = "downloadFailed";
    final String success_notify_func_name = "downloadSuccess";

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

                String file = path + file_name;

                File f = new File(file);
                if (f.exists()) {
                    AYAsyncTask tk = new AYAsyncTask();
                    JSONObject js = new JSONObject();
                    js.put("status", "ok");
                    js.put("path", file);
                    tk.onPostExecute(js);
                } else {
                    excuteImpl(file_name);
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
    }

    protected class AYAsyncTask extends AsyncTask<String, Integer, JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            try {
                result.put("resource_id", resource_id);
                result.put("resource_index", resource_index);
                if (handler != null) {
                    if (result.getString("status").equals("ok")) {
                        handler.handleNotifications(success_notify_func_name, result);
                    } else {
                        handler.handleNotifications(failed_notify_func_name, result);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected JSONObject doInBackground(String ... args) {

            JSONObject result = new JSONObject();

            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(endpoint + args[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    result.put("status", "eror");
                    result.put("error", "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage());
                    return result;
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
//                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream(path + file_name);

                byte data[] = new byte[4096];
//                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        result.put("status", "error");
                        result.put("error", "user cancelled");
                        return result;
                    }
//                    total += count;

                    // publishing the progress....
//                    if (fileLength > 0) // only if total length is known
//                        publishProgress((int) (total * 100 / fileLength));

                    output.write(data, 0, count);
                }

                result.put("status", "ok");
                result.put("path", path + file_name);

            } catch (Exception e) {
                return null;
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {

                }

                if (connection != null)
                    connection.disconnect();
            }
            return result;
        }
    }
}
