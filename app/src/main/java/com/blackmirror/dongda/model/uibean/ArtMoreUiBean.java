package com.blackmirror.dongda.model.uibean;

import com.blackmirror.dongda.model.serverbean.ArtMoreServerBean;

import java.util.List;

/**
 * Created by Ruge on 2018-04-16 下午3:38
 */
public class ArtMoreUiBean {
    public int code;
    public String message;//错误信息
    public boolean isSuccess;

    public List<ArtMoreServerBean.ResultBean.ServicesBean> services;
    public ArtMoreUiBean(ArtMoreServerBean bean) {
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
