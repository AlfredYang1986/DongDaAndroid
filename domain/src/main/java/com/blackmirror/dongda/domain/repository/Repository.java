package com.blackmirror.dongda.domain.repository;

import com.blackmirror.dongda.domain.model.PhoneLoginBean;

import io.reactivex.Observable;

/**
 * Created by Ruge on 2018-05-07 下午2:06
 */
public interface Repository {

    Observable<PhoneLoginBean> phoneLogin(String phone, String code,String reg_token);

}
