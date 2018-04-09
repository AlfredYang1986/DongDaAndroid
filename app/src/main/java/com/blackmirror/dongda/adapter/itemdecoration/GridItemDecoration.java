package com.blackmirror.dongda.adapter.itemdecoration;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blackmirror.dongda.Tools.OtherUtils;

/**
 * Created by Ruge on 2018-04-04 下午12:14
 */
public class GridItemDecoration extends RecyclerView.ItemDecoration {
    private int left = -1;
    private int top;
    private int right = -1;
    private int bottom = -1;
    private int space;
    private int column;
    private int inner_space = -1;


    public GridItemDecoration(int top, int space, int inner_space, int column) {
        this.top = OtherUtils.dp2px(top);
        this.space = OtherUtils.dp2px(space);
        this.inner_space = inner_space == -1 ? this.space / 2 : OtherUtils.dp2px(inner_space / 2);
        this.column = column;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State
            state) {
        super.getItemOffsets(outRect, view, parent, state);
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        int pos = parent.getChildAdapterPosition(view);
        int count = parent.getChildCount();

        if (pos >= 0 && pos < column) {
            outRect.top = top;
        } else {
            outRect.top = space;
        }


        if (pos % column == 0){//最左列元素
            outRect.left = space;
            outRect.right = inner_space;
        }else if ((pos + 1) % column == 0) {//最右列元素
            outRect.left = inner_space;
            outRect.right = space;
        } else {
            outRect.left = inner_space;
            outRect.right = inner_space;
        }


    }
}
