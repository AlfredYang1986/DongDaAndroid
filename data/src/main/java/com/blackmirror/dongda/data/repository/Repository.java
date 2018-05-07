package com.blackmirror.dongda.data.repository;


import com.blackmirror.dongda.data.model.response.PhoneLoginResponseBean;

import io.reactivex.Observable;

/**
 * Created by Ruge on 2018-05-07 下午2:06
 */
public interface Repository {

    Observable<PhoneLoginResponseBean> phoneLogin(String phone, String code, String reg_token);

}
