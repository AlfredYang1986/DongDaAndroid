package com.blackmirror.dongda.data.model.response;

import java.io.Serializable;

/**
 * Create By Ruge at 2018-05-15
 */
public class EnrolResponseBean extends BaseResponseBean {


    /**
     * result : {"recruit_id":"5afce8db4946b50d15a2acd0"}
     */

    public ResultBean result;

    public static class ResultBean implements Serializable {
        /**
         * recruit_id : 5afce8db4946b50d15a2acd0
         */

        public String recruit_id;
    }
}
