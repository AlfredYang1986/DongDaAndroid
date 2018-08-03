package com.blackmirror.dongda.ui

import com.blackmirror.dongda.kdomain.model.*

/**
 * Create By Ruge at 2018-05-17
 */
interface Contract {
    interface NameInputView {

        fun onUpdateUserInfoSuccess(bean: UpdateUserInfoBean)

        fun onError(bean: BaseDataBean)
    }

    interface MyLikeView {

        fun onGetLikeDataSuccess(bean: LikeDomainBean)

        fun onLikePushSuccess(bean: LikePushDomainBean)

        fun onLikePopSuccess(bean: LikePopDomainBean)

        fun onGetDataError(bean: BaseDataBean)
    }

    interface NearServiceView {

        fun onGetNearServiceSuccess(bean: NearServiceDomainBean)

        fun onGetDataError(bean: BaseDataBean)

    }

    interface DetailInfoView {

        fun onGetDetailInfoSuccess(bean: DetailInfoDomainBean)

        fun onLikePushSuccess(bean: LikePushDomainBean)

        fun onLikePopSuccess(bean: LikePopDomainBean)

        fun onGetDataError(bean: BaseDataBean)

    }

    interface UploadVideoView {

        fun onUploadSuccess(bean:UploadVideoDomainBean)

        fun onUploadImgSuccess(bean:UploadVideoImgDomainBean)

        fun onUploadError(bean: BaseDataBean)
    }

    interface LikePresenter {

        fun getLikeData()

        fun likePush(service_id: String)

        fun likePop(service_id: String)
    }

    interface NearServicePresenter {

        fun getNearService(latitude: Double, longitude: Double)
    }

    interface DetailInfoPresenter {

        fun getDetailInfo(service_id: String)

        fun likePush(service_id: String)

        fun likePop(service_id: String)
    }



}
