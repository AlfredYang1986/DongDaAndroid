package com.blackmirror.dongda.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.adapter.MyLikeListAdapter;
import com.blackmirror.dongda.adapter.itemdecoration.TopItemDecoration;
import com.blackmirror.dongda.di.component.DaggerMyLikeComponent;
import com.blackmirror.dongda.domain.model.BaseDataBean;
import com.blackmirror.dongda.domain.model.LikeDomainBean;
import com.blackmirror.dongda.domain.model.LikePopDomainBean;
import com.blackmirror.dongda.domain.model.LikePushDomainBean;
import com.blackmirror.dongda.presenter.MyLikePresenter;
import com.blackmirror.dongda.ui.Contract;
import com.blackmirror.dongda.ui.base.BaseActivity;
import com.blackmirror.dongda.utils.AppConstant;
import com.blackmirror.dongda.utils.SnackbarUtils;
import com.blackmirror.dongda.utils.ToastUtils;

public class MyLikeActivity extends BaseActivity implements Contract.MyLikeView {

    private CoordinatorLayout ctl_root;
    private RecyclerView rv_my_like;
    private ImageView iv_home_head_back;
    private TextView tv_home_head_title;
    private MyLikeListAdapter adapter;
    private int clickLikePos;
    private AlertDialog dialog;
    private MyLikePresenter presenter;
    private boolean isNeedRefresh;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_like;
    }

    @Override
    protected void initInject() {
        presenter = DaggerMyLikeComponent.builder()
                .activity(this)
                .view(this)
                .build()
                .getMyLikePresenter();
    }

    @Override
    protected void initView() {
        ctl_root = findViewById(R.id.ctl_root);
        rv_my_like = findViewById(R.id.rv_my_like);
        iv_home_head_back = findViewById(R.id.iv_home_head_back);
        tv_home_head_title = findViewById(R.id.tv_home_head_title);
        tv_home_head_title.setText(R.string.my_collection);
    }

    @Override
    protected void initData() {
        getLikeData();
    }

    @Override
    protected void initListener() {
        iv_home_head_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(isNeedRefresh ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
                finish();
            }
        });
    }

    private void getLikeData() {
        showProcessDialog();
        presenter.getLikeData();
    }

    @Override
    public void onGetLikeDataSuccess(LikeDomainBean bean) {
        closeProcessDialog();
        adapter = new MyLikeListAdapter(MyLikeActivity.this, bean);
        rv_my_like.setLayoutManager(new LinearLayoutManager(MyLikeActivity.this));
        rv_my_like.setAdapter(adapter);
        rv_my_like.addItemDecoration(new TopItemDecoration(40, 40));
        adapter.setOnLikeListClickListener(new MyLikeListAdapter.OnLikeListClickListener() {
            @Override
            public void onItemLikeListClick(View view, int position, String service_id) {
                Intent intent = new Intent(MyLikeActivity.this, ServiceDetailInfoActivity.class);
                intent.putExtra("service_id", service_id);
                startActivity(intent);
            }

            @Override
            public void onItemLikeClick(View view, int position, LikeDomainBean.ServicesBean likeBean) {
                clickLikePos = position;
                showConfirmUnLikeDialog(position, likeBean);
                //                    sendLikeData(likeBean);
            }
        });

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
        adapter.removeItem(clickLikePos);
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

    private void sendLikeData(LikeDomainBean.ServicesBean bean) {
        showProcessDialog();
        if (bean.is_collected) {//已收藏 点击取消
            presenter.likePop(bean.service_id);
        } else {
            presenter.likePush(bean.service_id);
        }
    }

    private void showConfirmUnLikeDialog(int position, final LikeDomainBean.ServicesBean bean) {
        dialog = new AlertDialog.Builder(MyLikeActivity.this)
                .setCancelable(false)
                .setTitle(R.string.dlg_tips)
                .setMessage(R.string.confirm_un_like)
                .setPositiveButton(getString(R.string.dlg_confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        sendLikeData(bean);
                    }
                })
                .setNegativeButton(getString(R.string.dlg_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();

    }

    @Override
    public void onBackPressed() {
        setResult(isNeedRefresh ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog !=null && dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }
    }
}
