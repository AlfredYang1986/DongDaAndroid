package com.blackmirror.dongda.model.serverbean;

import com.alibaba.fastjson.annotation.JSONField;
import com.blackmirror.dongda.model.BaseServerBean;

/**
 * Created by Ruge on 2018-04-16 下午6:12
 */
public class LikePushServerBean extends BaseServerBean {

    /**
     * result : {"minus collection":"success"}
     */

    public ResultBean result;

    public static class ResultBean {
        @JSONField(name = "add collection")
        public String _$MinusCollection192; // FIXME check this code
    }
}
