package com.blackmirror.dongda.Landing;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.blackmirror.dongda.Home.HomeActivity.AYHomeActivity;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.AYApplication;
import com.blackmirror.dongda.Tools.AppConstant;
import com.blackmirror.dongda.Tools.BasePrefUtils;
import com.blackmirror.dongda.Tools.OtherUtils;
import com.blackmirror.dongda.Tools.ToastUtils;
import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.facade.DongdaCommonFacade.SQLiteProxy.DAO.AYDaoUserProfile;
import com.blackmirror.dongda.model.serverbean.UpdateUserInfoServerBean;
import com.blackmirror.dongda.model.uibean.UpdateUserInfoUiBean;

import org.json.JSONException;
import org.json.JSONObject;

public class NameInputActivity extends AYActivity {

    final static String TAG = "Name Input Activity";
    private Button btn_next;
    private EditText et_input_name;
    private boolean has_photo;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_input);
        AYApplication.addActivity(this);
        OtherUtils.setStatusBarColor(this,getResources().getColor(R.color.colorPrimary));
        has_photo = getIntent().getBooleanExtra("has_photo", true);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        btn_next = findViewById(R.id.btn_next);
        et_input_name = findViewById(R.id.et_input_name);
    }

    private void initData() {

    }

    private void initListener() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = et_input_name.getText().toString().trim();
                if (TextUtils.isEmpty(name)){
                    ToastUtils.showShortToast("请输入正确的名字!");
                    return;
                }
                if (has_photo){
                    changeName();
                }else {
                    enterPhotoChangeActivity();
                }
            }
        });
    }

    private void changeName() {
        try {
            showProcessDialog();
            String json="{\"token\":\""+ BasePrefUtils.getAuthToken()+"\",\"condition\":{\"user_id\":\""+BasePrefUtils.getUserId()+"\"},\"profile\":{\"screen_name\":\""+name+"\",}}";
            JSONObject object = new JSONObject(json);
            facades.get("LoginFacade").execute("UpdateProfile",object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void enterPhotoChangeActivity() {

        Intent intent = new Intent(NameInputActivity.this, PhotoChangeActivity.class);
        intent.putExtra("from", AppConstant.FROM_NAME_INPUT);
        intent.putExtra("name", name);
        startActivity(intent);
    }


    /**
     * 修改用户信息回调
     * @param args
     * @return
     */
    public void AYUpdateProfileCommandSuccess(JSONObject args) {
        closeProcessDialog();
        UpdateUserInfoServerBean serverBean = JSON.parseObject(args.toString(), UpdateUserInfoServerBean.class);
        UpdateUserInfoUiBean uiBean = new UpdateUserInfoUiBean(serverBean);

        if (uiBean.isSuccess){
            AYDaoUserProfile profile = new AYDaoUserProfile();
            profile.auth_token = uiBean.token;
            profile.user_id = uiBean.user_id;
            profile.screen_name = uiBean.screen_name;
            profile.screen_photo = uiBean.screen_photo;
            profile.is_current=1;
            AYCommand cmd = facades.get("DongdaCommanFacade").cmds.get("UpdateLocalProfile");
            long result = cmd.excute(profile);
            if (result>0){
                closeProcessDialog();
                ToastUtils.showShortToast("修改成功!");
                startActivity(new Intent(NameInputActivity.this, AYHomeActivity.class));
                AYApplication.finishAllActivity();
            }else {
                closeProcessDialog();
                ToastUtils.showShortToast("系统异常(SQL)");
            }
        }
    }

    public void AYUpdateProfileCommandFailed(JSONObject args) {
        closeProcessDialog();
        ToastUtils.showShortToast("修改失败!");
    }

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected void bindingFragments() {

    }
}
