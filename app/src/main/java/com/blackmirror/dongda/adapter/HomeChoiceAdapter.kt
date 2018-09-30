package com.blackmirror.dongda.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.blackmirror.dongda.ui.fragment.BaseFragment

/**
 * Create By Ruge at 2018-09-27
 */
class HomeChoiceAdapter(fragmentList: MutableList<BaseFragment>, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {


    var list:MutableList<BaseFragment> = fragmentList


    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }
}