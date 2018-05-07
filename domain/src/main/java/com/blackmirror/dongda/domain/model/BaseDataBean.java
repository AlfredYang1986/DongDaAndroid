package com.blackmirror.dongda.domain.model;


import java.io.Serializable;

/**
 * Created by Ruge on 2018-05-07 下午2:51
 */
public class BaseDataBean implements Serializable {
    public int code= 10010;//没有网络
    public String message;//错误信息
    public boolean isSuccess;
}
