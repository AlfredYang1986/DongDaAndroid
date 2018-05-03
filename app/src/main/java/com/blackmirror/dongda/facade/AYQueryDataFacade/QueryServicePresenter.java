package com.blackmirror.dongda.facade.AYQueryDataFacade;

import com.blackmirror.dongda.command.TestCmd;
import com.blackmirror.dongda.model.BaseServerBean;

import io.reactivex.Observable;

/**
 * Created by xcx on 18-5-3.
 */
public class QueryServicePresenter {


    private QueryServicePresenter(){}

    public static class singleHolder{
        private static final QueryServicePresenter INSTANCE=new QueryServicePresenter();
    }

    public static QueryServicePresenter getInstance(){
        return singleHolder.INSTANCE;
    }

    public Observable<BaseServerBean> queryService(){
       return new TestCmd().execute();
    }



}
