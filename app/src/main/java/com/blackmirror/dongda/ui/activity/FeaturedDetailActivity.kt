package com.blackmirror.dongda.ui.activity

import android.app.Activity
import android.content.Intent
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.adapter.FeaturedDetailAdapter
import com.blackmirror.dongda.di.component.DaggerFeaturedDetailComponent
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.kdomain.model.CareMoreDomainBean
import com.blackmirror.dongda.kdomain.model.LikePopDomainBean
import com.blackmirror.dongda.kdomain.model.LikePushDomainBean
import com.blackmirror.dongda.presenter.GetMoreDataPresenter
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.AppConstant
import com.blackmirror.dongda.utils.SnackbarUtils
import com.blackmirror.dongda.utils.ToastUtils

/**
 * 精选主题详情页
 */
class FeaturedDetailActivity : BaseActivity(), ListMoreContract.View {

    private lateinit var iv_featured_detail_back: ImageView
    private lateinit var tv_featured_tb_title: TextView
    private lateinit var rv_featured_detail: RecyclerView
    private lateinit var toolbar: Toolbar
    private lateinit var ctl_root: CoordinatorLayout

    private var pos: Int = 0
    private var title: String? = null
    private var content: String? = null
    private var service_type: String? = null
    private var bg_resId: Int = 0
    private var clickLikePos: Int = 0
    private var skipCount = 0
    private var adapter: FeaturedDetailAdapter? = null
    private var isNeedRefresh: Boolean = false
    private var presenter: GetMoreDataPresenter? = null

    override val layoutResId: Int
        get() = R.layout.activity_featured_detail

    override fun init() {
        pos = intent.getIntExtra("pos", 0)
    }

    override fun initInject() {
        presenter = DaggerFeaturedDetailComponent.builder()
                .activity(this)
                .view(this)
                .build()
                .moreDataPresenter
    }

    override fun initView() {
        toolbar = findViewById(R.id.tb_toolbar)
        setSupportActionBar(toolbar)
        iv_featured_detail_back = findViewById(R.id.iv_featured_detail_back)
        tv_featured_tb_title = findViewById(R.id.tv_featured_tb_title)
        rv_featured_detail = findViewById(R.id.rv_featured_detail)
        ctl_root = findViewById(R.id.ctl_root)
    }

    override fun initData() {
        initTbTitle()
    }

    override fun initListener() {
        iv_featured_detail_back.setOnClickListener {
            setResult(if (isNeedRefresh) Activity.RESULT_OK else Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun initTbTitle() {
        when (pos) {
            0//蒙特俊利
            -> {
                bg_resId = R.drawable.coverlist_bg_theme
                tv_featured_tb_title.setText(R.string.str_mtjl)
                title = getString(R.string.str_mtjl_title)
                content = getString(R.string.str_mtjl_content)
                service_type = getString(R.string.str_care)
                getServerData(0, service_type)
            }
            1//浸入式英语
            -> {
                bg_resId = R.drawable.coverlist_bg_01
                tv_featured_tb_title.setText(R.string.str_jrsyy)
                title = getString(R.string.str_jrsyy_title)
                content = getString(R.string.str_jrsyy_content)
                service_type = getString(R.string.str_care)
                skipCount = 10
                getServerData(skipCount, service_type)
            }
            2//极限运动
            -> {
                bg_resId = R.drawable.coverlist_bg_03
                tv_featured_tb_title.text = getString(R.string.str_jxyd)
                title = getString(R.string.str_jxyd_title)
                content = getString(R.string.str_jxyd_content)
                service_type = getString(R.string.str_sport)
                getServerData(0, service_type)
            }
            3//修身养性
            -> {
                bg_resId = R.drawable.coverlist_bg_02
                tv_featured_tb_title.setText(R.string.str_xsyx)
                title = getString(R.string.str_xsyx_title)
                content = getString(R.string.str_xsyx_content)
                service_type = getString(R.string.str_art)
                getServerData(0, service_type)
            }
            4//STEAM
            -> {
                bg_resId = R.drawable.coverlist_bg_04
                tv_featured_tb_title.setText(R.string.str_steam)
                title = getString(R.string.str_steam_title)
                content = getString(R.string.str_steam_content)
                service_type = getString(R.string.str_science)
                getServerData(0, service_type)
            }
        }
    }

    private fun getServerData(skipCount: Int, service_type: String?) {

        showProcessDialog()
        presenter?.getServiceMoreData(skipCount, 10, service_type!!)
    }

    override fun onGetServiceMoreDataSuccess(bean: CareMoreDomainBean) {
        closeProcessDialog()
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
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(ctl_root, bean.message)
        } else {
            ToastUtils.showShortToast("${bean.message}(${bean.code})")
        }
    }


    private fun setDataToRecyclerView(bean: CareMoreDomainBean) {

        if (bean.isSuccess) {

            adapter = FeaturedDetailAdapter(this@FeaturedDetailActivity, bean)
            adapter!!.title = title
            adapter!!.content = content
            adapter!!.bg_resId = bg_resId
            rv_featured_detail.layoutManager = LinearLayoutManager(this@FeaturedDetailActivity)
            rv_featured_detail.adapter = adapter


            adapter?.setOnDetailListClickListener({
                view,position,service_id->
                val intent = Intent(this@FeaturedDetailActivity, ServiceDetailInfoActivity::class.java)
                intent.putExtra("service_id", service_id)
                startActivityForResult(intent, AppConstant.SERVICE_DETAIL_REQUEST_CODE)
            },{
                view,position,servicesBean->
                clickLikePos = position
                sendLikeData(servicesBean)
            })


        } else {
            ToastUtils.showShortToast("${bean.message}(${bean.code})")
        }
    }


    private fun sendLikeData(bean: CareMoreDomainBean.ServicesBean) {
        showProcessDialog()
        if (bean.is_collected) {//已收藏 点击取消
            presenter?.likePop(bean.service_id!!)
        } else {
            presenter?.likePush(bean.service_id!!)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AppConstant.SERVICE_DETAIL_REQUEST_CODE) {
                getServerData(skipCount, service_type)
            }
        }
    }

    override fun onBackPressed() {
        setResult(if (isNeedRefresh) Activity.RESULT_OK else Activity.RESULT_CANCELED)
        super.onBackPressed()
    }

}
