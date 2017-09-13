package com.blackmirror.dongda.fragment.DefaultFragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.blackmirror.dongda.Home.HomeActivity.AYHomeActivity;
import com.blackmirror.dongda.Message.MessageList.AYMessageListActivity;
import com.blackmirror.dongda.Order.OrderModeCommon.AYOrderCommonActivity;
import com.blackmirror.dongda.Profile.ProfileActivity.AYProfileActivity;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.AYScreenSingleton;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.fragment.AYFragment;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alfredyang on 29/6/17.
 */

public class AYTabBarFragment extends AYFragment {

    private Button btn_home;
    private Button btn_message;
    private Button btn_schedule;
    private Button btn_profile;

    private final String TAG = "AYTabBarFragment";

    public Drawable findImgAsSquare(int id) {
        Drawable drawable = ContextCompat.getDrawable(getContext(),id);
        float screenScale = (new AYScreenSingleton()).getScreenDensity(getContext());
        Log.d(TAG, "findImgAsSquare: "+screenScale);
        drawable.setBounds(0, (int)(6*screenScale), (int)(28*screenScale), (int)(28*screenScale));
        return drawable;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tabbar, container, false);
        btn_home = (Button) view.findViewById(R.id.tabbar_home);
        btn_home.setCompoundDrawables(null, findImgAsSquare(R.drawable.tab_icon_home_normal), null, null);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: btn_home");
                if ((getContext().getClass().getSimpleName()).equals("AYHomeActivity")) {
                    return;
                }
                Intent intent = new Intent(getContext(), AYHomeActivity.class);
                startActivity(intent);
                ((Activity) getContext()).overridePendingTransition(0, 0);
            }
        });

        btn_message = (Button)view.findViewById(R.id.tabbar_message);
        btn_message.setCompoundDrawables(null, findImgAsSquare(R.drawable.tab_icon_message_normal), null, null);
        btn_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: btn_message");
                if ((getContext().getClass().getSimpleName()).equals("AYMessageListActivity")) {
                    return;
                }
                Intent intent = new Intent(getContext(), AYMessageListActivity.class);
                startActivity(intent);
                ((Activity) getContext()).overridePendingTransition(0, 0);
            }
        });

        btn_schedule = (Button)view.findViewById(R.id.tabbar_schedule);
        btn_schedule.setCompoundDrawables(null, findImgAsSquare(R.drawable.tab_icon_schedule_normal), null, null);
        btn_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: btn_schedule");
                if ((getContext().getClass().getSimpleName()).equals("AYOrderCommonActivity")) {
                    return;
                }
                Intent intent = new Intent(getContext(), AYOrderCommonActivity.class);
                startActivity(intent);
                ((Activity) getContext()).overridePendingTransition(0, 0);
            }
        });

        btn_profile = (Button)view.findViewById(R.id.tabbar_profile);
        btn_profile.setCompoundDrawables(null, findImgAsSquare(R.drawable.tab_icon_user_normal), null, null);
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: btn_profile");
                if ((getContext().getClass().getSimpleName()).equals("AYProfileActivity")) {
                    return;
                }
                Intent intent = new Intent(getContext(), AYProfileActivity.class);
                startActivity(intent);
                ((Activity)getContext()).overridePendingTransition(0, 0);
            }
        });

        return view;
    }

    @Override
    protected void bindingSubFragments() {

    }

    private void setBtnFocus (Button focusBtn, int focusId) {
        focusBtn.setCompoundDrawables(null, findImgAsSquare(focusId), null, null);
        focusBtn.setTextColor(getResources().getColor(R.color.colorTheme));
    }

    public void setTabFocusOptionWithIndex(int index) {
        switch (index) {
            case 0 : {
                setBtnFocus(btn_home, R.drawable.tab_icon_home_select);
            }
            break;
            case 1 : {
                setBtnFocus(btn_message, R.drawable.tab_icon_message_select);
            }
            break;
            case 2 : {
                setBtnFocus(btn_schedule, R.drawable.tab_icon_schedule_select);
            }
            break;
            case 3 : {
                setBtnFocus(btn_profile, R.drawable.tab_icon_user_select);
            }
            break;
            default:
                break;
        }
    }

    private void changeFocusTabItemWithIndex(int index) {
        JSONObject args = new JSONObject();
//        args.put("args",index);
        try {
            args.put("args", index);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AYActivity act = (AYActivity) this.getActivity();
        act.handleNotifications("didChangeTabItemNotify", args);
    }
}
