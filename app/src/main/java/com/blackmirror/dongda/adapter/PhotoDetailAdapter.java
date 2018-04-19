package com.blackmirror.dongda.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.LogUtils;
import com.blackmirror.dongda.Tools.OSSUtils;
import com.blackmirror.dongda.model.ServiceDetailPhotoBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by xcx on 2018/4/14.
 */

public class PhotoDetailAdapter extends PagerAdapter {

    List<ServiceDetailPhotoBean> list;


    public PhotoDetailAdapter(List<ServiceDetailPhotoBean> list) {
        this.list=list;
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LogUtils.d("instantiateItem ");
        View view=View.inflate(container.getContext(), R.layout.vp_item_photo,null);
        SimpleDraweeView sv_detail_photo=view.findViewById(R.id.sv_detail_photo);
//        sv_detail_photo.setImageURI(list.get(position));
        String url= OSSUtils.getSignedUrl(list.get(position).image);
        sv_detail_photo.setImageURI(url);
        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
