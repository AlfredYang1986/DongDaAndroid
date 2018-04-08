package com.blackmirror.dongda.fragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.blackmirror.dongda.AY.AYSysHelperFunc;
import com.blackmirror.dongda.AY.AYSysNotificationHandler;
import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.facade.AYFacade;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by alfredyang on 5/9/17.
 */

public class AYViewGroup extends ViewGroup implements AYSysNotificationHandler {

    public Map<String, AYCommand> cmds;
    public Map<String, AYFacade> facades;
    public Map<String, AYFragment> fragments;

    public AYViewGroup(Context context) {
        super(context);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        unRegisterCallback();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        registerCallback();
    }

    @Override
    public String getClassTag() {
        return "AYViewGroup";
    }

    protected void registerCallback() {
        if (this.facades == null) return;

        for (AYFacade f : this.facades.values()) {
            f.registerActivity(this);
        }
    }

    protected void unRegisterCallback() {
        if (this.facades == null) return;

        for (AYFacade f : this.facades.values()) {
            f.unRegisterActivity(this);
        }
    }

    @Override
    public Boolean handleNotifications(String name, JSONObject args) {
        return AYSysHelperFunc.getInstance().handleNotifications(name, args, this);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        for(int index = 0; index < getChildCount(); index++){
            View v = getChildAt(index);
            v.layout(l, t, r, b);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        caculateWidthAndPadding(MeasureSpec.getSize(widthMeasureSpec));
//        for(int index = 0; index < getChildCount(); index++){
//
//            child.measure(MeasureSpec.makeMeasureSpec(childSize, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(childSize, MeasureSpec.AT_MOST));
//        }
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }

//    protected abstract void bindingSubFragments();
}
