package com.blackmirror.dongda.ui.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.blackmirror.dongda.utils.dp2px

/**
 * Create By Ruge at 2018-09-29
 */
class MyCircleView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var radius = dp2px(7).toFloat()

    var w = 0f
    var h = 0f

    val p1: Paint

    init {
        p1 = Paint(Paint.ANTI_ALIAS_FLAG)
        p1.style = Paint.Style.STROKE
        p1.strokeWidth = dp2px(1).toFloat()
//        p1.color=Color.parseColor("#FFE0E0E0")
        p1.color = Color.parseColor("#FFE0E0E0")

        val d1 = dp2px(8).toFloat()
        var d2 = dp2px(8).toFloat()

        val d = DashPathEffect(floatArrayOf(d1, d2), 0f)

        p1.pathEffect = d
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.w = w.toFloat()
        this.h = h.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCicle(canvas)
    }

    private fun drawCicle(canvas: Canvas) {

        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.color = Color.parseColor("#fffafafa")

        canvas.drawCircle(0f, radius, radius, p)
        canvas.drawCircle(w, radius, radius, p)

        val path = Path()
        path.moveTo(radius,radius)

        path.lineTo(w-radius,radius)

        canvas.drawPath(path,p1)

    }
}