package com.blackmirror.dongda.dialog

import android.content.Context
import android.view.LayoutInflater

/**
 * Create By Ruge at 2018-10-12
 */
class CommonBuilder(val context: Context) : CustomBuilder() {


    var cancelClick: (() -> Unit)? = null
    var confirmClick: (() -> Unit)? = null
    var cancelId = 0
    var confirmId = 0

    fun build(): CommonDialog {
//        dialog = CommonDialog()
        return CommonDialog().also {
            it.contentView = contentView
            it.resId = resId
            if (resId != 0) {
                it.contentView = LayoutInflater.from(context).inflate(resId, null, false)
            }
            it.initCancelClick(cancelId, cancelClick)
            it.initConfirmClick(confirmId, confirmClick)
            it.canCancel = canCancel
            it.dialogGravity = dialogGravity
        }
    }

    fun setCancelClick(cId: Int, click: () -> Unit) {
        cancelId = cId
        cancelClick = click
    }

    fun setConfirmClick(cId: Int, click: () -> Unit) {
        confirmId = cId
        confirmClick = click
    }

}