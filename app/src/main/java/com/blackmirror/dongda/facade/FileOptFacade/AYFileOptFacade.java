package com.blackmirror.dongda.facade.FileOptFacade;

import com.blackmirror.dongda.facade.AYFacade;
import com.blackmirror.dongda.facade.DongdaCommonFacade.SQLiteProxy.AYSQLiteProxy;
import com.blackmirror.dongda.facade.DongdaCommonFacade.SQLiteProxy.DAO.AYDaoUserProfile;
import com.blackmirror.dongda.factory.AYFactoryManager;

/**
 * Created by alfredyang on 28/06/2017.
 */
public class AYFileOptFacade extends AYFacade {

    private final String TAG = "AYFileOptFacade";

    @Override
    public String getClassTag() {
        return TAG;
    }
}
