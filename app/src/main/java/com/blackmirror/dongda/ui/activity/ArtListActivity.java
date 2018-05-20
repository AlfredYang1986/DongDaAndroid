package com.blackmirror.dongda.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.adapter.ArtListAdapter;
import com.blackmirror.dongda.adapter.itemdecoration.GridItemDecoration;
import com.blackmirror.dongda.di.component.DaggerArtListComponent;
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
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class ArtListActivity extends BaseActivity implements ListMoreContract.View{

    private CoordinatorLayout ctl_root;
    private ImageView iv_home_head_back;
    private TextView tv_home_head_title;
    private RecyclerView rv_art_list;
    private SmartRefreshLayout sl_art_list;

    private int totalCount;
    private String serviceType;
    private String title;
    private int skip = 0;
    private int take = 10;
    private ArtListAdapter adapter;
    private int clickLikePos;
    private boolean isNeedRefresh;
    private GetMoreDataPresenter presenter;

    @Override
    protected void init() {
        totalCount = getIntent().getIntExtra("totalCount", 10);
        serviceType = getIntent().getStringExtra("serviceType");
        title = getIntent().getStringExtra("title");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_art_list;
    }

    @Override
    protected void initInject() {
        presenter = DaggerArtListComponent.builder()
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
        rv_art_list = findViewById(R.id.rv_art_list);
        sl_art_list = findViewById(R.id.sl_art_list);
        sl_art_list.setEnableLoadMoreWhenContentNotFull(false);//内容不满屏幕的时候也开启加载更多
        sl_art_list.setEnableAutoLoadMore(false);//内容不满屏幕的时候也开启加载更多
        sl_art_list.setRefreshHeader(new MaterialHeader(ArtListActivity.this));
        BallPulseFooter footer = new BallPulseFooter(ArtListActivity.this);
        sl_art_list.setRefreshFooter(footer.setNormalColor(getResources().getColor(R.color.colorPrimary)));
    }

    @Override
    protected void initData() {
        showProcessDialog();
        getGridListData(skip, take);
        tv_home_head_title.setText(title);
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

        sl_art_list.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                skip = 0;
                sl_art_list.setNoMoreData(false);
                sl_art_list.setEnableLoadMore(true);
                getGridListData(skip, take);
            }
        });


        sl_art_list.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                LogUtils.d("skip==" + skip);
                if (refreshLayout.getState().dragging) {
                    LogUtils.d("dragging");
                }
                if (totalCount <= adapter.getItemCount()-1) {
                    sl_art_list.finishLoadMore();
                    sl_art_list.setEnableLoadMore(false);
                    sl_art_list.setNoMoreData(true);
                    return;
                }
                if ((skip + take) >= totalCount) {
                    getGridListData(skip, totalCount - skip);
                } else {
                    skip += take;
                    getGridListData(skip, take);
                }
            }
        });
    }

    private void getGridListData(int skipCount, int takeCount) {
        presenter.getServiceMoreData(skipCount, takeCount, serviceType);
    }

    @Override
    public void onGetServiceMoreDataSuccess(CareMoreDomainBean bean) {
        closeProcessDialog();
        if (sl_art_list.getState().opening) {
            sl_art_list.finishLoadMore();
            sl_art_list.finishRefresh();
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
        closeProcessDialog();
        if (sl_art_list.getState().opening) {
            sl_art_list.finishLoadMore(false);
            sl_art_list.finishRefresh(false);
        }
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(ctl_root, bean.message);
        } else {
            ToastUtils.showShortToast(bean.message + "(" + bean.code + ")");
        }
    }

    private void setDataToRecyclerView(CareMoreDomainBean bean) {

        if (bean.isSuccess) {

            if (skip == 0) {//首次加载或者下拉刷新
                if (adapter == null) {
                    adapter = new ArtListAdapter(ArtListActivity.this, bean);
                    adapter.totalCount = totalCount;
                    rv_art_list.setLayoutManager(new GridLayoutManager(ArtListActivity.this, 2));
                    rv_art_list.setAdapter(adapter);
                    rv_art_list.addItemDecoration(new GridItemDecoration(16, 16, 15, 48, 44, 2));
                    adapter.setOnArtListClickListener(new ArtListAdapter.OnArtListClickListener() {
                        @Override
                        public void onItemArtListClick(View view, int position, String service_id) {
                            Intent intent = new Intent(ArtListActivity.this, ServiceDetailInfoActivity.class);
                            intent.putExtra("service_id",service_id);
                            startActivity(intent);
                        }

                        @Override
                        public void onItemArtLikeClick(View view, int position, CareMoreDomainBean.ServicesBean servicesBean) {
                            clickLikePos=position;
                            sendLikeData(servicesBean);
                        }
                    });

                } else {
                    adapter.setRefreshData(bean.services);
                }

            } else {
                adapter.setMoreData(bean.services);
            }


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


}
