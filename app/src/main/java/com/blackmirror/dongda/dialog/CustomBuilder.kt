package com.blackmirror.dongda.dialog

import android.view.Gravity
import android.view.View

/**
 * Create By Ruge at 2018-10-12
 */
abstract class CustomBuilder {

    var contentView: View? = null
    var resId = 0
    var canCancel = true
    var dialogGravity = Gravity.CENTER

    /*abstract fun show(manager: FragmentManager, tag: String)

    abstract fun dismiss()*/

}