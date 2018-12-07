package com.blackmirror.dongda.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blackmirror.dongda.utils.logD

/**
 * Create By Ruge at 2018/9/27
 */
abstract class BaseFragment : Fragment() {

    protected var isPrepared = false
    protected var isShowToUser = false
    protected var isFirstLoad = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        isPrepared = true
        val view= getContentView(inflater, container, savedInstanceState)
        initInject()
        return view
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint){
            //界面可见
            logD("setUserVisibleHint showToUser")
            isShowToUser = true
//            showToUser()
        }else{
            logD("setUserVisibleHint hideToUser")
            isShowToUser = false
            hideToUser()
        }
    }

    private fun showToUser() {

        if (isPrepared && isShowToUser){
            logD("setUserVisibleHint isPrepared and isShowToUser ")

            if (isFirstLoad){
                isFirstLoad = false
                logD("setUserVisibleHint isFirstLoad will call getData")

                getData()
            }
        }else{
            logD("setUserVisibleHint not isPrepared or isShowToUser ")

        }

    }

    private fun hideToUser() {

    }

    protected abstract fun getContentView(inflater: LayoutInflater,
                                          container: ViewGroup?, savedInstanceState: Bundle?): View

    protected abstract fun initListener()

    protected abstract fun getData()

    protected abstract fun initInject()



    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        showToUser()
        getData()
        initListener()
    }

}
