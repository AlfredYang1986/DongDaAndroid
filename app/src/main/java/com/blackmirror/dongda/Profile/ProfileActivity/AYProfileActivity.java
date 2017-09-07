package com.blackmirror.dongda.Profile.ProfileActivity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.fragment.AYFragment;
import com.blackmirror.dongda.fragment.DefaultFragment.AYNavBarFragment;
import com.blackmirror.dongda.fragment.DefaultFragment.AYTabBarFragment;

/**
 * Created by alfredyang on 6/9/17.
 */

public class AYProfileActivity extends AYActivity {

//    private ListView profileListView;
//    private ListFragment profileListFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        RelativeLayout rl = new RelativeLayout(this);
//        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//        rl.setLayoutParams(rlp);
//
//        profileListView = new ListView(this);
    }

    @Override
    protected void bindingFragments() {

        FragmentTransaction task = mFragmentManage.beginTransaction();
        task.add(R.id.activity_profile, (AYFragment)this.fragments.get("frag_navbar"));
        task.add(R.id.activity_profile, (AYFragment)this.fragments.get("frag_tabbar"));
        task.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setTitleTextInvisible();
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setLeftBtnInvisible();
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setRightBtnInvisible();
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setBottomLineInvisible();
        ((AYTabBarFragment)this.fragments.get("frag_tabbar")).setTabFocusOptionWithIndex(3);
    }

}
