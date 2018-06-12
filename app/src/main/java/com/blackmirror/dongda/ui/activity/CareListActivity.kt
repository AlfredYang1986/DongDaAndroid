package com.blackmirror.dongda.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.adapter.CareListAdapter
import com.blackmirror.dongda.adapter.itemdecoration.TopItemDecoration
import com.blackmirror.dongda.di.component.DaggerCareListComponent
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.kdomain.model.CareMoreDomainBean
import com.blackmirror.dongda.kdomain.model.LikePopDomainBean
import com.blackmirror.dongda.kdomain.model.LikePushDomainBean
import com.blackmirror.dongda.presenter.GetMoreDataPresenter
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.AppConstant
import com.blackmirror.dongda.utils.LogUtils
import com.blackmirror.dongda.utils.SnackbarUtils
import com.blackmirror.dongda.utils.ToastUtils
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener

class CareListActivity : BaseActivity(), ListMoreContract.View {

    private val TAG = "CareListActivity"

    private lateinit var ctl_root: CoordinatorLayout
    private lateinit var iv_home_head_back: ImageView
    private lateinit var tv_home_head_title: TextView
    private lateinit var rv_care_list: RecyclerView
    private lateinit var sl_care_list: SmartRefreshLayout

    private var adapter: CareListAdapter? = null
    private var totalCount: Int = 0
    internal var skip = 0
    internal var take = 10
    private var isNeedRefresh: Boolean = false
    private var clickLikePos: Int = 0
    private var presenter: GetMoreDataPresenter? = null

    override val layoutResId: Int
        get() = R.layout.activity_care_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        totalCount = intent.getIntExtra("totalCount", 10)
    }

    override fun initInject() {
        presenter = DaggerCareListComponent.builder()
                .activity(this)
                .view(this)
                .build()
                .moreDataPresenter
    }


    override fun initView() {
        ctl_root = findViewById(R.id.ctl_root)
        iv_home_head_back = findViewById(R.id.iv_home_head_back)
        tv_home_head_title = findViewById(R.id.tv_home_head_title)
        rv_care_list = findViewById(R.id.rv_care_list)
        sl_care_list = findViewById(R.id.sl_care_list)
        sl_care_list.setEnableLoadMoreWhenContentNotFull(false)//内容不满屏幕的时候也开启加载更多
        sl_care_list.isEnableAutoLoadMore = false//内容不满屏幕的时候也开启加载更多
        sl_care_list.setRefreshHeader(MaterialHeader(this@CareListActivity))
    }

    override fun initData() {
        initData(skip, take)
    }

    private fun initData(skipCount: Int, takeCount: Int) {
        presenter!!.getServiceMoreData(skipCount, takeCount, "看顾")
    }


    override fun initListener() {
        iv_home_head_back.setOnClickListener {
            setResult(if (isNeedRefresh) Activity.RESULT_OK else Activity.RESULT_CANCELED)
            finish()
        }
        sl_care_list.setOnRefreshListener {
            skip = 0
            sl_care_list.setNoMoreData(false)
            sl_care_list.isEnableLoadMore = true
            initData(skip, take)
        }


        sl_care_list.setOnLoadMoreListener(OnLoadMoreListener { refreshLayout ->
            LogUtils.d("skip==$skip")
            if (refreshLayout.state.dragging) {
                LogUtils.d("dragging")
            }
            if (totalCount <= adapter!!.itemCount) {
                sl_care_list.finishLoadMore()
                sl_care_list.isEnableLoadMore = false
                sl_care_list.setNoMoreData(true)
                return@OnLoadMoreListener
            }
            if (skip + take >= totalCount) {
                initData(skip, totalCount - skip)
            } else {
                skip += take
                initData(skip, take)
            }
        })
    }

    override fun onGetServiceMoreDataSuccess(bean: CareMoreDomainBean) {
        closeProcessDialog()
        if (sl_care_list.state.opening) {
            sl_care_list.finishLoadMore()
            sl_care_list.finishRefresh()
        }
        setDataToRecyclerView(bean)
    }

    override fun onLikePushSuccess(bean: LikePushDomainBean) {
        isNeedRefresh = true
        closeProcessDialog()
        adapter!!.notifyItemChanged(clickLikePos, true)
    }

    override fun onLikePopSuccess(bean: LikePopDomainBean) {
        isNeedRefresh = true
        closeProcessDialog()
        adapter!!.notifyItemChanged(clickLikePos, false)

    }

    override fun onGetDataError(bean: BaseDataBean) {
        closeProcessDialog()
        if (sl_care_list.state.opening) {
            sl_care_list.finishLoadMore(false)
            sl_care_list.finishRefresh(false)
        }
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(ctl_root, bean.message)
        } else {
            ToastUtils.showShortToast(bean.message + "(" + bean.code + ")")
        }
    }

    private fun setDataToRecyclerView(bean: CareMoreDomainBean) {

        if (bean.isSuccess) {

            tv_home_head_title.setText(R.string.str_care)

            if (skip == 0) {//首次加载或者下拉刷新
                if (adapter == null) {
                    adapter = CareListAdapter(this@CareListActivity, bean)
                    rv_care_list.layoutManager = LinearLayoutManager(this@CareListActivity)
                    rv_care_list.adapter = adapter
                    rv_care_list.addItemDecoration(TopItemDecoration(40, 40))
                } else {
                    adapter!!.setRefreshData(bean.services)
                }

            } else {
                adapter!!.setMoreData(bean.services)
            }

            adapter!!.setOnCareListClickListener(object : CareListAdapter.OnCareListClickListener {
                override fun onItemCareListClick(view: View, position: Int, service_id: String) {
                    val intent = Intent(this@CareListActivity, ServiceDetailInfoActivity::class.java)
                    intent.putExtra("service_id", service_id)
                    clickLikePos = position
                    startActivityForResult(intent, AppConstant.CARE_LIST_REQUEST_CODE)
                }

                override fun onItemCareLikeClick(view: View, position: Int, servicesBean: CareMoreDomainBean.ServicesBean) {
                    clickLikePos = position
                    sendLikeData(servicesBean)
                }
            })
        } else {
            ToastUtils.showShortToast(bean.message + "(" + bean.code + ")")
        }
    }


    private fun sendLikeData(bean: CareMoreDomainBean.ServicesBean) {
        showProcessDialog()
        if (bean.is_collected) {//已收藏 点击取消
            presenter!!.likePop(bean.service_id!!)
        } else {
            presenter!!.likePush(bean.service_id!!)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            isNeedRefresh = true
            val isLike = data.getBooleanExtra("is_like", false)
            adapter!!.notifyItemChanged(clickLikePos, isLike)
        }

    }


    override fun onBackPressed() {
        setResult(if (isNeedRefresh) Activity.RESULT_OK else Activity.RESULT_CANCELED)
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}
