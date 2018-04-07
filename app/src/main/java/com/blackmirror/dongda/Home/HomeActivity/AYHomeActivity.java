package com.blackmirror.dongda.Home.HomeActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.blackmirror.dongda.Home.ServicePage.AYServicePageActivity;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.LogUtils;
import com.blackmirror.dongda.Tools.OtherUtils;
import com.blackmirror.dongda.Tools.ToastUtils;
import com.blackmirror.dongda.adapter.FeaturedThemeAdapter;
import com.blackmirror.dongda.adapter.HomeArtAdapter;
import com.blackmirror.dongda.adapter.HomeCareAdapter;
import com.blackmirror.dongda.adapter.HomeScienceAdapter;
import com.blackmirror.dongda.adapter.HomeSportAdapter;
import com.blackmirror.dongda.adapter.SpacesItemDecoration;
import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.facade.DongdaCommonFacade.SQLiteProxy.DAO.AYDaoUserProfile;
import com.blackmirror.dongda.factory.AYFactoryManager;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
        //        sv_head_pic.setImageURI("https://pic4.zhimg
        // .com/03b2d57be62b30f158f48f388c8f3f33_b.png");
        //        searchServiceRemote();

        serviceListAdapter = new AYHomeListServAdapter(this, serviceData);
        ((AYHomeListServFragment) this.fragments.get("frag_homelist_serv")).setListAdapter
                (serviceListAdapter);
        initView();
        initData();
        LogUtils.d("xcx","isMIUI "+OtherUtils.isMIUI());
        LogUtils.d("xcx","MANUFACTURER "+Build.MANUFACTURER);
        OtherUtils.setStatusBarColor(AYHomeActivity.this);
    }

    /**
     * 设置Android状态栏的字体颜色，状态栏为亮色的时候字体和图标是黑色，状态栏为暗色的时候字体和图标为白色
     *
     * @param dark 状态栏字体和图标是否为深色
     */
    protected void setStatusBarFontDark(boolean dark) {
        // 小米MIUI
        try {
            Window window = getWindow();
            Class clazz = getWindow().getClass();
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (dark) {    //状态栏亮色且黑色字体
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
            } else {       //清除黑色字体
                extraFlagField.invoke(window, 0, darkModeFlag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 魅族FlymeUI
        try {
            Window window = getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (dark) {
                value |= bit;
            } else {
                value &= ~bit;
            }
            meizuFlags.setInt(lp, value);
            window.setAttributes(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // android6.0+系统
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (dark) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
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
        sv_head_pic.setBackgroundResource(R.mipmap.dongda_logo);
        //精选主题
        List<Integer> featuredList = new ArrayList<>();
        featuredList.add(R.drawable.home_cover_00);
        featuredList.add(R.drawable.home_cover_01);
        featuredList.add(R.drawable.home_cover_02);
        featuredList.add(R.drawable.home_cover_03);
        featuredList.add(R.drawable.home_cover_04);
        LinearLayoutManager featuredManager = new LinearLayoutManager(AYHomeActivity.this);
        featuredManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        FeaturedThemeAdapter featuredAdapter = new FeaturedThemeAdapter(AYHomeActivity.this,
                featuredList);
        rv_featured_theme.setNestedScrollingEnabled(false);
        rv_featured_theme.setLayoutManager(featuredManager);
        rv_featured_theme.setAdapter(featuredAdapter);
        rv_featured_theme.addItemDecoration(new SpacesItemDecoration(28));

        //看顾
        List<Integer> careList = new ArrayList<>();
        careList.add(R.drawable.home_cover_00);
        careList.add(R.drawable.home_cover_01);
        careList.add(R.drawable.home_cover_02);
        careList.add(R.drawable.home_cover_03);
        LinearLayoutManager careManager = new LinearLayoutManager(AYHomeActivity.this);
        careManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        HomeCareAdapter caredAdapter = new HomeCareAdapter(AYHomeActivity.this, careList);
        rv_home_care.setNestedScrollingEnabled(false);
        rv_home_care.setLayoutManager(careManager);
        rv_home_care.setAdapter(caredAdapter);
        rv_home_care.addItemDecoration(new SpacesItemDecoration(8));

        //艺术
        initArt();
        //运动
        initSport();
        //科学
        initScience();

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
