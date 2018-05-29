package com.blackmirror.dongda.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.blackmirror.dongda.R;

/**
 * Created by Ruge on 2018-04-18 下午6:05
 */
public class CustomDialog extends Dialog {
    protected Context context;
    private boolean cancelTouchout;
    private boolean cancelable;
    private boolean focusable;
    private View view;
    protected int gravity;
    protected int width = Integer.MIN_VALUE;
    protected int offsetY = Integer.MIN_VALUE;

    protected CustomDialog(Builder builder, int resStyle) {
        super(builder.context, resStyle);
        view = builder.view;
        context = builder.context;
        cancelTouchout = builder.cancelTouchout;
        cancelable = builder.cancelable;
        gravity = builder.gravity;
        width = builder.width;
        offsetY = builder.offsetY;
        cancelable = builder.cancelable;
        focusable = builder.focusable;
    }


    protected CustomDialog(Builder builder) {
        super(builder.context);
        context = builder.context;
        cancelTouchout = builder.cancelTouchout;
        view = builder.view;
        gravity = builder.gravity;
        width = builder.width;
        offsetY = builder.offsetY;
        cancelable = builder.cancelable;
        focusable = builder.focusable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(view);
        setCanceledOnTouchOutside(cancelTouchout);
        setCancelable(cancelable);

        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        //lp.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

        lp.gravity = gravity;

        if (width != Integer.MIN_VALUE){
            lp.width = width;
        }
        if (offsetY != Integer.MIN_VALUE && gravity == Gravity.BOTTOM){
            lp.y = offsetY;
        }

        if (!focusable){
            win.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        }

        win.setAttributes(lp);

    }

    public static final class Builder {
        private Context context;
        private boolean cancelTouchout;
        private boolean cancelable = true;
        private boolean focusable = true;
        private View view;
        private int gravity = Gravity.CENTER;
        private int width = Integer.MIN_VALUE;
        private int offsetY = Integer.MIN_VALUE;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setCancelTouchOut(boolean cancelTouchout) {
            this.cancelTouchout = cancelTouchout;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setFocusable(boolean focusable) {
            this.cancelable = focusable;
            return this;
        }

        public Builder view(int resView) {
            view = LayoutInflater.from(context).inflate(resView, null);
            return this;
        }

        public Builder view(View view) {
            this.view = view;
            return this;
        }


        public Builder addViewOnclick(int viewRes, View.OnClickListener listener) {
            view.findViewById(viewRes).setOnClickListener(listener);
            return this;
        }


        public Builder setText(int viewRes, int stringRes) {
            ((TextView) view.findViewById(viewRes)).setText(stringRes);
            return this;
        }

        public Builder setText(int viewRes, String str) {
            ((TextView) view.findViewById(viewRes)).setText(str);
            return this;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setOffsetY(int offsetY) {
            this.offsetY = offsetY;
            return this;
        }

        public CustomDialog build() {
            return new CustomDialog(this, R.style.CustomDialog);
        }

        public CustomDialog build(int style) {
            return new CustomDialog(this, style);
        }

    }
}
