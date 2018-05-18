package com.blackmirror.dongda.presenter;

import com.blackmirror.dongda.domain.Interactor.LikeDataUseCase;
import com.blackmirror.dongda.domain.Interactor.LikePopUseCase;
import com.blackmirror.dongda.domain.Interactor.LikePushUseCase;
import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.domain.model.LikeDomainBean;
import com.blackmirror.dongda.domain.model.LikePopDomainBean;
import com.blackmirror.dongda.domain.model.LikePushDomainBean;
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
public class MyLikePresenter implements Contract.LikePresenter {

    @Inject
    LikeDataUseCase likeDataUseCase;

    @Inject
    LikePushUseCase pushUseCase;

    @Inject
    LikePopUseCase popUseCase;

    private Contract.MyLikeView likeView;

    @Inject
    public MyLikePresenter(Contract.MyLikeView likeView) {
        this.likeView = likeView;
    }

    @Override
    public void getLikeData() {
        likeDataUseCase.getLikeData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LikeDomainBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LikeDomainBean bean) {
                        if (likeView == null){
                            return;
                        }
                        if (bean.isSuccess){
                            likeView.onGetLikeDataSuccess(bean);
                        }else {
                            likeView.onGetDataError(bean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(MyLikePresenter.class,e);
                        if (likeView == null){
                            return;
                        }
                        likeView.onGetDataError(getErrorData(e));

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
                        if (likeView == null){
                            return;
                        }
                        if (bean.isSuccess){
                            likeView.onLikePushSuccess(bean);
                        }else {
                            likeView.onGetDataError(bean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(MyLikePresenter.class,e);
                        if (likeView == null){
                            return;
                        }
                        likeView.onGetDataError(getErrorData(e));

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
                        if (likeView == null){
                            return;
                        }
                        if (bean.isSuccess){
                            likeView.onLikePopSuccess(bean);
                        }else {
                            likeView.onGetDataError(bean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(MyLikePresenter.class,e);
                        if (likeView == null){
                            return;
                        }
                        likeView.onGetDataError(getErrorData(e));

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
