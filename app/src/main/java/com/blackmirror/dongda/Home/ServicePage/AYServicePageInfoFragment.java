package com.blackmirror.dongda.Home.ServicePage;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.fragment.AYFragment;

/**
 * Created by alfredyang on 14/7/17.
 */


public class AYServicePageInfoFragment extends AYFragment {

    private static final String TAG = "AYServicePageInfoFragment";
    public ViewPager mViewPager;

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_servpage, container, false);
        mViewPager = (ViewPager)view.findViewById(R.id.viewpage_servpage_cover);
        return view;
    }


    @Override
    protected void bindingSubFragments() {
//        FragmentTransaction task = mFragmentManage.beginTransaction();
//        task.add(R.id.scroll_servpage, new AYServicePageInfoFragment());
//        task.commit();
    }

}
//public class AYServicePageInfoFragment extends AYListFragment {
//
//    private static final String TAG = "AYServicePageInfoFragment";
//    private ListView listViewv;
//
//    @Override
//    public String getClassTag() {
//        return TAG;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_homelist_serv, container, false);
//        return view;
//    }
//
//
//    @Override
//    public void onListItemClick (ListView parent, View view, int position, long id) {
//        Log.d(TAG, "onListItemClick:" + position);
//
//        Map<String,Integer> tmp = new HashMap<String, Integer>();
//        tmp.put("position",position);
//        JSONObject js = new JSONObject(tmp);
//
//        AYActivity act = (AYActivity) getActivity();
//        act.handleNotifications("didSelectedPositionNotify", js);
//
//    }
//}
