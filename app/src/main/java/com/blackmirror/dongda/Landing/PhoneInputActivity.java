package com.blackmirror.dongda.Landing;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.facade.DongdaCommonFacade.SQLiteProxy.DAO.AYDaoUserProfile;
import com.blackmirror.dongda.facade.PhoneLoginFacade.LoginFacadeCommands.AYSendSMSCodeCommand;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;

public class PhoneInputActivity extends AYActivity {

    final static String TAG = "Phone Input Activity";

    private EditText et_phone = null;
    private EditText et_code = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_input);

        setTitle("");

        et_phone = (EditText) findViewById(R.id.phone_edit_text);
        et_code = (EditText) findViewById(R.id.code_edit_text);

        Button sms_code = (Button) findViewById(R.id.request_sms_code);
        sms_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "request SMS code from server");

                String input_phone = et_phone.getText().toString();
                if (input_phone != null && !input_phone.isEmpty()) {
                    AYFacade facade = facades.get("LoginFacade");
                    AYCommand cmd = facade.cmds.get("SendSMSCode");
                    Map<String, Object> m = new HashMap<>();
                    m.put("phoneNo", input_phone);
                    JSONObject args = new JSONObject(m);
                    cmd.excute(args);
                }
            }
        });

        Button next_step = (Button) findViewById(R.id.landing_phone_input_next_step);
        next_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginWithPhoneAndCode();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = false;
        switch (item.getItemId()) {
            case R.id.menu_next_step:
                Log.i(TAG, "next step selected");
                LoginWithPhoneAndCode();
                break;
            default:
                result = super.onOptionsItemSelected(item);
        }
        return result;
    }

    @Override
    public String getClassTag() {
        return TAG;
    }


    protected class SendSMSCodeResult {
        public SendSMSCodeResult(JSONObject args) {
            try {
                if (args.getString("status").equals("ok")) {
                    isSuccess = true;
                    reg_token = args.getJSONObject("result").getString("reg_token");
                    phoneNo = args.getJSONObject("result").getString("phoneNo");
                } else {
                    isSuccess = false;
                    errorMessage = args.getJSONObject("error").getString("messages");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                isSuccess = false;
                errorMessage = "unknown message";
            }
        }

        public Boolean canGoNext() {
            return isSuccess && !reg_token.isEmpty() && !phoneNo.isEmpty();
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public String getReg_token() {
            return reg_token;
        }

        private Boolean isSuccess;
        private String errorMessage = "";
        private String reg_token = "";
        private String phoneNo = "";
    }

    private SendSMSCodeResult sms_result = null;

    protected Boolean AYSendSMSCodeCommandSuccess(JSONObject arg) {
        Log.i(TAG, "send sms code result is " + arg.toString());
        Toast.makeText(this, "发送SMS Code成功", LENGTH_LONG).show();
        sms_result = new SendSMSCodeResult(arg);
        return true;
    }

    protected Boolean AYSendSMSCodeCommandFailed(JSONObject arg) {
        Log.i(TAG, "send sms code error is " + arg.toString());
        Toast.makeText(this, sms_result.getErrorMessage(), LENGTH_LONG).show();
        sms_result = new SendSMSCodeResult(arg);
        return true;
    }

    protected void LoginWithPhoneAndCode() {
        Log.i(TAG, "login with phone code");
        if (sms_result.canGoNext()) {

            String phone = et_phone.getText().toString();
            String code = et_code.getText().toString();

            AYFacade facade = facades.get("LoginFacade");
            AYCommand cmd = facade.cmds.get("LoginWithPhone");
            Map<String, Object> m = new HashMap<>();
            m.put("phoneNo", phone);
            m.put("reg_token", sms_result.getReg_token());
            m.put("code", code);
            JSONObject args = new JSONObject(m);
            cmd.excute(args);

        } else {
            Toast.makeText(this, R.string.phone_input_next_step_error, LENGTH_LONG).show();
        }
    }

    Boolean AYLoginWithPhoneCommandSuccess(JSONObject args) {
        Toast.makeText(this, "登陆成功", LENGTH_LONG).show();

        String screen_name = null;
        try {
            screen_name = args.getJSONObject("result").getString("screen_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (screen_name == null || screen_name.isEmpty()) {
            try {
                Intent intent = new Intent(PhoneInputActivity.this, NameInputActivity.class);
                AYDaoUserProfile p = new AYDaoUserProfile(args.getJSONObject("result"));
                intent.putExtra("current_user", p);
                startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Intent intent = new Intent(PhoneInputActivity.this, PhotoChangeActivity.class);
                AYDaoUserProfile p = new AYDaoUserProfile(args.getJSONObject("result"));
                intent.putExtra("current_user", p);
                startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    Boolean AYLoginWithPhoneCommandFailed(JSONObject args) {
        Toast.makeText(this, "登陆失败", LENGTH_LONG);
        return true;
    }

    @Override
    protected void bindingFragments() {

    }
}
