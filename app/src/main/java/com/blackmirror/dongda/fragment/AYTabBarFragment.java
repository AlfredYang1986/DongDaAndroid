package com.blackmirror.dongda.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.blackmirror.dongda.Tools.AYScreenSingleton;
import com.blackmirror.dongda.fragment.AYFragment;
import com.blackmirror.dongda.R;

import static android.content.ContentValues.TAG;

/**
 * Created by alfredyang on 29/6/17.
 */

public class AYTabBarFragment extends AYFragment {

    private Button btn_home;
    private Button btn_message;
    private Button btn_schedule;
    private Button btn_profile;

    private int holder_btn_image_normal_id;
    private Button btn_holder;

    private final String TAG = "AYTabBarFragment";

    @Override
    public String getClassTag() {
        return TAG;
    }

    public Drawable findImgAsSquare(int id) {
        Drawable drawable = ContextCompat.getDrawable(getContext(),id);
        float screenScale = getContext().getResources().getDisplayMetrics().density;
        screenScale = (new AYScreenSingleton()).getScreenDensity(getContext());
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
//                Activity ac = new MainActivity();
//                if (getActivity().getClass() == ac.getClass()) {
//                    Log.d(TAG, "onClick: no action");
//                    return;
//                }
                changeFocusBtnWith(R.drawable.tab_icon_home_select, R.drawable.tab_icon_home_normal, btn_home);
            }
        });

        btn_message = (Button)view.findViewById(R.id.tabbar_message);
        btn_message.setCompoundDrawables(null, findImgAsSquare(R.drawable.tab_icon_message_normal), null, null);
        btn_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: btn_message");
                changeFocusBtnWith(R.drawable.tab_icon_message_select, R.drawable.tab_icon_message_normal, btn_message);
            }
        });

        btn_schedule = (Button)view.findViewById(R.id.tabbar_schedule);
        btn_schedule.setCompoundDrawables(null, findImgAsSquare(R.drawable.tab_icon_schedule_normal), null, null);
        btn_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: btn_schedule");
                changeFocusBtnWith(R.drawable.tab_icon_schedule_select, R.drawable.tab_icon_schedule_normal, btn_schedule);
            }
        });

        btn_profile = (Button)view.findViewById(R.id.tabbar_profile);
        btn_profile.setCompoundDrawables(null, findImgAsSquare(R.drawable.tab_icon_user_normal), null, null);
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: btn_profile");
                changeFocusBtnWith(R.drawable.tab_icon_user_select, R.drawable.tab_icon_user_normal, btn_profile);
            }
        });

        setTabFocusOptionWithIndex(0);
        return view;
    }

    @Override
    protected void bindingSubFragments() {

    }

    private void changeFocusBtnWith(int focusImageId, int normalImageId, Button focusBtn) {
        btn_holder.setCompoundDrawables(null, findImgAsSquare(holder_btn_image_normal_id), null, null);
        btn_holder.setTextColor(getResources().getColor(R.color.colorBlack));

        focusBtn.setCompoundDrawables(null, findImgAsSquare(focusImageId), null, null);
        focusBtn.setTextColor(getResources().getColor(R.color.colorTheme));

        btn_holder = focusBtn;
        holder_btn_image_normal_id = normalImageId;
    }
    private void saveFocusArgs (Button focusBtn, int holderId, int focusId) {
        focusBtn.setCompoundDrawables(null, findImgAsSquare(focusId), null, null);
        focusBtn.setTextColor(getResources().getColor(R.color.colorTheme));
        btn_holder = focusBtn;
        holder_btn_image_normal_id = holderId;
    }

    public void setTabFocusOptionWithIndex(int index) {
        switch (index) {
            case 0 : {
                saveFocusArgs(btn_home, R.drawable.tab_icon_home_normal, R.drawable.tab_icon_home_select);
            }
            break;
            case 1 : {
                saveFocusArgs(btn_message, R.drawable.tab_icon_message_normal, R.drawable.tab_icon_message_select);
            }
            break;
            case 2 : {
                saveFocusArgs(btn_schedule, R.drawable.tab_icon_schedule_normal, R.drawable.tab_icon_schedule_select);
            }
            break;
            case 3 : {
                saveFocusArgs(btn_profile, R.drawable.tab_icon_user_normal, R.drawable.tab_icon_user_select);
            }
            break;
            default:
                break;
        }
    }
}
