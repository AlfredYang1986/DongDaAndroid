package com.blackmirror.dongda.ui.fragment


import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blackmirror.dongda.R

/**
 * Create By Ruge at 2018/9/21
 */
class SuperBrandFragment : BaseFragment() {

    lateinit var rv_super_brand:RecyclerView

    override fun getContentView(inflater: LayoutInflater, container: ViewGroup?,
                                savedInstanceState: Bundle?): View {
        val view=inflater.inflate(R.layout.fragment_super_brand, container, false)
        rv_super_brand=view.findViewById(R.id.rv_super_brand)
        return view
    }

    override fun initListener() {

    }

    override fun getData() {
        testData()
    }

    private fun testData() {

    }

    override fun initInject() {
    }

    /*override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_super_brand, container, false)
    }*/




}
