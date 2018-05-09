package com.blackmirror.dongda.ui;

public  interface BasePresenter<T> {
    void setView(T view);

    void destroy();
}
