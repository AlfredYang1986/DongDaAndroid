package com.blackmirror.dongda.Home.HomeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.blackmirror.dongda.Home.ServicePage.AYServicePageActivity;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.OtherUtils;
import com.blackmirror.dongda.Tools.ToastUtils;
import com.blackmirror.dongda.activity.CareListActivity;
import com.blackmirror.dongda.activity.ShowMapActivity;
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
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alfredyang on 29/6/17.
 */

public class AYHomeActivity extends AYActivity {

    final private String TAG = "AYHomeActivity";
    private AYHomeListServAdapter serviceListAdapter;
    private JSONArray serviceData;

    private long skipedCount;
    private long timeSpan;
    private RecyclerView rv_featured_theme;
    private RecyclerView rv_home_care;
    private SimpleDraweeView sv_head_pic;
    private RecyclerView rv_home_art;
    private RecyclerView rv_home_sport;
    private RecyclerView rv_home_science;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        skipedCount = 0;
        timeSpan = new Date().getTime();

        serviceListAdapter = new AYHomeListServAdapter(this, serviceData);
        ((AYHomeListServFragment) this.fragments.get("frag_homelist_serv")).setListAdapter
                (serviceListAdapter);
        initView();
        initData();
        OtherUtils.setStatusBarColor(AYHomeActivity.this);
    }



    private void initView() {
        sv_head_pic = findViewById(R.id.sv_head_pic);
        rv_featured_theme = findViewById(R.id.rv_featured_theme);
        rv_home_care = findViewById(R.id.rv_home_care);
        rv_home_art = findViewById(R.id.rv_home_art);
        rv_home_sport = findViewById(R.id.rv_home_sport);
        rv_home_science = findViewById(R.id.rv_home_science);
    }

    private void initData() {
        sv_head_pic.setImageURI(OtherUtils.resourceIdToUri(AYHomeActivity.this, R.mipmap.dongda_logo));

        //精选主题
        initSubject();
        //看顾
        initCare();
        //艺术
        initArt();
        //运动
        initSport();
        //科学
        initScience();

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
                startActivity(new Intent(AYHomeActivity.this, ShowMapActivity.class));
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    private void initCare() {
        List<Integer> careList = new ArrayList<>();
        careList.add(R.drawable.home_cover_00);
        careList.add(R.drawable.home_cover_01);
        careList.add(R.drawable.home_cover_02);
        careList.add(R.drawable.home_cover_03);
        LinearLayoutManager manager = new LinearLayoutManager(AYHomeActivity.this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        HomeCareAdapter adapter = new HomeCareAdapter(AYHomeActivity.this, careList);
        rv_home_care.setNestedScrollingEnabled(false);
        rv_home_care.setLayoutManager(manager);
        rv_home_care.setAdapter(adapter);
        rv_home_care.addItemDecoration(new SpacesItemDecoration(8));
        adapter.setOnCareClickListener(new HomeCareAdapter.OnCareClickListener() {
            @Override
            public void onItemCareClick(View view, int position) {
                startActivity(new Intent(AYHomeActivity.this, CareListActivity.class));
            }
        });
    }

    private void initArt() {
        List<Integer> artList = new ArrayList<>();
        artList.add(R.drawable.home_cover_00);
        artList.add(R.drawable.home_cover_01);
        artList.add(R.drawable.home_cover_02);
        artList.add(R.drawable.home_cover_03);
        LinearLayoutManager manager = new LinearLayoutManager(AYHomeActivity.this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        HomeArtAdapter adapter = new HomeArtAdapter(AYHomeActivity.this, artList);
        rv_home_art.setNestedScrollingEnabled(false);
        rv_home_art.setLayoutManager(manager);
        rv_home_art.setAdapter(adapter);
        rv_home_art.addItemDecoration(new SpacesItemDecoration(8));
        adapter.setOnItemClickListener(new HomeArtAdapter.OnItemClickListener() {
            @Override
            public void onArtLikeClick(View view, int postion) {
                ToastUtils.showShortToast("点击了收藏");
            }

            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    private void initSport() {
        List<Integer> sportList = new ArrayList<>();
        sportList.add(R.drawable.home_cover_00);
        sportList.add(R.drawable.home_cover_01);
        sportList.add(R.drawable.home_cover_02);
        sportList.add(R.drawable.home_cover_03);
        LinearLayoutManager manager = new LinearLayoutManager(AYHomeActivity.this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        HomeSportAdapter adapter = new HomeSportAdapter(AYHomeActivity.this, sportList);
        rv_home_sport.setNestedScrollingEnabled(false);
        rv_home_sport.setLayoutManager(manager);
        rv_home_sport.setAdapter(adapter);
        rv_home_sport.addItemDecoration(new SpacesItemDecoration(8));
    }

    private void initScience() {
        List<Integer> scienceList = new ArrayList<>();
        scienceList.add(R.drawable.home_cover_00);
        scienceList.add(R.drawable.home_cover_01);
        scienceList.add(R.drawable.home_cover_02);
        scienceList.add(R.drawable.home_cover_03);
        LinearLayoutManager manager = new LinearLayoutManager(AYHomeActivity.this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        HomeScienceAdapter adapter = new HomeScienceAdapter(AYHomeActivity.this, scienceList);
        rv_home_science.setNestedScrollingEnabled(false);
        rv_home_science.setLayoutManager(manager);
        rv_home_science.setAdapter(adapter);
        rv_home_science.addItemDecoration(new SpacesItemDecoration(8));
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
        ((AYHomeListServFragment) this.fragments.get("frag_homelist_serv"))
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
        }

        return true;
    }

    public Boolean AYSearchServiceCommandFailed(JSONObject args) {
        ((AYHomeListServFragment) this.fragments.get("frag_homelist_serv"))
                .refreshOrLoadMoreComplete();
        Toast.makeText(this, "请改善网络环境并重试", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String text = null;
            if (bundle != null)
                text = bundle.getString("second");
            Log.d("text", text);
        }
    }
}
