package com.blackmirror.dongda.model.uibean;

import com.blackmirror.dongda.model.BaseUiBean;
import com.blackmirror.dongda.model.serverbean.QueryLikeServerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruge on 2018-04-16 下午6:57
 */
public class QueryLikeUiBean extends BaseUiBean {

    public List<QueryLikeServerBean.ResultBean.ServicesBean> services = new ArrayList<>();

    public QueryLikeUiBean(QueryLikeServerBean bean) {
        if (bean!=null && "ok".equals(bean.status)){
            isSuccess=true;
            if (bean.result!=null) {
                services = bean.result.services;
            }
        }else {
            if (bean!=null && bean.error!=null){
                code=bean.error.code;
                message=bean.error.message;
            }
        }

    }
}
