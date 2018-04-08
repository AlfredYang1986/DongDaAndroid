package com.blackmirror.dongda.facade.DongdaCommonFacade.LoginSuccessCommand;

import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.facade.DongdaCommonFacade.AYDongdaCommonFacade;
import com.blackmirror.dongda.facade.DongdaCommonFacade.SQLiteProxy.AYSQLiteProxy;
import com.blackmirror.dongda.facade.DongdaCommonFacade.SQLiteProxy.DAO.AYDaoUserProfile;
import com.blackmirror.dongda.factory.AYFactoryManager;

/**
 * Created by alfredyang on 03/07/2017.
 */
public class AYUpdateLocalProfileCommand extends AYCommand {

    final private String TAG = "AYUpdateLocalProfileCommand";

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    public <Args, Result> Result excute(Args ... args) {
        AYDongdaCommonFacade f = (AYDongdaCommonFacade) AYFactoryManager.getInstance(null).
                queryInstance("facade", "DongdaCommanFacade");
        AYSQLiteProxy proxy = f.getProxy();

        long result = proxy.updateProfile((AYDaoUserProfile) args[0]);
        return (Result) Long.valueOf(result);
    }
}
