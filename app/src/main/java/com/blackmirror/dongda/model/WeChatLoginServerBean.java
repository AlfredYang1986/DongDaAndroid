package com.blackmirror.dongda.model;

/**
 * Created by Ruge on 2018-04-11 下午4:36
 */
public class WeChatLoginServerBean extends BaseBean {

    /**
     * result : {"user":{"screen_name":"轩辕剑客","has_auth_phone":0,"current_device_type":"","is_service_provider":0,"user_id":"09aa9fbe40a11f48734d9cb37ceaea88","screen_photo":"","current_device_id":""},"auth_token":"bearer09aa9fbe40a11f48734d9cb37ceaea88"}
     */



    public ResultBean result;


    public static class ResultBean {
        /**
         * user : {"screen_name":"轩辕剑客","has_auth_phone":0,"current_device_type":"","is_service_provider":0,"user_id":"09aa9fbe40a11f48734d9cb37ceaea88","screen_photo":"","current_device_id":""}
         * auth_token : bearer09aa9fbe40a11f48734d9cb37ceaea88
         */

        public UserBean user;
        public String auth_token;

        public static class UserBean {
            /**
             * screen_name : 轩辕剑客
             * has_auth_phone : 0
             * current_device_type :
             * is_service_provider : 0
             * user_id : 09aa9fbe40a11f48734d9cb37ceaea88
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
