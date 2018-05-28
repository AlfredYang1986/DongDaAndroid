package com.blackmirror.dongda.presenter;

import com.blackmirror.dongda.domain.Interactor.HomeUseCase;
import com.blackmirror.dongda.domain.Interactor.LikePopUseCase;
import com.blackmirror.dongda.domain.Interactor.LikePushUseCase;
import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.domain.model.HomepageDomainBean;
import com.blackmirror.dongda.domain.model.LikePopDomainBean;
import com.blackmirror.dongda.domain.model.LikePushDomainBean;
import com.blackmirror.dongda.ui.activity.HomeActivity.HomeContract;
import com.blackmirror.dongda.utils.AppConstant;
import com.blackmirror.dongda.utils.LogUtils;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Create By Ruge at 2018-05-11
 */
public class HomePresenter implements HomeContract.HomeBasePresenter {

    @Inject
    HomeUseCase homeUseCase;

    @Inject
    LikePushUseCase pushUseCase;

    @Inject
    LikePopUseCase popUseCase;

    private HomeContract.HomeView view;

    @Inject
    public HomePresenter(HomeContract.HomeView view) {
        this.view = view;
    }

    @Override
    public void getHomePageData() {
        homeUseCase.getHomePageData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomepageDomainBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HomepageDomainBean bean) {
                        LogUtils.d("getHomePageData onNext");
                        if (view == null){
                            return;
                        }
                        if (bean.isSuccess){
                            view.onGetHomePageData(bean);
                        }else {
                            view.onGetHomeDataError(bean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(HomePresenter.class,e);
                        if (view == null){
                            return;
                        }
                        view.onGetHomeDataError(getErrorData(e));

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void likePush(String service_id) {
        pushUseCase.likePush(service_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LikePushDomainBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LikePushDomainBean bean) {
                        if (view == null){
                            return;
                        }
                        if (bean.isSuccess){
                            view.onLikePushSuccess(bean);
                        }else {
                            view.onGetHomeDataError(bean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(HomePresenter.class,e);
                        if (view == null){
                            return;
                        }
                        view.onGetHomeDataError(getErrorData(e));

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void likePop(String service_id) {
        popUseCase.likePop(service_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LikePopDomainBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LikePopDomainBean bean) {
                        if (view == null){
                            return;
                        }
                        if (bean.isSuccess){
                            view.onLikePopSuccess(bean);
                        }else {
                            view.onGetHomeDataError(bean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(HomePresenter.class,e);
                        if (view == null){
                            return;
                        }
                        view.onGetHomeDataError(getErrorData(e));

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
    public void setView(HomeContract.HomeView view) {

    }

    @Override
    public void destroy() {

    }
}
