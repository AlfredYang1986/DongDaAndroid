package com.blackmirror.dongda.command;

import com.blackmirror.dongda.model.BaseServerBean;
import com.blackmirror.dongda.model.BaseUiBean;

import io.reactivex.Observable;

/**
 * Created by xcx on 18-5-3.
 */
public class TestCmd extends AYRemoteCommand2 {


    public Observable<BaseServerBean> execute(){
        return execute(new BaseUiBean(), BaseServerBean.class);
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

    @Override
    public String getClassTag() {
        return null;
    }
}
