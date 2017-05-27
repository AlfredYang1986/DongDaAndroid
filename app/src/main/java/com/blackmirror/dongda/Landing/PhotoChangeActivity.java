package com.blackmirror.dongda.Landing;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.facade.DongdaCommonFacade.SQLiteProxy.DAO.AYDaoUserProfile;

public class PhotoChangeActivity extends AYActivity {

    final static String TAG = "Photo Change Activity";

    private AYDaoUserProfile p = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_change);

        p = (AYDaoUserProfile) getIntent().getSerializableExtra("current_user");

        Button btn = (Button) findViewById(R.id.landing_enter_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "enter button clicked");
            }
        });
    }

    @Override
    public String getClassTag() {
        return TAG;
    }
}
