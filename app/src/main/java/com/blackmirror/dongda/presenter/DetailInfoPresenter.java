package com.blackmirror.dongda.presenter;

import com.blackmirror.dongda.domain.Interactor.DetailInfoUseCase;
import com.blackmirror.dongda.domain.Interactor.LikePopUseCase;
import com.blackmirror.dongda.domain.Interactor.LikePushUseCase;
import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.domain.model.DetailInfoDomainBean;
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
public class DetailInfoPresenter implements Contract.DetailInfoPresenter {

    @Inject
    DetailInfoUseCase detailInfoUseCase;

    @Inject
    LikePushUseCase pushUseCase;

    @Inject
    LikePopUseCase popUseCase;

    private Contract.DetailInfoView detailInfoView;

    @Inject
    public DetailInfoPresenter(Contract.DetailInfoView detailInfoView) {
        this.detailInfoView = detailInfoView;
    }

    @Override
    public void getDetailInfo(String service_id) {
        detailInfoUseCase.getDetailInfo(service_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DetailInfoDomainBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DetailInfoDomainBean bean) {
                        if (detailInfoView == null){
                            return;
                        }
                        if (bean.isSuccess){
                            detailInfoView.onGetDetailInfoSuccess(bean);
                        }else {
                            detailInfoView.onGetDataError(bean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(DetailInfoPresenter.class,e);
                        if (detailInfoView == null){
                            return;
                        }
                        detailInfoView.onGetDataError(getErrorData(e));

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
                        if (detailInfoView == null){
                            return;
                        }
                        if (bean.isSuccess){
                            detailInfoView.onLikePushSuccess(bean);
                        }else {
                            detailInfoView.onGetDataError(bean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(DetailInfoPresenter.class,e);
                        if (detailInfoView == null){
                            return;
                        }
                        detailInfoView.onGetDataError(getErrorData(e));

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
                        if (detailInfoView == null){
                            return;
                        }
                        if (bean.isSuccess){
                            detailInfoView.onLikePopSuccess(bean);
                        }else {
                            detailInfoView.onGetDataError(bean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(DetailInfoPresenter.class,e);
                        if (detailInfoView == null){
                            return;
                        }
                        detailInfoView.onGetDataError(getErrorData(e));

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
