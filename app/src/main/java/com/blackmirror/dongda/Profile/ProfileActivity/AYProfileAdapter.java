package com.blackmirror.dongda.Profile.ProfileActivity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;

import org.json.JSONObject;

/**
 * Created by alfredyang on 6/9/17.
 */

public class AYProfileAdapter extends BaseAdapter {

    public JSONObject querydata;

    public AYProfileAdapter(Context context, JSONObject object) {
        super();
        querydata = object;
    }

    public void changeQueryData(JSONObject object) {
        querydata = object;
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
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
//        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        if (i == 0) {
            view = new View(view.getContext());
            TextView nameText = new TextView(viewGroup.getContext());
            nameText.setText("Name");
            viewGroup.addView(nameText);

            ImageView photoView = new ImageView(viewGroup.getContext());
            photoView.setImageResource(R.drawable.default_image);
            viewGroup.addView(photoView);

            return viewGroup;

        } else {

        }
        return view;
    }

}
