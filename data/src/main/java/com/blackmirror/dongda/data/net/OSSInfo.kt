package com.blackmirror.dongda.data.net

import com.blackmirror.dongda.data.OSS_INFO_URL
import com.blackmirror.dongda.data.model.request.OssInfoRequestBean
import com.blackmirror.dongda.data.model.response.OssInfoResponseBean
import com.blackmirror.dongda.utils.*
import io.reactivex.Observable

/**
 * Created by xcx on 2018/6/11.
 */
fun getOssInfo(): Observable<OssInfoResponseBean> {

    if (isNeedRefreshToken(getExpiration())) {
        val bean = OssInfoRequestBean()
        bean.json = "{\"token\":\"${getAuthToken()}\"}"
        bean.url = OSS_INFO_URL
        return execute(bean, OssInfoResponseBean::class.java).doOnNext { bean ->
            if ("ok" == bean.status) {
                bean?.result?.OssConnectInfo?.apply {
                    setAccesskeyId(accessKeyId)
                    setSecurityToken(SecurityToken)
                    setAccesskeySecret(accessKeySecret)
                    setExpiration(Expiration)
                }

            }
        }
    } else {
        val b = OssInfoResponseBean()
        b.status = "ok"
        return Observable.just(b)
    }
}