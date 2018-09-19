package com.blackmirror.dongda.ui.activity.live

import com.blackmirror.dongda.R
import com.blackmirror.dongda.kdomain.model.BaseDataBean
import com.blackmirror.dongda.kdomain.model.UploadVideoDomainBean
import com.blackmirror.dongda.kdomain.model.UploadVideoImgDomainBean
import com.blackmirror.dongda.presenter.UploadVideoPresenter
import com.blackmirror.dongda.ui.Contract
import com.blackmirror.dongda.ui.base.BaseActivity
import com.blackmirror.dongda.utils.ToastUtils
import com.blackmirror.dongda.utils.getUUID32
import com.mabeijianxi.smallvideorecord2.Log
import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class RecordActivity : BaseActivity(), Contract.UploadVideoView {

    var presenter: UploadVideoPresenter? = null
    var disposable: Disposable? = null

    override val layoutResId: Int
        get() = R.layout.activity_record


    override fun initInject() {
        presenter = UploadVideoPresenter(this)
    }

    override fun initView() {
    }

    override fun initData() {
        showProcessDialog(message = "正在上传...", cancelable = false)
        Log.d("xcx", "MediaRecorderActivity.OUTPUT_DIRECTORY ${intent.getStringExtra(MediaRecorderActivity.OUTPUT_DIRECTORY)}")
        Log.d("xcx", "MediaRecorderActivity.VIDEO_URI ${intent.getStringExtra(MediaRecorderActivity.VIDEO_URI)}")
        Log.d("xcx", "MediaRecorderActivity.VIDEO_SCREENSHOT ${intent.getStringExtra(MediaRecorderActivity.VIDEO_SCREENSHOT)}")

        val uuid = getUUID32()

        val name = "video/$uuid.mp4"
        val image = "video/$uuid.jpg"
        val path = intent.getStringExtra(MediaRecorderActivity.VIDEO_URI)
        val imgPath = intent.getStringExtra(MediaRecorderActivity.VIDEO_SCREENSHOT)

        presenter?.uploadVideo(name, path, image, imgPath)

        disposable = Observable.timer(120, TimeUnit.SECONDS, Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (!isViewValid){
                       return@subscribe
                    }
                    unDispose()
                    ToastUtils.showShortToast("上传失败!")
                    closeProcessDialog()
                    finish()
                }
    }

    override fun initListener() {
    }

    override fun onUploadSuccess(bean: UploadVideoDomainBean) {
        ToastUtils.showShortToast("上传成功!")
        closeProcessDialog()
        finish()
    }

    override fun onUploadImgSuccess(bean: UploadVideoImgDomainBean) {

    }

    override fun onUploadError(bean: BaseDataBean) {
        showProcessDialog()
        ToastUtils.showShortToast("${bean.message}(${bean.code})")
        finish()
    }

    private fun unDispose(){
        if (disposable!=null && !disposable!!.isDisposed){
            disposable?.dispose()
            disposable = null

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unDispose()
    }

}
