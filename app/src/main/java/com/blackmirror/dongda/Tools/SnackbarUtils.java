package com.blackmirror.dongda.Tools;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.blackmirror.dongda.R;

/**
 * Created by Ruge on 2018-04-23 上午10:34
 */
public class SnackbarUtils {
    public static void show(View view, String content,String actionMsg){
        Snackbar snackbar=Snackbar.make(view,content,Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(actionMsg, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        snackbar.getView().setBackgroundColor(AYApplication.getAppConext().getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }

    public static void show(View view, String content){
        Snackbar snackbar=Snackbar.make(view,content,Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("关闭", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        snackbar.getView().setBackgroundColor(AYApplication.getAppConext().getResources().getColor(R.color.sys_bar_white));
        View v = snackbar.getView();
        ((TextView) v.findViewById(R.id.snackbar_text)).setTextColor(AYApplication.getAppConext().getResources().getColor(R.color.text_black));
        snackbar.show();
    }
}
