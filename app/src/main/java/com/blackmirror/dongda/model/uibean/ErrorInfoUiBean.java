package com.blackmirror.dongda.model.uibean;

import com.blackmirror.dongda.model.BaseUiBean;
import com.blackmirror.dongda.model.serverbean.ErrorInfoServerBean;

/**
 * Created by Ruge on 2018-04-20 下午6:38
 */
public class ErrorInfoUiBean extends BaseUiBean {
    public ErrorInfoUiBean(ErrorInfoServerBean bean) {
        if (bean!=null && bean.error!=null){
            code=bean.error.code;
            message=bean.error.message;
        }
    }
}
