package com.blackmirror.dongda.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.LogUtils;
import com.blackmirror.dongda.Tools.OtherUtils;
import com.blackmirror.dongda.Tools.ToastUtils;
import com.blackmirror.dongda.adapter.CareListAdapter;
import com.blackmirror.dongda.adapter.itemdecoration.TopItemDecoration;

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
    private SwipeRefreshLayout sl_care_list;
    //用来标记是否正在向上滑动
    private boolean isSlidingUpward = false;
    private boolean isloading = false;
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
        if (list.size()<totalCount){
            adapter.canLoadMore=true;
        }else {
            adapter.canLoadMore=false;
        }
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
        rv_care_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (newState==RecyclerView.SCROLL_STATE_IDLE){
                    //获取最后一个完全显示的itemPosition
                    int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
                    int itemCount = manager.getItemCount();
                    if (adapter.canLoadMore && lastItemPosition == itemCount-1 && isSlidingUpward){
                       /* if (adapter.canLoadMore){
                            adapter.notifyItemChanged(lastItemPosition,"visible");
                            loadMore();
                        }else {
                            adapter.notifyItemChanged(lastItemPosition,"nodata");
                        }*/

                        adapter.notifyItemChanged(lastItemPosition,"visible");
                        if (!isloading){
                            isloading = true;
                            loadMore();
                        }

                    }else {
                       /* if (lastItemPosition == itemCount -2){
                            *//*manager.scrollToPositionWithOffset(lastItemPosition, 0);
                            manager.setStackFromEnd(true);*//*
                            int top = recyclerView.getChildAt(itemCount -2 - manager.findFirstVisibleItemPosition()).getTop();
                            View at = recyclerView.getChildAt(itemCount - 2 - manager.findFirstVisibleItemPosition());
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) at.getLayoutParams();
                            int margin = params.topMargin;
                            recyclerView.scrollBy(0, -(top+margin));
                        }*/
//                        adapter.notifyItemChanged(lastItemPosition,"invisible");
                    }

                    if (lastItemPosition == itemCount -2 && manager.findLastVisibleItemPosition() == itemCount-1){
//                            manager.scrollToPositionWithOffset(lastItemPosition, 0);
//                            manager.setStackFromEnd(true);
                        int bottom = recyclerView.getChildAt(itemCount -1 - manager.findFirstVisibleItemPosition()).getBottom();
                        int top = recyclerView.getChildAt(itemCount -1 - manager.findFirstVisibleItemPosition()).getTop();
                        View at = recyclerView.getChildAt(itemCount - 1 - manager.findFirstVisibleItemPosition());
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) at.getLayoutParams();
                        int margin = params.topMargin;
                        recyclerView.scrollBy(0, -(bottom-top));
                    }


                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
                isSlidingUpward = dy > 0;
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastItemPosition = manager.findLastVisibleItemPosition();
                int itemCount = manager.getItemCount();
//                layoutManager.getChildAt(position-firstVisibilityPosition)
                int top = manager.getChildAt(lastItemPosition - manager.findFirstVisibleItemPosition()).getBottom();
                LogUtils.d("onScrolled "+top);
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
                        if (adapter.getItemCount()-1>=totalCount){
                            adapter.canLoadMore=false;
                        }else {
                            adapter.canLoadMore=true;
                        }
                        isloading=false;
                        adapter.load_complete=true;
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
        sl_care_list.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Observable.timer(1500, TimeUnit.MILLISECONDS, Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                if (sl_care_list.isRefreshing()){
                                    sl_care_list.setRefreshing(false);
                                }
                            }
                        });
            }
        });
    }
}
