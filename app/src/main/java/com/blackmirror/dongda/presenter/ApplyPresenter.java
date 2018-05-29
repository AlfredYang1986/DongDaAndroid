package com.blackmirror.dongda.presenter;

import com.blackmirror.dongda.domain.Interactor.ApplyServiceUseCase;
import com.blackmirror.dongda.domain.model.ApplyServiceDomainBean;
import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.ui.activity.apply.ApplyContract;
import com.blackmirror.dongda.utils.AppConstant;
import com.blackmirror.dongda.utils.LogUtils;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Create By Ruge at 2018-05-15
 */
public class ApplyPresenter implements ApplyContract.Presenter {

    @Inject
    ApplyServiceUseCase useCase;

    private ApplyContract.View view;

    @Inject
    public ApplyPresenter(ApplyContract.View view) {
        this.view = view;
    }

    @Override
    public void apply(String brand_name, String name, String category, String phone, String city) {
        useCase.apply(brand_name, name, category, phone, city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApplyServiceDomainBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ApplyServiceDomainBean bean) {
                        LogUtils.d("apply onNext");
                        if (view == null){
                            return;
                        }
                        if (bean.isSuccess){
                            view.onApplySuccess(bean);
                        }else {
                            view.onError(bean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(ApplyPresenter.class,e);
                        if (view == null){
                            return;
                        }
                        view.onError(getErrorData(e));
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

    @Override
    public void setView(ApplyContract.View view) {

    }

    @Override
    public void destroy() {

    }
}
