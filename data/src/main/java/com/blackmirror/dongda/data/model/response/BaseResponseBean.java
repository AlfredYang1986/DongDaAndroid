package com.blackmirror.dongda.data.model.response;

import java.io.Serializable;

/**
 * Created by Ruge on 2018-05-04 下午2:02
 */
public class BaseResponseBean implements Serializable {
    public String status;//ok 成功 其他失败

    /**
     * error : {"code":-9005,"message":"token过期"}
     */

    public ErrorBean error;

    public static class ErrorBean implements Serializable{
        /**
         * code : -9005
         * message : token过期
         */

        public int code;
        public String message;//错误信息
    }
}
