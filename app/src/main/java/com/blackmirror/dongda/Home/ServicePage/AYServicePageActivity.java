package com.blackmirror.dongda.Home.ServicePage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.fragment.AYFragment;
import com.blackmirror.dongda.fragment.AYListFragment;
import com.blackmirror.dongda.fragment.AYNavBarFragment;

import org.json.JSONObject;

/**
 * Created by alfredyang on 3/7/17.
 */

public class AYServicePageActivity extends AYActivity {

    final private String TAG = "AYHomeActivity";

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicepage);

        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        //接收name值
        String name = bundle.getString("name");
    }

    @Override
    protected void onStart() {
        super.onStart();
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setTitleTextWithString("服务详情");
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setRightBtnImageWithImageId(R.drawable.home_icon_love_select);
    }

    @Override
    protected void bindingFragments() {

        FragmentTransaction task = mFragmentManage.beginTransaction();
        task.add(R.id.activity_servicepage, (AYFragment)this.fragments.get("frag_navbar"));
        task.commit();
    }

    public void didNavLeftBtnClickNotify (JSONObject args) {
        Intent intent =getIntent();
        //这里使用bundle绷带来传输数据
        Bundle bundle = new Bundle();
        //传输的内容仍然是键值对的形式
        bundle.putString("second","hello world from secondActivity!");//回发的消息,hello world from secondActivity!
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);
        finish();
    }
    public void didNavRightBtnClickNotify (JSONObject args) {

        Log.d(TAG, "didNavRightBtnClickNotify: in Activity");
    }
}