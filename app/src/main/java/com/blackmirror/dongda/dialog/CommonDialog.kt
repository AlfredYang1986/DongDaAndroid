package com.blackmirror.dongda.dialog

import android.view.View

/**
 * Create By Ruge at 2018-10-11
 */
class CommonDialog :CustomDialog() {

    var cancelClick: (() -> Unit)? = null
    var confirmClick: (() -> Unit)? = null
    var cancelId = 0
    var confirmId = 0

    override fun initListener() {

        if (cancelId != 0) {
            contentView?.findViewById<View>(cancelId)?.setOnClickListener {
                cancelClick?.invoke()
                dismiss()
            }
        }

        if (confirmId != 0) {
            contentView?.findViewById<View>(confirmId)?.setOnClickListener {
                confirmClick?.invoke()
                dismiss()
            }
        }
    }

    fun initCancelClick(id: Int, click: (() -> Unit)?) {
        cancelId = id
        cancelClick = click
    }

    fun initConfirmClick(id: Int, click: (() -> Unit)?) {
        confirmId = id
        confirmClick = click
    }
}