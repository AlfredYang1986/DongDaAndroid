package com.blackmirror.dongda.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.ui.base.AYActivity;
import com.blackmirror.dongda.utils.AYPrefUtils;
import com.blackmirror.dongda.utils.AppConstant;
import com.blackmirror.dongda.utils.SnackbarUtils;
import com.blackmirror.dongda.utils.ToastUtils;
import com.blackmirror.dongda.adapter.MyLikeListAdapter;
import com.blackmirror.dongda.adapter.itemdecoration.TopItemDecoration;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.model.serverbean.ErrorInfoServerBean;
import com.blackmirror.dongda.model.serverbean.LikePopServerBean;
import com.blackmirror.dongda.model.serverbean.LikePushServerBean;
import com.blackmirror.dongda.model.serverbean.QueryLikeServerBean;
import com.blackmirror.dongda.model.uibean.ErrorInfoUiBean;
import com.blackmirror.dongda.model.uibean.LikePopUiBean;
import com.blackmirror.dongda.model.uibean.LikePushUiBean;
import com.blackmirror.dongda.model.uibean.QueryLikeUiBean;

import org.json.JSONException;
import org.json.JSONObject;

public class MyLikeActivity extends AYActivity {

    private CoordinatorLayout ctl_root;
    private RecyclerView rv_my_like;
    private ImageView iv_home_head_back;
    private TextView tv_home_head_title;
    private MyLikeListAdapter adapter;
    private int clickLikePos;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_like);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        ctl_root = findViewById(R.id.ctl_root);
        rv_my_like = findViewById(R.id.rv_my_like);
        iv_home_head_back = findViewById(R.id.iv_home_head_back);
        tv_home_head_title = findViewById(R.id.tv_home_head_title);
        tv_home_head_title.setText(R.string.my_collection);
    }

    private void initData() {
        getLikeData();
    }

    private void initListener() {
        iv_home_head_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getLikeData() {
        try {
            showProcessDialog();
            AYFacade facade = facades.get("QueryServiceFacade");
            String json="{\"token\":\""+ AYPrefUtils.getAuthToken()+"\",\"condition\":{\"user_id\":\""+ AYPrefUtils.getUserId()+"\"}}";
            JSONObject object = new JSONObject(json);
            facade.execute("AYLikeQueryCommand",object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 收藏列表
     * @param args
     */
    public void AYLikeQueryCommandSuccess(JSONObject args){
        closeProcessDialog();
        final QueryLikeServerBean serverBean = JSON.parseObject(args.toString(), QueryLikeServerBean.class);
        QueryLikeUiBean bean = new QueryLikeUiBean(serverBean);
        if (bean.isSuccess){
            adapter = new MyLikeListAdapter(MyLikeActivity.this, bean.services);
            rv_my_like.setLayoutManager(new LinearLayoutManager(MyLikeActivity.this));
            rv_my_like.setAdapter(adapter);
            rv_my_like.addItemDecoration(new TopItemDecoration(40,40));
            adapter.setOnLikeListClickListener(new MyLikeListAdapter.OnLikeListClickListener() {
                @Override
                public void onItemLikeListClick(View view, int position, String service_id) {
                    Intent intent = new Intent(MyLikeActivity.this, ServiceDetailInfoActivity.class);
                    intent.putExtra("service_id",service_id);
                    startActivity(intent);
                }

                @Override
                public void onItemLikeClick(View view, int position, QueryLikeServerBean
                        .ResultBean.ServicesBean likeBean) {
                    clickLikePos=position;
                    showConfirmUnLikeDialog(position, likeBean);
//                    sendLikeData(likeBean);
                }
            });
        }else {
            ToastUtils.showShortToast(bean.message+"("+bean.code+")");
        }
    }

    public void AYLikeQueryCommandFailed(JSONObject args) {

        closeProcessDialog();
        ErrorInfoServerBean serverBean = JSON.parseObject(args.toString(), ErrorInfoServerBean.class);
        ErrorInfoUiBean uiBean = new ErrorInfoUiBean(serverBean);
        if (uiBean.code== AppConstant.NET_WORK_UNAVAILABLE){
            SnackbarUtils.show(ctl_root,uiBean.message);
        }else {
            ToastUtils.showShortToast(uiBean.message+"("+uiBean.code+")");
        }
    }

    private void showConfirmUnLikeDialog(int position, final QueryLikeServerBean.ResultBean.ServicesBean bean) {
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

    private void sendLikeData(QueryLikeServerBean.ResultBean.ServicesBean bean) {
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
        closeProcessDialog();
        LikePopServerBean serverBean = JSON.parseObject(args.toString(), LikePopServerBean.class);
        LikePopUiBean popUiBean = new LikePopUiBean(serverBean);
        if (popUiBean.isSuccess){
//            adapter.notifyItemChanged(clickLikePos,false);
            adapter.removeItem(clickLikePos);
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
}
