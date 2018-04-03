package com.blackmirror.dongda.Home.HomeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.blackmirror.dongda.Home.ServicePage.AYServicePageActivity;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.facade.DongdaCommonFacade.SQLiteProxy.DAO.AYDaoUserProfile;
import com.blackmirror.dongda.factory.AYFactoryManager;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        skipedCount = 0;
        timeSpan = new Date().getTime();
        SimpleDraweeView sv_head_pic = findViewById(R.id.sv_head_pic);
//        sv_head_pic.setImageURI("https://pic4.zhimg.com/03b2d57be62b30f158f48f388c8f3f33_b.png");
//        searchServiceRemote();

        serviceListAdapter = new AYHomeListServAdapter(this, serviceData);
        ((AYHomeListServFragment)this.fragments.get("frag_homelist_serv")).setListAdapter(serviceListAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        /*Method method = context.getClass().getMethod(name, JSONObject.class);
        method.invoke(context, args);*/

        /*((AYNavBarFragment)this.fragments.get("frag_navbar")).setTitleTextInvisible();
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setLeftBtnTextWithString("北京市");
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setRightBtnImageWithImageId(R.drawable.home_icon_mapfilter);
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

        AYFacade f_login = (AYFacade) AYFactoryManager.getInstance(this).queryInstance("facade", "DongdaCommanFacade");
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
    
    public void didNavLeftBtnClickNotify (JSONObject args) {
        Log.d(TAG, "didNavLeftBtnClickNotify: in Activity");

    }

    public void didNavRightBtnClickNotify (JSONObject args) {

        Log.d(TAG, "didNavRightBtnClickNotify: in Activity");
    }

    public void didSegFristItemClickNotify (JSONObject args) {
        Log.d(TAG, "didSegFristItemClickNotify: in Activity");

    }
    public void didSegSecondItemClickNotify (JSONObject args) {
        Log.d(TAG, "didSegSecondItemClickNotify: in Activity");

    }

    public void sendRefreshDataNotify (JSONObject args) {
        Log.d(TAG, "sendRefreshDataNotify: in Activity");
        skipedCount = 0;
        timeSpan = new Date().getTime();
//        searchServiceRemote();
    }
    public void sendLoadMoreDataNotify (JSONObject args) {
        Log.d(TAG, "sendLoadMoreDataNotify: in Activity");
//        searchServiceRemote();
    }

    public void didSelectedPositionNotify (JSONObject args) {
        Log.d(TAG, "didSelectedPositionNotify: in Activity");

        int position = 0;
        try {
            position = args.getInt("position");
            Log.d(TAG, "didSelectedPositionNotify: selected-->"+position);
            if (position == 0) {

            } else {
                JSONObject js = serviceData.getJSONObject(position-1);
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


    public Boolean AYSearchServiceCommandSuccess (JSONObject args) {
        ((AYHomeListServFragment)this.fragments.get("frag_homelist_serv")).refreshOrLoadMoreComplete();

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
    public Boolean AYSearchServiceCommandFailed (JSONObject args) {
        ((AYHomeListServFragment)this.fragments.get("frag_homelist_serv")).refreshOrLoadMoreComplete();
        Toast.makeText(this, "请改善网络环境并重试", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0 && resultCode==RESULT_OK){
            Bundle bundle = data.getExtras();
            String text =null;
            if(bundle!=null)
                text=bundle.getString("second");
            Log.d("text",text);
        }
    }
}
