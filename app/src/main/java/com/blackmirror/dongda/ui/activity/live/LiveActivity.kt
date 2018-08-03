package com.blackmirror.dongda.ui.activity.live

import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import com.alivc.live.pusher.*
import com.blackmirror.dongda.R
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.DeviceUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class LiveActivity : BaseActivity() {


    override val layoutResId: Int
        get() = R.layout.activity_live

    override fun initInject() {
    }

    //    lateinit var mAlivcLivePushConfig: AlivcLivePushConfig
    lateinit var mAlivcLivePusher: AlivcLivePusher
    var sv_push: SurfaceView? = null
    lateinit var bt_push: Button
    lateinit var bt_stop_push: Button
    lateinit var iv_change_camera: ImageView

    var isPause: Boolean = false

    val push_url = "rtmp://video-center.alivecdn.com/dongda/test01?vhost=live.dongdakid.com&auth_key=1801532415776-0-0-6b0e2c52cfe359039fa49d75106fd014"

    override fun init() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun initView() {
        sv_push = findViewById(R.id.sv_push)
        bt_push = findViewById(R.id.bt_push)
        bt_stop_push = findViewById(R.id.bt_stop_push)
        iv_change_camera = findViewById(R.id.iv_change_camera)
        bt_stop_push.isEnabled = false
//        surfaceHolder.addCallback(MyCallBack())
    }

    override fun initData() {
        val mAlivcLivePushConfig = AlivcLivePushConfig()//初始化推流配置类
        mAlivcLivePushConfig.setResolution(AlivcResolutionEnum.RESOLUTION_540P)//分辨率540P，最大支持720P
        mAlivcLivePushConfig.setFps(AlivcFpsEnum.FPS_20) //建议用户使用20fps
        mAlivcLivePushConfig.isEnableBitrateControl = true // 打开码率自适应，默认为true
        mAlivcLivePushConfig.setPreviewOrientation(AlivcPreviewOrientationEnum.ORIENTATION_PORTRAIT) // 默认为竖屏，可设置home键向左或向右横屏。
        mAlivcLivePushConfig.previewDisplayMode = AlivcPreviewDisplayMode.ALIVC_LIVE_PUSHER_PREVIEW_ASPECT_FIT//显示模式 这个是适合
        mAlivcLivePushConfig.setAutoFocus(true)//自动对焦
        mAlivcLivePushConfig.audioProfile = AlivcAudioAACProfileEnum.AAC_LC//设置音频编码模式
        mAlivcLivePushConfig.isEnableBitrateControl = true// 打开码率自适应，默认为true
        mAlivcLivePushConfig.qualityMode = AlivcQualityModeEnum.QM_RESOLUTION_FIRST//清晰度优先模式
        mAlivcLivePushConfig.isEnableAutoResolution = true// 打开分辨率自适应，默认为false
        mAlivcLivePusher = AlivcLivePusher()
        mAlivcLivePusher.init(this, mAlivcLivePushConfig)
        sv_push?.holder?.addCallback(MyCallBack())


    }


    override fun initListener() {

        iv_change_camera.setOnClickListener {
            mAlivcLivePusher.switchCamera()
        }

        bt_push.setOnClickListener {
            mAlivcLivePusher.startPushAysnc(push_url)
        }

        bt_stop_push.setOnClickListener {
            stopPush()
            finish()
        }


        /**
         * 设置推流错误事件
         *
         * @param errorListener 错误监听器
         */
        mAlivcLivePusher.setLivePushErrorListener(object : AlivcLivePushErrorListener {
            override fun onSystemError(livePusher: AlivcLivePusher, error: AlivcLivePushError?) {
                if (error != null) {
                    //添加UI提示或者用户自定义的错误处理
                    Log.e("xcx", "onSystemError $error")
                    stopPush()
                }
            }

            override fun onSDKError(livePusher: AlivcLivePusher, error: AlivcLivePushError?) {
                if (error != null) {
                    //添加UI提示或者用户自定义的错误处理
                    Log.e("xcx", "onSDKError $error")
                    stopPush()
                }
            }
        })


        /**
         * 设置推流通知事件
         *
         * @param infoListener 通知监听器
         */
        mAlivcLivePusher.setLivePushInfoListener(object : AlivcLivePushInfoListener {
            override fun onFirstAVFramePushed(p0: AlivcLivePusher?) {
                Log.d("xcx", "onFirstAVFramePushed ")
                Observable.just("")
                        .map {
                            bt_push.isEnabled = false
                            bt_stop_push.isEnabled = true
                        }.subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe()

            }

            override fun onPreviewStarted(pusher: AlivcLivePusher) {
                //预览开始通知
                Log.d("xcx", "onPreviewStarted ")
            }

            override fun onPreviewStoped(pusher: AlivcLivePusher) {
                //预览结束通知
                Log.d("xcx", "onPreviewStoped ")
            }

            override fun onPushStarted(pusher: AlivcLivePusher) {
                //推流开始通知
                Log.d("xcx", "onPushStarted ")
                Log.d("xcx", "start ${Thread.currentThread().name}")


            }

            override fun onPushPauesed(pusher: AlivcLivePusher) {
                //推流暂停通知
                Log.d("xcx", "onPushPauesed ")
            }

            override fun onPushResumed(pusher: AlivcLivePusher) {
                //推流恢复通知
                Log.d("xcx", "onPushResumed ")
            }

            override fun onPushStoped(pusher: AlivcLivePusher) {
                //推流停止通知
                Log.d("xcx", "onPushStoped ")
            }

            override fun onPushRestarted(pusher: AlivcLivePusher) {
                //推流重启通知
                Log.d("xcx", "onPushRestarted ")
            }

            override fun onFirstFramePreviewed(pusher: AlivcLivePusher) {
                //首帧渲染通知
                Log.d("xcx", "onFirstFramePreviewed ")
            }

            override fun onDropFrame(pusher: AlivcLivePusher, countBef: Int, countAft: Int) {
                //丢帧通知
                Log.d("xcx", "onDropFrame ")
            }

            override fun onAdjustBitRate(pusher: AlivcLivePusher, curBr: Int, targetBr: Int) {
                //调整码率通知
                Log.d("xcx", "onAdjustBitRate ")
            }

            override fun onAdjustFps(pusher: AlivcLivePusher, curFps: Int, targetFps: Int) {
                //调整帧率通知
                Log.d("xcx", "onAdjustFps ")
            }
        })

        /**
         * 设置网络通知事件
         *
         * @param infoListener 通知监听器
         */
        mAlivcLivePusher.setLivePushNetworkListener(object : AlivcLivePushNetworkListener {
            override fun onSendMessage(p0: AlivcLivePusher?) {
                Log.d("xcx", "onSendMessage ")
            }

            override fun onPushURLAuthenticationOverdue(p0: AlivcLivePusher?): String {
                Log.d("xcx", "onPushURLAuthenticationOverdue ")
                return p0!!.pushUrl
            }

            override fun onNetworkPoor(pusher: AlivcLivePusher) {
                //网络差通知
                Log.d("xcx", "onNetworkPoor ")
            }

            override fun onNetworkRecovery(pusher: AlivcLivePusher) {
                //网络恢复通知
                Log.d("xcx", "onNetworkRecovery ")
            }

            override fun onReconnectStart(pusher: AlivcLivePusher) {
                //重连开始通知
                Log.d("xcx", "onReconnectStart ")
            }

            override fun onReconnectFail(pusher: AlivcLivePusher) {
                //重连失败通知
                Log.d("xcx", "onReconnectFail ")
            }

            override fun onReconnectSucceed(pusher: AlivcLivePusher) {
                //重连成功通知
                Log.d("xcx", "onReconnectSucceed ")
            }

            override fun onSendDataTimeout(pusher: AlivcLivePusher) {
                //发送数据超时通知
                Log.d("xcx", "onSendDataTimeout ")
            }

            override fun onConnectFail(pusher: AlivcLivePusher) {
                //连接失败通知
                Log.d("xcx", "onConnectFail ")
            }
        })
        /**
         * 设置背景音乐播放通知事件
         *
         * @param pushBGMListener 通知监听器
         */
        mAlivcLivePusher.setLivePushBGMListener(object : AlivcLivePushBGMListener {
            override fun onStarted() {
                //播放开始通知
                Log.d("xcx", "onStarted ")
            }

            override fun onStoped() {
                //播放停止通知
                Log.d("xcx", "onStoped ")
            }

            override fun onPaused() {
                //播放暂停通知
                Log.d("xcx", "onPaused ")
            }

            override fun onResumed() {
                //播放恢复通知
                Log.d("xcx", "onResumed ")
            }

            override fun onProgress(progress: Long, duration: Long) {
                //播放进度事件
                Log.d("xcx", "onProgress ")
            }

            override fun onCompleted() {
                //播放结束通知
                Log.d("xcx", "onCompleted ")
            }

            override fun onDownloadTimeout() {
                //播放器超时事件，在这里处理播放器重连并且seek到播放位置
                Log.d("xcx", "onDownloadTimeout ")
            }

            override fun onOpenFailed() {
                //流无效通知，在这里提示流不可访问
                Log.d("xcx", "onOpenFailed ")
            }

        })

    }

    private fun stopPush() {
        mAlivcLivePusher.stopPush()
        sv_push = null
    }

    override fun onPause() {
        super.onPause()
        mAlivcLivePusher.pause()
        sv_push?.visibility = View.GONE
        isPause = true
    }

    override fun onResume() {
        super.onResume()
        if (isPause) {
            isPause = false
            sv_push?.visibility = View.VISIBLE
            mAlivcLivePusher.resume()
        }
    }

    override fun onDestroy() {
        closeProcessDialog()
        mAlivcLivePusher?.destroy()
        sv_push = null
        super.onDestroy()

    }

    override fun setStatusBarColor() {
        DeviceUtils.initSystemBarColor(this)
    }

    inner class MyCallBack : SurfaceHolder.Callback {
        override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
//            mAlivcLivePusher.startPreview(sv_push)//开始预览，也可根据需求调用异步接口startPreviewAysnc来实现
//            mAlivcLivePusher.startPreviewAysnc(sv_push)
            closeProcessDialog()
            mAlivcLivePusher.startPreview(sv_push)//开始预览，也可根据需求调用异步接口startPreviewAysnc来实现
        }

        override fun surfaceDestroyed(holder: SurfaceHolder?) {
        }

        override fun surfaceCreated(holder: SurfaceHolder?) {


        }

    }
}
