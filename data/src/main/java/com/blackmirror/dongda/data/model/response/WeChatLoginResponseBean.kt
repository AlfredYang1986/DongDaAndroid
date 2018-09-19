package com.blackmirror.dongda.data.model.response

import java.io.Serializable

/**
 * Created by Ruge on 2018-04-19 下午6:08
 */
class WeChatLoginResponseBean : BaseResponseBean() {

    /**
     * result : {"user":{"screen_name":"xcx","has_auth_phone":1,"current_device_type":"","is_service_provider":0,"user_id":"2737d748bce21504447cf27d7b1f4f99","screen_photo":"","current_device_id":""},"auth_token":"bearer2737d748bce21504447cf27d7b1f4f99"}
     */

    var result: ResultBean? = null

    class ResultBean : Serializable {
        /**
         * user : {"screen_name":"xcx","has_auth_phone":1,"current_device_type":"","is_service_provider":0,"user_id":"2737d748bce21504447cf27d7b1f4f99","screen_photo":"","current_device_id":""}
         * auth_token : bearer2737d748bce21504447cf27d7b1f4f99
         */

        var user: UserBean? = null
        var auth_token: String? = null

        class UserBean : Serializable {
            /**
             * screen_name : xcx
             * has_auth_phone : 1
             * current_device_type :
             * is_service_provider : 0
             * user_id : 2737d748bce21504447cf27d7b1f4f99
             * screen_photo :
             * current_device_id :
             */

            var screen_name: String? = null
            var has_auth_phone: Int = 0
            var current_device_type: String? = null
            var is_service_provider: Int = 0
            var user_id: String? = null
            var screen_photo: String? = null
            var current_device_id: String? = null
        }
    }
}
