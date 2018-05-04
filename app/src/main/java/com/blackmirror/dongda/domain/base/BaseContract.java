package com.blackmirror.dongda.domain.base;

/**
 * Created by Ruge on 2018-05-04 下午1:15
 */
public class BaseContract {
    public interface View<T>{

        void getDataSuccess(T bean);

        void getDataError(T t);

    }

    public interface CommonFacade extends BaseFacade<View>{

    }
}
