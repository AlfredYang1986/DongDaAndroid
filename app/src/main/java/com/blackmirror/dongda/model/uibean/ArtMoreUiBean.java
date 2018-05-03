package com.blackmirror.dongda.model.uibean;

import com.blackmirror.dongda.model.BaseUiBean;
import com.blackmirror.dongda.model.serverbean.ArtMoreServerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruge on 2018-04-16 下午3:38
 */
public class ArtMoreUiBean extends BaseUiBean{

    public List<ArtMoreServerBean.ResultBean.ServicesBean> services = new ArrayList<>();
    public ArtMoreUiBean(ArtMoreServerBean bean) {
        if (bean!=null && "ok".equals(bean.status)){
            if (bean.result!=null) {
                services = bean.result.services;
            }
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
