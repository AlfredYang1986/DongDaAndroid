package com.blackmirror.dongda.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.ui.activity.AYActivity;

import org.json.JSONObject;

/**
 * Created by alfredyang on 5/7/17.
 */

public class AYHomeSegFragment extends AYFragment {

    private Button btn_item_frist;
    private Button btn_item_second;
    private ImageButton btn_item_filter;

    private final String TAG = "AYHomeSegFragment";

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected void bindingSubFragments() {

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homeseg, container, false);
        btn_item_frist = (Button)view.findViewById(R.id.homeseg_item_frist);
        btn_item_frist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFocusItemWithIndex(0);
            }
        });
        btn_item_second = (Button)view.findViewById(R.id.homeseg_item_second);
        btn_item_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFocusItemWithIndex(1);
            }
        });
        btn_item_filter = (ImageButton)view.findViewById(R.id.homeseg_btn_filter);
        return view;
    }

    public void changeFocusItemWithIndex(int index) {
        switch (index) {
            case 0 : {
                btn_item_frist.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_bottom_border));
                btn_item_second.setBackgroundColor(getResources().getColor(R.color.colorAlpha));

                AYActivity act = (AYActivity) this.getActivity();
                act.handleNotifications("didSegFristItemClickNotify", new JSONObject());
            }
                break;
            case 1 : {
                btn_item_second.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_bottom_border));
                btn_item_frist.setBackgroundColor(getResources().getColor(R.color.colorAlpha));

                AYActivity act = (AYActivity) this.getActivity();
                act.handleNotifications("didSegSecondItemClickNotify", new JSONObject());
            }
                break;
            default:
                break;
        }
    }
}
