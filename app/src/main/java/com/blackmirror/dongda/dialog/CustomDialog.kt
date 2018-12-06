package com.blackmirror.dongda.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.*

/**
 * Create By Ruge at 2018-10-11
 */
open class CustomDialog : DialogFragment() {

    var contentView: View? = null
    var resId = 0
    var canCancel = true
    var dialogGravity = Gravity.CENTER


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (resId != 0) {
            contentView = inflater.inflate(resId, container, false)
        }
        return contentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    open fun initListener() {

    }

    override fun onStart() {
        super.onStart()
        isCancelable = canCancel
        val win = dialog.window
        // 一定要设置Background，如果不设置，window属性设置无效
        win.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        win.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        val param = win.attributes
        param.width = ViewGroup.LayoutParams.MATCH_PARENT
        param.gravity = dialogGravity
        win.attributes = param

    }

}