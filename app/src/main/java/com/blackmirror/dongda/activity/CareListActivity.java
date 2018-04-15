package com.blackmirror.dongda.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.OtherUtils;
import com.blackmirror.dongda.Tools.ToastUtils;
import com.blackmirror.dongda.adapter.CareListAdapter;
import com.blackmirror.dongda.adapter.itemdecoration.TopItemDecoration;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CareListActivity extends AppCompatActivity {

    private ImageView iv_home_head_back;
    private TextView tv_home_head_title;
    private RecyclerView rv_care_list;
    private SmartRefreshLayout sl_care_list;
    private CareListAdapter adapter;
    private int totalCount=30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_list);
        initView();
        initData();
        initListener();
        OtherUtils.setStatusBarColor(this);
    }

    private void initView() {
        iv_home_head_back = findViewById(R.id.iv_home_head_back);
        tv_home_head_title = findViewById(R.id.tv_home_head_title);
        rv_care_list = findViewById(R.id.rv_care_list);
        sl_care_list = findViewById(R.id.sl_care_list);
    }

    private void initData() {

        sl_care_list.setEnableLoadMore(true);
        sl_care_list.setNoMoreData(false);
        sl_care_list.setEnableLoadMoreWhenContentNotFull(true);//内容不满屏幕的时候也开启加载更多
        sl_care_list.setRefreshHeader(new MaterialHeader(CareListActivity.this));
        tv_home_head_title.setText("看顾");
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.home_cover_00);
        list.add(R.drawable.home_cover_01);
        list.add(R.drawable.home_cover_02);
        list.add(R.drawable.home_cover_03);
        list.add(R.drawable.home_cover_04);
        list.add(R.drawable.home_cover_00);
        list.add(R.drawable.home_cover_01);
        list.add(R.drawable.home_cover_02);
        list.add(R.drawable.home_cover_03);
        list.add(R.drawable.home_cover_04);

        adapter = new CareListAdapter(CareListActivity.this, list);
        rv_care_list.setLayoutManager(new LinearLayoutManager(CareListActivity.this));
        rv_care_list.setAdapter(adapter);
        rv_care_list.addItemDecoration(new TopItemDecoration(40,40));
        adapter.setOnCareListClickListener(new CareListAdapter.OnCareListClickListener() {
            @Override
            public void onItemCareListClick(View view, int position) {

            }

            @Override
            public void onItemCareLikeClick(View view, int position) {
                ToastUtils.showShortToast("点击了 "+position);
            }
        });

    }

    private void loadMore() {
        Observable.timer(3000,TimeUnit.MILLISECONDS,Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        List<Integer> list = new ArrayList<>();
                        list.add(R.drawable.home_btn_nearyou);
                        list.add(R.drawable.home_btn_nearyou);
                        list.add(R.drawable.home_btn_nearyou);
                        list.add(R.drawable.home_btn_nearyou);
                        list.add(R.drawable.home_btn_nearyou);
                        adapter.setMoreData(list);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void initListener() {
        iv_home_head_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*sl_care_list.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Observable.timer(1500, TimeUnit.MILLISECONDS, Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                if (sl_care_list.isRefreshing()){
//                                    sl_care_list.setRefreshing(false);
                                }
                            }
                        });
            }
        });*/
    }
}
