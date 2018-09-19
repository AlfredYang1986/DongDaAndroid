package com.blackmirror.dongda.data.model.response

import java.io.Serializable

/**
 * Created by xcx on 2018/5/10.
 */

class UpdateUserInfoResponseBean : BaseResponseBean() {
    /**
     * result : {"profile":{"screen_name":"xcx","description":"qwertydf","has_auth_phone":1,"owner_name":"","is_service_provider":0,"user_id":"2737d748bce21504447cf27d7b1f4f99","company":"","screen_photo":"","date":1522736121280,"token":"bearer2737d748bce21504447cf27d7b1f4f99","address":"asdfgh","contact_no":"","social_id":""}}
     */

    var result: ResultBean? = null
    var img_uuid: String? = null

    class ResultBean : Serializable {
        /**
         * profile : {"screen_name":"xcx","description":"qwertydf","has_auth_phone":1,"owner_name":"","is_service_provider":0,"user_id":"2737d748bce21504447cf27d7b1f4f99","company":"","screen_photo":"","date":1522736121280,"token":"bearer2737d748bce21504447cf27d7b1f4f99","address":"asdfgh","contact_no":"","social_id":""}
         */

        var profile: ProfileBean? = null

        class ProfileBean : Serializable {
            /**
             * screen_name : xcx
             * description : qwertydf
             * has_auth_phone : 1
             * owner_name :
             * is_service_provider : 0
             * user_id : 2737d748bce21504447cf27d7b1f4f99
             * company :
             * screen_photo :
             * date : 1522736121280
             * token : bearer2737d748bce21504447cf27d7b1f4f99
             * address : asdfgh
             * contact_no :
             * social_id :
             */

            var screen_name: String? = null
            var description: String? = null
            var has_auth_phone: Int = 0
            var owner_name: String? = null
            var is_service_provider: Int = 0
            var user_id: String? = null
            var company: String? = null
            var screen_photo: String? = null
            var date: Long = 0
            var token: String? = null
            var address: String? = null
            var contact_no: String? = null
            var social_id: String? = null
        }
    }
}
