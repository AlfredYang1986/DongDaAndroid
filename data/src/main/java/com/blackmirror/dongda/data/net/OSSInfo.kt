package com.blackmirror.dongda.data.net

import com.blackmirror.dongda.data.DataConstant
import com.blackmirror.dongda.data.model.request.OssInfoRequestBean
import com.blackmirror.dongda.data.model.response.OssInfoResponseBean
import com.blackmirror.dongda.utils.AYPrefUtils
import com.blackmirror.dongda.utils.DateUtils
import io.reactivex.Observable

/**
 * Created by xcx on 2018/6/11.
 */
fun getOssInfo2(): Observable<OssInfoResponseBean> {

    if (DateUtils.isNeedRefreshToken(AYPrefUtils.getExpiration())) {
        val bean = OssInfoRequestBean()
        bean.json = "{\"token\":\"${AYPrefUtils.getAuthToken()}\"}"
        bean.url = DataConstant.OSS_INFO_URL
        return execute(bean, OssInfoResponseBean::class.java).doOnNext { bean ->
            if ("ok" == bean.status) {
                bean?.result?.OssConnectInfo?.apply {
                    AYPrefUtils.setAccesskeyId(accessKeyId)
                    AYPrefUtils.setSecurityToken(SecurityToken)
                    AYPrefUtils.setAccesskeySecret(accessKeySecret)
                    AYPrefUtils.setExpiration(Expiration)
                }

            }
        }
    } else {
        val b = OssInfoResponseBean()
        b.status = "ok"
        return Observable.just(b)
    }
}