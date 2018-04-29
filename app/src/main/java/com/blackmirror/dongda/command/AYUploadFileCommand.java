package com.blackmirror.dongda.command;

import android.os.AsyncTask;
import android.util.Log;

import com.blackmirror.dongda.AY.AYSysHelperFunc;
import com.blackmirror.dongda.AY.AYSysNotificationHandler;
import com.blackmirror.dongda.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.zip.GZIPInputStream;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by alfredyang on 29/06/2017.
 */
public abstract class AYUploadFileCommand extends AYCommand {

    final String TAG = "upload file";
    String path = null;
    private Disposable disposable;
    private AYSysNotificationHandler mNotificationHandler;

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
                    excuteImpl((JSONObject)args[0]);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void excuteImpl(JSONObject... args) {
        AYAsyncTask tk = new AYAsyncTask();
//        tk.execute(args);
        sendRequestData(args);
    }

    private JSONObject executeRequest(JSONObject args) {
        LogUtils.d("flag","做网络请求前的json数据: "+args.toString());
        Request request = new Request.Builder()
                .url(getUrl()).post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), args.toString())).build();
        try {
            Response response = httpClient.newCall(request).execute();
            InputStream in = inputStreamAfterCheck(response);
            InputStreamReader iReader = new InputStreamReader(in);
            BufferedReader bReader = new BufferedReader(iReader);
            StringBuilder json = new StringBuilder();
            String line = null;
            while ((line = bReader.readLine()) != null) {
                json.append(line).append('\n');
            }
            bReader.close();
            iReader.close();
            in.close();
            LogUtils.d("flag", "返回的数据：" + json.toString());
            JSONObject js_result = null;
            js_result = new JSONObject(json.toString());
            return js_result;
        } catch (SocketTimeoutException e1) {
            return getErrorData(e1);
        } catch (ConnectException e2) {
            return getErrorData(e2);
        } catch (JSONException e3) {
            return getErrorData(e3);
        } catch (Exception e4) {
            return getErrorData(e4);
        }
        /*net_call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtils.d("onResponse " + Thread.currentThread().getName());
                InputStream in = inputStreamAfterCheck(response);
                InputStreamReader iReader = new InputStreamReader(in);
                BufferedReader bReader = new BufferedReader(iReader);
                StringBuilder json = new StringBuilder();
                String line = null;
                while ((line = bReader.readLine()) != null) {
                    json.append(line).append('\n');
                }
                bReader.close();
                iReader.close();
                in.close();
                //            T obj = (T)JSON.parseObject(json.toString(), myClass);
                LogUtils.d("xcx", "返回的数据：" + json.toString());
                JSONObject js_result = null;
                try {
                    js_result = new JSONObject(json.toString());
                    if (js_result == null || !js_result.getString("status").equals("ok")) {
                        if (mNotificationHandler != null) {
                            mNotificationHandler.handleNotifications(getFailedCallBackName(), js_result);
                        }
                    } else {
                        if (mNotificationHandler != null) {
                            mNotificationHandler.handleNotifications(getSuccessCallBackName(), js_result);
                        }
                    }

                } catch (JSONException e) {

                }

            }
        });*/

    }

    private void sendRequestData(JSONObject[] args) {
        if (args == null || args.length <= 0) {
            return;
        }
        Observable.just(args[0]).map(new Function<JSONObject, JSONObject>() {
            @Override
            public JSONObject apply(JSONObject object) throws Exception {
                return executeRequest(object);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        mNotificationHandler = getTarget();
                    }

                    @Override
                    public void onNext(JSONObject o) {
                        LogUtils.d("flag","onNext "+o.toString());
                        unSubscribe();
                        try {
                            if (o == null || !o.getString("status").equals("ok")) {
                                if (mNotificationHandler != null) {
                                    mNotificationHandler.handleNotifications(getFailedCallBackName(), o);
                                }
                            } else {
                                if (mNotificationHandler != null) {
                                    mNotificationHandler.handleNotifications(getSuccessCallBackName(), o);
                                }
                            }
                        } catch (JSONException e) {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d("flag","onError "+e.getMessage());
                        unSubscribe();
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d("flag","onComplete ");
                        unSubscribe();
                    }
                });
    }

    private void unSubscribe() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
        }
    }

    private static InputStream inputStreamAfterCheck(Response response) throws IOException {
        InputStream input = response.body().byteStream();
        if ("gzip".equalsIgnoreCase(response.header("Content-Encoding"))) {
            input = new GZIPInputStream(input);
        }
        return input;
    }
    private JSONObject getErrorData(Exception e) {


        StringBuilder sb = new StringBuilder();
        sb.append("{\"status\":\"error\",")
                .append("\"error\":{")
                .append("\"code\":")
                .append("-9999,")
                .append("\"message\":\"")
                .append(e.getMessage())
                .append("\"}}");
        JSONObject object = null;
        try {
            return new JSONObject(sb.toString());
        } catch (JSONException e1) {

        }
        return object;
    }

    protected abstract String getSuccessCallBackName();

    protected abstract String getFailedCallBackName();

    protected abstract String getUrl();

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
