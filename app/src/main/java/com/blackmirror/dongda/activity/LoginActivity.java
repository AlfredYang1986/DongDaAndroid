package com.blackmirror.dongda.activity;

import android.os.Bundle;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.controllers.AYActivity;

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
