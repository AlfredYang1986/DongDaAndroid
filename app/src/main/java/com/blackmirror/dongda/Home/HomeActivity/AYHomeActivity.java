package com.blackmirror.dongda.Home.HomeActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.blackmirror.dongda.Home.ServicePage.AYServicePageActivity;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.AYApplication;
import com.blackmirror.dongda.Tools.AppConstant;
import com.blackmirror.dongda.Tools.BasePrefUtils;
import com.blackmirror.dongda.Tools.CalUtils;
import com.blackmirror.dongda.Tools.LogUtils;
import com.blackmirror.dongda.Tools.OSSUtils;
import com.blackmirror.dongda.Tools.OtherUtils;
import com.blackmirror.dongda.Tools.ToastUtils;
import com.blackmirror.dongda.activity.ArtListActivity;
import com.blackmirror.dongda.activity.CareListActivity;
import com.blackmirror.dongda.activity.FeaturedDetailActivity;
import com.blackmirror.dongda.activity.MyLikeActivity;
import com.blackmirror.dongda.activity.NearServiceActivity;
import com.blackmirror.dongda.activity.ServiceDetailInfoActivity;
import com.blackmirror.dongda.adapter.FeaturedThemeAdapter;
import com.blackmirror.dongda.adapter.HomeArtAdapter;
import com.blackmirror.dongda.adapter.HomeCareAdapter;
import com.blackmirror.dongda.adapter.HomeScienceAdapter;
import com.blackmirror.dongda.adapter.HomeSportAdapter;
import com.blackmirror.dongda.adapter.itemdecoration.SpacesItemDecoration;
import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.facade.DongdaCommonFacade.SQLiteProxy.DAO.AYDaoUserProfile;
import com.blackmirror.dongda.factory.AYFactoryManager;
import com.blackmirror.dongda.model.serverbean.ErrorInfoServerBean;
import com.blackmirror.dongda.model.serverbean.HomeInfoServerBean;
import com.blackmirror.dongda.model.serverbean.ImgTokenServerBean;
import com.blackmirror.dongda.model.serverbean.LikePopServerBean;
import com.blackmirror.dongda.model.serverbean.LikePushServerBean;
import com.blackmirror.dongda.model.uibean.ErrorInfoUiBean;
import com.blackmirror.dongda.model.uibean.ImgTokenUiBean;
import com.blackmirror.dongda.model.uibean.LikePopUiBean;
import com.blackmirror.dongda.model.uibean.LikePushUiBean;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by alfredyang on 29/6/17.
 */

public class AYHomeActivity extends AYActivity implements View.OnClickListener{

    final private String TAG = "AYHomeActivity";
    private AYHomeListServAdapter serviceListAdapter;
    private JSONArray serviceData;

    private long skipedCount;
    private long timeSpan;
    private CoordinatorLayout ctl_root;
    private RecyclerView rv_featured_theme;
    private RecyclerView rv_home_care;
    private SimpleDraweeView sv_head_pic;
    private RecyclerView rv_home_art;
    private RecyclerView rv_home_sport;
    private RecyclerView rv_home_science;
    private ImageView iv_home_location;
    private SwipeRefreshLayout sl_home_refresh;
    private TextView tv_home_care_more;
    private TextView tv_home_art_more;
    private TextView tv_home_sport_more;
    private TextView tv_home_science_more;
    private ImageView iv_home_like;
    private boolean isFirstLoad;
    private HomeCareAdapter careAdapter;
    private HomeArtAdapter artAdapter;
    private HomeSportAdapter sportAdapter;
    private HomeScienceAdapter scienceAdapter;
    private HomeInfoServerBean bean;
    private int clickLikePos;
    private int clickAdapter=0;//1 art 2 sport 3 science
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        skipedCount = 0;
        timeSpan = new Date().getTime();

        AYApplication.finishAllActivity();

        serviceListAdapter = new AYHomeListServAdapter(this, serviceData);
        ((AYHomeListServFragment) this.fragments.get("frag_homelist_serv")).setListAdapter
                (serviceListAdapter);
        initView();
        initData();
        initListener();
    }


    private void initView() {
        ctl_root = findViewById(R.id.ctl_root);
        sv_head_pic = findViewById(R.id.sv_head_pic);
        rv_featured_theme = findViewById(R.id.rv_featured_theme);
        rv_home_care = findViewById(R.id.rv_home_care);
        rv_home_art = findViewById(R.id.rv_home_art);
        rv_home_sport = findViewById(R.id.rv_home_sport);
        rv_home_science = findViewById(R.id.rv_home_science);
        iv_home_location = findViewById(R.id.iv_home_location);
        sl_home_refresh = findViewById(R.id.sl_home_refresh);
        tv_home_care_more = findViewById(R.id.tv_home_care_more);
        tv_home_art_more = findViewById(R.id.tv_home_art_more);
        tv_home_sport_more = findViewById(R.id.tv_home_sport_more);
        tv_home_science_more = findViewById(R.id.tv_home_science_more);
        iv_home_like = findViewById(R.id.iv_home_like);
    }

    private void initData() {
        isFirstLoad=true;
        showProcessDialog();
        sl_home_refresh.setEnabled(false);
        AYFacade facade = facades.get("QueryServiceFacade");
        try {
            JSONObject object = new JSONObject();
            object.put("token",BasePrefUtils.getAuthToken());
            facade.execute("getImgToken",object);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        sv_head_pic.setImageURI(OtherUtils.resourceIdToUri(AYHomeActivity.this, R.mipmap.dongda_logo));
        //精选主题
        initSubject();

    }

    private void initHomeData() {
//        showProcessDialog();
        String url = OSSUtils.getSignedUrl(CalUtils.md5(BasePrefUtils.getUserId()));
        LogUtils.d("pic url "+url);
        sv_head_pic.setImageURI(url);
        AYFacade facade = facades.get("QueryServiceFacade");

        String json = "{ \"token\": \"" + BasePrefUtils.getAuthToken() + "\", \"condition\": { \"user_id\": \"" + BasePrefUtils.getUserId() + "\", \"service_type_list\": [{ \"service_type\": \"看顾\", \"count\": 6 }, { \"service_type\": \"艺术\", \"count\": 4 }, { \"service_type\": \"运动\", \"count\": 4 }, { \"service_type\": \"科学\", \"count\": 4 }]}}";
        try {
            JSONObject root = new JSONObject(json);
            facade.execute("SearchService", root);

        } catch (JSONException e) {

        }
    }


    private void initListener() {
        tv_home_care_more.setOnClickListener(this);
        tv_home_art_more.setOnClickListener(this);
        tv_home_sport_more.setOnClickListener(this);
        tv_home_science_more.setOnClickListener(this);
        iv_home_location.setOnClickListener(this);
        iv_home_like.setOnClickListener(this);

        sl_home_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initHomeData();
            }
        });
    }

    private void initSubject() {
        List<Integer> featuredList = new ArrayList<>();
        featuredList.add(R.drawable.home_cover_00);
        featuredList.add(R.drawable.home_cover_01);
        featuredList.add(R.drawable.home_cover_02);
        featuredList.add(R.drawable.home_cover_03);
        featuredList.add(R.drawable.home_cover_04);
        LinearLayoutManager manager = new LinearLayoutManager(AYHomeActivity.this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        FeaturedThemeAdapter adapter = new FeaturedThemeAdapter(AYHomeActivity.this,
                featuredList);
        rv_featured_theme.setNestedScrollingEnabled(false);
        rv_featured_theme.setLayoutManager(manager);
        rv_featured_theme.setAdapter(adapter);
        rv_featured_theme.addItemDecoration(new SpacesItemDecoration(28));



        adapter.setOnItemClickListener(new FeaturedThemeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(AYHomeActivity.this, FeaturedDetailActivity.class);
                intent.putExtra("pos",position);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    private void initCare(HomeInfoServerBean.ResultBean.HomepageServicesBean bean) {
        if (careAdapter==null) {
            LinearLayoutManager manager = new LinearLayoutManager(AYHomeActivity.this);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            careAdapter = new HomeCareAdapter(AYHomeActivity.this, bean);
            rv_home_care.setNestedScrollingEnabled(false);
            rv_home_care.setLayoutManager(manager);
            rv_home_care.setAdapter(careAdapter);
            rv_home_care.addItemDecoration(new SpacesItemDecoration(8));

            careAdapter.setOnCareClickListener(new HomeCareAdapter.OnCareClickListener() {
                @Override
                public void onItemCareClick(View view, int position, String service_id) {

//                    startActivity(new Intent(AYHomeActivity.this, CareListActivity.class));
                    Intent intent = new Intent(AYHomeActivity.this, ServiceDetailInfoActivity.class);
                    intent.putExtra("service_id",service_id);
                    startActivity(intent);
                }
            });
        }else {
            careAdapter.setRefreshData(bean.services);
        }
    }

    private void initArt(final HomeInfoServerBean.ResultBean.HomepageServicesBean bean) {

        if (artAdapter==null) {
            LinearLayoutManager manager = new LinearLayoutManager(AYHomeActivity.this);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            artAdapter = new HomeArtAdapter(AYHomeActivity.this, bean);
            rv_home_art.setNestedScrollingEnabled(false);
            rv_home_art.setLayoutManager(manager);
            rv_home_art.setAdapter(artAdapter);
            rv_home_art.addItemDecoration(new SpacesItemDecoration(8));
            artAdapter.setOnItemClickListener(new HomeArtAdapter.OnItemClickListener() {
                @Override
                public void onArtLikeClick(View view, int position, HomeInfoServerBean.ResultBean.HomepageServicesBean.ServicesBean bean) {
//                    ToastUtils.showShortToast("点击了收藏");
                    clickLikePos=position;
                    clickAdapter=1;
                    sendLikeData(bean);
                }

                @Override
                public void onItemClick(View view, int position, String service_id) {
                    Intent intent = new Intent(AYHomeActivity.this, ServiceDetailInfoActivity.class);
                    intent.putExtra("service_id",service_id);
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
        }else {
            artAdapter.setRefreshData(bean.services);
        }
    }

    private void initSport(final HomeInfoServerBean.ResultBean.HomepageServicesBean bean) {

        if (sportAdapter==null) {
            LinearLayoutManager manager = new LinearLayoutManager(AYHomeActivity.this);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            sportAdapter = new HomeSportAdapter(AYHomeActivity.this, bean);
            rv_home_sport.setNestedScrollingEnabled(false);
            rv_home_sport.setLayoutManager(manager);
            rv_home_sport.setAdapter(sportAdapter);
            rv_home_sport.addItemDecoration(new SpacesItemDecoration(8));
            sportAdapter.setOnItemClickListener(new HomeSportAdapter.OnItemClickListener() {
                @Override
                public void onSportLikeClick(View view, int position, HomeInfoServerBean.ResultBean.HomepageServicesBean.ServicesBean servicesBean) {
                    sendLikeData(servicesBean);
                    clickLikePos=position;
                    clickAdapter=2;
                }

                @Override
                public void onSportItemClick(View view, int position, String service_id) {
                    Intent intent = new Intent(AYHomeActivity.this, ServiceDetailInfoActivity.class);
                    intent.putExtra("service_id",service_id);
                    startActivity(intent);
                }
            });
        }else {
            sportAdapter.setRefreshData(bean.services);
        }
    }

    private void initScience(HomeInfoServerBean.ResultBean.HomepageServicesBean bean) {
        if (scienceAdapter==null) {
            LinearLayoutManager manager = new LinearLayoutManager(AYHomeActivity.this);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            scienceAdapter = new HomeScienceAdapter(AYHomeActivity.this, bean);
            rv_home_science.setNestedScrollingEnabled(false);
            rv_home_science.setLayoutManager(manager);
            rv_home_science.setAdapter(scienceAdapter);
            rv_home_science.addItemDecoration(new SpacesItemDecoration(8));
            scienceAdapter.setOnItemClickListener(new HomeScienceAdapter.OnItemClickListener() {
                @Override
                public void onScienceLikeClick(View view, int position, HomeInfoServerBean.ResultBean
                        .HomepageServicesBean.ServicesBean servicesBean) {
                    clickLikePos=position;
                    clickAdapter=3;
                    sendLikeData(servicesBean);
                }

                @Override
                public void onScienceItemClick(View view, int position, String service_id) {
                    Intent intent = new Intent(AYHomeActivity.this, ServiceDetailInfoActivity.class);
                    intent.putExtra("service_id",service_id);
                    startActivity(intent);
                }
            });
        }else {
            sportAdapter.setRefreshData(bean.services);
        }
    }

    private void sendLikeData(HomeInfoServerBean.ResultBean.HomepageServicesBean.ServicesBean bean) {
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

    @Override
    protected void onStart() {
        super.onStart();

        /*Method method = context.getClass().getMethod(name, JSONObject.class);
        method.invoke(context, args);*/

        /*((AYNavBarFragment)this.fragments.get("frag_navbar")).setTitleTextInvisible();
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setLeftBtnTextWithString("北京市");
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setRightBtnImageWithImageId(R
        .drawable.home_icon_mapfilter);
        ((AYTabBarFragment)this.fragments.get("frag_tabbar")).setTabFocusOptionWithIndex(0);*/
    }

    @Override
    protected void bindingFragments() {

       /* FragmentTransaction task = mFragmentManage.beginTransaction();
        task.add(R.id.activity_home, (AYFragment)this.fragments.get("frag_navbar"));
        task.add(R.id.activity_home, (AYFragment)this.fragments.get("frag_tabbar"));
        task.add(R.id.activity_home, (AYFragment)this.fragments.get("frag_homeseg"));
        task.add(R.id.activity_home, (AYListFragment)this.fragments.get("frag_homelist_serv"));
        task.commit();*/
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(AYHomeActivity.this, ArtListActivity.class);
        switch(v.getId()){
            case R.id.tv_home_care_more:

                Intent careIntent = new Intent(AYHomeActivity.this, CareListActivity.class);
                if (bean!=null && bean.result!=null){
                    int m = bean.result.homepage_services.get(0).totalCount;
                    careIntent.putExtra("totalCount",m);
                }
                startActivityForResult(careIntent,AppConstant.CARE_MORE_REQUEST_CODE);
                break;
            case R.id.tv_home_art_more:
                if (bean!=null && bean.result!=null){
                    int m = bean.result.homepage_services.get(1).totalCount;
                    intent.putExtra("totalCount",m);
                }
                intent.putExtra("serviceType","艺术");
                intent.putExtra("title","艺术");
                startActivityForResult(intent,AppConstant.ART_MORE_REQUEST_CODE);
                break;
            case R.id.tv_home_sport_more:
                if (bean!=null && bean.result!=null){
                    int m = bean.result.homepage_services.get(2).totalCount;
                    intent.putExtra("totalCount",m);
                }
                intent.putExtra("serviceType","运动");
                intent.putExtra("title","运动");
                startActivityForResult(intent,AppConstant.SPORT_MORE_REQUEST_CODE);
                break;
            case R.id.tv_home_science_more:
                if (bean!=null && bean.result!=null){
                    int m = bean.result.homepage_services.get(3).totalCount;
                    intent.putExtra("totalCount",m);
                }
                intent.putExtra("serviceType","科学");
                intent.putExtra("title","科学");
                startActivityForResult(intent,AppConstant.SCIENCE_REQUEST_CODE);
                break;
            case R.id.iv_home_location:
                startActivity(new Intent(AYHomeActivity.this,NearServiceActivity.class));
                break;
            case R.id.iv_home_like:
                startActivityForResult(new Intent(AYHomeActivity.this,MyLikeActivity.class), AppConstant.MY_LIKE_REQUEST_CODE);
                break;
        }
    }

    private void searchServiceRemote() {

        AYFacade f_login = (AYFacade) AYFactoryManager.getInstance(this).queryInstance("facade",
                "DongdaCommanFacade");
        AYCommand cmd_profile = f_login.cmds.get("QueryCurrentLoginUser");
        AYDaoUserProfile user = cmd_profile.excute();

        AYFacade facade = facades.get("QueryServiceFacade");
        AYCommand cmd = facade.cmds.get("SearchService");
        Map<String, Object> search_args = new HashMap<>();
        search_args.put("user_id", user.getUser_id());
        search_args.put("auth_token", user.getAuth_token());
        search_args.put("skip", skipedCount);
        search_args.put("date", timeSpan);
        JSONObject args = new JSONObject(search_args);
        cmd.excute(args);
    }

    /**
     * 获取图片token 用于生成url签名
     * @param args
     */
    public void AYGetImgTokenCommandSuccess(JSONObject args){
        sl_home_refresh.setEnabled(true);
        ImgTokenServerBean serverBean = JSON.parseObject(args.toString(), ImgTokenServerBean.class);
        ImgTokenUiBean bean = new ImgTokenUiBean(serverBean);
        BasePrefUtils.setAccesskeyId(bean.accessKeyId);
        BasePrefUtils.setSecurityToken(bean.SecurityToken);
        BasePrefUtils.setAccesskeySecret(bean.accessKeySecret);
        BasePrefUtils.setExpiration(bean.Expiration);
        refreshToken();
//        GetOSSClient.INSTANCE().initOSS(bean.accessKeyId,bean.accessKeySecret,bean.SecurityToken);
        if (isFirstLoad) {
            isFirstLoad=false;
            initHomeData();
        }
    }

    public void AYGetImgTokenCommandFailed(JSONObject args) {
        sl_home_refresh.setEnabled(false);
        if (isFirstLoad) {
            isFirstLoad=false;
            initHomeData();
        }
        ErrorInfoServerBean bean = JSON.parseObject(args.toString(), ErrorInfoServerBean.class);
        if (bean != null && bean.error != null) {
            ToastUtils.showShortToast(bean.error.message);
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
            if (clickAdapter==1) {
                artAdapter.notifyItemChanged(clickLikePos, true);
            }else if (clickAdapter==2){
                sportAdapter.notifyItemChanged(clickLikePos,true);
            }else if (clickAdapter==3){
                scienceAdapter.notifyItemChanged(clickLikePos,true);
            }
        }else {
            ToastUtils.showShortToast(pushUiBean.message+"("+pushUiBean.code+")");
        }
    }

    public void AYLikePushCommandFailed(JSONObject args) {
        closeProcessDialog();
        ErrorInfoServerBean serverBean = JSON.parseObject(args.toString(), ErrorInfoServerBean.class);
        ErrorInfoUiBean uiBean = new ErrorInfoUiBean(serverBean);
        if (uiBean.code==10010){
            Snackbar.make(ctl_root, uiBean.message, Snackbar.LENGTH_INDEFINITE)
                    .setAction("关闭", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();
        }else {
            ToastUtils.showShortToast(uiBean.message+"("+uiBean.code+")");
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
            if (clickAdapter==1) {
                artAdapter.notifyItemChanged(clickLikePos, false);
            }else if (clickAdapter==2){
                sportAdapter.notifyItemChanged(clickLikePos,false);
            }else if (clickAdapter==3){
                scienceAdapter.notifyItemChanged(clickLikePos,false);
            }
        }else {
            ToastUtils.showShortToast(popUiBean.message+"("+popUiBean.code+")");
        }
    }

    public void AYLikePopCommandFailed(JSONObject args) {
       closeProcessDialog();
        ErrorInfoServerBean serverBean = JSON.parseObject(args.toString(), ErrorInfoServerBean.class);
        ErrorInfoUiBean uiBean = new ErrorInfoUiBean(serverBean);
        if (uiBean.code==10010){
            Snackbar.make(ctl_root, uiBean.message, Snackbar.LENGTH_INDEFINITE)
                    .setAction("关闭", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();
        }else {
            ToastUtils.showShortToast(uiBean.message+"("+uiBean.code+")");
        }

    }

    private void refreshToken() {
        disposable = Observable.interval(OtherUtils.getRefreshTime(BasePrefUtils
                .getExpiration()), OtherUtils.getRefreshTime(BasePrefUtils.getExpiration()),
                TimeUnit.SECONDS, Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        AYFacade facade = facades.get("QueryServiceFacade");
                        try {
                            JSONObject object = new JSONObject();
                            object.put("token", BasePrefUtils.getAuthToken());
                            facade.execute("getImgToken", object);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    public void didNavLeftBtnClickNotify(JSONObject args) {
        Log.d(TAG, "didNavLeftBtnClickNotify: in Activity");

    }

    public void didNavRightBtnClickNotify(JSONObject args) {

        Log.d(TAG, "didNavRightBtnClickNotify: in Activity");
    }

    public void didSegFristItemClickNotify(JSONObject args) {
        Log.d(TAG, "didSegFristItemClickNotify: in Activity");

    }

    public void didSegSecondItemClickNotify(JSONObject args) {
        Log.d(TAG, "didSegSecondItemClickNotify: in Activity");

    }

    public void sendRefreshDataNotify(JSONObject args) {
        Log.d(TAG, "sendRefreshDataNotify: in Activity");
        skipedCount = 0;
        timeSpan = new Date().getTime();
        //        searchServiceRemote();
    }

    public void sendLoadMoreDataNotify(JSONObject args) {
        Log.d(TAG, "sendLoadMoreDataNotify: in Activity");
        //        searchServiceRemote();
    }

    public void didSelectedPositionNotify(JSONObject args) {
        Log.d(TAG, "didSelectedPositionNotify: in Activity");

        int position = 0;
        try {
            position = args.getInt("position");
            Log.d(TAG, "didSelectedPositionNotify: selected-->" + position);
            if (position == 0) {

            } else {
                JSONObject js = serviceData.getJSONObject(position - 1);
                //                Log.d(TAG, "didSelectedPositionNotify: "+js);
                Intent intent = new Intent(this, AYServicePageActivity.class);
                intent.putExtra("service_info", js.toString());
                //        startActivityForResult(intent, requestCode);
                startActivity(intent);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public Boolean AYSearchServiceCommandSuccess(JSONObject args) {
        /*((AYHomeListServFragment) this.fragments.get("frag_homelist_serv"))
                .refreshOrLoadMoreComplete();

        JSONArray data = null;
        try {
            data = args.getJSONArray("result");
            serviceData = data;
            skipedCount += data.length();

            serviceListAdapter.setQueryData(data);
            serviceListAdapter.refreshList();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        closeProcessDialog();
        sl_home_refresh.setRefreshing(false);
        bean = JSON.parseObject(args.toString(), HomeInfoServerBean.class);
        if (bean != null && "ok".equals(bean.status)) {
            initCare(bean.result.homepage_services.get(0));
            initArt(bean.result.homepage_services.get(1));
            initSport(bean.result.homepage_services.get(2));
            initScience(bean.result.homepage_services.get(3));
        } else if (bean != null && bean.error != null) {
            ToastUtils.showShortToast(bean.error.message);
        }

        return true;
    }

    public Boolean AYSearchServiceCommandFailed(JSONObject args) {
        closeProcessDialog();
        LogUtils.d("AYSearchServiceCommandFailed "+args.toString());
        ErrorInfoServerBean serverBean = JSON.parseObject(args.toString(), ErrorInfoServerBean.class);
        ErrorInfoUiBean uiBean = new ErrorInfoUiBean(serverBean);
        sl_home_refresh.setRefreshing(false);
        if (uiBean.code==10010){
            Snackbar.make(ctl_root, uiBean.message, Snackbar.LENGTH_INDEFINITE)
                    .setAction("关闭", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();
        }else {
            ((AYHomeListServFragment) this.fragments.get("frag_homelist_serv"))
                    .refreshOrLoadMoreComplete();
            Toast.makeText(this, "请改善网络环境并重试", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable!=null && !disposable.isDisposed()){
            disposable.dispose();
            disposable=null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        needsRefreshHomeData(requestCode,resultCode,data);

        if (requestCode == 0 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String text = null;
            if (bundle != null)
                text = bundle.getString("second");
            Log.d("text", text);
        }
    }

    private void needsRefreshHomeData(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case AppConstant.CARE_MORE_REQUEST_CODE:
            case AppConstant.ART_MORE_REQUEST_CODE:
            case AppConstant.SPORT_MORE_REQUEST_CODE:
            case AppConstant.SCIENCE_REQUEST_CODE:
            case AppConstant.MY_LIKE_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK){
                    sl_home_refresh.setRefreshing(false);
                    initHomeData();
                }
                break;
        }
    }


}
