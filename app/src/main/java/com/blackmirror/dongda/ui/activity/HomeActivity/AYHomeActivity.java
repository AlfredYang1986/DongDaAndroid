package com.blackmirror.dongda.ui.activity.HomeActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.adapter.FeaturedThemeAdapter;
import com.blackmirror.dongda.adapter.HomeArtAdapter;
import com.blackmirror.dongda.adapter.HomeCareAdapter;
import com.blackmirror.dongda.adapter.HomeScienceAdapter;
import com.blackmirror.dongda.adapter.HomeSportAdapter;
import com.blackmirror.dongda.adapter.itemdecoration.SpacesItemDecoration;
import com.blackmirror.dongda.base.AYApplication;
import com.blackmirror.dongda.di.component.DaggerAYHomeComponent;
import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.domain.model.HomepageDomainBean;
import com.blackmirror.dongda.domain.model.LikePopDomainBean;
import com.blackmirror.dongda.domain.model.LikePushDomainBean;
import com.blackmirror.dongda.presenter.HomePresenter;
import com.blackmirror.dongda.ui.activity.ArtListActivity;
import com.blackmirror.dongda.ui.activity.CareListActivity;
import com.blackmirror.dongda.ui.activity.FeaturedDetailActivity;
import com.blackmirror.dongda.ui.activity.MyLikeActivity;
import com.blackmirror.dongda.ui.activity.NearServiceActivity;
import com.blackmirror.dongda.ui.activity.ServiceDetailInfoActivity;
import com.blackmirror.dongda.ui.activity.UserInfoActivity;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.blackmirror.dongda.utils.AppConstant;
import com.blackmirror.dongda.utils.DensityUtils;
import com.blackmirror.dongda.utils.LogUtils;
import com.blackmirror.dongda.utils.OSSUtils;
import com.blackmirror.dongda.utils.SnackbarUtils;
import com.blackmirror.dongda.utils.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alfredyang on 29/6/17.
 */

public class AYHomeActivity extends BaseActivity implements View.OnClickListener,HomeContract.HomeView{

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
    private HomeCareAdapter careAdapter;
    private HomeArtAdapter artAdapter;
    private HomeSportAdapter sportAdapter;
    private HomeScienceAdapter scienceAdapter;
    private int clickLikePos;
    private int clickAdapter;//1 art 2 sport 3 science
    private String img_uuid;
    private HomePresenter presenter;
    private HomepageDomainBean bean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        img_uuid=getIntent().getStringExtra("img_uuid");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initInject() {
        presenter = DaggerAYHomeComponent.builder()
                .activity(this)
                .view(this)
                .build()
                .getHomePresenter();
    }

    @Override
    protected void initView() {
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

    @Override
    protected void initData() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            iv_home_location.setElevation(DensityUtils.dp2px(6));
        }
        showProcessDialog();
        //精选主题
        initSubject();
        initHomeData();
    }

    @Override
    protected void initListener() {
        tv_home_care_more.setOnClickListener(this);
        tv_home_art_more.setOnClickListener(this);
        tv_home_sport_more.setOnClickListener(this);
        tv_home_science_more.setOnClickListener(this);
        iv_home_location.setOnClickListener(this);
        iv_home_like.setOnClickListener(this);
        sv_head_pic.setOnClickListener(this);

        sl_home_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initHomeData();
            }
        });
    }

    private void initHomeData() {
        presenter.getHomePageData();
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
        rv_featured_theme.addItemDecoration(new SpacesItemDecoration(10));


        adapter.setOnItemClickListener(new FeaturedThemeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(AYHomeActivity.this, FeaturedDetailActivity.class);
                intent.putExtra("pos", position);
                startActivityForResult(intent, AppConstant.FEATURE_DETAIL_REQUEST_CODE);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    private void initCare(HomepageDomainBean.HomepageServicesBean bean) {
        if (careAdapter == null) {
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

                    //                    startActivityForResult(new Intent(AYHomeActivity.this, CareListActivity.class));
                    Intent intent = new Intent(AYHomeActivity.this, ServiceDetailInfoActivity.class);
                    intent.putExtra("service_id", service_id);
                    startActivityForResult(intent, AppConstant.SERVICE_DETAIL_REQUEST_CODE);
                }
            });
        } else {
            careAdapter.setRefreshData(bean.services);
        }
    }

    private void initArt(final HomepageDomainBean.HomepageServicesBean bean) {

        if (artAdapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(AYHomeActivity.this);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            artAdapter = new HomeArtAdapter(AYHomeActivity.this, bean);
            rv_home_art.setNestedScrollingEnabled(false);
            rv_home_art.setLayoutManager(manager);
            rv_home_art.setAdapter(artAdapter);
            rv_home_art.addItemDecoration(new SpacesItemDecoration(8));
            artAdapter.setOnItemClickListener(new HomeArtAdapter.OnItemClickListener() {
                @Override
                public void onArtLikeClick(View view, int position, HomepageDomainBean.HomepageServicesBean.ServicesBean bean) {
                    //                    ToastUtils.showShortToast("点击了收藏");
                    clickLikePos = position;
                    clickAdapter = AppConstant.HOME_ART_ADAPTER;
                    sendLikeData(bean);
                }

                @Override
                public void onItemClick(View view, int position, String service_id) {
                    Intent intent = new Intent(AYHomeActivity.this, ServiceDetailInfoActivity.class);
                    intent.putExtra("service_id", service_id);
                    startActivityForResult(intent, AppConstant.SERVICE_DETAIL_REQUEST_CODE);
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
        } else {
            artAdapter.setRefreshData(bean.services);
        }
    }

    private void initSport(final HomepageDomainBean.HomepageServicesBean bean) {

        if (sportAdapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(AYHomeActivity.this);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            sportAdapter = new HomeSportAdapter(AYHomeActivity.this, bean);
            rv_home_sport.setNestedScrollingEnabled(false);
            rv_home_sport.setLayoutManager(manager);
            rv_home_sport.setAdapter(sportAdapter);
            rv_home_sport.addItemDecoration(new SpacesItemDecoration(8));
            sportAdapter.setOnItemClickListener(new HomeSportAdapter.OnItemClickListener() {
                @Override
                public void onSportLikeClick(View view, int position, HomepageDomainBean.HomepageServicesBean.ServicesBean servicesBean) {
                    sendLikeData(servicesBean);
                    clickLikePos = position;
                    clickAdapter = AppConstant.HOME_SPORT_ADAPTER;
                }

                @Override
                public void onSportItemClick(View view, int position, String service_id) {
                    Intent intent = new Intent(AYHomeActivity.this, ServiceDetailInfoActivity.class);
                    intent.putExtra("service_id", service_id);
                    startActivityForResult(intent, AppConstant.SERVICE_DETAIL_REQUEST_CODE);
                }
            });
        } else {
            sportAdapter.setRefreshData(bean.services);
        }
    }

    private void initScience(HomepageDomainBean.HomepageServicesBean bean) {
        if (scienceAdapter == null) {
            LinearLayoutManager manager = new LinearLayoutManager(AYHomeActivity.this);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            scienceAdapter = new HomeScienceAdapter(AYHomeActivity.this, bean);
            rv_home_science.setNestedScrollingEnabled(false);
            rv_home_science.setLayoutManager(manager);
            rv_home_science.setAdapter(scienceAdapter);
            rv_home_science.addItemDecoration(new SpacesItemDecoration(8));
            scienceAdapter.setOnItemClickListener(new HomeScienceAdapter.OnItemClickListener() {
                @Override
                public void onScienceLikeClick(View view, int position, HomepageDomainBean.HomepageServicesBean.ServicesBean servicesBean) {
                    clickLikePos = position;
                    clickAdapter = AppConstant.HOME_SCIENCE_ADAPTER;
                    sendLikeData(servicesBean);
                }

                @Override
                public void onScienceItemClick(View view, int position, String service_id) {
                    Intent intent = new Intent(AYHomeActivity.this, ServiceDetailInfoActivity.class);
                    intent.putExtra("service_id", service_id);
                    startActivityForResult(intent, AppConstant.SERVICE_DETAIL_REQUEST_CODE);
                }
            });
        } else {
            sportAdapter.setRefreshData(bean.services);
        }
    }

    private void sendLikeData(HomepageDomainBean.HomepageServicesBean.ServicesBean bean) {
        showProcessDialog();

        if (bean.is_collected) {//已收藏 点击取消
            presenter.likePop(bean.service_id);
        } else {
            presenter.likePush(bean.service_id);
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(AYHomeActivity.this, ArtListActivity.class);
        switch (v.getId()) {
            case R.id.tv_home_care_more:

                Intent careIntent = new Intent(AYHomeActivity.this, CareListActivity.class);
                if (bean != null && bean.homepage_services != null) {
                    int m = bean.homepage_services.get(0).totalCount;
                    careIntent.putExtra("totalCount", m);
                }
                startActivityForResult(careIntent, AppConstant.CARE_MORE_REQUEST_CODE);
                break;
            case R.id.tv_home_art_more:
                if (bean != null && bean.homepage_services != null) {
                    int m = bean.homepage_services.get(1).totalCount;
                    intent.putExtra("totalCount", m);
                }
                intent.putExtra("serviceType", getString(R.string.type_art));
                intent.putExtra("title", getString(R.string.type_art));
                startActivityForResult(intent, AppConstant.ART_MORE_REQUEST_CODE);
                break;
            case R.id.tv_home_sport_more:
                if (bean != null && bean.homepage_services != null) {
                    int m = bean.homepage_services.get(2).totalCount;
                    intent.putExtra("totalCount", m);
                }
                intent.putExtra("serviceType", getString(R.string.type_sport));
                intent.putExtra("title", getString(R.string.title_sport));
                startActivityForResult(intent, AppConstant.SPORT_MORE_REQUEST_CODE);
                break;
            case R.id.tv_home_science_more:
                if (bean != null && bean.homepage_services != null) {
                    int m = bean.homepage_services.get(3).totalCount;
                    intent.putExtra("totalCount", m);
                }
                intent.putExtra("serviceType", getString(R.string.type_science));
                intent.putExtra("title", getString(R.string.title_science));
                startActivityForResult(intent, AppConstant.SCIENCE_REQUEST_CODE);
                break;
            case R.id.iv_home_location:
                startActivity(new Intent(AYHomeActivity.this, NearServiceActivity.class));
                //                SnackbarUtils.show(ctl_root,"hahaha ");
                break;
            case R.id.iv_home_like:
                startActivityForResult(new Intent(AYHomeActivity.this, MyLikeActivity.class), AppConstant.MY_LIKE_REQUEST_CODE);
                break;
            case R.id.sv_head_pic:
//                startActivityForResult(new Intent(AYHomeActivity.this, UserAboutMeActivity.class), AppConstant.ABOUT_USER_REQUEST_CODE);
                AYApplication.addActivity(this);
                startActivityForResult(new Intent(AYHomeActivity.this, UserInfoActivity.class),AppConstant.ABOUT_USER_REQUEST_CODE);
                break;
        }
    }

    private void needsRefreshHomeData(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case AppConstant.CARE_MORE_REQUEST_CODE:
            case AppConstant.ART_MORE_REQUEST_CODE:
            case AppConstant.SPORT_MORE_REQUEST_CODE:
            case AppConstant.SCIENCE_REQUEST_CODE:
            case AppConstant.MY_LIKE_REQUEST_CODE:
            case AppConstant.FEATURE_DETAIL_REQUEST_CODE:
            case AppConstant.SERVICE_DETAIL_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
//                    sl_home_refresh.setRefreshing(false);
                    sl_home_refresh.setRefreshing(true);
                    initHomeData();
                }
                break;
            case AppConstant.ABOUT_USER_REQUEST_CODE:
                refreshHeadPhoto(resultCode,data);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AYApplication.addActivity(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        Fresco.getImagePipeline().clearMemoryCaches();
    }

    private void refreshHeadPhoto(int resultCode, Intent data) {
        LogUtils.d("img_url ayhome "+data.getStringExtra("img_url"));
        if (resultCode == RESULT_OK){
            String img_url = data.getStringExtra("img_url");
            sv_head_pic.setImageURI(OSSUtils.getSignedUrl(img_url));
        }
    }


    @Override
    public void onGetHomePageData(HomepageDomainBean bean) {
        this.bean = bean;
        closeProcessDialog();
        String url = OSSUtils.getSignedUrl(img_uuid);
        LogUtils.d("pic url " + url);
        sv_head_pic.setImageURI(url);
        sl_home_refresh.setRefreshing(false);
        initCare(bean.homepage_services.get(0));
        initArt(bean.homepage_services.get(1));
        initSport(bean.homepage_services.get(2));
        initScience(bean.homepage_services.get(3));
    }

    @Override
    public void onLikePushSuccess(LikePushDomainBean bean) {
        closeProcessDialog();

        if (clickAdapter == AppConstant.HOME_ART_ADAPTER) {
            artAdapter.notifyItemChanged(clickLikePos, true);
        } else if (clickAdapter == AppConstant.HOME_SPORT_ADAPTER) {
            sportAdapter.notifyItemChanged(clickLikePos, true);
        } else if (clickAdapter == AppConstant.HOME_SCIENCE_ADAPTER) {
            scienceAdapter.notifyItemChanged(clickLikePos, true);
        }

    }

    @Override
    public void onLikePopSuccess(LikePopDomainBean bean) {
        closeProcessDialog();

        if (clickAdapter == AppConstant.HOME_ART_ADAPTER) {
            artAdapter.notifyItemChanged(clickLikePos, false);
        } else if (clickAdapter == AppConstant.HOME_SPORT_ADAPTER) {
            sportAdapter.notifyItemChanged(clickLikePos, false);
        } else if (clickAdapter == AppConstant.HOME_SCIENCE_ADAPTER) {
            scienceAdapter.notifyItemChanged(clickLikePos, false);
        }

    }

    @Override
    public void onGetHomeDataError(BaseDataBean bean) {
        closeProcessDialog();
        sl_home_refresh.setRefreshing(false);
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(ctl_root, bean.message);
        } else {
            ToastUtils.showShortToast(bean.message + "(" + bean.code + ")");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        needsRefreshHomeData(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        AYApplication.removeActivity(this);
        moveTaskToBack(true);
    }
}
