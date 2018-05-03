package com.blackmirror.dongda.adapter.itemdecoration;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blackmirror.dongda.utils.OtherUtils;

/**
 * Created by Ruge on 2018-04-04 下午12:14
 */
public class TopItemDecoration extends RecyclerView.ItemDecoration {
    private int left = -1;
    private int top = -1;
    private int right = -1;
    private int bottom = -1;



    public TopItemDecoration(int top, int bottom) {
        this.top = OtherUtils.dp2px(top);
        this.bottom = OtherUtils.dp2px(bottom);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        //竖直方向的
        if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
            /*//最后一项需要 bottom
            if (parent.getChildAdapterPosition(view) == layoutManager.getItemCount() - 1) {
                outRect.bottom = bottom;
            }*/
            outRect.bottom = bottom;
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
