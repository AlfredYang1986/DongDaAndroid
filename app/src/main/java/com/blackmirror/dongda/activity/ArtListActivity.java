package com.blackmirror.dongda.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.BasePrefUtils;
import com.blackmirror.dongda.Tools.LogUtils;
import com.blackmirror.dongda.Tools.ToastUtils;
import com.blackmirror.dongda.adapter.ArtListAdapter;
import com.blackmirror.dongda.adapter.itemdecoration.GridItemDecoration;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.model.ErrorInfoBean;
import com.blackmirror.dongda.model.serverbean.ArtMoreServerBean;
import com.blackmirror.dongda.model.uibean.ArtMoreUiBean;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

public class ArtListActivity extends AYActivity {

    private ImageView iv_home_head_back;
    private TextView tv_home_head_title;
    private RecyclerView rv_art_list;
    private SmartRefreshLayout sl_art_list;

    private int totalCount=30;
    private String serviceType;
    private String title;
    private int skip = 0;
    private int take = 10;
    private ArtListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art_list);
//        totalCount = getIntent().getIntExtra("totalCount", 20);
        serviceType = getIntent().getStringExtra("serviceType");
        title = getIntent().getStringExtra("title");
        initView();
        initData();
        initListener();
    }


    private void initView() {
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

    private void initData() {
        getGridListData(skip, take);
        tv_home_head_title.setText(title);
    }

    private void getGridListData(int skipCount, int takeCount) {
        AYFacade facade = facades.get("QueryServiceFacade");
        try {
            String json = "{\"skip\" : " + skipCount + ",\"take\" : " + takeCount + ",\"token\": \"" + BasePrefUtils.getAuthToken() + "\",\"condition\": {\"user_id\":\"" + BasePrefUtils.getUserId() + "\",\"service_type\": \"" +
                    serviceType + "\"}}";
            JSONObject object = new JSONObject(json);
            facade.execute("AYSubjectMoreCommand", object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取更多信息列表
     *
     * @param args
     */
    public void AYSubjectMoreCommandSuccess(JSONObject args) {
        ArtMoreServerBean serverBean = JSON.parseObject(args.toString(), ArtMoreServerBean.class);
        ArtMoreUiBean bean = new ArtMoreUiBean(serverBean);
        if (sl_art_list.getState().opening) {
            sl_art_list.finishLoadMore();
            sl_art_list.finishRefresh();
        }
        setDataToRecyclerView(bean);
    }

    public void AYSubjectMoreCommandFailed(JSONObject args) {
        if (sl_art_list.getState().opening) {
            sl_art_list.finishLoadMore(false);
            sl_art_list.finishRefresh(false);
        }
        ErrorInfoBean bean = JSON.parseObject(args.toString(), ErrorInfoBean.class);
        if (bean != null && bean.error != null) {
            ToastUtils.showShortToast(bean.error.message + "(" + bean.error.code + ")");
        }
    }

    private void initListener() {
        iv_home_head_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    private void setDataToRecyclerView(ArtMoreUiBean bean) {

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
                        public void onItemArtListClick(View view, int position) {

                        }

                        @Override
                        public void onItemArtLikeClick(View view, int position) {
                            ToastUtils.showShortToast("点击了 " + position);
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

    @Override
    protected void bindingFragments() {

    }

    public static void startArtListActivity(AppCompatActivity activity, int totalCount, String serviceType) {

    }

}
