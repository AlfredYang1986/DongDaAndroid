package com.blackmirror.dongda.ui.activity.Landing;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.data.model.request.PhoneLoginRequestBean;
import com.blackmirror.dongda.data.model.request.SendSmsRequestBean;
import com.blackmirror.dongda.di.component.DaggerPhoneInputComponent;
import com.blackmirror.dongda.domain.model.PhoneLoginBean;
import com.blackmirror.dongda.domain.model.SendSmsBean;
import com.blackmirror.dongda.facade.DongdaCommonFacade.SQLiteProxy.DAO.AYDaoUserProfile;
import com.blackmirror.dongda.model.serverbean.ErrorInfoServerBean;
import com.blackmirror.dongda.model.serverbean.PhoneLoginServerBean;
import com.blackmirror.dongda.model.serverbean.SendSmsServerBean;
import com.blackmirror.dongda.model.uibean.ErrorInfoUiBean;
import com.blackmirror.dongda.model.uibean.PhoneLoginUiBean;
import com.blackmirror.dongda.model.uibean.SendSmsUiBean;
import com.blackmirror.dongda.presenter.PhoneLoginPresenter;
import com.blackmirror.dongda.ui.PhoneLoginContract;
import com.blackmirror.dongda.ui.activity.HomeActivity.AYHomeActivity;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.blackmirror.dongda.utils.AYPrefUtils;
import com.blackmirror.dongda.utils.AppConstant;
import com.blackmirror.dongda.utils.DeviceUtils;
import com.blackmirror.dongda.utils.DongdaApplication;
import com.blackmirror.dongda.utils.LogUtils;
import com.blackmirror.dongda.utils.SnackbarUtils;
import com.blackmirror.dongda.utils.ToastUtils;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;


public class PhoneInputActivity extends BaseActivity implements PhoneLoginContract.View {

    final static String TAG = "Phone Input Activity";

    public PhoneLoginPresenter presenter;

    private EditText et_phone = null;
    private EditText et_code = null;
    private Button sms_code;
    private Disposable mSms_disposable;
    private Button next_step;
    private SendSmsUiBean sendSmsUiBean;
    private SendSmsBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DongdaApplication.addActivity(this);
        DeviceUtils.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_phone_input;
    }

    @Override
    protected void initInject() {
        presenter = DaggerPhoneInputComponent.builder()
                .activity(this)
                .view(this)
                .build()
                .getPhoneLoginPresenter();
    }

    @Override
    protected void initView() {
        et_phone = findViewById(R.id.phone_edit_text);
        et_code = findViewById(R.id.code_edit_text);
        sms_code = findViewById(R.id.request_sms_code);
        next_step = findViewById(R.id.landing_phone_input_next_step);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        sms_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.d(TAG, "request SMS code from server");

                String input_phone = et_phone.getText().toString();

                if (TextUtils.isEmpty(input_phone)) {
                    ToastUtils.showShortToast(getString(R.string.phone_not_empty));
                    return;
                }

                if (input_phone.trim().length() != 11) {
                    ToastUtils.showShortToast(getString(R.string.input_phone_error));
                    return;
                }

                /*AYFacade facade = facades.get("LoginFacade");
                LogUtils.d("PhoneInputActivity "+Thread.currentThread().getName());
                Map<String, Object> m = new HashMap<>();
                m.put("phone", input_phone);
                JSONObject args = new JSONObject(m);

                Map<String, Object> m1 = new HashMap<>();
                m1.put("login", "login");
                JSONObject login = new JSONObject(m1);

                facade.execute("SendSMSCode",args,login);*/
                SendSmsRequestBean bean = new SendSmsRequestBean();
                bean.phone_number = input_phone;
                //                loginFacade = new LoginFacade();
                //                loginFacade.setView(PhoneInputActivity.this);
                //                loginFacade.sendSms(bean);
                LogUtils.d("hello zxctyuogggooo");
                presenter.sendSms(bean);
                countDownSmsTime();

            }
        });

        next_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginWithPhoneAndCode();
            }
        });
    }

    private void countDownSmsTime() {
        sms_code.setEnabled(false);
        Observable.intervalRange(0, 30, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mSms_disposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        sms_code.setText(String.format(getString(R.string.sms_count_down), String.valueOf(30 - aLong)));
                    }

                    @Override
                    public void onError(Throwable e) {
                        sms_code.setEnabled(true);
                    }

                    @Override
                    public void onComplete() {
                        sms_code.setEnabled(true);
                        sms_code.setText(getString(R.string.phone_input_btn_action_query_code));
                    }
                });

    }



    public String getClassTag() {
        return TAG;
    }


    public Boolean AYSendSMSCodeCommandSuccess(JSONObject arg) {
        LogUtils.d("PhoneInputActivity " + Thread.currentThread().getName());

        LogUtils.d(TAG, "send sms code result is " + arg.toString());
        ToastUtils.showShortToast(getString(R.string.send_sms_code_success));
        //        sms_result = new SendSMSCodeResult(arg);
        SendSmsServerBean bean = JSON.parseObject(arg.toString(), SendSmsServerBean.class);
        sendSmsUiBean = new SendSmsUiBean(bean);
        return true;
    }

    public Boolean AYSendSMSCodeCommandFailed(JSONObject arg) {
        LogUtils.d(TAG, "send sms code error is " + arg.toString());
        //        Toast.makeText(this, sms_result.getErrorMessage(), LENGTH_LONG).show();
        //        sms_result = new SendSMSCodeResult(arg);
        ErrorInfoServerBean serverBean = JSON.parseObject(arg.toString(), ErrorInfoServerBean.class);
        ErrorInfoUiBean uiBean = new ErrorInfoUiBean(serverBean);
        if (uiBean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(sms_code, uiBean.message);
        } else {
            ToastUtils.showShortToast(uiBean.message + "(" + uiBean.code + ")");
        }
        return true;
    }

    protected void LoginWithPhoneAndCode() {
        LogUtils.d(TAG, "login with phone code");
        String phone = et_phone.getText().toString().trim();
        String code = et_code.getText().toString().trim();
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            ToastUtils.showShortToast(R.string.input_phone_error);
            return;
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtils.showShortToast(getString(R.string.input_code_error));
            return;
        }


        PhoneLoginRequestBean requestBean = new PhoneLoginRequestBean();
        requestBean.reg_token = bean.reg_token;
        requestBean.phone_number = bean.phone;
        requestBean.code = code;
        //        loginFacade.login(requestBean);

        if (bean != null && bean.isSuccess) {
            showProcessDialog(getString(R.string.logining_process));
            presenter.login(requestBean);
           /* AYFacade facade = facades.get("LoginFacade");
            Map<String, Object> m = new HashMap<>();
            m.put("phone", phone);
            m.put("reg_token", sendSmsUiBean.reg_token);
            m.put("code", code);
            JSONObject args = new JSONObject(m);
//            cmd.execute(args);

            //登陆不刷新ImageToken

            Map<String, Object> m1 = new HashMap<>();
            m1.put("login", "login");
            JSONObject login = new JSONObject(m1);

            facade.execute("LoginWithPhone",args, login);*/

        } else {
            ToastUtils.showShortToast(R.string.phone_input_next_step_error);
        }
    }

    public Boolean AYLoginWithPhoneCommandSuccess(JSONObject args) {
        closeProcessDialog();
        ToastUtils.showShortToast(R.string.login_success);
        LogUtils.d("AYLoginWithPhoneCommandSuccess " + args.toString());

        PhoneLoginServerBean serverBean = JSON.parseObject(args.toString(), PhoneLoginServerBean.class);
        PhoneLoginUiBean uiBean = new PhoneLoginUiBean(serverBean);

        if (!uiBean.isSuccess) {
            ToastUtils.showShortToast(getString(R.string.login_failare));
            return false;
        }


        AYPrefUtils.setUserId(uiBean.user_id);
        AYPrefUtils.setAuthToken(uiBean.auth_token);

        if (TextUtils.isEmpty(uiBean.screen_name)) {

            Intent intent = new Intent(PhoneInputActivity.this, NameInputActivity.class);
            AYDaoUserProfile p = new AYDaoUserProfile(args);
            intent.putExtra("has_photo", !TextUtils.isEmpty(uiBean.screen_photo));
            startActivity(intent);

        } else if (TextUtils.isEmpty(uiBean.screen_photo)) {

            Intent intent = new Intent(PhoneInputActivity.this, PhotoChangeActivity.class);
            intent.putExtra("from", AppConstant.FROM_PHONE_INPUT);
            AYDaoUserProfile p = new AYDaoUserProfile(args);
            intent.putExtra("current_user", p);
            intent.putExtra("name", uiBean.screen_name);
            startActivity(intent);
        } else {
            Intent intent = new Intent(PhoneInputActivity.this, AYHomeActivity.class);
            intent.putExtra("img_uuid", uiBean.screen_photo);
            startActivity(intent);
            DongdaApplication.finishAllActivity();
        }

        return true;
    }

    public Boolean AYLoginWithPhoneCommandFailed(JSONObject args) {
        closeProcessDialog();
        ErrorInfoServerBean serverBean = JSON.parseObject(args.toString(), ErrorInfoServerBean.class);
        ErrorInfoUiBean uiBean = new ErrorInfoUiBean(serverBean);
        if (uiBean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(sms_code, uiBean.message);
        } else {
            ToastUtils.showShortToast(uiBean.message + "(" + uiBean.code + ")");
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSms_disposable != null && !mSms_disposable.isDisposed()) {
            mSms_disposable.dispose();
            mSms_disposable = null;
        }
    }

    @Override
    public void onBackPressed() {
        DongdaApplication.removeActivity(this);
        super.onBackPressed();
    }


    protected void bindingFragments() {

    }

    private void gotoActivity(PhoneLoginBean bean) {
        if (!bean.isSuccess) {
            ToastUtils.showShortToast(getString(R.string.login_failare));
            return;
        }

        ToastUtils.showShortToast(R.string.login_success);

        if (TextUtils.isEmpty(bean.screen_name)) {

            Intent intent = new Intent(PhoneInputActivity.this, NameInputActivity.class);
            intent.putExtra("has_photo", !TextUtils.isEmpty(bean.screen_photo));
            startActivity(intent);

        } else if (TextUtils.isEmpty(bean.screen_photo)) {

            Intent intent = new Intent(PhoneInputActivity.this, PhotoChangeActivity.class);
            intent.putExtra("from", AppConstant.FROM_PHONE_INPUT);
            intent.putExtra("name", bean.screen_name);
            startActivity(intent);
        } else {
            Intent intent = new Intent(PhoneInputActivity.this, AYHomeActivity.class);
            intent.putExtra("img_uuid", bean.screen_photo);
            startActivity(intent);
            DongdaApplication.finishAllActivity();
        }
    }

    @Override
    public void loginSuccess(PhoneLoginBean bean) {
        closeProcessDialog();
        gotoActivity(bean);
    }

    @Override
    public void loginError(com.blackmirror.dongda.domain.model.PhoneLoginBean bean) {
        closeProcessDialog();
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(sms_code, bean.message);
        } else {
            ToastUtils.showShortToast(bean.message + "(" + bean.code + ")");
        }
    }

    @Override
    public void sendSmsSuccess(SendSmsBean bean) {
        ToastUtils.showShortToast(getString(R.string.send_sms_code_success));
        closeProcessDialog();
        this.bean = bean == null ? new SendSmsBean():bean;
    }

    @Override
    public void sendSmsError(com.blackmirror.dongda.domain.model.SendSmsBean bean) {
        closeProcessDialog();
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(sms_code, bean.message);
        } else {
            ToastUtils.showShortToast(bean.message + "(" + bean.code + ")");
        }
    }
}
