package com.blackmirror.dongda.ui.activity.live

import android.content.Intent
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.adapter.VideoListAdapter
import com.blackmirror.dongda.adapter.itemdecoration.GridItemDecoration
import com.blackmirror.dongda.kdomain.model.VideoListDomainBean
import com.blackmirror.dongda.ui.base.BaseActivity
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.BallPulseFooter

class VideoListActivity : BaseActivity() {

    lateinit var iv_back: ImageView
    lateinit var rv_video_list: RecyclerView
    private lateinit var ctl_root: CoordinatorLayout
    private lateinit var iv_home_head_back: ImageView
    private lateinit var tv_home_head_title: TextView
    private lateinit var sl_video_list: SmartRefreshLayout

    private var totalCount: Int = 0
    private var serviceType: String? = null
    private var title: String? = null
    private var skip = 0
    private val take = 10
    private var adapter: VideoListAdapter? = null
    private var clickLikePos: Int = 0
    private var isNeedRefresh: Boolean = false

    override val layoutResId: Int
        get() = R.layout.activity_video_list

    override fun initInject() {
    }

    override fun initView() {
        iv_back = findViewById(R.id.iv_back)
        rv_video_list = findViewById(R.id.rv_video_list)
        sl_video_list = findViewById(R.id.sl_video_list)
        sl_video_list.setEnableLoadMoreWhenContentNotFull(false)//内容不满屏幕的时候也开启加载更多
        sl_video_list.isEnableAutoLoadMore = false//内容不满屏幕的时候也开启加载更多
        sl_video_list.setRefreshHeader(MaterialHeader(this@VideoListActivity))
        val footer = BallPulseFooter(this@VideoListActivity)
        sl_video_list.setRefreshFooter(footer.setNormalColor(resources.getColor(R.color.colorPrimary)))
        sl_video_list.isEnableRefresh = false
    }

    override fun initData() {

        val bean = VideoListDomainBean()
        for (i in 0..10) {
            bean.imgList.add(i.toString())
            bean.videoList.add(i.toString())
        }

        adapter = VideoListAdapter(this, bean)
        rv_video_list.layoutManager = GridLayoutManager(this@VideoListActivity, 2)
        rv_video_list.adapter = adapter
        rv_video_list.addItemDecoration(GridItemDecoration(16, 16, 15, 48, 44, 2))


    }

    override fun initListener() {
        iv_back.setOnClickListener {
            finish()
        }
        adapter!!.setOnVideoItemClickListener { view, positon, url ->
            startActivity(Intent(this@VideoListActivity,VideoDetailActivity::class.java))
        }
    }

}
