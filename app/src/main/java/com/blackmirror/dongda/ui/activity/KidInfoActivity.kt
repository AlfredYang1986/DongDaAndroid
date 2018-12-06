package com.blackmirror.dongda.ui.activity

import android.view.View
import com.bigkoo.pickerview.view.OptionsPickerView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.dialog.makeCommonDialog
import com.blackmirror.dongda.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_kid_info.*

class KidInfoActivity : BaseActivity() {

    private var pvCustomOptions: OptionsPickerView<String>? = null
    private val year= mutableListOf<Int>()
    private val month= mutableListOf<Int>()
    private val day= mutableListOf<Int>()

    override val layoutResId: Int
        get() = R.layout.activity_kid_info

    override fun initInject() {
    }

    override fun initView() {
        tv_add_kid_dec.visibility = View.VISIBLE
        bt_add_kid.visibility = View.VISIBLE
    }

    override fun initData() {
        year.add(2018)
        year.add(2017)
        year.add(2016)
        year.add(2015)
        year.add(2014)
        year.add(2013)
        year.add(2012)
        year.add(2011)
        year.add(2010)
        year.add(1999)
        year.add(1998)
        year.add(1997)
        year.add(1996)
        year.add(1995)

        month.add(1)
        month.add(2)
        month.add(3)
        month.add(4)
        month.add(5)
        month.add(6)
        month.add(7)
        month.add(8)
        month.add(9)
        month.add(10)
        month.add(11)
        month.add(12)

    }

    override fun initListener() {
        bt_add_kid.setOnClickListener {
            addKid()
        }
    }

    private fun addKid() {
        val view=layoutInflater.inflate(R.layout.dialog_add_kid,null,false)
        view.findViewById<View>(R.id.view_date_bg).setOnClickListener {

        }

        makeCommonDialog {

        }
    }


}
