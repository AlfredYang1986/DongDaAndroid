package com.blackmirror.dongda.Profile.ProfileActivity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.blackmirror.dongda.R;

import org.json.JSONArray;

/**
 * Created by alfredyang on 6/9/17.
 */

public class AYProfileAdapter extends BaseAdapter {

    public JSONArray querydata;
    private LayoutInflater itemInflater;

    public AYProfileAdapter(Context context, JSONArray object) {
        super();
        querydata = object;
        itemInflater = LayoutInflater.from(context);
    }

    public void changeQueryData(JSONArray object) {
        querydata = object;
        Log.d(this.getClass().getSimpleName(), "changeQueryData: " + querydata);
    }

    public void refreshList() {
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 4;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (i == 0) {
            view = itemInflater.inflate(R.layout.cell_profile_head, null);

        } else {
            view = itemInflater.inflate(R.layout.cell_profile_row, null);
        }
        return view;
    }

//    HeaderViewListAdapter

}
