package com.blackmirror.dongda.domain.Interactor;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.data.LoginRepository;
import com.blackmirror.dongda.domain.base.BaseUserCase;
import com.blackmirror.dongda.model.request.SendSmsRequestBean;
import com.blackmirror.dongda.model.response.SendSmsResponseBean;
import com.blackmirror.dongda.model.response.uibean.SendSmsBean;
import com.blackmirror.dongda.utils.AYApplication;
import com.blackmirror.dongda.utils.AppConstant;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ruge on 2018-05-04 下午5:45
 */
public class SendSmsTask extends BaseUserCase<SendSmsRequestBean> {

    LoginRepository repository = new LoginRepository();

    private SendSmsCallback listener;

    public void setSendSmsCallback(SendSmsCallback listener) {
        this.listener = listener;
    }

    @Override
    public void executeTask(SendSmsRequestBean bean) {
        repository.sendSms(bean)
                .map(new Function<SendSmsResponseBean, SendSmsBean>() {
                    @Override
                    public SendSmsBean apply(SendSmsResponseBean responseBean) throws Exception {
                        SendSmsBean bean = new SendSmsBean();
                        if ("ok".equals(responseBean.status)){
                            bean.isSuccess = true;
                            if ( responseBean.result != null && responseBean.result.reg != null) {
                                bean.phone=responseBean.result.reg.phone;
                                bean.reg_token=responseBean.result.reg.reg_token;
                                bean.is_reg=responseBean.result.reg.is_reg;
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
                .subscribe(new Observer<SendSmsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SendSmsBean bean) {
                        if (listener == null) {
                            return;
                        }
                        if (bean.isSuccess){
                            listener.onSendSuccess(bean);
                        }else {
                            listener.onSendError(bean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (listener != null) {
                            SendSmsBean bean = new SendSmsBean();
                            bean.code = AppConstant.NET_UNKNOWN_ERROR;
                            bean.message = e.getMessage();
                            listener.onSendError(bean);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public interface SendSmsCallback {

        void onSendSuccess(SendSmsBean response);

        void onSendError(SendSmsBean response);
    }

}
