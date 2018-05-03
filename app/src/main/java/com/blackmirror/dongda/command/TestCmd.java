package com.blackmirror.dongda.command;

import com.blackmirror.dongda.model.BaseUiBean;
import com.blackmirror.dongda.model.serverbean.HomeInfoServerBean;

import io.reactivex.Observable;

/**
 * Created by Ruge on 2018-05-03 下午7:25
 */
public class TestCmd extends AYRemoteCommand2 {



    public Observable<HomeInfoServerBean> execute(){
        return execute(new BaseUiBean(), HomeInfoServerBean.class);
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected String getSuccessCallBackName() {
        return null;
    }

    @Override
    protected String getFailedCallBackName() {
        return null;
    }
}
