package com.blackmirror.dongda.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.blackmirror.dongda.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by xcx on 2018/4/14.
 */

public class PhotoDetailAdapter extends PagerAdapter {

    List<Integer> list;

    public PhotoDetailAdapter(List<Integer> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=View.inflate(container.getContext(), R.layout.vp_item_photo,null);
        SimpleDraweeView sv_detail_photo=view.findViewById(R.id.sv_detail_photo);
//        sv_detail_photo.setImageURI(list.get(position));
        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(container.getChildAt(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
