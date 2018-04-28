package com.blackmirror.dongda.ui.activity;

import android.os.Bundle;

import com.blackmirror.dongda.R;

public class LoginActivity extends AYActivity {

    final static String TAG = "Login Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void bindingFragments() {

    }
    @Override
    public String getClassTag() {
        return TAG;
    }
}
