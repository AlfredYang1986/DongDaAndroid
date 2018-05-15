package com.blackmirror.dongda.presenter;

import com.blackmirror.dongda.domain.Interactor.enrol.BrandAllLocUseCase;
import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.domain.model.BrandAllLocDomainBean;
import com.blackmirror.dongda.ui.activity.enrol.EnrolContract;
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
public class EnrolPresenter implements EnrolContract.Presenter {

    @Inject
    BrandAllLocUseCase useCase;

    private EnrolContract.View view;

    @Inject
    public EnrolPresenter(EnrolContract.View view) {
        this.view = view;
    }

    @Override
    public void getBrandAllLocation(String brand_id) {
        useCase.getBrandAllLocation(brand_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BrandAllLocDomainBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BrandAllLocDomainBean bean) {
                        LogUtils.d("apply onNext");
                        if (view == null){
                            return;
                        }
                        if (bean.isSuccess){
                            view.onGetBrandAllLocationSuccess(bean);
                        }else {
                            view.onError(bean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(EnrolPresenter.class,e);
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
    public void setView(EnrolContract.View view) {

    }

    @Override
    public void destroy() {

    }
}
