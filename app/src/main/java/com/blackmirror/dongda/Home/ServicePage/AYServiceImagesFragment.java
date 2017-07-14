package com.blackmirror.dongda.Home.ServicePage;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.fragment.AYFragment;

/**
 * Created by alfredyang on 14/7/17.
 */

public class AYServiceImagesFragment extends AYFragment {

    private final String TAG = "AYServiceImagesFragment";
    public ViewPager mViewPager;

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
        View view = inflater.inflate(R.layout.viewpage_servpage_cover, container, false);
//        mViewPager = (ViewPager)view.findViewById(R.id.viewpage_servpage_cover);
        return view;
    }

    @Override
    protected void bindingSubFragments() {
    }
}
