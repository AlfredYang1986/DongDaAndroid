package com.blackmirror.dongda.data.model.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Create By Ruge at 2018-05-11
 */
public class LikePopResponseBean extends BaseResponseBean {
    /**
     * result : {"minus collection":"success"}
     */

    public ResultBean result;

    public static class ResultBean implements Serializable {
        @JSONField(name = "minus collection")
        public String _$MinusCollection192; // FIXME check this code
    }
}
