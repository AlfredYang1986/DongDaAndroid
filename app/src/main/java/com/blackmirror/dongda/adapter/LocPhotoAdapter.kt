package com.blackmirror.dongda.adapter

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blackmirror.dongda.R
import com.facebook.drawee.view.SimpleDraweeView

/**
 * Create By Ruge at 2018-10-16
 */
class LocPhotoAdapter : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view=LayoutInflater.from(container.context).inflate(R.layout.vp_item_loc_photo,null,false)
        val sv=view.findViewById<SimpleDraweeView>(R.id.sv_photo)
        container.addView(view)
        return view
    }

    override fun isViewFromObject(view: View?, o: Any?): Boolean {
        return view == o
    }

    override fun destroyItem(container: ViewGroup, position: Int, o: Any) {
        container.removeView(o as View)
    }

    override fun getCount(): Int {
        return 8
    }
}