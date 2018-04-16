package com.blackmirror.dongda.model.uibean;

import com.blackmirror.dongda.model.UiBaseBean;
import com.blackmirror.dongda.model.serverbean.QueryLikeServerBean;

import java.util.List;

/**
 * Created by Ruge on 2018-04-16 下午6:57
 */
public class QueryLikeUiBean extends UiBaseBean {

    public List<QueryLikeServerBean.ResultBean.ServicesBean> services;

    public QueryLikeUiBean(QueryLikeServerBean bean) {
        if (bean!=null && bean.result!=null && "ok".equals(bean.status)){
            isSuccess=true;
            services=bean.result.services;
        }else {
            if (bean!=null && bean.error!=null){
                code=bean.error.code;
                message=bean.error.message;
            }
        }

    }
}
