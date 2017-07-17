package com.blackmirror.dongda.Home.ServicePage;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.Tools.AYScreenSingleton;
import com.blackmirror.dongda.fragment.AYFragment;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alfredyang on 12/7/17.
 */

public class AYServicePageBottomFragment extends AYFragment {

    private final String TAG = "AYServicePageBottomFragment";
    private TextView text_price;
    private TextView text_leastcount;

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_servpage_bottom, container, false);
        Button btn_chat = (Button)view.findViewById(R.id.servpage_botoom_chat);
        btn_chat.setCompoundDrawables(null, findImgAsSquare(R.drawable.details_icon_chat), null, null);

        text_price = (TextView)view.findViewById(R.id.servpage_bottom_price);
        text_leastcount = (TextView)view.findViewById(R.id.servpage_bottom_leastcount);
        return view;
    }

    @Override
    protected void bindingSubFragments() {
    }


    public Drawable findImgAsSquare(int id) {
        Drawable drawable = ContextCompat.getDrawable(getContext(),id);
        float screenScale = (new AYScreenSingleton()).getScreenDensity(getContext());
//        Log.d(TAG, "findImgAsSquare: "+ screenScale);
        drawable.setBounds(0, (int)(6*screenScale), (int)(26*screenScale), (int)(26*screenScale));
        return drawable;
    }

    public void setServPageBottomInfo (JSONObject args) {
        try {

            int least_hours = args.getInt("least_hours");
            int least_times = args.getInt("least_times");
            double price = args.getDouble("price");

            if (least_hours != 0) {
                text_price.setText("¥ "+ price + "/小时");
                text_leastcount.setText("至少预订" + least_hours + "小时");
            } else if(least_times != 0) {
                text_price.setText("¥ "+ price + "/次");
                text_leastcount.setText("至少预订" + least_times + "次");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
