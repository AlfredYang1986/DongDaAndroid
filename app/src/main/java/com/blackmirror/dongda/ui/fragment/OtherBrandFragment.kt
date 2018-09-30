package com.blackmirror.dongda.ui.fragment


import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blackmirror.dongda.R
import com.blackmirror.dongda.adapter.HomeOtherBrandAdapter
import com.blackmirror.dongda.kdomain.model.HomeOtherBrandDomainBean
import com.mabeijianxi.smallvideorecord2.Log

/**
 * Create By Ruge at 2018/9/21
 */
class OtherBrandFragment : BaseFragment() {

    lateinit var rv_other_brand:RecyclerView

    override fun getContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view=inflater.inflate(R.layout.fragment_other_brand, container, false)
        rv_other_brand=view.findViewById(R.id.rv_other_brand)
        return view
    }

    override fun initListener() {
    }

    override fun getData() {
        Log.d("xcx","OtherBrandFragment getData")
        testData()
    }

    private fun testData() {
        println("OtherBrandFragment getData")
        val adapter=HomeOtherBrandAdapter(activity, HomeOtherBrandDomainBean())
        rv_other_brand.layoutManager= GridLayoutManager(activity,2)
        rv_other_brand.adapter=adapter
    }

    override fun initInject() {
    }

    /*override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_other_brand, container, false)
    }*/


}
