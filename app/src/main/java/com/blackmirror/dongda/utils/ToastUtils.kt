package com.blackmirror.dongda.utils

import android.support.design.widget.Snackbar
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.blackmirror.dongda.R
import com.blackmirror.dongda.base.AYApplication

/**
 * Create By Ruge at 2018-12-06
 */
class SingltonToast private constructor(){

    private val toast:Toast

    init {
        toast= Toast.makeText(AYApplication.appContext,"",Toast.LENGTH_SHORT)
    }

    private object Holder {
        val singleton = SingltonToast()
    }

    companion object {
        val INSTANCE: SingltonToast by lazy { Holder.singleton }
    }

    fun getToast(): Toast {
        return toast
    }
}

fun showToast(msg: CharSequence,resId:Int=0,length:Int=Toast.LENGTH_SHORT){


    if (msg.isNullOrEmpty() && resId<=0){
        return
    }

    val toast = SingltonToast.INSTANCE.getToast()

    if (resId>0){
        toast.setText(resId)
    }else{
        toast.setText(msg)
    }

    toast.duration=length
    toast.show()
}

fun showSnackbar(view: View, content: String) {
    val snackbar = Snackbar.make(view, content, Snackbar.LENGTH_INDEFINITE)
    snackbar.setAction("关闭") { }
    snackbar.view.setBackgroundColor(AYApplication.appContext.resources.getColor(R.color.sys_bar_white))
    val v = snackbar.view
    (v.findViewById(R.id.snackbar_text) as TextView).setTextColor(
            AYApplication.appContext.resources.getColor(
                    R.color.text_black
            )
    )
    snackbar.show()
}