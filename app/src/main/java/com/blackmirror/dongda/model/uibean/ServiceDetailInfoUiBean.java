package com.blackmirror.dongda.model.uibean;

import com.blackmirror.dongda.model.BaseUiBean;
import com.blackmirror.dongda.model.serverbean.ServiceDetailInfoServerBean;

/**
 * Created by Ruge on 2018-04-17 上午10:12
 */
public class ServiceDetailInfoUiBean extends BaseUiBean {
    public ServiceDetailInfoServerBean.ResultBean.ServiceBean service = new ServiceDetailInfoServerBean.ResultBean.ServiceBean();
    public ServiceDetailInfoUiBean(ServiceDetailInfoServerBean bean) {
        if (bean!=null &&"ok".equals(bean.status)){
            isSuccess=true;
            if (bean.result!=null && bean.result.service!=null) {
                service = bean.result.service;
            }
        }else {
            if (bean!=null && bean.error!=null){
                code=bean.error.code;
                message=bean.error.message;
            }
        }
    }
}
