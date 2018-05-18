package com.blackmirror.dongda.ui.activity.Landing;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.base.AYApplication;
import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.di.component.DaggerNameInputComponent;
import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.domain.model.UpdateUserInfoBean;
import com.blackmirror.dongda.domain.model.UpdateUserInfoDomainBean;
import com.blackmirror.dongda.facade.DongdaCommonFacade.SQLiteProxy.DAO.AYDaoUserProfile;
import com.blackmirror.dongda.model.serverbean.ErrorInfoServerBean;
import com.blackmirror.dongda.model.serverbean.UpdateUserInfoServerBean;
import com.blackmirror.dongda.model.uibean.ErrorInfoUiBean;
import com.blackmirror.dongda.model.uibean.UpdateUserInfoUiBean;
import com.blackmirror.dongda.presenter.UserInfoPresenter;
import com.blackmirror.dongda.ui.Contract;
import com.blackmirror.dongda.ui.activity.HomeActivity.AYHomeActivity;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.blackmirror.dongda.utils.AYPrefUtils;
import com.blackmirror.dongda.utils.AppConstant;
import com.blackmirror.dongda.utils.DeviceUtils;
import com.blackmirror.dongda.utils.SnackbarUtils;
import com.blackmirror.dongda.utils.ToastUtils;

import org.json.JSONObject;

public class NameInputActivity extends BaseActivity implements Contract.NameInputView{

    final static String TAG = "Name Input Activity";
    private Button btn_next;
    private EditText et_input_name;
    private boolean has_photo;
    private String name;
    private UserInfoPresenter presenter;

    @Override
    protected void init() {
        AYApplication.addActivity(this);
        has_photo = getIntent().getBooleanExtra("has_photo", true);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_name_input;
    }

    @Override
    protected void initInject() {
        presenter = DaggerNameInputComponent.builder()
                .activity(this)
                .build()
                .getUserInfoPresenter();
        presenter.setNameInputView(this);
    }

    @Override
    protected void initView() {
        btn_next = findViewById(R.id.btn_next);
        et_input_name = findViewById(R.id.et_input_name);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = et_input_name.getText().toString().trim();
                if (TextUtils.isEmpty(name)){
                    ToastUtils.showShortToast(getString(R.string.input_name_error));
                    return;
                }
                if (has_photo){
                    changeName();
                }else {
                    AYApplication.addActivity(NameInputActivity.this);
                    enterPhotoChangeActivity();
                }
            }
        });
    }

    private void changeName() {
        showProcessDialog();
        UpdateUserInfoDomainBean bean = new UpdateUserInfoDomainBean();
        bean.json = "{\"token\":\""+ AYPrefUtils.getAuthToken()+"\",\"condition\":{\"user_id\":\""+ AYPrefUtils.getUserId()+"\"},\"profile\":{\"screen_name\":\""+name+"\"}}";
        presenter.updateUserInfo(bean);
        /*try {
            showProcessDialog();
            String json="{\"token\":\""+ AYPrefUtils.getAuthToken()+"\",\"condition\":{\"user_id\":\""+ AYPrefUtils.getUserId()+"\"},\"profile\":{\"screen_name\":\""+name+"\"}}";
            JSONObject object = new JSONObject(json);
            Map<String, Object> m1 = new HashMap<>();
            m1.put("login", "login");
            JSONObject login = new JSONObject(m1);
//            facades.get("LoginFacade").execute("UpdateProfile",object,login);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
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
//            AYCommand cmd = facades.get("LoginFacade").cmds.get("UpdateLocalProfile");
            AYCommand cmd = null;
            long result = cmd.execute(profile);
            if (result>0){
                closeProcessDialog();
                ToastUtils.showShortToast(getString(R.string.update_user_info_success));
                Intent intent = new Intent(NameInputActivity.this, AYHomeActivity.class);
                intent.putExtra("img_uuid",uiBean.screen_photo);
                startActivity(intent);
                AYApplication.finishAllActivity();
            }else {
                closeProcessDialog();
                ToastUtils.showShortToast(getString(R.string.update_sql_error));
            }
        }
    }

    public void AYUpdateProfileCommandFailed(JSONObject args) {
        closeProcessDialog();
        ErrorInfoServerBean serverBean = JSON.parseObject(args.toString(), ErrorInfoServerBean.class);
        ErrorInfoUiBean uiBean = new ErrorInfoUiBean(serverBean);
        if (uiBean.code==AppConstant.NET_WORK_UNAVAILABLE){
            SnackbarUtils.show(btn_next,uiBean.message);
        }else {
            ToastUtils.showShortToast(uiBean.message+"("+uiBean.code+")");
        }
//        ToastUtils.showShortToast("修改失败!");
    }

    @Override
    public void onBackPressed() {
        AYApplication.removeActivity(this);
        super.onBackPressed();
    }

    @Override
    public void onUpdateUserInfoSuccess(UpdateUserInfoBean bean) {
        closeProcessDialog();
        ToastUtils.showShortToast(getString(R.string.update_user_info_success));
        Intent intent = new Intent(NameInputActivity.this, AYHomeActivity.class);
        intent.putExtra("img_uuid",bean.screen_photo);
        startActivity(intent);
        AYApplication.finishAllActivity();
    }

    @Override
    public void onError(BaseDataBean bean) {
        closeProcessDialog();
        if (bean.code==AppConstant.NET_WORK_UNAVAILABLE){
            SnackbarUtils.show(btn_next,bean.message);
        }else {
            ToastUtils.showShortToast(bean.message+"("+bean.code+")");
        }
    }

    @Override
    protected void setStatusBarColor() {
        DeviceUtils.setStatusBarColor(this,getResources().getColor(R.color.colorPrimary));
    }
}
