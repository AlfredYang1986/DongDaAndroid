package com.blackmirror.dongda.Home;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.fragment.AYNavBarFragment;
import com.blackmirror.dongda.fragment.AYTabBarFragment;

/**
 * Created by alfredyang on 29/6/17.
 */

public class AYHomeActivity extends AYActivity {

    final private String TAG = "AYHomeActivity";

    private FragmentManager mFragmentManage;
    private FragmentTransaction mTransaction;

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mFragmentManage = getSupportFragmentManager();
        mTransaction = mFragmentManage.beginTransaction();

        AYTabBarFragment fragment_tabbar = new AYTabBarFragment();
        AYNavBarFragment fragment_navbar = new AYNavBarFragment();
        mTransaction.add(R.id.activity_home, fragment_navbar, "fragment_navbar");
        mTransaction.add(R.id.activity_home, fragment_tabbar, "fragment_tabbar");
        mTransaction.commit();

    }

    @Override
    protected void bindingFragments() {

    }
}
