package com.blackmirror.dongda.ui.activity.live

import android.util.Log
import com.aliyun.vodplayer.media.AliyunLocalSource
import com.aliyun.vodplayer.media.IAliyunVodPlayer
import com.aliyun.vodplayerview.widget.AliyunVodPlayerView
import com.blackmirror.dongda.R
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.DeviceUtils

class VideoDetailActivity : BaseActivity() {

    lateinit var mAliyunVodPlayerView: AliyunVodPlayerView

    var url: String = ""

    override val layoutResId: Int
        get() = R.layout.activity_video_detail

    override fun initInject() {
    }

    override fun initView() {
        mAliyunVodPlayerView = findViewById(R.id.video_view)
    }

    override fun initData() {
        //        val url = "http://player.alicdn.com/video/aliyunmedia.mp4"
//                    val url = "rtmp://live.hkstv.hk.lxdns.com/live/hks"
//        url = intent.getStringExtra("url")

//        url = "rtmp://live.dongdakid.com/dd/test02?auth_key=1801533031429-0-0-ab5f227cae6925c2983ff670a0661b91"
        url="https://bm-dongda.oss-cn-beijing.aliyuncs.com/video/a385504d-c41f-431d-8bbe-098c9295420b.mp4?Expires=1533265043&OSSAccessKeyId=TMP.AQFiBJrJ3GO5mFg0EJrNZnrKC4rEU-H1BBshMx-4n0OCtP9nMII2rMdO-WNmADAtAhQHWVPKiAgDxdh14iLyhIch_ul6IwIVAIdfATVNau7_HZLPR6Nqr8LdCsM5&Signature=qMw70H1AWndN2sbNVHw%2BQTStcpg%3D"
        val asb = AliyunLocalSource.AliyunLocalSourceBuilder()
        asb.setSource(url)
        //aliyunVodPlayer.setLocalSource(asb.build());
        val mLocalSource = asb.build()
        mAliyunVodPlayerView.setLocalSource(mLocalSource)
    }

    override fun initListener() {
        
        mAliyunVodPlayerView.setOnErrorListener { errorCode, errorEvent, errorMsg ->
            Log.d("xcx","errorCode=$errorCode, errorEvent=$errorEvent, errorMsg=$errorMsg")
        }
        
        //设置播放器监听
        mAliyunVodPlayerView.setOnPreparedListener {
            //准备完成时触发
            Log.d("xcx", "准备完成时触发")
            mAliyunVodPlayerView.start()
        }
        mAliyunVodPlayerView.setOnCompletionListener {
            //播放正常完成时触发
            Log.d("xcx", "播放正常完成时触发")
        }
        mAliyunVodPlayerView.setOnFirstFrameStartListener {
            //首帧显示时触发
            Log.d("xcx", "首帧显示时触发")
        }
        mAliyunVodPlayerView.setOnChangeQualityListener(object : IAliyunVodPlayer.OnChangeQualityListener {
            override fun onChangeQualitySuccess(finalQuality: String) {
                //清晰度切换成功时触发
                Log.d("xcx", "清晰度切换成功时触发")
            }

            override fun onChangeQualityFail(code: Int, msg: String) {
                //清晰度切换失败时触发
                Log.d("xcx", "准备完成时触发")
            }
        })
        mAliyunVodPlayerView.setOnStoppedListener {
            //使用stop接口时触发
            Log.d("xcx", "使用stop接口时触发")
        }
        mAliyunVodPlayerView.setOnCircleStartListener {
            //循环播放开始
            Log.d("xcx", "循环播放开始")
        }
    }

    override fun setStatusBarColor() {
        DeviceUtils.initSystemBarColor(this)
    }

}
