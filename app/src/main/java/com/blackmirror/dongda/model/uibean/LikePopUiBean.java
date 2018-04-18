package com.blackmirror.dongda.model.uibean;

import com.blackmirror.dongda.model.BaseUiBean;
import com.blackmirror.dongda.model.serverbean.LikePopServerBean;

/**
 * Created by Ruge on 2018-04-16 下午6:13
 */
public class LikePopUiBean extends BaseUiBean {
    public LikePopUiBean(LikePopServerBean bean) {
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
