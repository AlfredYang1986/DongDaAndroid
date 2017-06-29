package com.blackmirror.dongda.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blackmirror.dongda.R;

import static android.content.ContentValues.TAG;

/**
 * Created by alfredyang on 29/6/17.
 */

public class AYNavBarFragment extends AYFragment {

    private final String TAG = "AYNavBarFragment";

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected void bindingSubFragments() {

    }

    private Button nav_left_btn;
    private TextView text_center;
    private Button nav_right_btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_navbar, container, false);
//        text_center = (TextView) view.findViewById(R.id.title_center);
//        text_center.setText("标题");
//        text_center.setVisibility(text_center.GONE);
//
//        nav_left_btn = (Button) view.findViewById(R.id.nav_left_btn);
//        setLeftBtnTextWithString("返回");

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //定义了一个RelativeLayout ，LayoutParams 是控件属性
//        RelativeLayout.LayoutParams.MATCH_PARENT
//        FrameLayout.LayoutParams params_left_btn = new FrameLayout.LayoutParams(500, 244);
//        params_left_btn.gravity = Gravity.CENTER;

        RelativeLayout left_layout = new RelativeLayout(getActivity());
        left_layout.setPadding(8, 8, 8, 8);
        left_layout.setBackgroundColor(Color.parseColor("#F5F5DC"));

        FrameLayout.LayoutParams params_btn_back = new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        //定义了一个Button，
        nav_left_btn = new Button(getActivity());
        nav_left_btn.setBackgroundResource(R.drawable.nav_icon_back_black);
        nav_left_btn.setLayoutParams(params_btn_back);

        //Layout中添加Button
        left_layout.addView(nav_left_btn);
        //View中添加layout
        getActivity().addContentView(left_layout, params_btn_back);

        nav_left_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: nav_left_btn");
            }
        });
    }

    public void setLeftBtnWithImageId(int id) {
        nav_left_btn.setBackgroundResource(id);
    }

    public void setRightBtnWithImageId(int id) {
        nav_left_btn.setBackgroundResource(id);
    }

    public void setTitleTextWithString(String string) {
        text_center.setText(string);
    }
}
