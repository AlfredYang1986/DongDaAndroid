package com.blackmirror.dongda.Home;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.AYScreenSingleton;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.fragment.AYNavBarFragment;
import com.blackmirror.dongda.fragment.AYTabBarFragment;

import org.json.JSONObject;

/**
 * Created by alfredyang on 29/6/17.
 */

public class AYHomeActivity extends AYActivity {

    final private String TAG = "AYHomeActivity";

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }

    @Override
    protected void onStart() {
        super.onStart();
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setTitleTextWithString("123");
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setLeftBtnImageWithImageId(R.drawable.nav_icon_back_black);
    }

    @Override
    protected void bindingFragments() {

        FragmentTransaction task = mFragmentManage.beginTransaction();
        task.add(R.id.activity_home, this.fragments.get("frag_navbar"));
        task.add(R.id.activity_home, this.fragments.get("frag_tabbar"));
        task.commit();
    }
    
    
    public void didNavLeftBtnClickNotify (JSONObject args) {
        Log.d(TAG, "didNavLeftBtnClickNotify: in Activity");

    }

    public void didNavRightBtnClickNotify (JSONObject args) {

        Log.d(TAG, "didNavRightBtnClickNotify: in Activity");
    }
}
