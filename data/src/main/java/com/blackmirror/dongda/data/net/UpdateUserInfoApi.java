package com.blackmirror.dongda.data.net;

import android.text.TextUtils;

import com.blackmirror.dongda.data.DataConstant;
import com.blackmirror.dongda.data.model.db.UserInfoDbBean;
import com.blackmirror.dongda.data.model.request.UpdateUserInfoRequestBean;
import com.blackmirror.dongda.data.model.request.UploadImageRequestBean;
import com.blackmirror.dongda.data.model.response.OssInfoResponseBean;
import com.blackmirror.dongda.data.model.response.UpLoadImgResponseBean;
import com.blackmirror.dongda.data.model.response.UpdateUserInfoResponseBean;
import com.blackmirror.dongda.data.repository.DbRepository;
import com.blackmirror.dongda.domain.model.UpdateUserInfoBean;
import com.blackmirror.dongda.domain.model.UpdateUserInfoDomainBean;
import com.blackmirror.dongda.utils.AYPrefUtils;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Create By Ruge at 2018-05-10
 */
public class UpdateUserInfoApi extends AYRemoteApi {

    /**
     * 分两种情况：需要上传图片和不需要上传图片
     * 1 需要上传图片：获取OSS信息(如果需要)->上传图片到OSS服务器->修改用户信息->修改数据库
     * 2 不需要上传图片：获取OSS信息(如果需要)->修改用户信息->修改数据库
     *
     * @param bean
     */
    public static Observable<UpdateUserInfoBean> updateUserInfo(UpdateUserInfoDomainBean bean) {
        if (!TextUtils.isEmpty(bean.imgUUID)) {//需要上传图片
           return updateUserInfoWithPhoto(bean);
        } else {//不需要
            return updateUserInfoWithOutPhoto(bean);
        }
    }

    private static Observable<UpdateUserInfoBean> updateUserInfoWithPhoto(final UpdateUserInfoDomainBean requestBean) {
        UploadImageRequestBean bean = new UploadImageRequestBean();
        bean.json = requestBean.json;
        bean.imgUUID = requestBean.imgUUID;
        bean.url = DataConstant.UPDATE_USER_INFO_URL;
        return UpLoadImageApi.execute(bean, UpLoadImgResponseBean.class)
                .flatMap(new Function<UpLoadImgResponseBean, Observable<UpdateUserInfoBean>>() {
                    @Override
                    public Observable<UpdateUserInfoBean> apply(UpLoadImgResponseBean bean) throws Exception {
                        UpdateUserInfoBean infoBean = new UpdateUserInfoBean();
                        //修改用户信息
                        if ("ok".equals(bean.getStatus())) {
                            final UpdateUserInfoRequestBean b = new UpdateUserInfoRequestBean();
                            b.json = requestBean.json;
                            b.imgUUID = requestBean.imgUUID;
                            b.url = DataConstant.UPDATE_USER_INFO_URL;
                            return UpdateUserInfoApi.execute(b, UpdateUserInfoResponseBean.class)
                                    .map(new Function<UpdateUserInfoResponseBean, UpdateUserInfoBean>() {
                                        @Override
                                        public UpdateUserInfoBean apply(UpdateUserInfoResponseBean bean) throws Exception {
                                            UpdateUserInfoBean infoBean = new UpdateUserInfoBean();
                                            trans2UpdateUserInfoBean(bean, infoBean);

                                            UserInfoDbBean dbBean = new UserInfoDbBean();
                                            dbBean.is_current = 1;//目前没什么卵用
                                            dbBean.screen_name = infoBean.screen_name;
                                            dbBean.screen_photo = infoBean.screen_photo;
                                            dbBean.user_id = infoBean.user_id;
                                            dbBean.auth_token = AYPrefUtils.getAuthToken();
                                            DbRepository.updateProfile(dbBean);
                                            return infoBean;
                                        }
                                    });
                        }else {
                            if (bean!=null && bean.getError()!=null){

                                infoBean.code = bean.error.code;
                                infoBean.message = bean.error.message;
                            }
                            return Observable.just(infoBean);
                        }
                    }
                });
    }

    private static Observable<UpdateUserInfoBean> updateUserInfoWithOutPhoto(final UpdateUserInfoDomainBean requestBean) {
        final UpdateUserInfoRequestBean b = new UpdateUserInfoRequestBean();
        b.json = requestBean.json;
        b.url = DataConstant.UPDATE_USER_INFO_URL;

        return OSSInfoApi.getOssInfo()
                .flatMap(new Function<OssInfoResponseBean, Observable<UpdateUserInfoBean>>() {
                    @Override
                    public Observable<UpdateUserInfoBean> apply(OssInfoResponseBean bean) throws Exception {
                        final UpdateUserInfoBean infoBean = new UpdateUserInfoBean();
                        if (bean != null && "ok".equals(bean.status)) {
                            return execute(b, UpdateUserInfoResponseBean.class)
                                    .map(new Function<UpdateUserInfoResponseBean, UpdateUserInfoBean>() {
                                        @Override
                                        public UpdateUserInfoBean apply(UpdateUserInfoResponseBean bean) throws Exception {
                                            trans2UpdateUserInfoBean(bean, infoBean);

                                            UserInfoDbBean dbBean = new UserInfoDbBean();
                                            dbBean.is_current = 1;//目前没什么卵用
                                            dbBean.screen_name = infoBean.screen_name;
                                            dbBean.screen_photo = infoBean.screen_photo;
                                            dbBean.user_id = infoBean.user_id;
                                            dbBean.auth_token = AYPrefUtils.getAuthToken();
                                            DbRepository.updateProfile(dbBean);
                                            return infoBean;
                                        }
                                    });
                        }else if (bean!=null && bean.error!=null){

                            infoBean.code = bean.error.code;
                            infoBean.message = bean.error.message;
                        }
                        return Observable.just(infoBean);
                    }
                });

    }

    private static void trans2UpdateUserInfoBean(UpdateUserInfoResponseBean bean, UpdateUserInfoBean infoBean) {
        if (bean != null && "ok".equals(bean.getStatus())) {
            infoBean.isSuccess = true;
            if (bean.result != null && bean.result.profile != null) {
                infoBean.screen_name = bean.result.profile.screen_name;
                infoBean.description = bean.result.profile.description;
                infoBean.has_auth_phone = bean.result.profile.has_auth_phone;
                infoBean.owner_name = bean.result.profile.owner_name;
                infoBean.is_service_provider = bean.result.profile.is_service_provider;
                infoBean.user_id = bean.result.profile.user_id;
                infoBean.company = bean.result.profile.company;
                infoBean.screen_photo = bean.result.profile.screen_photo;
                infoBean.date = bean.result.profile.date;
                infoBean.token = bean.result.profile.token;
                infoBean.address = bean.result.profile.address;
                infoBean.contact_no = bean.result.profile.contact_no;
                infoBean.social_id = bean.result.profile.social_id;
            }
        } else {
            if (bean != null && bean.getError() != null) {
                infoBean.code = bean.getError().getCode();
                infoBean.message = bean.getError().getMessage();
            }
        }
    }


}
