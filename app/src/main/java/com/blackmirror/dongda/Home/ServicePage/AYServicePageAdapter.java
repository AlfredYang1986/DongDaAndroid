package com.blackmirror.dongda.Home.ServicePage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by alfredyang on 14/7/17.
 */

public class AYServicePageAdapter extends BaseAdapter {
    private LayoutInflater itemInflater;
    private JSONArray serviceData;

    public AYServicePageAdapter(Context context, JSONArray querydata) {
        super();
        serviceData = querydata;
        itemInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /* 重置数据 刷新视图 */
    public void setQueryData (JSONArray querydata) {
        serviceData = querydata;
    }
    public void refreshList () {
        notifyDataSetChanged();
    }

    @Override
    public int getCount (){
        if (serviceData == null) {
            return 2;
        } else
            return serviceData.length() + 1;
    }

    @Override
    public Object getItem(int position) {
        Object item = null;
        try {
            item = serviceData.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        return convertView;
    }
}
