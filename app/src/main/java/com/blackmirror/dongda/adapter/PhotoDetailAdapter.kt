package com.blackmirror.dongda.adapter

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.blackmirror.dongda.R
import com.blackmirror.dongda.model.ServiceDetailPhotoBean
import com.blackmirror.dongda.utils.getSignedUrl
import com.blackmirror.dongda.utils.logD
import com.facebook.drawee.view.SimpleDraweeView

/**
 * Created by xcx on 2018/4/14.
 */

class PhotoDetailAdapter(internal var list: List<ServiceDetailPhotoBean>?) : PagerAdapter() {

    override fun getCount(): Int {
        return list?.size ?: 0
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        logD("instantiateItem ")
        val view = View.inflate(container.context, R.layout.vp_item_photo, null)
        val sv_detail_photo = view.findViewById<SimpleDraweeView>(R.id.sv_detail_photo)
        //        sv_detail_photo.setImageURI(list.get(position));
        val url = getSignedUrl(list!![position].image)
        sv_detail_photo.setImageURI(url)
        container.addView(view)
        return view

    }

    override fun destroyItem(container: ViewGroup, position: Int, o: Any) {
        container.removeView(o as View)
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }
}
