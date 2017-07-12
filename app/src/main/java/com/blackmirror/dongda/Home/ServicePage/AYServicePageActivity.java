package com.blackmirror.dongda.Home.ServicePage;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.fragment.AYFragment;
import com.blackmirror.dongda.fragment.AYNavBarFragment;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alfredyang on 3/7/17.
 */

public class AYServicePageActivity extends AYActivity {

    final private String TAG = "AYServicePageActivity";
    private JSONObject js_service_info;

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicepage);

        //新页面接收数据
        String info = this.getIntent().getStringExtra("service_info");
        Log.d(TAG, "onCreate: "+info);
        //接收name值
        try {
            js_service_info = new JSONObject(info);
            Log.d(TAG, "onCreate: " + js_service_info.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setTitleTextWithString("服务详情");
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setRightBtnImageWithImageId(R.drawable.home_icon_love_select);
        ((AYServicePageBottomFragment)this.fragments.get("frag_servpage_bottom")).setServPageBottomInfo(js_service_info);
    }

    @Override
    protected void bindingFragments() {

        Log.d(TAG, "bindingFragments: "+this.fragments);
        FragmentTransaction task = mFragmentManage.beginTransaction();
        task.add(R.id.activity_servicepage, (AYFragment)this.fragments.get("frag_navbar"));
        task.add(R.id.activity_servicepage, (AYFragment)this.fragments.get("frag_servpage_bottom"));
        task.commit();
    }

    public void didNavLeftBtnClickNotify (JSONObject args) {
//        Intent intent = getIntent();
//        //这里使用bundle绷带来传输数据
//        Bundle bundle = new Bundle();
//        //传输的内容仍然是键值对的形式
//        bundle.putString("second","hello world from secondActivity!");//回发的消息,hello world from secondActivity!
//        intent.putExtras(bundle);
//        setResult(RESULT_OK,intent);
        this.finish();
    }

    public void didNavRightBtnClickNotify (JSONObject args) {
        Log.d(TAG, "didNavRightBtnClickNotify: in Activity");
    }
}
