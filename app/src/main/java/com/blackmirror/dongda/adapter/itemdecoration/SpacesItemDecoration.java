package com.blackmirror.dongda.adapter.itemdecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blackmirror.dongda.utils.OtherUtils;

/**
 * Created by Ruge on 2018-04-04 下午12:14
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space = -1;
    private int left = -1;
    private int top = -1;
    private int right = -1;
    private int bottom = -1;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    public SpacesItemDecoration(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (left !=-1){
            outRect.left = OtherUtils.dp2px(left);
        }
        if (top !=-1){
            outRect.top = OtherUtils.dp2px(top);
        }
        if (right !=-1){
            outRect.right = OtherUtils.dp2px(right);
        }
        if (bottom !=-1){
            outRect.bottom = OtherUtils.dp2px(bottom);
        }
        if (space !=-1) {
            outRect.right = OtherUtils.dp2px(space);
        }
    }
}
