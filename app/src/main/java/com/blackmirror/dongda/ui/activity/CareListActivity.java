package com.blackmirror.dongda.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.utils.AYPrefUtils;
import com.blackmirror.dongda.utils.AppConstant;
import com.blackmirror.dongda.utils.LogUtils;
import com.blackmirror.dongda.utils.SnackbarUtils;
import com.blackmirror.dongda.utils.ToastUtils;
import com.blackmirror.dongda.adapter.CareListAdapter;
import com.blackmirror.dongda.adapter.itemdecoration.TopItemDecoration;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.model.serverbean.CareMoreServerBean;
import com.blackmirror.dongda.model.serverbean.ErrorInfoServerBean;
import com.blackmirror.dongda.model.serverbean.LikePopServerBean;
import com.blackmirror.dongda.model.serverbean.LikePushServerBean;
import com.blackmirror.dongda.model.uibean.CareMoreUiBean;
import com.blackmirror.dongda.model.uibean.ErrorInfoUiBean;
import com.blackmirror.dongda.model.uibean.LikePopUiBean;
import com.blackmirror.dongda.model.uibean.LikePushUiBean;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

public class CareListActivity extends AYActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_list);
        totalCount = getIntent().getIntExtra("totalCount", 10);
        initView();
        initData(skip, take);
        initListener();
    }

    @Override
    protected void bindingFragments() {

    }

    private void initView() {
        ctl_root = findViewById(R.id.ctl_root);
        iv_home_head_back = findViewById(R.id.iv_home_head_back);
        tv_home_head_title = findViewById(R.id.tv_home_head_title);
        rv_care_list = findViewById(R.id.rv_care_list);
        sl_care_list = findViewById(R.id.sl_care_list);
        sl_care_list.setEnableLoadMoreWhenContentNotFull(false);//内容不满屏幕的时候也开启加载更多
        sl_care_list.setEnableAutoLoadMore(false);//内容不满屏幕的时候也开启加载更多
        sl_care_list.setRefreshHeader(new MaterialHeader(CareListActivity.this));
    }

    private void initData(int skipCount, int takeCount) {

        AYFacade facade = facades.get("QueryServiceFacade");
        try {
            String json = "{\"skip\" : " + skipCount + ",\"take\" : " + takeCount + ",\"token\": \"" + AYPrefUtils.getAuthToken() + "\",\"condition\": {\"user_id\":\"" + AYPrefUtils.getUserId() + "\",\"service_type\": \"看顾\"}}";
            JSONObject object = new JSONObject(json);
            facade.execute("AYSubjectMoreCommand", object);
        } catch (JSONException e) {
            e.printStackTrace();
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

    /**
     * 获取更多信息列表
     *
     * @param args
     */
    public void AYSubjectMoreCommandSuccess(JSONObject args) {
        CareMoreServerBean serverBean = JSON.parseObject(args.toString(), CareMoreServerBean.class);
        CareMoreUiBean bean = new CareMoreUiBean(serverBean);
        if (sl_care_list.getState().opening) {
            sl_care_list.finishLoadMore();
            sl_care_list.finishRefresh();
        }
        setDataToRecyclerView(bean);
    }

    public void AYSubjectMoreCommandFailed(JSONObject args) {
        if (sl_care_list.getState().opening) {
            sl_care_list.finishLoadMore(false);
            sl_care_list.finishRefresh(false);
        }
        ErrorInfoServerBean serverBean = JSON.parseObject(args.toString(), ErrorInfoServerBean.class);
        ErrorInfoUiBean uiBean = new ErrorInfoUiBean(serverBean);
        if (uiBean.code== AppConstant.NET_WORK_UNAVAILABLE){
            SnackbarUtils.show(ctl_root,uiBean.message);
        }else {
            ToastUtils.showShortToast(uiBean.message+"("+uiBean.code+")");
        }
    }

    private void setDataToRecyclerView(CareMoreUiBean bean) {

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
                public void onItemCareLikeClick(View view, int position, CareMoreServerBean
                        .ResultBean.ServicesBean servicesBean) {
                    clickLikePos = position;
                    sendLikeData(servicesBean);
                }
            });
        } else {
            ToastUtils.showShortToast(bean.message + "(" + bean.code + ")");
        }
    }


    private void sendLikeData(CareMoreServerBean.ResultBean.ServicesBean bean) {
        String t = AYPrefUtils.getAuthToken();
        String u = AYPrefUtils.getUserId();
        showProcessDialog();
        if (bean.is_collected) {//已收藏 点击取消
            String json = "{\"token\":\"" + t + "\",\"condition\": {\"user_id\":\"" + u + "\",\"service_id\":\"" + bean.service_id + "\"},\"collections\":{\"user_id\": \"" + u + "\",\"service_id\":\"" + bean.service_id + "\"}}";
            try {
                JSONObject object = new JSONObject(json);
                facades.get("QueryServiceFacade").execute("AYLikePopCommand", object);
            } catch (JSONException e) {
                e.printStackTrace();
                closeProcessDialog();
            }
        } else {
            String json = "{\"token\":\"" + t + "\",\"condition\": {\"user_id\":\"" + u + "\",\"service_id\":\"" + bean.service_id + "\"},\"collections\":{\"user_id\": \"" + u + "\",\"service_id\":\"" + bean.service_id + "\"}}";
            try {
                JSONObject object = new JSONObject(json);
                facades.get("QueryServiceFacade").execute("AYLikePushCommand", object);
            } catch (JSONException e) {
                e.printStackTrace();
                closeProcessDialog();
            }
        }
    }

    /**
     * 收藏相关
     *
     * @param args
     */
    public void AYLikePushCommandSuccess(JSONObject args) {
        isNeedRefresh = true;
        closeProcessDialog();
        LikePushServerBean serverBean = JSON.parseObject(args.toString(), LikePushServerBean.class);
        LikePushUiBean pushUiBean = new LikePushUiBean(serverBean);
        if (pushUiBean.isSuccess) {
            adapter.notifyItemChanged(clickLikePos, true);
        } else {
            ToastUtils.showShortToast(pushUiBean.message + "(" + pushUiBean.code + ")");
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
     *
     * @param args
     */
    public void AYLikePopCommandSuccess(JSONObject args) {
        isNeedRefresh = true;
        closeProcessDialog();
        LikePopServerBean serverBean = JSON.parseObject(args.toString(), LikePopServerBean.class);
        LikePopUiBean popUiBean = new LikePopUiBean(serverBean);
        if (popUiBean.isSuccess) {
            adapter.notifyItemChanged(clickLikePos, false);
        } else {
            ToastUtils.showShortToast(popUiBean.message + "(" + popUiBean.code + ")");
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
    public void onBackPressed() {
        setResult(isNeedRefresh ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void clearMemBitMap() {
        if (adapter!=null){
            for (String url : adapter.urlSet) {
                try {
                    Uri uri = Uri.parse(url);
                    ImageRequest request= ImageRequestBuilder.newBuilderWithSource(uri).build();
                    boolean cache = Fresco.getImagePipeline().isInBitmapMemoryCache(uri);

                    LogUtils.d(url+" in memory "+cache);
                    Fresco.getImagePipeline().evictFromMemoryCache(uri);
                    Fresco.getImagePipeline().evictFromMemoryCache(uri);

                } catch (Exception e) {
                    LogUtils.e(ArtListActivity.class,"CacheException: ",e);
                }
            }
            adapter.urlSet.clear();
            adapter.urlSet=null;
            adapter=null;
        }
    }
}
