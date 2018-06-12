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
                    if (bean != null && "ok".equals(bean.getStatus()) && bean.getResult() != null && bean.getResult().getOssConnectInfo() != null) {
                        AYPrefUtils.setAccesskeyId(bean.getResult().getOssConnectInfo().getAccessKeyId());
                        AYPrefUtils.setSecurityToken(bean.getResult().getOssConnectInfo().getSecurityToken());
                        AYPrefUtils.setAccesskeySecret(bean.getResult().getOssConnectInfo().getAccessKeySecret());
                        AYPrefUtils.setExpiration(bean.getResult().getOssConnectInfo().getExpiration());
                    }
                }
            });
        } else {
            OssInfoResponseBean b = new OssInfoResponseBean();
            b.setStatus("ok");
            return Observable.just(b);
        }
    }
}
