package com.blackmirror.dongda.net;

/**
 * Created by Ruge on 2018-04-23 下午6:26
 * 网络请求重试器
 */

import com.blackmirror.dongda.Tools.NetUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class RetryFactory implements Function<Observable<? extends Throwable>, Observable<?>> {
    /**
     * 重试间隔时间
     */
    private long mInterval = 1;
    /**
     * 重试次数
     */
    private int mMaxRetryTime = 3;
    /**
     * 是否每次重试的时间间隔递增
     */
    private boolean mIsIncrease;
    private int mCurRetryCount;
    /**
     * 重试工厂
     * @param interval 每次重试间隔, 以秒为单位
     * @param isProgressiveIncrease 是否每次重试的时间间隔递增，递增规律为：interval当前执行到的重试次数的次方
     * @param retryTime 重试次数
     */
    public RetryFactory(long interval, boolean isProgressiveIncrease, int retryTime) {
        mInterval = interval;
        mIsIncrease = isProgressiveIncrease;
        mMaxRetryTime = retryTime;
    }
    /**
     * 对网络无连接的情况不进行重试，并且重试有超时机制与重试间隔。
     * @param observable
     * @return
     */
    @Override
    public Observable<?> apply(Observable<? extends Throwable> observable) throws Exception {
        return observable.flatMap(new Function<Throwable, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Throwable throwable) throws Exception {
                if (!NetUtils.isNetworkAvailable()) {//没有网络、网络可连不可用、网络被劫持等直接抛异常，不进行重试
                    return Observable.error(throwable);
                }
                //重试mMaxRetryTime次
                if (++mCurRetryCount <= mMaxRetryTime) {
                    long delayTime = mInterval;
                    if(mIsIncrease){
                        delayTime = (long) Math.pow(mInterval, mCurRetryCount);
                    }
                    return Observable.timer(delayTime, TimeUnit.SECONDS);
                }
                return Observable.error(throwable);
            }

        });
    }
}
