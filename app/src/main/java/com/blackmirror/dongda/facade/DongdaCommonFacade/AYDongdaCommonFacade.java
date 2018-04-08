package com.blackmirror.dongda.facade.DongdaCommonFacade;

import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.facade.DongdaCommonFacade.SQLiteProxy.AYSQLiteProxy;
import com.blackmirror.dongda.facade.DongdaCommonFacade.SQLiteProxy.DAO.AYDaoUserProfile;
import com.blackmirror.dongda.factory.AYFactoryManager;

/**
 * Created by alfredyang on 26/05/2017.
 */
public class AYDongdaCommonFacade extends AYFacade {
    private final String TAG = "AYDongdaCommonFacade";

    private AYSQLiteProxy proxy = new AYSQLiteProxy(AYFactoryManager.getInstance(null).getContext());

    public AYSQLiteProxy getProxy() {
        return proxy;
    }

    @Override
    public String getClassTag() {
        return TAG;
    }

    public AYDaoUserProfile currentUser() {
        return proxy.currentProfile();
    }
}