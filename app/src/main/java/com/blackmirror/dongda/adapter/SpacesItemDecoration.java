package com.blackmirror.dongda.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blackmirror.dongda.Tools.OtherUtils;

/**
 * Created by Ruge on 2018-04-04 下午12:14
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.right = OtherUtils.dp2px(space);
    }
}
