package com.blackmirror.dongda.adapter.itemdecoration

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

import com.blackmirror.dongda.utils.DensityUtils

/**
 * Created by Ruge on 2018-04-04 下午12:14
 */
class GridInnerItemDecoration(private val space: Int,var column:Int=2) : RecyclerView.ItemDecoration() {



    val defalutSpace=DensityUtils.dp2px(9)
    var inner_space:Int=-1

    init {
        inner_space = if (space == -1) this.defalutSpace / 2 else DensityUtils.dp2px(space / 2)
    }




    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        val layoutManager = parent.layoutManager as GridLayoutManager
        val pos = parent.getChildAdapterPosition(view)

        if (pos % column == 0) {//最左列元素
            outRect.right = inner_space
        } else if ((pos + 1) % column == 0) {//最右列元素
            outRect.left = inner_space
        }

    }
}
