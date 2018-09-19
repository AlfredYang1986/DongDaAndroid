package com.blackmirror.dongda.ui.activity.enrol

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.adapter.UnOpenDateAdapter
import com.blackmirror.dongda.model.UnOpenDateBean
import com.blackmirror.dongda.ui.base.BaseActivity
import java.util.*

class EnrolUnOpenDayActivity : BaseActivity() {

    private lateinit var rv_cur_date: RecyclerView
    private lateinit var rv_next_date: RecyclerView
    private lateinit var iv_back: ImageView
    private lateinit var tv_save: TextView
    private lateinit var tv_cur_month: TextView
    private lateinit var tv_next_month: TextView

    override val layoutResId: Int
        get() = R.layout.activity_enrol_un_open_day

    override fun initInject() {

    }

    override fun initView() {
        iv_back = findViewById(R.id.iv_back)
        tv_save = findViewById(R.id.tv_save)
        rv_cur_date = findViewById(R.id.rv_cur_date)
        rv_next_date = findViewById(R.id.rv_next_date)
        tv_cur_month = findViewById(R.id.tv_cur_month)
        tv_next_month = findViewById(R.id.tv_next_month)

    }

    override fun initData() {
        initCurDate()
        initNextDate()
    }

    private fun initCurDate() {
        val list = ArrayList<String>()
        val bean = UnOpenDateBean()
        bean.list = list

        /**获取日历实例 */
        val cld = Calendar.getInstance()

        val month = cld.get(Calendar.MONTH) + 1
        val day = cld.get(Calendar.DAY_OF_MONTH)

        cld.set(Calendar.DATE, 1)
        cld.roll(Calendar.DATE, -1)
        val maxDate = cld.get(Calendar.DATE)


        /**设置日历成当月的第一天 */
        cld.set(Calendar.DAY_OF_MONTH, 1)
        //星期对应数字
        val i = cld.get(Calendar.DAY_OF_WEEK) - 2

        tv_cur_month.text = month.toString() + "月"

        bean.month = month
        bean.firstWeek = i

        list.add("一")
        list.add("二")
        list.add("三")
        list.add("四")
        list.add("五")
        list.add("六")
        list.add("日")

        for (j in 0 until i) {
            list.add("")
        }

        for (j in 0 until maxDate) {
            if (day - 1 == j) {
                list.add("今")
                continue
            }
            list.add((j + 1).toString() + "")
        }

        rv_cur_date.isNestedScrollingEnabled = false
        val adapter = UnOpenDateAdapter(this, bean)
        val manager = GridLayoutManager(this, 7)
        rv_cur_date.layoutManager = manager
        rv_cur_date.adapter = adapter


        adapter.setOnDateClickListener { view, position, isSelect -> }
    }

    private fun initNextDate() {
        val list = ArrayList<String>()
        val bean = UnOpenDateBean()
        bean.list = list

        /**获取日历实例 */

        val cld = Calendar.getInstance()
        val month = cld.get(Calendar.MONTH) + 1
        cld.set(Calendar.MONTH, month)


        cld.set(Calendar.DATE, 1)
        cld.roll(Calendar.DATE, -1)
        val maxDate = cld.get(Calendar.DATE)


        /**设置日历成下个月的第一天 */
        cld.set(Calendar.DAY_OF_MONTH, 1)
        //星期对应数字
        val i = cld.get(Calendar.DAY_OF_WEEK) - 2

        list.add("一")
        list.add("二")
        list.add("三")
        list.add("四")
        list.add("五")
        list.add("六")
        list.add("日")

        tv_next_month.text = month.toString() + "月"

        bean.month = month
        bean.firstWeek = i

        for (j in 0 until i) {
            list.add("")
        }

        for (j in 0 until maxDate) {
            list.add((j + 1).toString() + "")
        }

        rv_next_date.isNestedScrollingEnabled = false
        val adapter = UnOpenDateAdapter(this, bean)
        val manager = GridLayoutManager(this, 7)
        rv_next_date.layoutManager = manager
        rv_next_date.adapter = adapter


        adapter.setOnDateClickListener { view, position, isSelect -> }
    }

    override fun initListener() {
        iv_back.setOnClickListener { finish() }
        tv_save.setOnClickListener { finish() }
    }
}
