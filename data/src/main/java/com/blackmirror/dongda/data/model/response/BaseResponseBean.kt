package com.blackmirror.dongda.data.model.response

import java.io.Serializable

/**
 * Created by Ruge on 2018-05-04 下午2:02
 */
open class BaseResponseBean : Serializable {
    var status: String? = null//ok 成功 其他失败

    /**
     * error : {"code":-9005,"message":"token过期"}
     */

    var error: ErrorBean? = null

    inner class ErrorBean : Serializable {
        /**
         * code : -9005
         * message : token过期
         */

        var code: Int = 0
        var message: String? = null//错误信息
    }
}
