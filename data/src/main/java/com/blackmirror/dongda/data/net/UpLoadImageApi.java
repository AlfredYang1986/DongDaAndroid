package com.blackmirror.dongda.data.net;

import com.blackmirror.dongda.data.model.request.BaseRequestBean;
import com.blackmirror.dongda.data.model.response.UpLoadImgResponseBean;

import io.reactivex.Observable;

/**
 * Create By Ruge at 2018-05-10
 */
public class UpLoadImageApi extends UpLoadFileApi{
    public Observable<UpLoadImgResponseBean> uploadImage(){
        return execute(new BaseRequestBean(), UpLoadImgResponseBean.class);
    }
}
