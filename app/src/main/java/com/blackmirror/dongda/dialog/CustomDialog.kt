package com.blackmirror.dongda.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.blackmirror.dongda.R

/**
 * Created by Ruge on 2018-04-18 下午6:05
 */
class CustomDialog : Dialog {
    protected var ctx: Context
    private var cancelTouchout: Boolean = false
    private var cancel: Boolean = false
    private var focusable: Boolean = false
    private var view: View? = null
    protected var gravity: Int = 0
    protected var width = Integer.MIN_VALUE
    protected var offsetY = Integer.MIN_VALUE

    protected constructor(builder: Builder, resStyle: Int) : super(builder.context, resStyle) {
        view = builder.view
        ctx = builder.context
        cancelTouchout = builder.cancelTouchout
        cancel = builder.cancelable
        gravity = builder.gravity
        width = builder.width
        offsetY = builder.offsetY
        focusable = builder.focusable
    }


    protected constructor(builder: Builder) : super(builder.context) {
        ctx = builder.context
        cancelTouchout = builder.cancelTouchout
        view = builder.view
        gravity = builder.gravity
        width = builder.width
        offsetY = builder.offsetY
        cancel = builder.cancelable
        focusable = builder.focusable
    }

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)

        setContentView(view!!)
        setCanceledOnTouchOutside(cancelTouchout)
        setCancelable(cancel)

        val win = window
        val lp = win!!.attributes
        //lp.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

        lp.gravity = gravity

        if (width != Integer.MIN_VALUE) {
            lp.width = width
        }
        if (offsetY != Integer.MIN_VALUE && gravity == Gravity.BOTTOM) {
            lp.y = offsetY
        }

        if (!focusable) {
            win.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
        }

        win.attributes = lp

    }

    class Builder(val context: Context) {
         var cancelTouchout: Boolean = false
         var cancelable = true
         val focusable = true
         var view: View? = null
         var gravity = Gravity.CENTER
         var width = Integer.MIN_VALUE
         var offsetY = Integer.MIN_VALUE

        fun setCancelTouchOut(cancelTouchout: Boolean): Builder {
            this.cancelTouchout = cancelTouchout
            return this
        }

        fun setCancelable(cancelable: Boolean): Builder {
            this.cancelable = cancelable
            return this
        }

        fun setFocusable(focusable: Boolean): Builder {
            this.cancelable = focusable
            return this
        }

        fun view(resView: Int): Builder {
            view = LayoutInflater.from(context).inflate(resView, null)
            return this
        }

        fun view(view: View): Builder {
            this.view = view
            return this
        }


        fun addViewOnclick(viewRes: Int, listener: View.OnClickListener): Builder {
            view!!.findViewById<View>(viewRes).setOnClickListener(listener)
            return this
        }


        fun setText(viewRes: Int, stringRes: Int): Builder {
            (view!!.findViewById<View>(viewRes) as TextView).setText(stringRes)
            return this
        }

        fun setText(viewRes: Int, str: String): Builder {
            (view!!.findViewById<View>(viewRes) as TextView).text = str
            return this
        }

        fun setGravity(gravity: Int): Builder {
            this.gravity = gravity
            return this
        }

        fun setWidth(width: Int): Builder {
            this.width = width
            return this
        }

        fun setOffsetY(offsetY: Int): Builder {
            this.offsetY = offsetY
            return this
        }

        fun build(): CustomDialog {
            return CustomDialog(this, R.style.CustomDialog)
        }

        fun build(style: Int): CustomDialog {
            return CustomDialog(this, style)
        }

    }
}
