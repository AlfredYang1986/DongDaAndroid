package com.blackmirror.dongda.facade.DongdaCommonFacade.LoginSuccessCommand;

import com.blackmirror.dongda.command.AYCommand;
import com.blackmirror.dongda.facade.DongdaCommonFacade.AYDongdaCommonFacade;
import com.blackmirror.dongda.factory.AYFactoryManager;

/**
 * Created by alfredyang on 27/05/2017.
 */
public class AYQueryDAOProxyCommand extends AYCommand {

    final private String TAG = "AYQueryDAOProxyCommand";

    @Override
    public String getClassTag() {
        return TAG;
    }

    @Override
    public <Args, Result> Result execute(Args ... args) {
        AYDongdaCommonFacade f = (AYDongdaCommonFacade) AYFactoryManager.getInstance(null).
                                        queryInstance("facade", "DongdaCommanFacade");
        return (Result) f.getProxy();
    }
}
