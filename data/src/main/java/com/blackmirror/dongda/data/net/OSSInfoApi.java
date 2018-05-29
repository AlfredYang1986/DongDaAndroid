package com.blackmirror.dongda.data.net;

import com.blackmirror.dongda.data.DataConstant;
import com.blackmirror.dongda.data.model.request.OssInfoRequestBean;
import com.blackmirror.dongda.data.model.response.OssInfoResponseBean;
import com.blackmirror.dongda.utils.AYPrefUtils;
import com.blackmirror.dongda.utils.DateUtils;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Create By Ruge at 2018-05-10
 */
public class OSSInfoApi extends AYRemoteApi {

    public static Observable<OssInfoResponseBean> getOssInfo(){

        if (DateUtils.isNeedRefreshToken(AYPrefUtils.getExpiration())) {
            OssInfoRequestBean bean = new OssInfoRequestBean();
            bean.json = "{\"token\":\"" + AYPrefUtils.getAuthToken() + "\"}";
            bean.url = DataConstant.OSS_INFO_URL;
            return execute(bean,OssInfoResponseBean.class).doOnNext(new Consumer<OssInfoResponseBean>() {
                @Override
                public void accept(OssInfoResponseBean bean) throws Exception {
                    if (bean != null && "ok".equals(bean.status) && bean.result != null && bean.result.OssConnectInfo != null) {
                        AYPrefUtils.setAccesskeyId(bean.result.OssConnectInfo.accessKeyId);
                        AYPrefUtils.setSecurityToken(bean.result.OssConnectInfo.SecurityToken);
                        AYPrefUtils.setAccesskeySecret(bean.result.OssConnectInfo.accessKeySecret);
                        AYPrefUtils.setExpiration(bean.result.OssConnectInfo.Expiration);
                    }
                }
            });
        } else {
            OssInfoResponseBean b = new OssInfoResponseBean();
            b.status = "ok";
            return Observable.just(b);
        }
    }
}
