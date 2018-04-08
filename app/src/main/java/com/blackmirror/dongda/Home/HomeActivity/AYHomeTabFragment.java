package com.blackmirror.dongda.Home.HomeActivity;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.view.ContextMenu;
import android.view.View;

/**
 * Created by alfredyang on 5/9/17.
 */

public class AYHomeTabFragment extends TabLayout {

    public AYHomeTabFragment (Context context){
        super(context);
    }

    @Override
    protected void onCreateContextMenu(ContextMenu menu) {
        super.onCreateContextMenu(menu);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        for(int index = 0; index < getChildCount(); index++){
            View v = getChildAt(index);
            v.layout(l, t, r, b);
        }
    }
}
