package com.blackmirror.dongda.Home.ServicePage;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by alfredyang on 13/7/17.
 */

public class AYServiceImagesAdapter extends PagerAdapter {


    private List<View> viewList = null;

    public AYServiceImagesAdapter (List<View> viewList) {
        this.viewList = viewList;
    }

    // 实例化一个页卡
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    // 销毁一个页卡
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
