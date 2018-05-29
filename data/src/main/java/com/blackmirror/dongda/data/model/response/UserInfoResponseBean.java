package com.blackmirror.dongda.data.model.response;

import java.io.Serializable;

/**
 * Create By Ruge at 2018-05-15
 */
public class UserInfoResponseBean extends BaseResponseBean {

    /**
     * result : {"profile":{"screen_name":"sd","description":"qwertydf","has_auth_phone":1,"owner_name":"","is_service_provider":0,"user_id":"2737d748bce21504447cf27d7b1f4f99","company":"","screen_photo":"678f2f11-c7de-416e-9560-488de2b51bbd","date":1522736121280,"address":"asdfgh","contact_no":"","social_id":""}}
     */

    public ResultBean result;

    public static class ResultBean implements Serializable {
        /**
         * profile : {"screen_name":"sd","description":"qwertydf","has_auth_phone":1,"owner_name":"","is_service_provider":0,"user_id":"2737d748bce21504447cf27d7b1f4f99","company":"","screen_photo":"678f2f11-c7de-416e-9560-488de2b51bbd","date":1522736121280,"address":"asdfgh","contact_no":"","social_id":""}
         */

        public ProfileBean profile;

        public static class ProfileBean implements Serializable{
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

            public String screen_name;
            public String description;
            public int has_auth_phone;
            public String owner_name;
            public int is_service_provider;
            public String user_id;
            public String company;
            public String screen_photo;
            public long date;
            public String address;
            public String contact_no;
            public String social_id;
        }
    }
}
