package com.blackmirror.dongda.net;

/**
 * Created by Ruge on 2018-04-23 下午6:33
 * 重复发射器，通过自定义Transformer，将源Observable按照自定义的方式转化成另外一个新的Observable，
 * 并用Compose组合到使用此工厂的Obervable中
 */

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
//Function<? super Observable<Object>, ? extends ObservableSource<?>>
public class RepeatFactory implements Function<Observable<Object>, Observable<?>> {
    private final String TAG = RepeatFactory.class.getSimpleName();
    /**
     * 重试间隔时间, 单位秒
     */
    private long mInterval = 1;
    /**
     * 重试次数
     */
    private int mMaxRetryTime = 3;
    private long mCurInterval;
    /**
     * 重试间隔时间增加阀值，不能大小 mMaxRetryTime
     */
    private int mIncreaseValve;
    /**
     * @param interval 每次重试间隔时间
     * @param retryTime 重试总次数
     * @param increaseValve 重试间隔时间增加阀值，不能大小 retryTime
     */
    public RepeatFactory(long interval, int retryTime, int increaseValve) {
        if(interval < 0) {
            mInterval = 0;
            mCurInterval = 0;
        } else {
            mInterval = interval;
            mCurInterval = interval;
        }
        if(retryTime <= 0) {
            mMaxRetryTime = 1;
        } else {
            mMaxRetryTime = retryTime;
        }
        if(increaseValve > mMaxRetryTime){
            mIncreaseValve = mMaxRetryTime;
        } else {
            mIncreaseValve = increaseValve;
        }
    }

    private <T> ObservableTransformer<T, Long> zipWithFlatMap() {

        return new ObservableTransformer<T,Long>(){

            @Override
            public ObservableSource<Long> apply(Observable<T> upstream) {
                //重试次数
                return upstream.zipWith(Observable.range(1, mMaxRetryTime), new BiFunction<T, Integer, Integer>() {
                    @Override
                    public Integer apply(T t, Integer repeatAttempt) throws Exception {
                        return repeatAttempt;
                    }
                }).flatMap(new Function<Integer, ObservableSource<Long>>() {
                    @Override
                    public ObservableSource<Long> apply(Integer repeatAttempt) throws Exception {
                        //增加等待时间
                        long interal = mInterval;
                        int curCount = repeatAttempt;
                        if (++curCount > mIncreaseValve) {
                            mCurInterval = mInterval + repeatAttempt >> 4;
                            interal = mCurInterval;
                        }
                        return Observable.timer(interal, TimeUnit.SECONDS, Schedulers.io());
                    }

                });
            }
        };
    }


   /* public Observable<?> apply(Observable<? extends Void> observable) throws Exception {
        return observable.compose(zipWithFlatMap());
    }

    @Override
    public Observable<?> apply(Object o) throws Exception {
        return null;
    }*/


    @Override
    public Observable<?> apply(Observable<Object> observable) throws Exception {
        return observable.compose(zipWithFlatMap());
    }
}

