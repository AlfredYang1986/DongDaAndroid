package com.blackmirror.dongda.Order.OrderModeCommon;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.fragment.AYFragment;
import com.blackmirror.dongda.fragment.DefaultFragment.AYNavBarFragment;
import com.blackmirror.dongda.fragment.DefaultFragment.AYTabBarFragment;

/**
 * Created by alfredyang on 11/9/17.
 */

public class AYOrderCommonActivity extends AYActivity {

    RelativeLayout mainLaypot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_common);

        mainLaypot = new RelativeLayout(this);
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mainLaypot.setLayoutParams(rlp);

        RelativeLayout.LayoutParams rlp_ttv = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rlp_ttv.setMargins(20,50,0,0);
        TextView tTV = new TextView(this);
        tTV.setLayoutParams(rlp_ttv);
        tTV.setText("日程");
        mainLaypot.addView(tTV);
        addContentView(mainLaypot, rlp);
    }

    @Override
    protected void bindingFragments() {

        FragmentTransaction task = mFragmentManage.beginTransaction();
        task.add(R.id.activity_order_common, (AYFragment)this.fragments.get("frag_navbar"));
        task.add(R.id.activity_order_common, (AYFragment)this.fragments.get("frag_tabbar"));
        task.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setTitleTextInvisible();
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setLeftBtnInvisible();
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setRightBtnInvisible();
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setBottomLineInvisible();
        ((AYTabBarFragment)this.fragments.get("frag_tabbar")).setTabFocusOptionWithIndex(2);
    }
}
