package com.blackmirror.dongda.data.model.response

import java.io.Serializable

/**
 * Create By Ruge at 2018-05-15
 */
class EnrolResponseBean : BaseResponseBean() {


    /**
     * result : {"recruit_id":"5afce8db4946b50d15a2acd0"}
     */

    var result: ResultBean? = null

    class ResultBean : Serializable {
        /**
         * recruit_id : 5afce8db4946b50d15a2acd0
         */

        var recruit_id: String? = null
    }
}
