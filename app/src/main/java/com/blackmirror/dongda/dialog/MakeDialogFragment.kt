package com.blackmirror.dongda.dialog

import android.content.Context
import android.support.v4.app.Fragment

/**
 * Create By Ruge at 2018-10-11
 */
fun Context.makeCommonDialog(init: CommonBuilder.() -> Unit): CustomDialog {
    return CommonBuilder(this).apply(init).build()
}

fun Fragment.makeCommonDialog(init: CommonBuilder.() -> Unit): CustomDialog{
    return CommonBuilder(this.activity).apply(init).build()
}
