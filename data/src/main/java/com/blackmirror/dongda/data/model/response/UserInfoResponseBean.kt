package com.blackmirror.dongda.data.model.response

import java.io.Serializable

/**
 * Create By Ruge at 2018-05-15
 */
class UserInfoResponseBean : BaseResponseBean() {

    /**
     * result : {"profile":{"screen_name":"sd","description":"qwertydf","has_auth_phone":1,"owner_name":"","is_service_provider":0,"user_id":"2737d748bce21504447cf27d7b1f4f99","company":"","screen_photo":"678f2f11-c7de-416e-9560-488de2b51bbd","date":1522736121280,"address":"asdfgh","contact_no":"","social_id":""}}
     */

    var result: ResultBean? = null

    class ResultBean : Serializable {
        /**
         * profile : {"screen_name":"sd","description":"qwertydf","has_auth_phone":1,"owner_name":"","is_service_provider":0,"user_id":"2737d748bce21504447cf27d7b1f4f99","company":"","screen_photo":"678f2f11-c7de-416e-9560-488de2b51bbd","date":1522736121280,"address":"asdfgh","contact_no":"","social_id":""}
         */

        var profile: ProfileBean? = null

        class ProfileBean : Serializable {
            /**
             * screen_name : sd
             * description : qwertydf
             * has_auth_phone : 1
             * owner_name :
             * is_service_provider : 0
             * user_id : 2737d748bce21504447cf27d7b1f4f99
             * company :
             * screen_photo : 678f2f11-c7de-416e-9560-488de2b51bbd
             * date : 1522736121280
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
            var address: String? = null
            var contact_no: String? = null
            var social_id: String? = null
        }
    }
}
