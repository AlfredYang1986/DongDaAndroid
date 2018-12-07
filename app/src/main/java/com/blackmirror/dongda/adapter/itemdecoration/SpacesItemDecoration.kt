package com.blackmirror.dongda.adapter.itemdecoration

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import com.blackmirror.dongda.utils.dp2px

/**
 * Created by Ruge on 2018-04-04 下午12:14
 */
class SpacesItemDecoration : RecyclerView.ItemDecoration {
    private var space = -1
    private var left = -1
    private var top = -1
    private var right = -1
    private var bottom = -1

    constructor(space: Int) {
        this.space = space
    }

    constructor(left: Int, top: Int, right: Int, bottom: Int) {
        this.left = left
        this.top = top
        this.right = right
        this.bottom = bottom
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        if (left != -1) {
            outRect.left = dp2px(left)
        }
        if (top != -1) {
            outRect.top = dp2px(top)
        }
        if (right != -1) {
            outRect.right = dp2px(right)
        }
        if (bottom != -1) {
            outRect.bottom = dp2px(bottom)
        }
        if (space != -1) {
            outRect.right = dp2px(space)
        }
    }
}
