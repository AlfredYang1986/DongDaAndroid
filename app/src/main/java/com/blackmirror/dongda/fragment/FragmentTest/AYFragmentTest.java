package com.blackmirror.dongda.fragment.FragmentTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.blackmirror.dongda.R;
import com.blackmirror.dongda.fragment.AYFragment;

/**
 * Created by alfredyang on 27/06/2017.
 */
public class AYFragmentTest extends AYFragment {

    private final String TAG = "AYFragmentTest";

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_test, container, false);
        return v;
    }

    @Override
    protected void bindingSubFragments() {
        FragmentManager fm = this.getFragmentManager();
    }
}
