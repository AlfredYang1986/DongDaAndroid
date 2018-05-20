package com.blackmirror.dongda.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.adapter.FeaturedDetailAdapter;
import com.blackmirror.dongda.di.component.DaggerFeaturedDetailComponent;
import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.domain.model.CareMoreDomainBean;
import com.blackmirror.dongda.domain.model.LikePopDomainBean;
import com.blackmirror.dongda.domain.model.LikePushDomainBean;
import com.blackmirror.dongda.presenter.GetMoreDataPresenter;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.blackmirror.dongda.utils.AppConstant;
import com.blackmirror.dongda.utils.SnackbarUtils;
import com.blackmirror.dongda.utils.ToastUtils;

/**
 * 精选主题详情页
 */
public class FeaturedDetailActivity extends BaseActivity implements ListMoreContract.View{

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
    private GetMoreDataPresenter presenter;

    @Override
    protected void init() {
        pos = getIntent().getIntExtra("pos", 0);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_featured_detail;
    }

    @Override
    protected void initInject() {
        presenter = DaggerFeaturedDetailComponent.builder()
                .activity(this)
                .view(this)
                .build()
                .getMoreDataPresenter();
    }

    @Override
    protected void initView() {
        toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(toolbar);
        iv_featured_detail_back = findViewById(R.id.iv_featured_detail_back);
        tv_featured_tb_title = findViewById(R.id.tv_featured_tb_title);
        rv_featured_detail = findViewById(R.id.rv_featured_detail);
        ctl_root = findViewById(R.id.ctl_root);
    }

    @Override
    protected void initData() {
        initTbTitle();
    }

    @Override
    protected void initListener() {
        iv_featured_detail_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(isNeedRefresh ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
                finish();
            }
        });
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

    private void getServerData(int skipCount,String service_type) {

        showProcessDialog();
        presenter.getServiceMoreData(skipCount, 10, service_type);
    }

    @Override
    public void onGetServiceMoreDataSuccess(CareMoreDomainBean bean) {
        closeProcessDialog();
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
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(ctl_root, bean.message);
        } else {
            ToastUtils.showShortToast(bean.message + "(" + bean.code + ")");
        }
    }


    private void setDataToRecyclerView(CareMoreDomainBean bean) {

        if (bean.isSuccess) {

            adapter = new FeaturedDetailAdapter(FeaturedDetailActivity.this,bean);
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
                public void onItemDetailLikeClick(View view, int position, CareMoreDomainBean.ServicesBean servicesBean) {
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
    }
}
