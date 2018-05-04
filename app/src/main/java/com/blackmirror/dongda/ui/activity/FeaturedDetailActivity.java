package com.blackmirror.dongda.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.utils.AYPrefUtils;
import com.blackmirror.dongda.utils.AppConstant;
import com.blackmirror.dongda.utils.DeviceUtils;
import com.blackmirror.dongda.utils.LogUtils;
import com.blackmirror.dongda.utils.SnackbarUtils;
import com.blackmirror.dongda.utils.ToastUtils;
import com.blackmirror.dongda.adapter.FeaturedDetailAdapter;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.model.serverbean.CareMoreServerBean;
import com.blackmirror.dongda.model.serverbean.ErrorInfoServerBean;
import com.blackmirror.dongda.model.serverbean.LikePopServerBean;
import com.blackmirror.dongda.model.serverbean.LikePushServerBean;
import com.blackmirror.dongda.model.uibean.CareMoreUiBean;
import com.blackmirror.dongda.model.uibean.ErrorInfoUiBean;
import com.blackmirror.dongda.model.uibean.LikePopUiBean;
import com.blackmirror.dongda.model.uibean.LikePushUiBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 精选主题详情页
 */
public class FeaturedDetailActivity extends AYActivity {

    final static String TAG = "FeaturedDetailActivity";
    private ImageView iv_featured_detail_back;
    private TextView tv_featured_tb_title;
    private RecyclerView rv_featured_detail;
    private Toolbar toolbar;
    private CoordinatorLayout ctl_root;
    private int pos;
    private String title;
    private String content;
    private String service_type;
    private int bg_resId;
    private int clickLikePos;
    private int skipCount=0;
    private FeaturedDetailAdapter adapter;
    private boolean isNeedRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured_detail);
        DeviceUtils.setStatusBarColor(FeaturedDetailActivity.this);
        pos = getIntent().getIntExtra("pos", 0);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        iv_featured_detail_back = findViewById(R.id.iv_featured_detail_back);
        tv_featured_tb_title = findViewById(R.id.tv_featured_tb_title);
        rv_featured_detail = findViewById(R.id.rv_featured_detail);
        ctl_root = findViewById(R.id.ctl_root);
    }

    private void initData() {

        initTbTitle();

    }

    private void initTbTitle() {
        switch(pos){
            case 0://蒙特俊利
                bg_resId = R.drawable.coverlist_bg_theme;
                tv_featured_tb_title.setText(R.string.str_mtjl);
                title=getString(R.string.str_mtjl_title);
                content = getString(R.string.str_mtjl_content);
                service_type = getString(R.string.str_care);
                getServerData(0, service_type);
                break;
            case 1://浸入式英语
                bg_resId = R.drawable.coverlist_bg_01;
                tv_featured_tb_title.setText(R.string.str_jrsyy);
                title=getString(R.string.str_jrsyy_title);
                content = getString(R.string.str_jrsyy_content);
                service_type = getString(R.string.str_care);
                skipCount = 10;
                getServerData(skipCount, service_type);
                break;
            case 2://极限运动
                bg_resId = R.drawable.coverlist_bg_03;
                tv_featured_tb_title.setText(getString(R.string.str_jxyd));
                title=getString(R.string.str_jxyd_title);
                content=getString(R.string.str_jxyd_content);
                service_type = getString(R.string.str_sport);
                getServerData(0, service_type);
                break;
            case 3://修身养性
                bg_resId = R.drawable.coverlist_bg_02;
                tv_featured_tb_title.setText(R.string.str_xsyx);
                title=getString(R.string.str_xsyx_title);
                content = getString(R.string.str_xsyx_content);
                service_type = getString(R.string.str_art);
                getServerData(0, service_type);
                break;
            case 4://STEAM
                bg_resId = R.drawable.coverlist_bg_04;
                tv_featured_tb_title.setText(R.string.str_steam);
                title=getString(R.string.str_steam_title);
                content = getString(R.string.str_steam_content);
                service_type = getString(R.string.str_science);
                getServerData(0, service_type);
                break;
        }
    }

    private void initListener() {
        iv_featured_detail_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(isNeedRefresh ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
                finish();
            }
        });
    }

    private void getServerData(int skipCount,String service_type) {
        showProcessDialog();
        try {
            AYFacade facade = facades.get("QueryServiceFacade");
            String json = "{\"skip\" : " + skipCount + ",\"take\" : 10,\"token\": \"" + AYPrefUtils.getAuthToken() + "\",\"condition\": {\"user_id\":\"" + AYPrefUtils.getUserId() + "\",\"service_type\": \""+service_type+"\"}}";
            JSONObject object = new JSONObject(json);
            facade.execute("AYSubjectMoreCommand", object);
        } catch (Exception e) {
            closeProcessDialog();
            LogUtils.e(FeaturedDetailActivity.class, e);
        }
    }

    /**
     * 获取更多信息列表
     *
     * @param args
     */
    public void AYSubjectMoreCommandSuccess(JSONObject args) {
        closeProcessDialog();
        CareMoreServerBean serverBean = JSON.parseObject(args.toString(), CareMoreServerBean.class);
        CareMoreUiBean uiBean = new CareMoreUiBean(serverBean);
        setDataToRecyclerView(uiBean);
    }

    public void AYSubjectMoreCommandFailed(JSONObject args) {
        closeProcessDialog();
        ErrorInfoServerBean serverBean = JSON.parseObject(args.toString(), ErrorInfoServerBean.class);
        ErrorInfoUiBean uiBean = new ErrorInfoUiBean(serverBean);
        if (uiBean.code==AppConstant.NET_WORK_UNAVAILABLE){
            SnackbarUtils.show(ctl_root,uiBean.message);
        }else {
            ToastUtils.showShortToast(uiBean.message+"("+uiBean.code+")");
        }
    }

    private void setDataToRecyclerView(CareMoreUiBean bean) {

        if (bean.isSuccess) {

            adapter = new FeaturedDetailAdapter(FeaturedDetailActivity.this,bean.services);
            adapter.title = title;
            adapter.content=content;
            adapter.bg_resId=bg_resId;
            rv_featured_detail.setLayoutManager(new LinearLayoutManager(FeaturedDetailActivity.this));
            rv_featured_detail.setAdapter(adapter);


            adapter.setOnDetailListClickListener(new FeaturedDetailAdapter.OnDetailListClickListener() {
                @Override
                public void onItemDetailListClick(View view, int position, String service_id) {
                    Intent intent = new Intent(FeaturedDetailActivity.this, ServiceDetailInfoActivity.class);
                    intent.putExtra("service_id", service_id);
                    startActivityForResult(intent, AppConstant.SERVICE_DETAIL_REQUEST_CODE);
                }

                @Override
                public void onItemDetailLikeClick(View view, int position, CareMoreServerBean.ResultBean.ServicesBean servicesBean) {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == AppConstant.SERVICE_DETAIL_REQUEST_CODE){
                getServerData(skipCount,service_type);
            }
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
        clearMemBitMap();
    }

    private void clearMemBitMap() {
        /*if (adapter!=null){
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
        }*/
    }


    @Override
    protected void bindingFragments() {

    }
}
