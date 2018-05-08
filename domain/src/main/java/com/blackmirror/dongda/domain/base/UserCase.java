package com.blackmirror.dongda.domain.base;

import io.reactivex.Observable;

/**
 * Created by Ruge on 2018-05-07 下午1:57
 */
public interface UserCase<R> {

    Observable<R> execute(String... args);
}
