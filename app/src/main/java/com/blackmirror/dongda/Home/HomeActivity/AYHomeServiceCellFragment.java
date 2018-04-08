package com.blackmirror.dongda.Home.HomeActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackmirror.dongda.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alfredyang on 7/7/17.
 */

public class AYHomeServiceCellFragment extends Fragment {

    private ImageView coverImage;
    private TextView textTitle;
    private TextView textAddr;
    private TextView textPrice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cell_homelist_serv, container, false);

        coverImage = (ImageView)view.findViewById(R.id.img_cover);
        textTitle = (TextView)view.findViewById(R.id.text_title);
        textAddr = (TextView)view.findViewById(R.id.text_addr);
        textPrice = (TextView)view.findViewById(R.id.text_price);

        return view;
    }

    public void setCellInfo (JSONObject args) {
        try {
            coverImage.setImageResource(R.drawable.default_image);
            textTitle.setText((String)args.get("title"));
            textAddr.setText((String)args.get("address"));
            textPrice.setText("¥" + args.get("price"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
