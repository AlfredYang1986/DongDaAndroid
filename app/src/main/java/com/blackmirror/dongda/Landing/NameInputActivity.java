package com.blackmirror.dongda.Landing;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.controllers.AYActivity;

public class NameInputActivity extends AYActivity {

    final static String TAG = "Name Input Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_input);
        findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NameInputActivity.this,PhotoChangeActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.landing_next_menu, menu);
        return result;
    }

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected void bindingFragments() {

    }
}
