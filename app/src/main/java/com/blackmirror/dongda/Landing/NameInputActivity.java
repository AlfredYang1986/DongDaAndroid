package com.blackmirror.dongda.Landing;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.controllers.AYActivity;

public class NameInputActivity extends AYActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_input);

        setTitle("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.landing_next_menu, menu);
        return result;
    }
}
