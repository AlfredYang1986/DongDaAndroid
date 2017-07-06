package com.blackmirror.dongda.command;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import com.blackmirror.dongda.AY.AYSysNotificationHandler;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.factory.AYFactoryManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by alfredyang on 23/05/2017.
 */
public abstract class AYRemoteCommand extends AYCommand {
    protected class AYAsyncTask extends AsyncTask<JSONObject, Integer, JSONObject> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected JSONObject doInBackground(JSONObject ... params) {
            String result = "";
            HttpURLConnection conn = null;
            try {
                URL urls = new URL(Uri.parse(getUrl()).buildUpon().build().toString());
                conn = (HttpURLConnection) urls.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setChunkedStreamingMode(0);

                conn.setReadTimeout(50000); //milliseconds
                conn.setConnectTimeout(20000); // milliseconds

                conn.connect();

                JSONObject p = null;
                if (params.length > 0) {
                    p = params[0];
                }

                Log.i("remote task", p.toString());
                byte[] outputBytes = p.toString().getBytes("UTF-8");
                OutputStream os = conn.getOutputStream();
                os.write(outputBytes);
                os.flush();
                os.close();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                            conn.getInputStream(), "UTF-8"), 8);
//                            conn.getInputStream(), "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");

                    }
                    result = sb.toString();

                } else {
                    result = "";
                }

            } catch (Exception e) {
                // System.out.println("exception in jsonparser class ........");
                e.printStackTrace();
            } finally {
                if (conn != null)
                    conn.disconnect();
            }

            JSONObject js_result = null;
            try {
                js_result = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return js_result;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            AYSysNotificationHandler t = getTarget();

            try {
                if (result == null || !result.getString("status").equals("ok"))
                    t.handleNotifications(getFailedCallBackName(), result);
                else
                    t.handleNotifications(getSuccessCallBackName(), result);

            } catch (JSONException e) {
                e.printStackTrace();
                // t.handleNotifications(getSuccessCallBackName(), result);
            }
        }
    }

    @Override
    public <Args, Result> Result excute(Args[] args) {
        excuteImpl((JSONObject[])args);
        return null;
    }

    public void excuteImpl(JSONObject ... args) {
        AYAsyncTask tk = new AYAsyncTask();
        tk.execute(args);
    }

    protected abstract String getUrl();
    protected abstract String getSuccessCallBackName();
    protected abstract String getFailedCallBackName();
}
