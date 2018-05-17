package com.blackmirror.dongda.presenter;

import com.blackmirror.dongda.domain.Interactor.enrol.BrandAllLocUseCase;
import com.blackmirror.dongda.domain.Interactor.enrol.EnrolUseCase;
import com.blackmirror.dongda.domain.Interactor.enrol.LocAllServiceUseCase;
import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.domain.model.BrandAllLocDomainBean;
import com.blackmirror.dongda.domain.model.EnrolDomainBean;
import com.blackmirror.dongda.domain.model.LocAllServiceDomainBean;
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

    @Inject
    LocAllServiceUseCase locAllServiceUseCase;

    @Inject
    EnrolUseCase enrolUseCase;

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
                        if (view == null){
                            return;
                        }
                        if (bean.isSuccess){
                            view.onGetBrandAllLocationSuccess(bean);
                            LogUtils.d("apply onNext isSuccess");
                        }else {
                            LogUtils.d("apply onNext onError");
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

    @Override
    public void getLocAllService(String json, String locations) {
        locAllServiceUseCase.getLocAllService(json, locations)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LocAllServiceDomainBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LocAllServiceDomainBean bean) {
                        if (view == null){
                            return;
                        }
                        if (bean.isSuccess){
                            view.onGetLocAllServiceSuccess(bean);
                            LogUtils.d("apply onNext isSuccess");
                        }else {
                            LogUtils.d("apply onNext onError");
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

    @Override
    public void enrol(String json) {
        enrolUseCase.enrol(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EnrolDomainBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(EnrolDomainBean bean) {
                        if (view == null){
                            return;
                        }
                        if (bean.isSuccess){
                            view.onEnrolSuccess(bean);
                            LogUtils.d("apply onNext isSuccess");
                        }else {
                            LogUtils.d("apply onNext onError");
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
