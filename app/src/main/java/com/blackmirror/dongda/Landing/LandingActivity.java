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
import com.blackmirror.dongda.factory.common.AYFactory;

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

        {
            AYFacade facade = (AYFacade) AYFactoryManager.getInstance(this).queryInstance("facade", "DongdaCommanFacade");
            AYCommand cmd = facade.cmds.get("QueryCurrentLoginUser");
            Object o = cmd.excute();
            if (o != null) {

                /**
                 * 打印已登陆用户
                 */
                Log.i(TAG, o.toString());
            }
        }
    }

    @Override
    public String getClassTag() {
        return TAG;
    }
}
