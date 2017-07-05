package com.blackmirror.dongda.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.blackmirror.dongda.R;
import com.blackmirror.dongda.fragment.AYFragment;

/**
 * Created by alfredyang on 5/7/17.
 */

public class AYHomeSegFragment extends AYFragment {

    private Button btn_item_first;
    private Button btn_item_second;

    private final String TAG = "AYHomeSegFragment";

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    protected void bindingSubFragments() {

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homeseg, container, false);

        return view;
    }
}
