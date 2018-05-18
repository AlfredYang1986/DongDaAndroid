package com.blackmirror.dongda.presenter;

import com.blackmirror.dongda.domain.Interactor.NearServiceUseCase;
import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.domain.model.NearServiceDomainBean;
import com.blackmirror.dongda.ui.Contract;
import com.blackmirror.dongda.utils.AppConstant;
import com.blackmirror.dongda.utils.LogUtils;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Create By Ruge at 2018-05-18
 */
public class NearServicePresenter implements Contract.NearServicePresenter {

    @Inject
    NearServiceUseCase nearServiceUseCase;

    private Contract.NearServiceView nearServiceView;

    @Inject
    public NearServicePresenter(Contract.NearServiceView nearServiceView) {
        this.nearServiceView = nearServiceView;
    }

    @Override
    public void getNearService(double latitude, double longitude) {
        nearServiceUseCase.getNearService(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NearServiceDomainBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NearServiceDomainBean bean) {
                        if (nearServiceView == null){
                            return;
                        }
                        if (bean.isSuccess){
                            nearServiceView.onGetNearServiceSuccess(bean);
                        }else {
                            nearServiceView.onGetDataError(bean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(NearServicePresenter.class,e);
                        if (nearServiceView == null){
                            return;
                        }
                        nearServiceView.onGetDataError(getErrorData(e));

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private BaseDataBean getErrorData(Throwable e) {
        BaseDataBean bean = new BaseDataBean();
        bean.code = AppConstant.NET_UNKNOWN_ERROR;
        bean.message = e.getMessage();
        return bean;
    }
}
