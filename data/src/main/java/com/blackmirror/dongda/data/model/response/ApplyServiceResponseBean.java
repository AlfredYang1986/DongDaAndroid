package com.blackmirror.dongda.data.model.response;

import java.io.Serializable;

/**
 * Create By Ruge at 2018-05-15
 */
public class ApplyServiceResponseBean extends BaseResponseBean {

    /**
     * result : {"apply_id":"5afa5e054946b50d15a2acc1"}
     */

    public ResultBean result;

    public static class ResultBean implements Serializable {
        /**
         * apply_id : 5afa5e054946b50d15a2acc1
         */

        public String apply_id;
    }
}
