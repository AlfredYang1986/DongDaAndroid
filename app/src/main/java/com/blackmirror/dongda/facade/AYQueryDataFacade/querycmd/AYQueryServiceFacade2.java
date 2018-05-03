package com.blackmirror.dongda.facade.AYQueryDataFacade.querycmd;

import com.blackmirror.dongda.command.TestCmd;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.model.serverbean.HomeInfoServerBean;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by alfredyang on 6/7/17.
 */

public class AYQueryServiceFacade2 extends AYFacade {

    private final String TAG = "AYQueryServiceFacade2";

    @Override
    public String getClassTag() {
        return TAG;
    }

    public void execute(){
        new TestCmd().execute().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeInfoServerBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HomeInfoServerBean bean) {

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
