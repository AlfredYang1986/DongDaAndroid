package com.blackmirror.dongda.data.model.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Create By Ruge at 2018-05-11
 */
public class LikePushResponseBean extends BaseResponseBean {
    /**
     * result : {"minus collection":"success"}
     */

    public ResultBean result;

    public static class ResultBean {
        @JSONField(name = "add collection")
        public String _$MinusCollection192; // FIXME check this code
    }
}
