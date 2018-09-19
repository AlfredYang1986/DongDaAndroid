package com.blackmirror.dongda.data.model.response

import java.io.Serializable

/**
 * Created by Ruge on 2018-04-10 下午2:43
 */
class SendSmsResponseBean : BaseResponseBean() {

    /**
     * status : ok
     * result : {"reg":{"phone":"17610279929","code":"1111","reg_token":"4ea2f7f8c9ef85eb2c229d2038cac3db","is_reg":1}}
     */
    var result: ResultBean? = null

    class ResultBean : Serializable {
        /**
         * reg : {"phone":"17610279929","code":"1111","reg_token":"4ea2f7f8c9ef85eb2c229d2038cac3db","is_reg":1}
         */

        var reg: RegBean? = null

        class RegBean : Serializable {
            /**
             * phone : 17610279929
             * code : 1111
             * reg_token : 4ea2f7f8c9ef85eb2c229d2038cac3db
             * is_reg : 1
             */

            var phone: String? = null
            var code: String? = null
            var reg_token: String? = null
            var is_reg: Int = 0
        }
    }
}
