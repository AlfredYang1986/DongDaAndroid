package com.blackmirror.dongda.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blackmirror.dongda.R
import com.blackmirror.dongda.adapter.*
import com.blackmirror.dongda.adapter.itemdecoration.SpacesItemDecoration
import com.blackmirror.dongda.kdomain.model.*
import com.blackmirror.dongda.ui.activity.homeActivity.SearchCourseActivity
import com.blackmirror.dongda.ui.view.MyTabLayout
import com.blackmirror.dongda.utils.showToast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.concurrent.TimeUnit


/**
 * Create by Ruge at 2018-09-20
 */
class HomeFragment : HomeBaseFragment(), View.OnClickListener {
    override fun getContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun initInject() {
    }

    override fun getData() {
        testData()

    }

    override fun initListener() {
        iv_home_search.setOnClickListener(this)
        iv_home_icon_science.setOnClickListener(this)
        iv_home_icon_sports.setOnClickListener(this)
        iv_home_icon_art.setOnClickListener(this)
        iv_home_icon_life.setOnClickListener(this)
        iv_enter_hot_recommend.setOnClickListener(this)
        iv_now_exp_refresh.setOnClickListener(this)
        sl_home_refresh.setOnRefreshListener {
            Observable.timer(1500, TimeUnit.MILLISECONDS, Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        if (sl_home_refresh.isRefreshing) {
                            sl_home_refresh.isRefreshing = false
                        }
                    }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_home_icon_science -> {

            }
            R.id.iv_home_icon_sports -> {

            }
            R.id.iv_home_icon_art -> {

            }
            R.id.iv_home_icon_life -> {

            }
            R.id.iv_enter_hot_recommend -> {

            }
            R.id.iv_now_exp_refresh -> {

            }
            R.id.iv_home_search -> {
                startActivity(Intent(activity, SearchCourseActivity::class.java))
            }
        }
    }


    private fun testData() {
        expCourse()
        hotRecommend()
        nowExp()
        choice()
    }

    private fun expCourse() {
        //体验课
        rv_home_exp.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val homeExperienceAdapter = HomeExperienceAdapter(activity, HomeExpDomainBean())
        rv_home_exp.addItemDecoration(SpacesItemDecoration(14))
        rv_home_exp.adapter = homeExperienceAdapter

        homeExperienceAdapter.setOnItemClickListener { _, _ ->

        }

    }

    private fun hotRecommend() {
        //热门推荐
        rv_home_hot_recommend.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val homeHotRecommendAdapter = HomeHotRecommendAdapter(activity, HomeHotRecommendDomainBean())
        rv_home_hot_recommend.addItemDecoration(SpacesItemDecoration(16))
        rv_home_hot_recommend.adapter = homeHotRecommendAdapter
    }

    private fun nowExp() {
        //立即体验
        rv_now_exp.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val homeNowExperienceAdapter = HomeNowExperienceAdapter(activity, HomeNowExpDomainBean())
        rv_now_exp.addItemDecoration(SpacesItemDecoration(4))
        rv_now_exp.adapter = homeNowExperienceAdapter
    }

    private fun choice() {


        val v1 = layoutInflater.inflate(R.layout.mtl_fix_first_item, null, false)

//        mtl_home_choice.addTab(mtl_home_choice.newTab().setText("Super Brand"))
        mtl_home_choice.addTab(mtl_home_choice.newTab().setCustomView(R.layout.mtl_fix_first_item).setText("Super Brand"))
        mtl_home_choice.addTab(mtl_home_choice.newTab().setCustomView(R.layout.mtl_fix_first_item).setText("STEM"))
        mtl_home_choice.addTab(mtl_home_choice.newTab().setCustomView(R.layout.mtl_fix_first_item).setText("艺术"))
        mtl_home_choice.addTab(mtl_home_choice.newTab().setCustomView(R.layout.mtl_fix_first_item).setText("运动"))
        mtl_home_choice.addTab(mtl_home_choice.newTab().setCustomView(R.layout.mtl_fix_first_item).setText("科学"))


        val list = mutableListOf<HomeSuperBrandDomainBean>()

        list.add(HomeSuperBrandDomainBean(1))

        for (i in 0..3) {
            list.add(HomeSuperBrandDomainBean(0))
        }

        list.add(HomeSuperBrandDomainBean(1))

        for (i in 0..4) {
            list.add(HomeSuperBrandDomainBean(0))
        }

        rv_home_choice.layoutManager = GridLayoutManager(activity, 2)
        val adapter = HomeSuperBrandAdapter(activity)
        adapter.list = list
        rv_home_choice.adapter = adapter
        rv_home_choice.isNestedScrollingEnabled = false

        mtl_home_choice.addOnTabSelectedListener(object : MyTabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: MyTabLayout.Tab) {
                showToast("position = ${tab.position}")
                when (tab.position) {
                    0 -> {
                        rv_home_choice.layoutManager = GridLayoutManager(activity, 2)
                        val adapter = HomeSuperBrandAdapter(activity)
                        adapter.list = list
                        rv_home_choice.adapter = adapter
                    }
                    1 -> {
                        rv_home_choice.layoutManager = GridLayoutManager(activity, 2)
                        val adapter = HomeOtherBrandAdapter(activity, HomeOtherBrandDomainBean())
                        rv_home_choice.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                    2 -> {
                        rv_home_choice.layoutManager = GridLayoutManager(activity, 2)
                        val adapter = HomeOtherBrandAdapter(activity, HomeOtherBrandDomainBean())
                        rv_home_choice.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                    3 -> {
                        rv_home_choice.layoutManager = GridLayoutManager(activity, 2)
                        val adapter = HomeOtherBrandAdapter(activity, HomeOtherBrandDomainBean())
                        rv_home_choice.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                    4 -> {
                        rv_home_choice.layoutManager = GridLayoutManager(activity, 2)
                        val adapter = HomeOtherBrandAdapter(activity, HomeOtherBrandDomainBean())
                        rv_home_choice.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onTabUnselected(tab: MyTabLayout.Tab) {

            }

            override fun onTabReselected(tab: MyTabLayout.Tab) {

            }

        })
    }


}

