package com.blackmirror.dongda.activity;

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
import com.blackmirror.dongda.Tools.AYPrefUtils;
import com.blackmirror.dongda.Tools.AppConstant;
import com.blackmirror.dongda.Tools.LogUtils;
import com.blackmirror.dongda.Tools.OtherUtils;
import com.blackmirror.dongda.Tools.SnackbarUtils;
import com.blackmirror.dongda.Tools.ToastUtils;
import com.blackmirror.dongda.adapter.FeaturedDetailAdapter;
import com.blackmirror.dongda.controllers.AYActivity;
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
        OtherUtils.setStatusBarColor(FeaturedDetailActivity.this);
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
                tv_featured_tb_title.setText("蒙特俊利");
                title="准备好爱与耐心，自律才能自由";
                content = "「妈妈，帮帮我，让我可以自己做」\n" +
                        "「孩子能做，就别插手」\n" +
                        "追随孩子，Aid to life，蒙特梭利教育（Montessori），是意大利心理学家玛丽亚 · " +
                        "蒙特梭利发展的教育方法。强调独立，有限度的自由和对孩子天然的心理、生理及社会性发展的尊重。";
                service_type = "看顾";
                getServerData(0, service_type);
                break;
            case 1://浸入式英语
                bg_resId = R.drawable.coverlist_bg_01;
                tv_featured_tb_title.setText("浸入式英语");
                title="语言，是一种思考方式";
                content = "Water 就是 Water，不是水。Apple就是圆圆的果子，咬一口。  习得语言，而非学习语言，是对孩子至关重要的概念。ESL（English as a second language）,给孩子们创造时时刻刻应用的契机，无论是学科中还是生活里。";
                service_type = "看顾";
                skipCount = 10;
                getServerData(skipCount, service_type);
                break;
            case 2://极限运动
                bg_resId = R.drawable.coverlist_bg_03;
                tv_featured_tb_title.setText("极限运动");
                title="跳出温室，体验非凡张力";
                content="惊喜，孩子们无时无刻不在创造惊喜给我们。在这里，突破的不仅仅是身体，还有心理。更重要的是在注视和保护下，用科学的训练方式，探索能力的界限，进行体能的尝试。";
                service_type = "运动";
                getServerData(0, service_type);
                break;
            case 3://修身养性
                bg_resId = R.drawable.coverlist_bg_02;
                tv_featured_tb_title.setText("修身养性");
                title="触摸与众不同的感受";
                content = "善琴者通达从容，善棋者筹谋睿智，善书者至情至性，善画者至善至美，善诗者韵至心声，善酒者情逢知己，善茶者陶冶情操，善花者品性怡然。琴棋书画，诗酒花茶。心灵中充满的情趣，将伴随孩子始终，让他们成为生活的欣赏者和创造者。";
                service_type = "艺术";
                getServerData(0, service_type);
                break;
            case 4://STEAM
                bg_resId = R.drawable.coverlist_bg_04;
                tv_featured_tb_title.setText("STEAM");
                title="身临其境，体验科学的力量";
                content = "科学Science，技术Technology，工程Engineering，艺术Arts，数学Math。以Project-based learning的学习方式，帮助孩子们将知识与兴趣连接。了解世界上正在发生的事儿，自然而然地共同面对和动手解决实际的问题。";
                service_type = "科学";
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
        if (uiBean.code==10010){
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
        if (uiBean.code==10010){
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
        if (uiBean.code==10010){
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
   /* @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected void bindingFragments() {

    }*/
}
