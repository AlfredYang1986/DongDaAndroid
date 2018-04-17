package com.blackmirror.dongda.Landing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.AYApplication;
import com.blackmirror.dongda.Tools.AppConstant;
import com.blackmirror.dongda.controllers.AYActivity;

public class NameInputActivity extends AYActivity {

    final static String TAG = "Name Input Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_input);
        AYApplication.addActivity(this);
        findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NameInputActivity.this, PhotoChangeActivity.class);
                intent.putExtra("from", AppConstant.FROM_NAME_INPUT);
                startActivity(intent);
            }
        });
    }


    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected void bindingFragments() {

    }
}
