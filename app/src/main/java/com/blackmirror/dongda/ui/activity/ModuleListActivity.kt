package com.blackmirror.dongda.ui.activity

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.blackmirror.dongda.R
import com.blackmirror.dongda.adapter.ModuleListAdapter
import com.blackmirror.dongda.kdomain.model.ModuleListDomainBean
import com.blackmirror.dongda.ui.activity.homeActivity.SearchCourseActivity
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.ui.view.MyTabLayout
import kotlinx.android.synthetic.main.activity_module_list.*

class ModuleListActivity : BaseActivity() {
    override val layoutResId: Int
        get() = R.layout.activity_module_list

    override fun initInject() {
    }

    override fun initView() {
    }

    override fun initData() {
        testData()
    }

    private fun testData() {

        mtl_content.addTab(mtl_content.newTab().setText("科学"))
        mtl_content.addTab(mtl_content.newTab().setText("艺术"))
        mtl_content.addTab(mtl_content.newTab().setText("运动"))
        mtl_content.addTab(mtl_content.newTab().setText("生活"))

        mtl_content.addOnTabSelectedListener(object :MyTabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: MyTabLayout.Tab) {

            }

            override fun onTabUnselected(tab: MyTabLayout.Tab?) {
            }

            override fun onTabReselected(tab: MyTabLayout.Tab?) {
            }

        })

        rv_module.layoutManager=LinearLayoutManager(this)
        val adapter=ModuleListAdapter(this, ModuleListDomainBean())
        rv_module.adapter=adapter
    }

    override fun initListener() {
        iv_back.setOnClickListener {
            finish()
        }
        iv_search.setOnClickListener {
            startActivity(Intent(this@ModuleListActivity,SearchCourseActivity::class.java))
        }
    }


}
