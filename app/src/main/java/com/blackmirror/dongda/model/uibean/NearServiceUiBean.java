package com.blackmirror.dongda.model.uibean;

import com.blackmirror.dongda.model.BaseUiBean;
import com.blackmirror.dongda.model.serverbean.NearServiceServerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruge on 2018-04-18 下午3:39
 */
public class NearServiceUiBean extends BaseUiBean {

    public List<NearServiceServerBean.ResultBean.ServicesBean> services = new ArrayList<>();

    public NearServiceUiBean(NearServiceServerBean bean) {
        if (bean != null && "ok".equals(bean.status)) {
            isSuccess = true;
            if (bean.result != null && bean.result.services != null) {
                services = bean.result.services;
            }
        } else {
            if (bean != null && bean.error != null) {
                code = bean.error.code;
                message = bean.error.message;
            }
        }
    }

    @Override
    public String toString() {
        return "NearServiceUiBean{" +
                "services=" + services +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", isSuccess=" + isSuccess +
                '}';
    }
}
