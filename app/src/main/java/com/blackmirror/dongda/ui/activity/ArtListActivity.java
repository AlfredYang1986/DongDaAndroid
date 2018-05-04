package com.blackmirror.dongda.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.adapter.ArtListAdapter;
import com.blackmirror.dongda.adapter.itemdecoration.GridItemDecoration;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.model.serverbean.ArtMoreServerBean;
import com.blackmirror.dongda.model.serverbean.ErrorInfoServerBean;
import com.blackmirror.dongda.model.serverbean.LikePopServerBean;
import com.blackmirror.dongda.model.serverbean.LikePushServerBean;
import com.blackmirror.dongda.model.uibean.ArtMoreUiBean;
import com.blackmirror.dongda.model.uibean.ErrorInfoUiBean;
import com.blackmirror.dongda.model.uibean.LikePopUiBean;
import com.blackmirror.dongda.model.uibean.LikePushUiBean;
import com.blackmirror.dongda.utils.AYPrefUtils;
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

import org.json.JSONException;
import org.json.JSONObject;

public class ArtListActivity extends AYActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art_list);
        totalCount = getIntent().getIntExtra("totalCount", 10);
        serviceType = getIntent().getStringExtra("serviceType");
        title = getIntent().getStringExtra("title");
        initView();
        initData();
        initListener();
    }


    private void initView() {
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

    private void initData() {
        showProcessDialog();
        getGridListData(skip, take);
        tv_home_head_title.setText(title);
    }

    private void getGridListData(int skipCount, int takeCount) {
        try {
            AYFacade facade = facades.get("QueryServiceFacade");
//            showProcessDialog();
            String json = "{\"skip\" : " + skipCount + ",\"take\" : " + takeCount + ",\"token\": \"" + AYPrefUtils.getAuthToken() + "\",\"condition\": {\"user_id\":\"" + AYPrefUtils.getUserId() + "\",\"service_type\": \"" +
                    serviceType + "\"}}";
            JSONObject object = new JSONObject(json);
            facade.execute("AYSubjectMoreCommand", object);
        } catch (JSONException e) {
//            closeProcessDialog();
            e.printStackTrace();
        }
    }

    /**
     * 获取更多信息列表
     *
     * @param args
     */
    public void AYSubjectMoreCommandSuccess(JSONObject args) {
        closeProcessDialog();
        ArtMoreServerBean serverBean = JSON.parseObject(args.toString(), ArtMoreServerBean.class);
        ArtMoreUiBean bean = new ArtMoreUiBean(serverBean);
        if (sl_art_list.getState().opening) {
            sl_art_list.finishLoadMore();
            sl_art_list.finishRefresh();
        }
        if (bean.isSuccess) {
            setDataToRecyclerView(bean);
        }else {
            ToastUtils.showShortToast(bean.message + "(" + bean.code + ")");
        }
    }

    public void AYSubjectMoreCommandFailed(JSONObject args) {
        LogUtils.d("flag","AYSubjectMoreCommandFailed");
        closeProcessDialog();
        if (sl_art_list.getState().opening) {
            sl_art_list.finishLoadMore(false);
            sl_art_list.finishRefresh(false);
        }
        ErrorInfoServerBean serverBean = JSON.parseObject(args.toString(), ErrorInfoServerBean.class);
        ErrorInfoUiBean uiBean = new ErrorInfoUiBean(serverBean);
        if (uiBean.code== AppConstant.NET_WORK_UNAVAILABLE){
            SnackbarUtils.show(ctl_root,uiBean.message);
        }else {
            ToastUtils.showShortToast(uiBean.message+"("+uiBean.code+")");
        }
    }

    private void initListener() {
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
                        public void onItemArtListClick(View view, int position, String service_id) {
                            Intent intent = new Intent(ArtListActivity.this, ServiceDetailInfoActivity.class);
                            intent.putExtra("service_id",service_id);
                            startActivity(intent);
                        }

                        @Override
                        public void onItemArtLikeClick(View view, int position, ArtMoreServerBean.ResultBean.ServicesBean servicesBean) {
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

    private void sendLikeData(ArtMoreServerBean.ResultBean.ServicesBean bean) {
        String t= AYPrefUtils.getAuthToken();
        String u= AYPrefUtils.getUserId();
        showProcessDialog();
        if (bean.is_collected){//已收藏 点击取消
            String json="{\"token\":\""+t+"\",\"condition\": {\"user_id\":\""+u+"\",\"service_id\":\""+bean.service_id+"\"},\"collections\":{\"user_id\": \""+u+"\",\"service_id\":\""+bean.service_id+"\"}}";
            try {
                JSONObject object = new JSONObject(json);
                facades.get("QueryServiceFacade").execute("AYLikePopCommand",object);
            } catch (JSONException e) {
                e.printStackTrace();
                closeProcessDialog();
            }
        }else {
            String json="{\"token\":\""+t+"\",\"condition\": {\"user_id\":\""+u+"\",\"service_id\":\""+bean.service_id+"\"},\"collections\":{\"user_id\": \""+u+"\",\"service_id\":\""+bean.service_id+"\"}}";
            try {
                JSONObject object = new JSONObject(json);
                facades.get("QueryServiceFacade").execute("AYLikePushCommand",object);
            } catch (JSONException e) {
                e.printStackTrace();
                closeProcessDialog();
            }
        }
    }

    /**
     * 收藏相关
     * @param args
     */
    public void AYLikePushCommandSuccess(JSONObject args){
        isNeedRefresh = true;
        closeProcessDialog();
        LikePushServerBean serverBean = JSON.parseObject(args.toString(), LikePushServerBean.class);
        LikePushUiBean pushUiBean = new LikePushUiBean(serverBean);
        if (pushUiBean.isSuccess){
            adapter.notifyItemChanged(clickLikePos,true);
        }else {
            ToastUtils.showShortToast(pushUiBean.message+"("+pushUiBean.code+")");
        }
    }

    public void AYLikePushCommandFailed(JSONObject args) {
        closeProcessDialog();
        ErrorInfoServerBean serverBean = JSON.parseObject(args.toString(), ErrorInfoServerBean.class);
        ErrorInfoUiBean uiBean = new ErrorInfoUiBean(serverBean);
        if (uiBean.code==AppConstant.NET_WORK_UNAVAILABLE){
            SnackbarUtils.show(ctl_root,uiBean.message);
        }else {
            ToastUtils.showShortToast(uiBean.message+"("+uiBean.code+")");
        }
    }

    /**
     * 取消收藏相关
     * @param args
     */
    public void AYLikePopCommandSuccess(JSONObject args){
        isNeedRefresh = false;
        closeProcessDialog();
        LikePopServerBean serverBean = JSON.parseObject(args.toString(), LikePopServerBean.class);
        LikePopUiBean popUiBean = new LikePopUiBean(serverBean);
        if (popUiBean.isSuccess){
            adapter.notifyItemChanged(clickLikePos,false);
        }else {
            ToastUtils.showShortToast(popUiBean.message+"("+popUiBean.code+")");
        }
    }

    public void AYLikePopCommandFailed(JSONObject args) {
        closeProcessDialog();
        ErrorInfoServerBean serverBean = JSON.parseObject(args.toString(), ErrorInfoServerBean.class);
        ErrorInfoUiBean uiBean = new ErrorInfoUiBean(serverBean);
        if (uiBean.code==AppConstant.NET_WORK_UNAVAILABLE){
            SnackbarUtils.show(ctl_root,uiBean.message);
        }else {
            ToastUtils.showShortToast(uiBean.message+"("+uiBean.code+")");
        }
    }

    @Override
    protected void bindingFragments() {

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
