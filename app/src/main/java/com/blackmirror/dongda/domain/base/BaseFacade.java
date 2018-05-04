package com.blackmirror.dongda.domain.base;

/**
 * Created by Ruge on 2018-05-04 下午1:14
 */
public interface BaseFacade<T> {

    void setView(T view);

    void destroy();
}
