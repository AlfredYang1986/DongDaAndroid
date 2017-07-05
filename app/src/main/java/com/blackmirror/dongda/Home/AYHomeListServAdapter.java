package com.blackmirror.dongda.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.ServiceData;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by alfredyang on 3/7/17.
 */

public class AYHomeListServAdapter extends BaseAdapter {
    private LayoutInflater itemInflater;
    private ArrayList serviceData;

    public AYHomeListServAdapter(Context context) {
        super();
        itemInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount (){
        serviceData = ServiceData.getDataInstance().getServDataWithArgs();
        return serviceData.size();
    }

    @Override
    public Object getItem(int position) {
        return serviceData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Map<String,Object> tmp = (Map<String, Object>) serviceData.get(position);

        if (convertView == null) {
            convertView = itemInflater.inflate(R.layout.cell_homelist_serv, null);
            ((ImageView)convertView.findViewById(R.id.img_cover)).setImageResource(R.drawable.default_image);
            ((TextView)convertView.findViewById(R.id.text_title)).setText((String)tmp.get("service_title"));
            ((TextView)convertView.findViewById(R.id.text_addr)).setText((String)tmp.get("service_addr"));
            ((TextView)convertView.findViewById(R.id.text_price)).setText("¥" + (String)tmp.get("service_price"));
        }

        return convertView;
    }

    public ArrayList changeQueryData () {
        return serviceData;
    }
}
