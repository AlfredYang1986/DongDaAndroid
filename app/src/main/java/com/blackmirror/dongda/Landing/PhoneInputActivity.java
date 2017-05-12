package com.blackmirror.dongda.Landing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.blackmirror.dongda.R;

public class PhoneInputActivity extends AppCompatActivity {

    final static String TAG = "phone input activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_input);

        setTitle("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.landing_next_menu, menu);
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = false;
        switch (item.getItemId()) {
            case R.id.menu_next_step:
                Log.i(TAG, "next step selected");
                Intent intent = new Intent(PhoneInputActivity.this, NameInputActivity.class);
                startActivity(intent);
                break;
            default:
                result = super.onOptionsItemSelected(item);
        }
        return result;
    }
}
