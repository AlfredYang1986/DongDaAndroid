package com.blackmirror.dongda.domain.Interactor;

import com.blackmirror.dongda.domain.base.UserCase;
import com.blackmirror.dongda.domain.model.PhoneLoginBean;
import com.blackmirror.dongda.domain.model.request.PhoneLoginRequestBean;

import io.reactivex.Observable;


/**
 * Created by Ruge on 2018-05-07 下午2:50
 */
public class PhoneLoginUserCase implements UserCase<PhoneLoginRequestBean,PhoneLoginBean> {



    @Override
    public Observable<PhoneLoginBean> execute(PhoneLoginRequestBean bean) {
        return null;
    }
}
