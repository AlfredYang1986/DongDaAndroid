package com.blackmirror.dongda.facade.AYQueryDataFacade;

/**
 * Created by xcx on 18-5-3.
 */
public interface QueryServiceCallBack<T> {
    void onSuccess(T t);
    void onError(T t);
}
