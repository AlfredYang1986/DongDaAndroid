package com.blackmirror.dongda.adapter.itemdecoration

import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

import com.blackmirror.dongda.utils.DensityUtils

/**
 * Created by Ruge on 2018-04-04 下午12:14
 */
class TopItemDecoration(top: Int, bottom: Int) : RecyclerView.ItemDecoration() {
    private var left = -1
    private var top = -1
    private var right = -1
    private var bottom = -1


    init {
        this.top = DensityUtils.dp2px(top)
        this.bottom = DensityUtils.dp2px(bottom)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        val layoutManager = parent.layoutManager as LinearLayoutManager
        //竖直方向的
        if (layoutManager.orientation == LinearLayoutManager.VERTICAL) {
            /*//最后一项需要 bottom
            if (parent.getChildAdapterPosition(view) == layoutManager.getItemCount() - 1) {
                outRect.bottom = bottom;
            }*/
            outRect.bottom = bottom
        } else {
            /* //最后一项需要right
            if (parent.getChildAdapterPosition(view) == layoutManager.getItemCount() - 1) {
                outRect.right = leftRight;
            }
            outRect.top = topBottom;
            outRect.left = leftRight;
            outRect.bottom = topBottom;*/
        }

    }
}
