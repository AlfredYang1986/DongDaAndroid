package com.blackmirror.dongda.domain.base;

/**
 * Created by Ruge on 2018-05-04 下午5:27
 */
public abstract class BaseUserCase<T> {

    public abstract void executeTask(T t);

   /* public interface OnTaskCallback <Q> {

        void onSuccess(Q response);

        void onError(Q response);
    }

    private  OnTaskCallback listener;

    public void setOnTaskCallBack(OnTaskCallback<Q> listener) {
        this.listener = listener;
    }*/
}
