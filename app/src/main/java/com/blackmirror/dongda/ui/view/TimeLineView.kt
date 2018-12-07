package com.blackmirror.dongda.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.blackmirror.dongda.utils.dp2px

/**
 * Create By Ruge at 2018-09-28
 */
class TimeLineView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var total_width = 0
    var total_height = 0
    var point_raduis = dp2px(3)
    var line_width = dp2px(1)
    var point_color = Color.parseColor("#FF5464AC")
    var line_color = Color.parseColor("#FFDFE0E3")

    var l = 0
    var t = 0
    var r = 0
    var b = 0

    var pos = 0 //0中间 1头 2 尾


    var point_paint: Paint
    var line_paint: Paint

    init {
        point_paint = Paint(Paint.ANTI_ALIAS_FLAG)
        point_paint.color = point_color

        line_paint = Paint(Paint.ANTI_ALIAS_FLAG)
        line_paint.color = line_color
        line_paint.style = Paint.Style.FILL
        line_paint.strokeWidth = line_width.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawTimeLine(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        total_width = w
        total_height = h
        l = left
        t = top
        r = right
        b = bottom
    }

    private fun drawTimeLine(canvas: Canvas) {

        when (pos) {
            0 -> {
                val startX = point_raduis.toFloat()
                val startY = dp2px(18).toFloat()
//        canvas.translate(startX,startY)

                canvas.drawLine(startX, 0f, startX, dp2px(15).toFloat(), line_paint)
                canvas.drawCircle(startX, startY, point_raduis.toFloat(), point_paint)
                canvas.drawLine(startX, dp2px(21).toFloat(), startX, total_height.toFloat(), line_paint)
            }
            1 -> {
                pos = 0
                val startX = point_raduis.toFloat()
                val startY = dp2px(18).toFloat()

                val f = RectF(0f, 0f, total_width.toFloat(), total_height.toFloat())

                val p = Paint(Paint.ANTI_ALIAS_FLAG)
                p.color = Color.parseColor("#FFFFFFFF")

//            canvas.drawRect(f,p)

                canvas.drawCircle(startX, startY, point_raduis.toFloat(), point_paint)

                canvas.drawLine(startX, dp2px(21).toFloat(), startX, total_height.toFloat(), line_paint)
            }
            2 -> {
                pos = 0
                val startX = point_raduis.toFloat()
                val startY = dp2px(18).toFloat()
//        canvas.translate(startX,startY)

                canvas.drawLine(startX, 0f, startX, dp2px(15).toFloat(), line_paint)
                canvas.drawCircle(startX, startY, point_raduis.toFloat(), point_paint)
            }
        }


    }

    fun setFristItem() {
        pos = 1
        postInvalidate()
    }

    fun setLastItem() {
        pos = 2
        postInvalidate()
    }
}