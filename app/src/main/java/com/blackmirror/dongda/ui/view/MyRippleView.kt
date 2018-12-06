package com.blackmirror.dongda.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Create By Ruge at 2018-10-10
 */
class MyRippleView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var w = 0f
    var h = 0f

    var centerX = 0f
    var centerY = 0f

    val p: Paint
    val centerP: Paint

    var start = false

    var center_r = dp2px(6)
    var distance = dp2px(1).toInt()
    var maxR = distance+center_r
    var maxWidth=300

    val alphas = mutableListOf(255)
    val radius = mutableListOf(0)

    init {
        p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.color = Color.parseColor("#FF80AEFF")

        centerP = Paint(Paint.ANTI_ALIAS_FLAG)
        centerP.color = Color.parseColor("#FF80AEFF")
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.w = w.toFloat()
        this.h = h.toFloat()
        centerX = w / 2f
        centerY = h / 2f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for ((k, v) in radius.withIndex()) {
            val al = alphas[k]
            p.alpha = al
            canvas.drawCircle(centerX, centerY, center_r + v, p)

            if (al > 0 && v < 300) {
                alphas[k] = if (al - 10 > 0) al - 10 else 1
                radius[k] = v + distance
            }

        }

        if (radius[radius.size - 1] >= maxR) {
            alphas.add(255)
            radius.add(0)
        }

        if (radius.size >= 8) {
            alphas.removeAt(0)
            radius.removeAt(0)
        }

        canvas.drawCircle(centerX, centerY, center_r, centerP)


//        if (radius[radius.size-1]>)

        postInvalidateDelayed(70)

    }


    fun dp2px(dp:Int): Float {
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f)
    }


}