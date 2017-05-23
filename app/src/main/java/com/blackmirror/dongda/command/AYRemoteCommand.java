package com.blackmirror.dongda.command;

import android.os.AsyncTask;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.factory.AYFactoryManager;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

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
            try {
                URL urls = new URL(getUrl());
                HttpURLConnection conn = (HttpURLConnection) urls.openConnection();
                conn.setReadTimeout(50000); //milliseconds
                conn.setConnectTimeout(20000); // milliseconds
                conn.setRequestMethod("POST");

                conn.connect();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    BufferedReader reader = new BufferedReader(new InputStreamReader(
                            conn.getInputStream(), "iso-8859-1"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");

                    }
                    result = sb.toString();

                } else {
                    return null;
                }

                return new JSONObject(result);

            } catch (Exception e) {
                // System.out.println("exception in jsonparser class ........");
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            AYFacade facade = (AYFacade) AYFactoryManager.getInstance(null)
                                    .queryInstance("facade", "RemoteCommonFacade");
            if (result != null)
                facade.broadcastingNotification(getSuccessCallBackName(), result);
            else
                facade.broadcastingNotification(getFailedCallBackName(), result);
        }
    }

    public void excute(JSONObject ... args) {
        AYAsyncTask tk = new AYAsyncTask();
        tk.execute(args);
    }

    protected abstract String getUrl();
    protected abstract String getSuccessCallBackName();
    protected abstract String getFailedCallBackName();
}
