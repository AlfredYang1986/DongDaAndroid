package com.blackmirror.dongda.Landing;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.factory.AYFactoryManager;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LandingActivity extends AYActivity {

    final static String TAG = "Landing Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        setTitle("");
        {
            Button btn = (Button) findViewById(R.id.phone_login_btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "login dongda with phone number");
                    Intent intent = new Intent(LandingActivity.this, PhoneInputActivity.class);
                    startActivity(intent);
                }
            });
        }

        {
            Button btn = (Button) findViewById(R.id.wechat_login_btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "login dongda with wechat");
                }
            });
        }

//        {
//            AYFacade facade = this.facades.get("LoginFacade");
//            AYCommand cmd = facade.cmds.get("SendSMSCode");
//            Map<String, Object> m = new HashMap<>();
//            m.put("phoneNo", "13720200856");
//            JSONObject args = new JSONObject(m);
//            cmd.excute(args);
//        }
    }

    @Override
    public String getClassTag() {
        return TAG;
    }
}
