package com.blackmirror.dongda.Home.HomeActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.fragment.AYListFragment;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by alfredyang on 3/7/17.
 */

public class AYHomeListServFragment extends AYListFragment {

    private static final String TAG = "ListFragmentImpl";
    private PtrClassicFrameLayout ptrLayout;
    private ListView listViewv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, classes));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homelist_serv, container, false);
        ptrLayout = (PtrClassicFrameLayout) view.findViewById(R.id.prtframelayout);
//        listViewv = (ListView) view.findViewById(R.id.id_main_lv_lv);
//        listViewv.offsetTopAndBottom(20);

//        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(getContext());
//        ptrLayout.setHeaderView(header);
        ptrLayout.setLastUpdateTimeRelateObject(getContext());
//        ptrLayout.setPtrHandler(new PtrDefaultHandler() {
//            @Override
//            public void onRefreshBegin(PtrFrameLayout frame) {
//                Log.d(TAG, "onRefreshBegin: header");
//            }
//        });
        ptrLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                Log.d(TAG, "checkCanDoRefresh: header");
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

            }
        });



        ptrLayout.addPtrUIHandler(new PtrUIHandler() {
            @Override
            public void onUIReset(PtrFrameLayout frame) {
                Log.d(TAG, "onUIReset: ");
            }

            @Override
            public void onUIRefreshPrepare(PtrFrameLayout frame) {
                Log.d(TAG, "onUIRefreshPrepare: ");
            }

            @Override
            public void onUIRefreshBegin(PtrFrameLayout frame) {
                Log.d(TAG, "onUIRefreshBegin: ");
                AYActivity act = (AYActivity) getActivity();
                act.handleNotifications("sendRefreshDataNotify", new JSONObject());
            }

            @Override
            public void onUIRefreshComplete(PtrFrameLayout frame, boolean isHeader) {
                Log.d(TAG, "onUIRefreshComplete: ");
            }

            @Override
            public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
//                Log.d(TAG, "onUIPositionChange: ");
            }
        });

        /*
        * footer *
        */
//        PtrClassicDefaultFooter footer = new PtrClassicDefaultFooter(getContext());
//        ptrLayout.setFooterView(footer);
        ptrLayout.setLastUpdateTimeFooterRelateObject(getContext());
        ptrLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
//                AYActivity act = (AYActivity) getActivity();
//                act.handleNotifications("sendLoadMoreDataNotify", new JSONObject());
                Log.d(TAG, "onLoadMoreBegin: footer");
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
            }
        });

//        ptrLayout.setPtrHandler(new PtrHandler2() {
//            @Override
//            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
//                return PtrDefaultHandler2.checkContentCanBePulledDown(frame, content, footer);
//            }
//
//            @Override
//            public void onLoadMoreBegin(PtrFrameLayout frame) {
//                AYActivity act = (AYActivity) getActivity();
//                act.handleNotifications("sendLoadMoreDataNotify", new JSONObject());
//            }
//
//            @Override
//            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View footer) {
//                return PtrDefaultHandler2.checkContentCanBePulledDown(frame, content, footer);
//            }
//
//            @Override
//            public void onRefreshBegin(PtrFrameLayout frame) {
//
//            }
//        });

        return view;
    }

    @Override
    public void onListItemClick (ListView parent, View view, int position, long id) {
        Log.d(TAG, "onListItemClick:" + position);

        Map<String,Integer> tmp = new HashMap<String, Integer>();
        tmp.put("position",position);
        JSONObject js = new JSONObject(tmp);

        AYActivity act = (AYActivity) getActivity();
        act.handleNotifications("didSelectedPositionNotify", js);

    }

    public void refreshOrLoadMoreComplete() {
        ptrLayout.refreshComplete();
    }

}
