package com.blackmirror.dongda.model.uibean;

import com.blackmirror.dongda.model.BaseUiBean;
import com.blackmirror.dongda.model.serverbean.UpdateUserInfoServerBean;

/**
 * Created by Ruge on 2018-04-19 下午6:58
 */
public class UpdateUserInfoUiBean extends BaseUiBean {
    public UpdateUserInfoUiBean(UpdateUserInfoServerBean bean) {
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
