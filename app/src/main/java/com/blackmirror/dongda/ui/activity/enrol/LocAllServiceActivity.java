package com.blackmirror.dongda.ui.activity.enrol;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.adapter.LocAllServiceAdapter;
import com.blackmirror.dongda.di.component.DaggerLocAllServiceComponent;
import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.domain.model.BrandAllLocDomainBean;
import com.blackmirror.dongda.domain.model.LocAllServiceDomainBean;
import com.blackmirror.dongda.presenter.EnrolPresenter;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.blackmirror.dongda.utils.AppConstant;
import com.blackmirror.dongda.utils.LogUtils;
import com.blackmirror.dongda.utils.SnackbarUtils;
import com.blackmirror.dongda.utils.ToastUtils;

public class LocAllServiceActivity extends BaseActivity implements EnrolContract.View{


    private ImageView iv_back;
    private RecyclerView rv_service;
    private ConstraintLayout cl_add_service;
    private EnrolPresenter presenter;
    private String locations;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_loc_all_service;
    }

    @Override
    protected void initInject() {
        presenter = DaggerLocAllServiceComponent.builder()
                .activity(this)
                .view(this)
                .build()
                .getEnrolPresenter();
    }

    @Override
    protected void initView() {
        iv_back = findViewById(R.id.iv_back);
        rv_service = findViewById(R.id.rv_service);
        cl_add_service = findViewById(R.id.cl_add_service);
    }

    @Override
    protected void initData() {
        locations = getIntent().getStringExtra("locations");
        presenter.getLocAllService("", locations);
    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cl_add_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onGetBrandAllLocationSuccess(BrandAllLocDomainBean bean) {

    }

    @Override
    public void onGetLocAllServiceSuccess(LocAllServiceDomainBean bean) {
        LogUtils.d(bean.toString());
        closeProcessDialog();
        LocAllServiceAdapter adapter = new LocAllServiceAdapter(this, bean);
        rv_service.setLayoutManager(new LinearLayoutManager(this));
        rv_service.setAdapter(adapter);
        adapter.setOnItemClickListener(new LocAllServiceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, LocAllServiceDomainBean.ServicesBean bean) {
                Intent intent = new Intent(LocAllServiceActivity.this, EnrolServiceActivity.class);
                StringBuilder sb = new StringBuilder();
                if (bean.service_tags!=null && bean.service_tags.size()!=0 && !TextUtils.isEmpty(bean.service_tags.get(0))){
                    sb.append(bean.service_tags.get(0))
                            .append("的");
                }
                sb.append(bean.service_leaf);
                intent.putExtra("locations",locations);
                intent.putExtra("service_leaf",sb.toString());
                intent.putExtra("service_image",bean.service_image);
                intent.putExtra("service_id",bean.service_id);
                intent.putExtra("address",getIntent().getStringExtra("address"));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onError(BaseDataBean bean) {
        closeProcessDialog();
        if (bean.code == AppConstant.NET_WORK_UNAVAILABLE) {
            SnackbarUtils.show(iv_back, bean.message);
        } else {
            ToastUtils.showShortToast(bean.message + "(" + bean.code + ")");
        }
    }
}
