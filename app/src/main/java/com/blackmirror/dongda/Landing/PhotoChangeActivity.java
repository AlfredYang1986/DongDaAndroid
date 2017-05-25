package com.blackmirror.dongda.Landing;

import android.os.Bundle;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.controllers.AYActivity;

public class PhotoChangeActivity extends AYActivity {

    final static String TAG = "Photo Change Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_change);
    }

    @Override
    public String getClassTag() {
        return TAG;
    }
}
