package com.blackmirror.dongda.ui.fragment


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blackmirror.dongda.R
import com.blackmirror.dongda.adapter.HomeGrowthAdapter
import com.blackmirror.dongda.kdomain.model.HomeGrowthDomainBean
import kotlinx.android.synthetic.main.fragment_growth.*

/**
 * Create By Ruge at 2018/9/28
 */
class GrowthFragment : HomeBaseFragment() {
    override fun getContentView(inflater: LayoutInflater,
                                container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_growth, container, false)
    }

    override fun initListener() {
    }

    override fun getData() {
        rv_time_line.layoutManager=LinearLayoutManager(activity)
        rv_time_line.adapter=HomeGrowthAdapter(activity, HomeGrowthDomainBean())
    }

    override fun initInject() {
    }



}
