package com.blackmirror.dongda.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.blackmirror.dongda.Home.ServicePage.AYServicePageActivity;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.facade.DongdaCommonFacade.SQLiteProxy.DAO.AYDaoUserProfile;
import com.blackmirror.dongda.factory.AYFactoryManager;
import com.blackmirror.dongda.fragment.AYFragment;
import com.blackmirror.dongda.fragment.AYListFragment;
import com.blackmirror.dongda.fragment.AYNavBarFragment;

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

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        AYFacade f_login = (AYFacade) AYFactoryManager.getInstance(this).queryInstance("facade", "DongdaCommanFacade");
        AYCommand cmd_profile = f_login.cmds.get("QueryCurrentLoginUser");
        AYDaoUserProfile user = cmd_profile.excute();

        AYFacade facade = facades.get("QueryServiceFacade");
        AYCommand cmd = facade.cmds.get("SearchService");
        Map<String, Object> search_args = new HashMap<>();
        search_args.put("user_id", user.getUser_id());
        search_args.put("auth_token", user.getAuth_token());
        search_args.put("skip", 0);
        search_args.put("date", new Date().getTime());
        JSONObject args = new JSONObject(search_args);
        cmd.excute(args);

//        serviceData = ServiceData.getDataInstance().getServDataWithArgs();
        serviceListAdapter = new AYHomeListServAdapter(this, serviceData);
        ((AYHomeListServFragment)this.fragments.get("frag_homelist_serv")).setListAdapter(serviceListAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setTitleTextInvisible();
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setLeftBtnTextWithString("北京市");
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setRightBtnImageWithImageId(R.drawable.home_icon_mapfilter);

//        ((AYHomeSegFragment)this.fragments.get("frag_homeseg")).changeFocusItemWithIndex(0);
    }

    @Override
    protected void bindingFragments() {

        FragmentTransaction task = mFragmentManage.beginTransaction();
        task.add(R.id.activity_home, (AYFragment)this.fragments.get("frag_navbar"));
        task.add(R.id.activity_home, (AYFragment)this.fragments.get("frag_tabbar"));
        task.add(R.id.activity_home, (AYFragment)this.fragments.get("frag_homeseg"));
        task.add(R.id.activity_home, (AYListFragment)this.fragments.get("frag_homelist_serv"));
        task.commit();
    }
    
    
    public void didNavLeftBtnClickNotify (JSONObject args) {
        Log.d(TAG, "didNavLeftBtnClickNotify: in Activity");

    }

    public void didNavRightBtnClickNotify (JSONObject args) {

        Log.d(TAG, "didNavRightBtnClickNotify: in Activity");
    }

    public void didSelectedPositionNotify (JSONObject args) {
        Log.d(TAG, "didSelectedPositionNotify: in Activity");

        int position = 0;
        try {
            position = args.getInt("position");
            Map<String,Integer> tmp = (Map<String, Integer>) serviceData.get(position);
            JSONObject js = new JSONObject(tmp);

            Intent intent = new Intent(this, AYServicePageActivity.class);
            intent.putExtra("service_info", js.toString());
    //        startActivityForResult(intent, requestCode);
            startActivity(intent);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void didSegFristItemClickNotify (JSONObject args) {

        Log.d(TAG, "didSegFristItemClickNotify: in Activity");
    }
    public void didSegSecondItemClickNotify (JSONObject args) {

        Log.d(TAG, "didSegSecondItemClickNotify: in Activity");
    }

    public Boolean AYSearchServiceCommandSuccess (JSONObject args) {

        JSONArray data = null;
        try {
            data = args.getJSONArray("result");
            serviceListAdapter.setQueryData(data);
            serviceListAdapter.refreshList();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return true;
    }
    public Boolean AYSearchServiceCommandFailed (JSONObject args) {
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
