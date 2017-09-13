package com.blackmirror.dongda.Profile.ProfileActivity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.ListView;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.fragment.AYFragment;
import com.blackmirror.dongda.fragment.DefaultFragment.AYNavBarFragment;
import com.blackmirror.dongda.fragment.DefaultFragment.AYTabBarFragment;

/**
 * Created by alfredyang on 6/9/17.
 */

public class AYProfileActivity extends AYActivity {

    private ListView profileListView;
//    private ListFragment profileListFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        RelativeLayout rl = new RelativeLayout(this);
//        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//        rl.setLayoutParams(rlp);
//
//        RelativeLayout.LayoutParams rlp_ttv = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        rlp_ttv.setMargins(50,100,0,0);
//        TextView tTV = new TextView(this);
//        tTV.setLayoutParams(rlp_ttv);
//        tTV.setText("Text Done");
//        rl.addView(tTV);

//        addContentView(rl, rlp);
//        setContentView(rl);
//        setContentView(rl, rlp);
//        profileListView = new ListView(this);
//        AYProfileAdapter pAdapter = new AYProfileAdapter(this, null);
//        profileListView.setAdapter(pAdapter);
//        addContentView(profileListView, rlp);

//        LinearLayout layout =new LinearLayout(this);
//        layout.setOrientation(LinearLayout.VERTICAL);
//        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
//        mTextView =new TextView(this);
//        mTextView.setText("Hello World");
//        layout.addView(mTextView);
//        setContentView(layout);

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
