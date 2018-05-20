package com.blackmirror.dongda.presenter;

import com.blackmirror.dongda.domain.Interactor.UpdateUserInfoUseCase;
import com.blackmirror.dongda.domain.Interactor.userinfo.QueryUserInfoUseCase;
import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.domain.model.UpdateUserInfoBean;
import com.blackmirror.dongda.domain.model.UpdateUserInfoDomainBean;
import com.blackmirror.dongda.domain.model.UserInfoDomainBean;
import com.blackmirror.dongda.ui.Contract;
import com.blackmirror.dongda.ui.activity.UserInfoContract;
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
public class UserInfoPresenter implements UserInfoContract.presenter {

    @Inject
    QueryUserInfoUseCase queryUseCase;

    @Inject
    UpdateUserInfoUseCase updateUserInfoUseCase;

    UserInfoContract.View view;

    Contract.NameInputView nameInputView;

    public void setUserInfoView(UserInfoContract.View view) {
        this.view = view;
    }

    @Inject
    public UserInfoPresenter() {

    }


    public void setNameInputView(Contract.NameInputView nameInputView) {
        this.nameInputView = nameInputView;
    }

    @Override
    public void queryUserInfo() {
        queryUseCase.queryUserInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfoDomainBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserInfoDomainBean bean) {
                        LogUtils.d("apply onNext");
                        if (view == null){
                            return;
                        }
                        if (bean.isSuccess){
                            view.onQueryUserInfoSuccess(bean);
                        }else {
                            view.onGetDataError(bean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(UserInfoPresenter.class,e);
                        if (view == null){
                            return;
                        }
                        BaseDataBean bean = new BaseDataBean();
                        bean.code = AppConstant.NET_UNKNOWN_ERROR;
                        bean.message = e.getMessage();
                        view.onGetDataError(bean);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void updateUserInfo(UpdateUserInfoDomainBean bean) {
        updateUserInfoUseCase.updateUserInfo(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpdateUserInfoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UpdateUserInfoBean bean) {
                        LogUtils.d("apply onNext");
                        if (nameInputView == null){
                            return;
                        }
                        if (bean.isSuccess){
                            nameInputView.onUpdateUserInfoSuccess(bean);
                        }else {
                            nameInputView.onError(bean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(UserInfoPresenter.class,e);
                        if (nameInputView == null){
                            return;
                        }
                        BaseDataBean bean = new BaseDataBean();
                        bean.code = AppConstant.NET_UNKNOWN_ERROR;
                        bean.message = e.getMessage();
                        view.onGetDataError(bean);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void setView(UserInfoContract.View view) {

    }

    @Override
    public void destroy() {

    }
}
