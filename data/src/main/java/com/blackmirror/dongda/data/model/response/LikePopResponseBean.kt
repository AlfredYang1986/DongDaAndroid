package com.blackmirror.dongda.data.model.response

import com.alibaba.fastjson.annotation.JSONField

import java.io.Serializable

/**
 * Create By Ruge at 2018-05-11
 */
class LikePopResponseBean : BaseResponseBean() {
    /**
     * result : {"minus collection":"success"}
     */

    var result: ResultBean? = null

    class ResultBean : Serializable {
        @JSONField(name = "minus collection")
        var `_$MinusCollection192`: String? = null // FIXME check this code
    }
}
