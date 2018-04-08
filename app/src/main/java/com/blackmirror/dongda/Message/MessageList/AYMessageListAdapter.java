package com.blackmirror.dongda.Message.MessageList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.blackmirror.dongda.R;

import org.json.JSONArray;

/**
 * Created by alfredyang on 11/9/17.
 */

public class AYMessageListAdapter extends BaseAdapter {

    JSONArray querydata;
    private LayoutInflater itemInflater;

    public AYMessageListAdapter(Context context, JSONArray args) {
        super();
        querydata = args;
        itemInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
//        return querydata.length();
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = itemInflater.inflate(R.layout.cell_messagelist, parent);
        }
        return convertView;
    }
}
