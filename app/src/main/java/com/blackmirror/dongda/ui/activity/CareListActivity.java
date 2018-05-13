package com.blackmirror.dongda.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.adapter.CareListAdapter;
import com.blackmirror.dongda.adapter.itemdecoration.TopItemDecoration;
import com.blackmirror.dongda.di.component.DaggerCareListComponent;
import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.domain.model.CareMoreDomainBean;
import com.blackmirror.dongda.domain.model.LikePopDomainBean;
import com.blackmirror.dongda.domain.model.LikePushDomainBean;
import com.blackmirror.dongda.presenter.GetMoreDataPresenter;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.blackmirror.dongda.utils.AppConstant;
import com.blackmirror.dongda.utils.LogUtils;
import com.blackmirror.dongda.utils.SnackbarUtils;
import com.blackmirror.dongda.utils.ToastUtils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class CareListActivity extends BaseActivity implements ListMoreContract.View {

    private final String TAG = "CareListActivity";

    private CoordinatorLayout ctl_root;
    private ImageView iv_home_head_back;
    private TextView tv_home_head_title;
    private RecyclerView rv_care_list;
    private SmartRefreshLayout sl_care_list;
    private CareListAdapter adapter;
    private int totalCount;
    int skip = 0;
    int take = 10;
    private boolean isNeedRefresh;
    private int clickLikePos;
    private GetMoreDataPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        totalCount = getIntent().getIntExtra("totalCount", 10);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_care_list;
    }

    @Override
    protected void initInject() {
        presenter = DaggerCareListComponent.builder()
                .activity(this)
                .view(this)
                .build()
                .getMoreDataPresenter();
    }


    @Override
    protected void initView() {
        ctl_root = findViewById(R.id.ctl_root);
        iv_home_head_back = findViewById(R.id.iv_home_head_back);
        tv_home_head_title = findViewById(R.id.tv_home_head_title);
        rv_care_list = findViewById(R.id.rv_care_list);
        sl_care_list = findViewById(R.id.sl_care_list);
        sl_care_list.setEnableLoadMoreWhenContentNotFull(false);//内容不满屏幕的时候也开启加载更多
        sl_care_list.setEnableAutoLoadMore(false);//内容不满屏幕的时候也开启加载更多
        sl_care_list.setRefreshHeader(new MaterialHeader(CareListActivity.this));
    }

    @Override
    protected void initData() {
        initData(skip, take);
    }

    private void initData(int skipCount, int takeCount) {
        presenter.getServiceMoreData(skipCount, takeCount, "看顾");
    }


    @Override
    protected void initListener() {
        iv_home_head_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(isNeedRefresh ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
                finish();
            }
        });
        sl_care_list.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                skip = 0;
                sl_care_list.setNoMoreData(false);
                sl_care_list.setEnableLoadMore(true);
                initData(skip, take);
            }
        });


        sl_care_list.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                LogUtils.d("skip==" + skip);
                if (refreshLayout.getState().dragging) {
                    LogUtils.d("dragging");
                }
                if (totalCount <= adapter.getItemCount()) {
                    sl_care_list.finishLoadMore();
                    sl_care_list.setEnableLoadMore(false);
                    sl_care_list.setNoMoreData(true);
                    return;
                }
                if ((skip + take) >= totalCount) {
                    initData(skip, totalCount - skip);
                } else {
                    skip += take;
                    initData(skip, take);
                }
            }
        });
    }

    private void setDataToRecyclerView(CareMoreDomainBean bean) {

        if (bean.isSuccess) {

            tv_home_head_title.setText(R.string.str_care);

            if (skip == 0) {//首次加载或者下拉刷新
                if (adapter == null) {
                    adapter = new CareListAdapter(CareListActivity.this, bean);
                    rv_care_list.setLayoutManager(new LinearLayoutManager(CareListActivity.this));
                    rv_care_list.setAdapter(adapter);
                    rv_care_list.addItemDecoration(new TopItemDecoration(40, 40));
                } else {
                    adapter.setRefreshData(bean.services);
                }

            } else {
                adapter.setMoreData(bean.services);
            }

            adapter.setOnCareListClickListener(new CareListAdapter.OnCareListClickListener() {
                @Override
                public void onItemCareListClick(View view, int position, String service_id) {
                    Intent intent = new Intent(CareListActivity.this, ServiceDetailInfoActivity.class);
                    intent.putExtra("service_id", service_id);
                    startActivity(intent);
                }

                @Override
                public void onItemCareLikeClick(View view, int position, CareMoreDomainBean.ServicesBean servicesBean) {
                    clickLikePos = position;
                    sendLikeData(servicesBean);
                }
            });
        } else {
            ToastUtils.showShortToast(bean.message + "(" + bean.code + ")");
        }
    }


    private void sendLikeData(CareMoreDomainBean.ServicesBean bean) {
        showProcessDialog();
        if (bean.is_collected) {//已收藏 点击取消
            presenter.likePop(bean.service_id);
        } else {
            presenter.likePush(bean.service_id);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(isNeedRefresh ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onGetServiceMoreDataSuccess(CareMoreDomainBean bean) {
        if (sl_care_list.getState().opening) {
            sl_care_list.finishLoadMore();
            sl_care_list.finishRefresh();
        }
        setDataToRecyclerView(bean);
    }

    @Override
    public void onLikePushSuccess(LikePushDomainBean bean) {
        isNeedRefresh = true;
        closeProcessDialog();
        adapter.notifyItemChanged(clickLikePos, true);
    }

    @Override
    public void onLikePopSuccess(LikePopDomainBean bean) {
        isNeedRefresh = true;
        closeProcessDialog();
        adapter.notifyItemChanged(clickLikePos, false);

    }

    @Override
    public void onGetDataError(BaseDataBean bean) {
        if (sl_care_list.getState().opening) {
            sl_care_list.finishLoadMore(false);
            sl_care_list.finishRefresh(false);
        }
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(ctl_root, bean.message);
        } else {
            ToastUtils.showShortToast(bean.message + "(" + bean.code + ")");
        }
    }

}
