package com.blackmirror.dongda.Message.MessageList;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
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

public class AYMessageListActivity extends AYActivity {

    RelativeLayout mainLaypot;
    LayoutInflater mainInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagelist);

        mainInflater = LayoutInflater.from(this);

        mainLaypot = new RelativeLayout(this);
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mainLaypot.setLayoutParams(rlp);

//        RelativeLayout.LayoutParams rlp_list = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//        rlp_list.setMargins(0, 100, 0, 0);
//        ListView messageListView = new ListView(this);
//        AYMessageListAdapter adapter = new AYMessageListAdapter(this, null);
//        messageListView.setAdapter(adapter);
//        messageListView.setLayoutParams(rlp_list);
//        mainLaypot.addView(messageListView);

        RelativeLayout.LayoutParams rlp_ttv = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rlp_ttv.setMargins(20,50,0,0);
        TextView tTV = new TextView(this);
        tTV.setLayoutParams(rlp_ttv);
        tTV.setText("消息");
        mainLaypot.addView(tTV);
        addContentView(mainLaypot, rlp);

    }

    @Override
    protected void onStart() {
        super.onStart();
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setTitleTextInvisible();
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setLeftBtnInvisible();
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setRightBtnInvisible();
        ((AYNavBarFragment)this.fragments.get("frag_navbar")).setBottomLineInvisible();
        ((AYTabBarFragment)this.fragments.get("frag_tabbar")).setTabFocusOptionWithIndex(1);
    }

    @Override
    protected void bindingFragments() {
        FragmentTransaction task = mFragmentManage.beginTransaction();
        task.add(R.id.activity_messagelist, (AYFragment)this.fragments.get("frag_navbar"));
        task.add(R.id.activity_messagelist, (AYFragment)this.fragments.get("frag_tabbar"));
        task.commit();
    }
}
