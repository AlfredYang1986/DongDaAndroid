package com.blackmirror.dongda.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.BasePrefUtils;
import com.blackmirror.dongda.Tools.LogUtils;
import com.blackmirror.dongda.Tools.ToastUtils;
import com.blackmirror.dongda.adapter.CareListAdapter;
import com.blackmirror.dongda.adapter.itemdecoration.TopItemDecoration;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.model.ErrorInfoBean;
import com.blackmirror.dongda.model.serverbean.CareMoreServerBean;
import com.blackmirror.dongda.model.serverbean.LikePopServerBean;
import com.blackmirror.dongda.model.serverbean.LikePushServerBean;
import com.blackmirror.dongda.model.uibean.CareMoreUiBean;
import com.blackmirror.dongda.model.uibean.LikePopUiBean;
import com.blackmirror.dongda.model.uibean.LikePushUiBean;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

public class CareListActivity extends AYActivity {

    private final String TAG="CareListActivity";

    private ImageView iv_home_head_back;
    private TextView tv_home_head_title;
    private RecyclerView rv_care_list;
    private SmartRefreshLayout sl_care_list;
    private CareListAdapter adapter;
    private int totalCount;
    int skip=0;
    int take=10;
    private int clickLikePos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_list);
        totalCount=getIntent().getIntExtra("totalCount",10);
        initView();
        initData(skip,take);
        initListener();
//        OtherUtils.setStatusBarColor(this);
    }

    @Override
    protected void bindingFragments() {

    }

    private void initView() {
        iv_home_head_back = findViewById(R.id.iv_home_head_back);
        tv_home_head_title = findViewById(R.id.tv_home_head_title);
        rv_care_list = findViewById(R.id.rv_care_list);
        sl_care_list = findViewById(R.id.sl_care_list);
        sl_care_list.setEnableLoadMoreWhenContentNotFull(false);//内容不满屏幕的时候也开启加载更多
        sl_care_list.setEnableAutoLoadMore(false);//内容不满屏幕的时候也开启加载更多
        sl_care_list.setRefreshHeader(new MaterialHeader(CareListActivity.this));
    }

    private void initData(int skipCount,int takeCount) {

        AYFacade facade = facades.get("QueryServiceFacade");
        try {
            String json="{\"skip\" : "+skipCount+",\"take\" : "+takeCount+",\"token\": \""+ BasePrefUtils.getAuthToken()+"\",\"condition\": {\"user_id\":\""+BasePrefUtils.getUserId()+"\",\"service_type\": \"看顾\"}}";
            JSONObject object = new JSONObject(json);
            facade.execute("AYSubjectMoreCommand",object);
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }


    private void initListener() {
        iv_home_head_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sl_care_list.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                skip=0;
                sl_care_list.setNoMoreData(false);
                sl_care_list.setEnableLoadMore(true);
                initData(skip,take);
            }
        });


        sl_care_list.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                LogUtils.d("skip=="+skip);
                if (refreshLayout.getState().dragging){
                    LogUtils.d("dragging");
                }
                if (totalCount<=adapter.getItemCount()){
                    sl_care_list.finishLoadMore();
                    sl_care_list.setEnableLoadMore(false);
                    sl_care_list.setNoMoreData(true);
                    return;
                }
                if ((skip+take)>=totalCount){
                    initData(skip,totalCount-skip);
                }else {
                    skip+=take;
                    initData(skip,take);
                }
            }
        });
    }

    /**
     * 获取更多信息列表
     * @param args
     */
    public void AYSubjectMoreCommandSuccess(JSONObject args){
        CareMoreServerBean serverBean = JSON.parseObject(args.toString(), CareMoreServerBean.class);
        CareMoreUiBean bean = new CareMoreUiBean(serverBean);
        if (sl_care_list.getState().opening){
            sl_care_list.finishLoadMore();
            sl_care_list.finishRefresh();
        }
        setDataToRecyclerView(bean);
    }

    public void AYSubjectMoreCommandFailed(JSONObject args) {
        if (sl_care_list.getState().opening){
            sl_care_list.finishLoadMore(false);
            sl_care_list.finishRefresh(false);
        }
        ErrorInfoBean bean = JSON.parseObject(args.toString(), ErrorInfoBean.class);
        if (bean != null && bean.error != null) {
            ToastUtils.showShortToast(bean.error.message+"("+bean.error.code+")");
        }
    }

    private void setDataToRecyclerView(CareMoreUiBean bean) {

        if (bean.isSuccess) {

            tv_home_head_title.setText("看顾");

            if (skip==0){//首次加载或者下拉刷新
                if (adapter==null){
                    adapter = new CareListAdapter(CareListActivity.this, bean);
                    rv_care_list.setLayoutManager(new LinearLayoutManager(CareListActivity.this));
                    rv_care_list.setAdapter(adapter);
                    rv_care_list.addItemDecoration(new TopItemDecoration(40, 40));
                }else {
                    adapter.setRefreshData(bean.services);
                    adapter.notifyDataSetChanged();
                }

            }else {
                adapter.setMoreData(bean.services);
                adapter.notifyDataSetChanged();
            }


            adapter.setOnCareListClickListener(new CareListAdapter.OnCareListClickListener() {
                @Override
                public void onItemCareListClick(View view, int position, String service_id) {
                    Intent intent = new Intent(CareListActivity.this, ServiceDetailInfoActivity.class);
                    intent.putExtra("service_id",service_id);
                    startActivity(intent);
                }

                @Override
                public void onItemCareLikeClick(View view, int position, CareMoreServerBean
                        .ResultBean.ServicesBean servicesBean) {
                    clickLikePos=position;
                    sendLikeData(servicesBean);
                }
            });
        }else {
            ToastUtils.showShortToast(bean.message+"("+bean.code+")");
        }
    }


    private void sendLikeData(CareMoreServerBean.ResultBean.ServicesBean bean) {
        String t=BasePrefUtils.getAuthToken();
        String u=BasePrefUtils.getUserId();
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
        ErrorInfoBean bean = JSON.parseObject(args.toString(), ErrorInfoBean.class);
        if (bean != null && bean.error != null) {
            ToastUtils.showShortToast(bean.error.message+"("+bean.error.code+")");
        }
    }

    /**
     * 取消收藏相关
     * @param args
     */
    public void AYLikePopCommandSuccess(JSONObject args){
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
        ErrorInfoBean bean = JSON.parseObject(args.toString(), ErrorInfoBean.class);
        if (bean != null && bean.error != null) {
            ToastUtils.showShortToast(bean.error.message+"("+bean.error.code+")");
        }
    }




}
