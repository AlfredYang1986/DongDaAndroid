package com.blackmirror.dongda.data.model.response;

import java.io.Serializable;

/**
 * Created by Ruge on 2018-04-10 下午2:43
 */
public class SendSmsResponseBean extends BaseResponseBean {

    /**
     * status : ok
     * result : {"reg":{"phone":"17610279929","code":"1111","reg_token":"4ea2f7f8c9ef85eb2c229d2038cac3db","is_reg":1}}
     */
    public ResultBean result;

    public static class ResultBean implements Serializable{
        /**
         * reg : {"phone":"17610279929","code":"1111","reg_token":"4ea2f7f8c9ef85eb2c229d2038cac3db","is_reg":1}
         */

        public RegBean reg;

        public static class RegBean {
            /**
             * phone : 17610279929
             * code : 1111
             * reg_token : 4ea2f7f8c9ef85eb2c229d2038cac3db
             * is_reg : 1
             */

            public String phone;
            public String code;
            public String reg_token;
            public int is_reg;
        }
    }
}
