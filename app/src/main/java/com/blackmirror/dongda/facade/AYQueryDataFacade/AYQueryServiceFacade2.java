package com.blackmirror.dongda.facade.AYQueryDataFacade;

import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.model.BaseServerBean;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by alfredyang on 6/7/17.
 */

public class AYQueryServiceFacade2 extends AYFacade {


    private AYQueryServiceFacade2(){}

    public static class SingleHolder{
        private static final AYQueryServiceFacade2 INSTANCE=new AYQueryServiceFacade2();
    }

    public static AYQueryServiceFacade2 getInstance(){
        return SingleHolder.INSTANCE;
    }


    private final String TAG = "AYQueryServiceFacade";

    @Override
    public String getClassTag() {
        return TAG;
    }

    public void query(){
        QueryServicePresenter.getInstance().queryService()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseServerBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseServerBean bean) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
