package com.blackmirror.dongda.data.model.response;

/**
 * Created by xcx on 2018/5/10.
 */

public class UpdateUserInfoResponseBean extends BaseResponseBean {
    /**
     * result : {"profile":{"screen_name":"xcx","description":"qwertydf","has_auth_phone":1,"owner_name":"","is_service_provider":0,"user_id":"2737d748bce21504447cf27d7b1f4f99","company":"","screen_photo":"","date":1522736121280,"token":"bearer2737d748bce21504447cf27d7b1f4f99","address":"asdfgh","contact_no":"","social_id":""}}
     */

    public ResultBean result;

    public static class ResultBean {
        /**
         * profile : {"screen_name":"xcx","description":"qwertydf","has_auth_phone":1,"owner_name":"","is_service_provider":0,"user_id":"2737d748bce21504447cf27d7b1f4f99","company":"","screen_photo":"","date":1522736121280,"token":"bearer2737d748bce21504447cf27d7b1f4f99","address":"asdfgh","contact_no":"","social_id":""}
         */

        public ProfileBean profile;

        public static class ProfileBean {
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

            public String screen_name;
            public String description;
            public int has_auth_phone;
            public String owner_name;
            public int is_service_provider;
            public String user_id;
            public String company;
            public String screen_photo;
            public long date;
            public String token;
            public String address;
            public String contact_no;
            public String social_id;
        }
    }
    public String img_uuid;
}
