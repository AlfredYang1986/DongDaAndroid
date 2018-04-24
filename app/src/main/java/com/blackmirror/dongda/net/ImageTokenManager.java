package com.blackmirror.dongda.net;

/**
 * Created by xcx on 18-4-23.
 */
public class ImageTokenManager {

    private ImageTokenManager(){}

    public static class singleHolder{
        private static final ImageTokenManager INSTANCE=new ImageTokenManager();
    }

    public static ImageTokenManager getInstance(){
        return singleHolder.INSTANCE;
    }

    public void refreshImgToken(){
        /*disposable = Observable.interval(OtherUtils.getInitRefreshTime(BasePrefUtils.getExpiration()), OtherUtils.getExpirateTime(BasePrefUtils.getExpiration()),
                TimeUnit.SECONDS, Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        getImgToken();
                    }
                });*/
    }
}
