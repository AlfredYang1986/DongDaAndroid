package com.blackmirror.dongda.ui.activity

import android.app.Activity
import android.content.Intent
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.adapter.ArtListAdapter
import com.blackmirror.dongda.adapter.itemdecoration.GridItemDecoration
import com.blackmirror.dongda.di.component.DaggerArtListComponent
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
import com.scwang.smartrefresh.layout.footer.BallPulseFooter
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener

class ArtListActivity : BaseActivity(), ListMoreContract.View {

    private lateinit var ctl_root: CoordinatorLayout
    private lateinit var iv_home_head_back: ImageView
    private lateinit var tv_home_head_title: TextView
    private lateinit var rv_art_list: RecyclerView
    private lateinit var sl_art_list: SmartRefreshLayout

    private var totalCount: Int = 0
    private var serviceType: String? = null
    private var title: String? = null
    private var skip = 0
    private val take = 10
    private var adapter: ArtListAdapter? = null
    private var clickLikePos: Int = 0
    private var isNeedRefresh: Boolean = false
    private var presenter: GetMoreDataPresenter? = null

    override val layoutResId: Int
        get() = R.layout.activity_art_list

    override fun init() {
        totalCount = intent.getIntExtra("totalCount", 10)
        serviceType = intent.getStringExtra("serviceType")
        title = intent.getStringExtra("title")
    }

    override fun initInject() {
        presenter = DaggerArtListComponent.builder()
                .activity(this)
                .view(this)
                .build()
                .moreDataPresenter
    }


    override fun initView() {
        ctl_root = findViewById(R.id.ctl_root)
        iv_home_head_back = findViewById(R.id.iv_home_head_back)
        tv_home_head_title = findViewById(R.id.tv_home_head_title)
        rv_art_list = findViewById(R.id.rv_art_list)
        sl_art_list = findViewById(R.id.sl_art_list)
        sl_art_list.setEnableLoadMoreWhenContentNotFull(false)//内容不满屏幕的时候也开启加载更多
        sl_art_list.isEnableAutoLoadMore = false//内容不满屏幕的时候也开启加载更多
        sl_art_list.setRefreshHeader(MaterialHeader(this@ArtListActivity))
        val footer = BallPulseFooter(this@ArtListActivity)
        sl_art_list.setRefreshFooter(footer.setNormalColor(resources.getColor(R.color.colorPrimary)))
    }

    override fun initData() {
        showProcessDialog()
        getGridListData(skip, take)
        tv_home_head_title.text = title
    }


    override fun initListener() {
        iv_home_head_back.setOnClickListener {
            setResult(if (isNeedRefresh) Activity.RESULT_OK else Activity.RESULT_CANCELED)
            finish()
        }

        sl_art_list.setOnRefreshListener {
            skip = 0
            sl_art_list.setNoMoreData(false)
            sl_art_list.isEnableLoadMore = true
            getGridListData(skip, take)
        }


        sl_art_list.setOnLoadMoreListener(OnLoadMoreListener { refreshLayout ->
            LogUtils.d("skip==$skip")
            if (refreshLayout.state.dragging) {
                LogUtils.d("dragging")
            }
            if (totalCount <= adapter!!.itemCount - 1) {
                sl_art_list.finishLoadMore()
                sl_art_list.isEnableLoadMore = false
                sl_art_list.setNoMoreData(true)
                return@OnLoadMoreListener
            }
            if (skip + take >= totalCount) {
                getGridListData(skip, totalCount - skip)
            } else {
                skip += take
                getGridListData(skip, take)
            }
        })
    }

    private fun getGridListData(skipCount: Int, takeCount: Int) {
        presenter!!.getServiceMoreData(skipCount, takeCount, serviceType!!)
    }

    override fun onGetServiceMoreDataSuccess(bean: CareMoreDomainBean) {
        closeProcessDialog()
        if (sl_art_list.state.opening) {
            sl_art_list.finishLoadMore()
            sl_art_list.finishRefresh()
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
        if (sl_art_list.state.opening) {
            sl_art_list.finishLoadMore(false)
            sl_art_list.finishRefresh(false)
        }
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(ctl_root, bean.message)
        } else {
            ToastUtils.showShortToast(bean.message + "(" + bean.code + ")")
        }
    }

    private fun setDataToRecyclerView(bean: CareMoreDomainBean) {

        if (bean.isSuccess) {

            if (skip == 0) {//首次加载或者下拉刷新
                if (adapter == null) {
                    adapter = ArtListAdapter(this@ArtListActivity, bean)
                    adapter!!.totalCount = totalCount
                    rv_art_list.layoutManager = GridLayoutManager(this@ArtListActivity, 2)
                    rv_art_list.adapter = adapter
                    rv_art_list.addItemDecoration(GridItemDecoration(16, 16, 15, 48, 44, 2))
                    adapter!!.setOnArtListClickListener(object : ArtListAdapter.OnArtListClickListener {
                        override fun onItemArtListClick(view: View, position: Int, service_id: String) {
                            val intent = Intent(this@ArtListActivity, ServiceDetailInfoActivity::class.java)
                            clickLikePos = position
                            intent.putExtra("service_id", service_id)
                            startActivityForResult(intent, AppConstant.ART_LIST_REQUEST_CODE)
                        }

                        override fun onItemArtLikeClick(view: View, position: Int, servicesBean: CareMoreDomainBean.ServicesBean) {
                            clickLikePos = position
                            sendLikeData(servicesBean)
                        }
                    })

                } else {
                    adapter!!.setRefreshData(bean.services)
                }

            } else {
                adapter!!.setMoreData(bean.services)
            }


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
