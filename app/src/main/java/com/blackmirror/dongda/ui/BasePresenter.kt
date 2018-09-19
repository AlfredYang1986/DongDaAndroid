package com.blackmirror.dongda.ui

interface BasePresenter<T> {
    fun setView(view: T)

    fun destroy()
}
