package com.blackmirror.dongda.model.uibean;

import com.blackmirror.dongda.model.BaseUiBean;
import com.blackmirror.dongda.model.serverbean.LikePushServerBean;

/**
 * Created by Ruge on 2018-04-16 下午6:13
 */
public class LikePushUiBean extends BaseUiBean {
    public LikePushUiBean(LikePushServerBean bean) {
        if (bean!=null && "ok".equals(bean.status)){
            isSuccess=true;
        }else {
            if (bean!=null && bean.error!=null){
                code=bean.error.code;
                message=bean.error.message;
            }
        }
    }
}
