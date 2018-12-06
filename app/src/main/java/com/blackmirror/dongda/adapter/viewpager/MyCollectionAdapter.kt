package com.blackmirror.dongda.adapter.viewpager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.blackmirror.dongda.ui.fragment.HomeFragment


/**
 * Create By Ruge at 2018-10-16
 */
class MyCollectionAdapter(manager:FragmentManager) : FragmentPagerAdapter(manager) {
    override fun getItem(position: Int): Fragment {
        return HomeFragment()
    }

    override fun getCount(): Int {
        return 2
    }

}