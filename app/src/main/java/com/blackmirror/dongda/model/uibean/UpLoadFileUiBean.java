package com.blackmirror.dongda.model.uibean;

import com.blackmirror.dongda.model.BaseUiBean;
import com.blackmirror.dongda.model.serverbean.UpLoadFileServerBean;

/**
 * Created by Ruge on 2018-04-27 下午6:24
 */
public class UpLoadFileUiBean extends BaseUiBean {

    /**
     * img_uuid : uuid
     */

    public String img_uuid;

    public UpLoadFileUiBean(UpLoadFileServerBean serverBean) {
        if (serverBean!=null && "ok".equals(serverBean.status)){
            isSuccess=true;
            img_uuid=serverBean.img_uuid;
        }else {
            if (serverBean!=null && serverBean.error!=null){
                code=serverBean.error.code;
                message=serverBean.error.message;
            }
        }
    }
}
