package com.blackmirror.dongda.Home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.AYTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * Created by alfredyang on 3/7/17.
 */

public class AYHomeListServAdapter extends BaseAdapter {
    private LayoutInflater itemInflater;
    private JSONArray serviceData;

    public AYHomeListServAdapter(Context context, JSONArray querydata) {
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
            return 1;
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

        if (position == 0 && convertView == null) {
            convertView = itemInflater.inflate(R.layout.cell_home_hello, null);
            convertView.setTag("0");
            String dateStr = getTimeOnString();
            ((TextView)convertView.findViewById(R.id.hello_title)).setText(dateStr);
        }
        else if (position == 0) {
            if (convertView.getTag().equals("1")) {
                convertView = itemInflater.inflate(R.layout.cell_home_hello, null);
                convertView.setTag("0");
                String dateStr = getTimeOnString();
                ((TextView)convertView.findViewById(R.id.hello_title)).setText(dateStr);
            }
        }
        else if (convertView == null) {

            JSONObject tmp = null;
            try {
                tmp = serviceData.getJSONObject(position-1);

                convertView = itemInflater.inflate(R.layout.cell_homelist_serv, null);
                convertView.setTag("1");
                ((ImageView)convertView.findViewById(R.id.img_cover)).setImageResource(R.drawable.default_image);
                ((TextView)convertView.findViewById(R.id.text_title)).setText((String)tmp.get("title"));
                ((TextView)convertView.findViewById(R.id.text_addr)).setText((String)tmp.get("address"));
                ((TextView)convertView.findViewById(R.id.text_price)).setText("¥" + tmp.get("price"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            if (convertView.getTag().equals("0")) {
                convertView = itemInflater.inflate(R.layout.cell_homelist_serv, null);
                convertView.setTag("1");
            }
            JSONObject tmp = null;
            try {
                tmp = serviceData.getJSONObject(position-1);
                ((ImageView)convertView.findViewById(R.id.img_cover)).setImageResource(R.drawable.default_image);
                ((TextView)convertView.findViewById(R.id.text_title)).setText((String)tmp.get("title"));
                ((TextView)convertView.findViewById(R.id.text_addr)).setText((String)tmp.get("address"));
                ((TextView)convertView.findViewById(R.id.text_price)).setText("¥" + tmp.get("price"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return convertView;
    }

    public JSONArray changeQueryData () {
        return serviceData;
    }

    private String getTimeOnString () {
        String timeOnStr;
        String hourStr = (new AYTools()).getTimeStrHours();
        Log.d(TAG, "getTimeStrHours: "+ hourStr);
        int hour = Integer.parseInt(hourStr);
        if (hour >= 6 && hour < 12) {
            timeOnStr = "上午好";
        } else if (hour >= 12 && hour < 13){
            timeOnStr = "中午好";
        } else if (hour >= 13 && hour < 18){
            timeOnStr = "下午好";
        } else if (hour >= 18 || hour < 24){
            timeOnStr = "晚上好";
        } else if (hour < 6){
            timeOnStr = "夜深了，注意休息";
        } else {
            timeOnStr = "获取时间错误";
        }
        return timeOnStr;
    }
}
