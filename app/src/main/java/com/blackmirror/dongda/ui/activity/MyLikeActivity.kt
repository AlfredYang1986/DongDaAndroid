package com.blackmirror.dongda.ui.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.adapter.MyLikeListAdapter
import com.blackmirror.dongda.adapter.itemdecoration.TopItemDecoration
import com.blackmirror.dongda.di.component.DaggerMyLikeComponent
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.kdomain.model.LikeDomainBean
import com.blackmirror.dongda.kdomain.model.LikePopDomainBean
import com.blackmirror.dongda.kdomain.model.LikePushDomainBean
import com.blackmirror.dongda.presenter.MyLikePresenter
import com.blackmirror.dongda.ui.Contract
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.AppConstant
import com.blackmirror.dongda.utils.SnackbarUtils
import com.blackmirror.dongda.utils.ToastUtils

class MyLikeActivity : BaseActivity(), Contract.MyLikeView {

    private lateinit var ctl_root: CoordinatorLayout
    private lateinit var rv_my_like: RecyclerView
    private lateinit var iv_home_head_back: ImageView
    private lateinit var tv_home_head_title: TextView

    private var adapter: MyLikeListAdapter? = null
    private var clickLikePos: Int = 0
    private var dialog: AlertDialog? = null
    private var presenter: MyLikePresenter? = null
    private var isNeedRefresh: Boolean = false

    override val layoutResId: Int
        get() = R.layout.activity_my_like

    override fun initInject() {
        presenter = DaggerMyLikeComponent.builder()
                .activity(this)
                .view(this)
                .build()
                .myLikePresenter
    }

    override fun initView() {
        ctl_root = findViewById(R.id.ctl_root)
        rv_my_like = findViewById(R.id.rv_my_like)
        iv_home_head_back = findViewById(R.id.iv_home_head_back)
        tv_home_head_title = findViewById(R.id.tv_home_head_title)
        tv_home_head_title.setText(R.string.my_collection)
    }

    override fun initData() {
        getLikeData()
    }

    override fun initListener() {
        iv_home_head_back.setOnClickListener {
            setResult(if (isNeedRefresh) Activity.RESULT_OK else Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun getLikeData() {
        showProcessDialog()
        presenter?.getLikeData()
    }

    override fun onGetLikeDataSuccess(bean: LikeDomainBean) {
        closeProcessDialog()
        adapter = MyLikeListAdapter(this@MyLikeActivity, bean)
        rv_my_like.layoutManager = LinearLayoutManager(this@MyLikeActivity)
        rv_my_like.adapter = adapter
        rv_my_like.addItemDecoration(TopItemDecoration(40, 40))
        adapter?.setOnLikeListClickListener({
            view,position,service_id->
            val intent = Intent(this@MyLikeActivity, ServiceDetailInfoActivity::class.java)
            intent.putExtra("service_id", service_id)
            startActivity(intent)
        },{
            view,position,likeBean->
            clickLikePos = position
            showConfirmUnLikeDialog(position, likeBean)
        })
    }

    override fun onLikePushSuccess(bean: LikePushDomainBean) {
        isNeedRefresh = true
        closeProcessDialog()
        adapter!!.notifyItemChanged(clickLikePos, true)
    }

    override fun onLikePopSuccess(bean: LikePopDomainBean) {
        isNeedRefresh = true
        closeProcessDialog()
        adapter!!.removeItem(clickLikePos)
    }

    override fun onGetDataError(bean: BaseDataBean) {
        closeProcessDialog()
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(ctl_root, bean.message)
        } else {
            ToastUtils.showShortToast(bean.message + "(" + bean.code + ")")
        }
    }

    private fun sendLikeData(bean: LikeDomainBean.ServicesBean) {
        showProcessDialog()
        if (bean.is_collected) {//已收藏 点击取消
            presenter?.likePop(bean.service_id!!)
        } else {
            presenter?.likePush(bean.service_id!!)
        }
    }

    private fun showConfirmUnLikeDialog(position: Int, bean: LikeDomainBean.ServicesBean) {
        dialog = AlertDialog.Builder(this@MyLikeActivity)
                .setCancelable(false)
                .setTitle(R.string.dlg_tips)
                .setMessage(R.string.confirm_un_like)
                .setPositiveButton(getString(R.string.dlg_confirm)) { dialog, which ->
                    dialog.dismiss()
                    sendLikeData(bean)
                }
                .setNegativeButton(getString(R.string.dlg_cancel)) { dialog, which -> dialog.dismiss() }.create()
        dialog?.show()

    }

    override fun onBackPressed() {
        setResult(if (isNeedRefresh) Activity.RESULT_OK else Activity.RESULT_CANCELED)
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
            dialog = null
        }
    }
}
