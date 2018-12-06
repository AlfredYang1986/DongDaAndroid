package com.blackmirror.dongda.ui.activity.homeActivity

import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.adapter.HomeSerachAdapter
import com.blackmirror.dongda.adapter.itemdecoration.GridInnerItemDecoration
import com.blackmirror.dongda.kdomain.model.HomeSearchDomainBean
import com.blackmirror.dongda.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_search_course.*

class SearchCourseActivity : BaseActivity(), View.OnClickListener {


    /*lateinit var et_text:EditText
    lateinit var iv_clear_text:ImageView
    lateinit var tv_search_cancel:TextView
    lateinit var tb_search:MyTabLayout
    lateinit var view_1:View
    lateinit var rv_search_course:RecyclerView*/

    override val layoutResId: Int
        get() = R.layout.activity_search_course

    override fun initInject() {
    }

    override fun initView() {
//        TODO("缺一个清除文字按钮")

    }

    override fun initData() {
        testData(true)
    }

    private fun testData(hasData: Boolean) {

        if (hasData) {
            tb_search.visibility = View.VISIBLE
            view_1.visibility = View.VISIBLE
            rv_search_course.visibility = View.VISIBLE
        } else {
            tb_search.visibility = View.GONE
            view_1.visibility = View.GONE
            rv_search_course.visibility = View.GONE
        }

        val v1 = layoutInflater.inflate(R.layout.tb_item_search_course, null, false)
        val v2 = layoutInflater.inflate(R.layout.tb_item_search_course, null, false)

        val tv1 = v1.findViewById<TextView>(R.id.tv_course_num)
        val tv2 = v2.findViewById<TextView>(R.id.tv_course_num)

        tv1.text = "4"
        tv2.text = "6"

        tb_search.addTab(tb_search.newTab().setCustomView(v1).setText("课程"))
        tb_search.addTab(tb_search.newTab().setCustomView(v2).setText("体验"))


        rv_search_course.layoutManager = GridLayoutManager(this, 2)
        val adapter = HomeSerachAdapter(this, HomeSearchDomainBean())
        rv_search_course.addItemDecoration(GridInnerItemDecoration(9))
        rv_search_course.adapter = adapter

    }

    override fun initListener() {
        iv_clear_text.setOnClickListener(this)
        tv_search_cancel.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_clear_text -> {
                et_text.setText("")
            }
            R.id.tv_search_cancel -> {
                finish()
            }
        }
    }

}
