package com.blackmirror.dongda.data.net

import android.util.Log
import com.alibaba.sdk.android.oss.ClientConfiguration
import com.alibaba.sdk.android.oss.ClientException
import com.alibaba.sdk.android.oss.OSSClient
import com.alibaba.sdk.android.oss.ServiceException
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider
import com.alibaba.sdk.android.oss.model.ResumableUploadRequest
import com.alibaba.sdk.android.oss.model.ResumableUploadResult
import com.blackmirror.dongda.base.AYApplication
import com.blackmirror.dongda.data.OTHER_EXCEPTION
import com.blackmirror.dongda.data.model.request.UploadVideoRequestBean
import com.blackmirror.dongda.data.model.response.BaseResponseBean
import com.blackmirror.dongda.data.model.response.UpLoadVideoResponseBean
import com.blackmirror.dongda.utils.AYPrefUtils

/**
 * Create By Ruge at 2018-07-31
 */

fun executeUolpadVideo(bean: UploadVideoRequestBean) {
    val oss = getOSSClient()
    // 创建断点上传请求
    val request = ResumableUploadRequest("bm-dongda", bean.name, bean.path)
    // 设置上传过程回调
    request.progressCallback = OSSProgressCallback<ResumableUploadRequest> { request, currentSize, totalSize ->
        Log.d("xcx", "currentSize: $currentSize totalSize: $totalSize")
    }
    val b = UpLoadVideoResponseBean()
    // 异步调用断点上传
    val resumableTask = oss.asyncResumableUpload(request, object : OSSCompletedCallback<ResumableUploadRequest, ResumableUploadResult> {
        override fun onSuccess(request: ResumableUploadRequest, result: ResumableUploadResult) {
            Log.d("xcx", "upload success!")
            b.status = "ok"
        }

        override fun onFailure(request: ResumableUploadRequest, clientExcepion: ClientException?, serviceException: ServiceException?) {
            // 异常处理
            Log.d("xcx", "onFailure: ${clientExcepion?.message} ServiceException:${serviceException.toString()}")
            val e = BaseResponseBean.ErrorBean()
            if (clientExcepion != null) {
                e.code = OTHER_EXCEPTION
                e.message = clientExcepion.message
            }
            if (serviceException != null) {
                e.code = serviceException.statusCode
                e.message = serviceException.message
            }
            b.error = e
        }
    })
}

inline fun getOSSClient(): OSSClient {
    val endpoint = "https://oss-cn-beijing.aliyuncs.com"
    val stsServer = "STS应用服务器地址，例如http://abc.com"
    //推荐使用OSSAuthCredentialsProvider。token过期可以及时更新
    val accId = "STS.NJfAq1aqrbJARrWmb2hy6zFjB"
    val secId = "Dy5JjYFNcVmhCzstp1P8wQvHE8xQ99uWfgJCmzCougEF"
    val seToken = "CAIShQJ1q6Ft5B2yfSjIr4nTCsuFjK5T1YiqUFTmiWJnZPYalYPBoDz2IHlMfnVhAe0asv03lGtR6PgflqJ5T5ZORknFd9F39MyTK+Izxc6T1fau5Jko1beHewHKeTOZsebWZ+LmNqC/Ht6md1HDkAJq3LL+bk/Mdle5MJqP+/UFB5ZtKWveVzddA8pMLQZPsdITMWCrVcygKRn3mGHdfiEK00he8TohuPrimJDDsEWG0Aahk7Yvyt6vcsT+Xa5FJ4xiVtq55utye5fa3TRYgxowr/4u1vAVoWqb4ojFUwIIvUvbKZnd9tx+MQl+fbMmHK1Jqvfxk/Bis/DUjZ7wzxtduhT90f5oresagAFkknvaHqRw9LkkUF9t9KlgqbBK4F9g89Xd/RbYijvGs6SVjXdWaEzbgMi6bWDzOjP24EgPOJmSNcbUnekBxmhSdqMHzGWPAiC1wSBzvqz9uwbAUA8ce5S1ccwFft8uhmBeRRZvo0LNOmbCuMOIm8iyqfsfxGE/EaWBvmbT7sCZ6g=="
    val credentialProvider = OSSStsTokenCredentialProvider(AYPrefUtils.getAccesskeyId(), AYPrefUtils.getAccesskeySecret(), AYPrefUtils.getSecurityToken())
    //该配置类如果不设置，会有默认配置，具体可看该类
    val conf = ClientConfiguration()
    conf.setConnectionTimeout(15 * 1000) // 连接超时，默认15秒
    conf.setSocketTimeout(15 * 1000) // socket超时，默认15秒
    conf.setMaxConcurrentRequest(5) // 最大并发请求数，默认5个
    conf.setMaxErrorRetry(2) // 失败后最大重试次数，默认2次
    return OSSClient(AYApplication.appContext, endpoint, credentialProvider)
}