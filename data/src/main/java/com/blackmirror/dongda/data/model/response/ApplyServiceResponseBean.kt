package com.blackmirror.dongda.data.model.response

import java.io.Serializable

/**
 * Create By Ruge at 2018-05-15
 */
class ApplyServiceResponseBean : BaseResponseBean() {

    /**
     * result : {"apply_id":"5afa5e054946b50d15a2acc1"}
     */

    var result: ResultBean? = null

    class ResultBean : Serializable {
        /**
         * apply_id : 5afa5e054946b50d15a2acc1
         */

        var apply_id: String? = null
    }
}
