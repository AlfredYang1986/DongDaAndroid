package com.blackmirror.dongda.model.serverbean;

import com.blackmirror.dongda.model.BaseServerBean;

/**
 * Created by Ruge on 2018-04-19 下午6:08
 */
public class PhoneLoginServerBean extends BaseServerBean {

    /**
     * result : {"user":{"screen_name":"xcx","has_auth_phone":1,"current_device_type":"","is_service_provider":0,"user_id":"2737d748bce21504447cf27d7b1f4f99","screen_photo":"","current_device_id":""},"auth_token":"bearer2737d748bce21504447cf27d7b1f4f99"}
     */

    public ResultBean result;

    public static class ResultBean {
        /**
         * user : {"screen_name":"xcx","has_auth_phone":1,"current_device_type":"","is_service_provider":0,"user_id":"2737d748bce21504447cf27d7b1f4f99","screen_photo":"","current_device_id":""}
         * auth_token : bearer2737d748bce21504447cf27d7b1f4f99
         */

        public UserBean user;
        public String auth_token;

        public static class UserBean {
            /**
             * screen_name : xcx
             * has_auth_phone : 1
             * current_device_type :
             * is_service_provider : 0
             * user_id : 2737d748bce21504447cf27d7b1f4f99
             * screen_photo :
             * current_device_id :
             */

            public String screen_name;
            public int has_auth_phone;
            public String current_device_type;
            public int is_service_provider;
            public String user_id;
            public String screen_photo;
            public String current_device_id;
        }
    }
}
