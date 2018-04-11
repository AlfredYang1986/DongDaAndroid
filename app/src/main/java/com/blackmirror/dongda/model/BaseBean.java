package com.blackmirror.dongda.model;

import java.io.Serializable;

/**
 * Created by Ruge on 2018-04-10 下午2:43
 */
public class BaseBean implements Serializable {

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
