package com.blackmirror.dongda.adapter.itemdecoration

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

import com.blackmirror.dongda.utils.DensityUtils

/**
 * Created by Ruge on 2018-04-04 下午12:14
 */
class GridItemDecoration(top: Int, space: Int, inner_space: Int, lastTop: Int, lastBottom: Int, private val column: Int) : RecyclerView.ItemDecoration() {
    private var left = -1
    private var top: Int
    private var right = -1
    private var bottom = -1
    private var space: Int
    private var inner_space = -1
    private var lastTop: Int
    private var lastBottom: Int


    init {
        this.lastTop = DensityUtils.dp2px(lastTop)
        this.lastBottom = DensityUtils.dp2px(lastBottom)
        this.top = DensityUtils.dp2px(top)
        this.space = DensityUtils.dp2px(space)
        this.inner_space = if (inner_space == -1) this.space / 2 else DensityUtils.dp2px(inner_space / 2)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        val layoutManager = parent.layoutManager as GridLayoutManager
        val pos = parent.getChildAdapterPosition(view)
        val count = parent.adapter.itemCount

        /*if (pos == 2 || pos == 3){
            outRect.top = OtherUtils.dp2px(55);
        }else if (pos >= 0 && pos < column) {
            outRect.top = top;
        } else {
            outRect.top = OtherUtils.dp2px(32);
        }*/
        if (pos >= 0 && pos < column) {
            outRect.top = top
        } else {
            outRect.top = DensityUtils.dp2px(55)
        }

        if (pos % column == 0) {//最左列元素
            outRect.left = space
            outRect.right = inner_space
        } else if ((pos + 1) % column == 0) {//最右列元素
            outRect.left = inner_space
            outRect.right = space
        } else {
            outRect.left = inner_space
            outRect.right = inner_space
        }

        /*if (pos == count - 1) {
            outRect.top = lastTop;
            outRect.bottom = lastBottom;
        }*/


    }
}
