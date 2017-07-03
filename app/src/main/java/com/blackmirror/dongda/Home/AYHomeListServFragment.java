package com.blackmirror.dongda.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.blackmirror.dongda.Home.ServicePage.AYServicePageActivity;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.fragment.AYFragment;
import com.blackmirror.dongda.fragment.AYListFragment;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by alfredyang on 3/7/17.
 */

public class AYHomeListServFragment extends AYListFragment {

    private static final String TAG = "ListFragmentImpl";

    private AYHomeListServAdapter homelistAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homelistAdapter = new AYHomeListServAdapter(getActivity());
        setListAdapter(homelistAdapter);

//        this.setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, classes));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homelist_serv, container, false);
        return view;
    }

    @Override
    public void onListItemClick (ListView parent, View view, int position, long id) {
        Log.d(TAG, "onListItemClick:" + position);

        AYActivity act = (AYActivity) this.getActivity();
        act.handleNotifications("didSelectedPositionNotify", new JSONObject());

//        Intent intent = new Intent(getActivity(),AYServicePageActivity.class);
//        //采用Intent普通传值的方式
//        intent.putExtra("skip", "我是MainActivity传过来的值！");
//        //跳转Activity
////        startActivityForResult(intent, requestCode);
//        startActivity(intent);
    }
}
