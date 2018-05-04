package com.blackmirror.dongda.domain.Interactor;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.data.LoginRepository;
import com.blackmirror.dongda.domain.base.BaseUserCase;
import com.blackmirror.dongda.model.request.PhoneLoginRequestBean;
import com.blackmirror.dongda.model.response.PhoneLoginResponseBean;
import com.blackmirror.dongda.model.response.uibean.PhoneLoginBean;
import com.blackmirror.dongda.utils.AYApplication;
import com.blackmirror.dongda.utils.AYPrefUtils;
import com.blackmirror.dongda.utils.AppConstant;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Ruge on 2018-05-04 下午2:32
 */
public class LoginTask extends BaseUserCase<PhoneLoginRequestBean>{

    LoginRepository repository = new LoginRepository();

    private LoginCallback listener;

    public void setLoginCallback(LoginCallback listener) {
        this.listener = listener;
    }


    public static LoginTask getInstance() {
        return new LoginTask();
    }

    @Override
    public void executeTask(PhoneLoginRequestBean bean) {
        repository.phoneLogin(bean)
                .map(new Function<PhoneLoginResponseBean, PhoneLoginBean>() {
                    @Override
                    public PhoneLoginBean apply(PhoneLoginResponseBean responseBean) throws Exception {
                        PhoneLoginBean bean = new PhoneLoginBean();
                        if ("ok".equals(responseBean.status)) {
                            bean.isSuccess = true;
                            if (responseBean.result != null) {
                                bean.auth_token = responseBean.result.auth_token;
                            }
                            if (responseBean.result != null && responseBean.result.user != null) {
                                bean.screen_name = responseBean.result.user.screen_name;
                                bean.has_auth_phone = responseBean.result.user.has_auth_phone;
                                bean.current_device_type = responseBean.result.user.current_device_type;
                                bean.is_service_provider = responseBean.result.user.is_service_provider;
                                bean.user_id = responseBean.result.user.user_id;
                                bean.screen_photo = responseBean.result.user.screen_photo;
                                bean.current_device_id = responseBean.result.user.current_device_id;

                                AYPrefUtils.setUserId(bean.user_id);
                                AYPrefUtils.setAuthToken(bean.auth_token);
                            }
                        }else {
                            bean.code = responseBean.error!=null ? responseBean.error.code : AppConstant.NET_UNKNOWN_ERROR;
                            bean.message = responseBean.error!=null ? responseBean.error.message : AYApplication.appContext.getString(R.string.net_work_error);
                        }
                        return bean;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PhoneLoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PhoneLoginBean bean) {
                        if (listener == null){
                            return;
                        }

                        if (bean.isSuccess) {
                            listener.onLoginSuccess(bean);
                        }else {
                            listener.onLoginError(bean);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (listener != null) {
                            PhoneLoginBean bean = new PhoneLoginBean();
                            bean.code = AppConstant.NET_UNKNOWN_ERROR;
                            bean.message = e.getMessage();
                            listener.onLoginError(bean);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public interface LoginCallback {

        void onLoginSuccess(PhoneLoginBean response);

        void onLoginError(PhoneLoginBean response);
    }

}
