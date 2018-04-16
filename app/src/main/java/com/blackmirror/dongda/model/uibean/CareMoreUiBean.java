package com.blackmirror.dongda.model.uibean;

import com.blackmirror.dongda.model.serverbean.CareMoreServerBean;

import java.util.List;

/**
 * Created by Ruge on 2018-04-16 下午12:14
 */
public class CareMoreUiBean {

    public int code;
    public String message;//错误信息
    public boolean isSuccess;

    public List<CareMoreServerBean.ResultBean.ServicesBean> services;
    public CareMoreUiBean(CareMoreServerBean bean) {
        if (bean!=null && "ok".equals(bean.status) && bean.result!=null){
            services=bean.result.services;
            isSuccess=true;
        }else {
            isSuccess = false;
            if (bean!=null && bean.error!=null){
                code=bean.error.code;
                message=bean.error.message;
            }
        }
    }
}
