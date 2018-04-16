package com.blackmirror.dongda.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.BasePrefUtils;
import com.blackmirror.dongda.Tools.ToastUtils;
import com.blackmirror.dongda.adapter.MyLikeListAdapter;
import com.blackmirror.dongda.adapter.itemdecoration.TopItemDecoration;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.model.ErrorInfoBean;
import com.blackmirror.dongda.model.serverbean.QueryLikeServerBean;
import com.blackmirror.dongda.model.uibean.QueryLikeUiBean;

import org.json.JSONException;
import org.json.JSONObject;

public class MyLikeActivity extends AYActivity {

    private RecyclerView rv_my_like;
    private ImageView iv_home_head_back;
    private TextView tv_home_head_title;
    private MyLikeListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_like);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        rv_my_like = findViewById(R.id.rv_my_like);
        iv_home_head_back = findViewById(R.id.iv_home_head_back);
        tv_home_head_title = findViewById(R.id.tv_home_head_title);
        tv_home_head_title.setText("我的收藏");
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
        AYFacade facade = facades.get("QueryServiceFacade");
        try {
            String json="{\"token\":\""+BasePrefUtils.getAuthToken()+"\",\"condition\":{\"user_id\":\""+BasePrefUtils.getUserId()+"\"}}";
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

        QueryLikeServerBean serverBean = JSON.parseObject(args.toString(), QueryLikeServerBean.class);
        QueryLikeUiBean bean = new QueryLikeUiBean(serverBean);
        if (bean.isSuccess){
            adapter = new MyLikeListAdapter(MyLikeActivity.this, bean.services);
            rv_my_like.setLayoutManager(new LinearLayoutManager(MyLikeActivity.this));
            rv_my_like.setAdapter(adapter);
            rv_my_like.addItemDecoration(new TopItemDecoration(40,40));
            adapter.setOnLikeListClickListener(new MyLikeListAdapter.OnLikeListClickListener() {
                @Override
                public void onItemLikeListClick(View view, int position) {

                }

                @Override
                public void onItemLikeClick(View view, int position) {
                    ToastUtils.showShortToast("点击了 "+position);
                }
            });
        }else {
            ToastUtils.showShortToast(bean.message+"("+bean.code+")");
        }
    }


    public void AYLikeQueryCommandFailed(JSONObject args) {

        ErrorInfoBean bean = JSON.parseObject(args.toString(), ErrorInfoBean.class);
        if (bean != null && bean.error != null) {
            if (bean != null && bean.error != null) {
                ToastUtils.showShortToast(bean.error.message+"("+bean.error.code+")");
            }
        }
    }

    @Override
    protected void bindingFragments() {

    }
}
