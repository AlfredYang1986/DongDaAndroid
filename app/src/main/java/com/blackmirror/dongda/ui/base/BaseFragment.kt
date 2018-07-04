package com.blackmirror.dongda.ui.base


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
abstract class BaseFragment : Fragment() {

    protected abstract val layoutResId:Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(layoutResId, container)
        initView(view)
        initInject()
        initListener()
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        initData()
    }

    protected abstract fun initView(view: View)

    protected abstract fun initInject()

    protected abstract fun initListener()

    protected abstract fun initData()

}
