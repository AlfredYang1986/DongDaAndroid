package com.blackmirror.dongda.mvp.ui;

public  interface BasePresenter<T> {
    void setView(T view);

    void destroy();
}
