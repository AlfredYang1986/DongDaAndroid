package com.blackmirror.dongda.presenter

import android.util.Log
import com.alibaba.sdk.android.oss.ClientException
import com.alibaba.sdk.android.oss.ServiceException
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback
import com.alibaba.sdk.android.oss.model.PutObjectRequest
import com.alibaba.sdk.android.oss.model.PutObjectResult
import com.alibaba.sdk.android.oss.model.ResumableUploadRequest
import com.alibaba.sdk.android.oss.model.ResumableUploadResult
import com.blackmirror.dongda.data.OTHER_EXCEPTION
import com.blackmirror.dongda.data.net.getOSSClient
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.kdomain.model.UploadVideoDomainBean
import com.blackmirror.dongda.kdomain.model.UploadVideoImgDomainBean
import com.blackmirror.dongda.ui.Contract
import io.reactivex.Observable


/**
 * Create By Ruge at 2018-07-31
 */
class UploadVideoPresenter(val view: Contract.UploadVideoView?) {

    fun uploadImg(name:String,path:String){
        val oss = getOSSClient()
        // 构造上传请求
        val put = PutObjectRequest("bm-dongda", name, path)
// 异步上传时可以设置进度回调
        put.progressCallback = OSSProgressCallback { request, currentSize, totalSize -> Log.d("PutObject", "currentSize: $currentSize totalSize: $totalSize") }
        val task = oss.asyncPutObject(put, object : OSSCompletedCallback<PutObjectRequest, PutObjectResult> {
            override fun onSuccess(request: PutObjectRequest, result: PutObjectResult) {
                Log.d("PutObject", "UploadSuccess")
                Log.d("ETag", result.eTag)
                Log.d("RequestId", result.requestId)
                val b = UploadVideoImgDomainBean()
                view?.onUploadImgSuccess(b)
            }

            override fun onFailure(request: PutObjectRequest, clientExcepion: ClientException?, serviceException: ServiceException?) {
                // 请求异常
                clientExcepion?.printStackTrace()
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.errorCode)
                    Log.e("RequestId", serviceException.requestId)
                    Log.e("HostId", serviceException.hostId)
                    Log.e("RawMessage", serviceException.rawMessage)
                }

                val e = BaseDataBean()
                if (clientExcepion != null) {
                    e.code = OTHER_EXCEPTION
                    e.message = clientExcepion.message
                }
                if (serviceException != null) {
                    e.code = serviceException.statusCode
                    e.message = serviceException.message
                }
                view?.onUploadError(e)
            }
        })
        task.waitUntilFinished()
    }

    fun uploadVideo(name:String,path:String,imgName:String,imgPath:String){

        Observable.just("").map {
            uploadImg(imgName,imgPath)
        }.map {
            val oss = getOSSClient()

            // 创建断点上传请求
            val request = ResumableUploadRequest("bm-dongda", name, path)
            // 设置上传过程回调
            request.progressCallback = OSSProgressCallback<ResumableUploadRequest> { request, currentSize, totalSize ->
                Log.d("xcx", "currentSize: $currentSize totalSize: $totalSize")
            }

            // 异步调用断点上传
            val resumableTask = oss.asyncResumableUpload(request, object : OSSCompletedCallback<ResumableUploadRequest, ResumableUploadResult> {
                override fun onSuccess(request: ResumableUploadRequest, result: ResumableUploadResult) {
                    Log.d("xcx", "upload success!")
                    val b = UploadVideoDomainBean()
                    view?.onUploadSuccess(b)
                }

                override fun onFailure(request: ResumableUploadRequest, clientExcepion: ClientException?, serviceException: ServiceException?) {
                    // 异常处理
                    Log.d("xcx", "onFailure: ${clientExcepion?.message} ServiceException:${serviceException.toString()}")
                    val e = BaseDataBean()
                    if (clientExcepion != null) {
                        e.code = OTHER_EXCEPTION
                        e.message = clientExcepion.message
                    }
                    if (serviceException != null) {
                        e.code = serviceException.statusCode
                        e.message = serviceException.message
                    }
                    view?.onUploadError(e)
                }
            })
        }.subscribe()


    }
}