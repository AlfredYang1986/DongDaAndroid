package com.blackmirror.dongda.Landing;

import android.support.v4.app.FragmentManager;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.controllers.AYActivity;
import com.blackmirror.dongda.fragment.AYFragment;

/**
 * Created by alfredyang on 29/06/2017.
 */
public class LoginSuccessTmpActivity extends AYActivity {
    final static String TAG = "Login Success Tmp Activity";

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected void bindingFragments()  {
        FragmentManager fm = getSupportFragmentManager();
        AYFragment f = (AYFragment)this.fragments.get("frag_test");
        fm.beginTransaction().add(R.id.login_success_container, f).commit();
    }
}
