package com.blackmirror.dongda.data.net;

import com.blackmirror.dongda.data.DataConstant;
import com.blackmirror.dongda.data.model.request.ApplyServiceRequestBean;
import com.blackmirror.dongda.data.model.request.BrandAllLocRequestBean;
import com.blackmirror.dongda.data.model.request.DetailInfoRequestBean;
import com.blackmirror.dongda.data.model.request.EnrolRequestBean;
import com.blackmirror.dongda.data.model.request.LikePopRequestBean;
import com.blackmirror.dongda.data.model.request.LikePushRequestBean;
import com.blackmirror.dongda.data.model.request.LikeRequestBean;
import com.blackmirror.dongda.data.model.request.LocAllServiceRequestBean;
import com.blackmirror.dongda.data.model.request.NearServiceRequestBean;
import com.blackmirror.dongda.data.model.request.SearchServiceRequestBean;
import com.blackmirror.dongda.data.model.request.UserInfoRequestBean;
import com.blackmirror.dongda.data.model.response.ApplyServiceResponseBean;
import com.blackmirror.dongda.data.model.response.BrandAllLocResponseBean;
import com.blackmirror.dongda.data.model.response.CareMoreResponseBean;
import com.blackmirror.dongda.data.model.response.DetailInfoResponseBean;
import com.blackmirror.dongda.data.model.response.EnrolResponseBean;
import com.blackmirror.dongda.data.model.response.LikePopResponseBean;
import com.blackmirror.dongda.data.model.response.LikePushResponseBean;
import com.blackmirror.dongda.data.model.response.LikeResponseBean;
import com.blackmirror.dongda.data.model.response.LocAllServiceResponseBean;
import com.blackmirror.dongda.data.model.response.NearServiceResponseBean;
import com.blackmirror.dongda.data.model.response.OssInfoResponseBean;
import com.blackmirror.dongda.data.model.response.SearchServiceResponseBean;
import com.blackmirror.dongda.data.model.response.UserInfoResponseBean;
import com.blackmirror.dongda.utils.AYPrefUtils;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Create By Ruge at 2018-05-10
 */
public class CommonApi extends AYRemoteApi {
    public static Observable<SearchServiceResponseBean> searchService() {
        final SearchServiceRequestBean bean = new SearchServiceRequestBean();
        bean.json = "{ \"token\": \"" + AYPrefUtils.getAuthToken() + "\", \"condition\": { \"user_id\": \"" + AYPrefUtils.getUserId() + "\", \"service_type_list\": [{ \"service_type\": \"看顾\", \"count\": 6 }, { \"service_type\": \"艺术\", \"count\": 4 }, { \"service_type\": \"运动\", \"count\": 4 }, { \"service_type\": \"科学\", \"count\": 4 }]}}";
        bean.url = DataConstant.HOME_PAGE_URL;

        return OSSInfoApi.getOssInfo().flatMap(new Function<OssInfoResponseBean, Observable<SearchServiceResponseBean>>() {
            @Override
            public Observable<SearchServiceResponseBean> apply(OssInfoResponseBean b) throws Exception {
                if ("ok".equals(b.status)){
                    return execute(bean, SearchServiceResponseBean.class);
                }
                SearchServiceResponseBean sb = new SearchServiceResponseBean();
                sb.error = new SearchServiceResponseBean.ErrorBean();
                if (b!=null && b.error!=null){
                    sb.error.code = b.error.code;
                    sb.error.message = b.error.message;
                }
                return Observable.just(sb);
            }
        });

    }

    public static Observable<LikePushResponseBean> likePush(String service_id) {
        final LikePushRequestBean bean = new LikePushRequestBean();
        bean.json = "{\"token\":\"" + AYPrefUtils.getAuthToken() + "\",\"condition\": {\"user_id\":\"" + AYPrefUtils.getUserId() + "\",\"service_id\":\"" + service_id + "\"},\"collections\":{\"user_id\": \"" + AYPrefUtils.getUserId() + "\",\"service_id\":\"" + service_id + "\"}}";
        bean.url = DataConstant.LIKE_PUSH_URL;

        return OSSInfoApi.getOssInfo().flatMap(new Function<OssInfoResponseBean, Observable<LikePushResponseBean>>() {
            @Override
            public Observable<LikePushResponseBean> apply(OssInfoResponseBean b) throws Exception {
                if ("ok".equals(b.status)){
                    return execute(bean, LikePushResponseBean.class);
                }
                LikePushResponseBean sb = new LikePushResponseBean();
                sb.error = new LikePushResponseBean.ErrorBean();
                if (b!=null && b.error!=null){
                    sb.error.code = b.error.code;
                    sb.error.message = b.error.message;
                }
                return Observable.just(sb);
            }
        });

//        return execute(bean, LikePushResponseBean.class);
    }

    public static Observable<LikePopResponseBean> likePop(String service_id) {
        final LikePopRequestBean bean = new LikePopRequestBean();
        bean.json = "{\"token\":\"" + AYPrefUtils.getAuthToken() + "\",\"condition\": {\"user_id\":\"" + AYPrefUtils.getUserId() + "\",\"service_id\":\"" + service_id + "\"},\"collections\":{\"user_id\": \"" + AYPrefUtils.getUserId() + "\",\"service_id\":\"" + service_id + "\"}}";
        bean.url = DataConstant.LIKE_POP_URL;

        return OSSInfoApi.getOssInfo().flatMap(new Function<OssInfoResponseBean, Observable<LikePopResponseBean>>() {
            @Override
            public Observable<LikePopResponseBean> apply(OssInfoResponseBean b) throws Exception {
                if ("ok".equals(b.status)){
                    return execute(bean, LikePopResponseBean.class);
                }
                LikePopResponseBean sb = new LikePopResponseBean();
                sb.error = new LikePopResponseBean.ErrorBean();
                if (b!=null && b.error!=null){
                    sb.error.code = b.error.code;
                    sb.error.message = b.error.message;
                }
                return Observable.just(sb);
            }
        });

//        return execute(bean, LikePushResponseBean.class);
    }

    public static Observable<CareMoreResponseBean> getServiceMoreData(int skipCount, int takeCount, String serviceType) {
        final SearchServiceRequestBean bean = new SearchServiceRequestBean();
        bean.json = "{\"skip\" : " + skipCount + ",\"take\" : " + takeCount + ",\"token\": \"" + AYPrefUtils.getAuthToken() + "\",\"condition\": {\"user_id\":\"" + AYPrefUtils.getUserId() + "\",\"service_type\": \"" + serviceType + "\"}}";

        bean.url = DataConstant.SERVICE_MORE_URL;

        return OSSInfoApi.getOssInfo().flatMap(new Function<OssInfoResponseBean, Observable<CareMoreResponseBean>>() {
            @Override
            public Observable<CareMoreResponseBean> apply(OssInfoResponseBean b) throws Exception {
                if ("ok".equals(b.status)){
                    return execute(bean, CareMoreResponseBean.class);
                }
                CareMoreResponseBean sb = new CareMoreResponseBean();
                sb.error = new CareMoreResponseBean.ErrorBean();
                if (b!=null && b.error!=null){
                    sb.error.code = b.error.code;
                    sb.error.message = b.error.message;
                }
                return Observable.just(sb);
            }
        });

//        return execute(bean, CareMoreResponseBean.class);
    }

    public static Observable<ApplyServiceResponseBean> apply(String brand_name, String name, String category, String phone, String city) {
        final ApplyServiceRequestBean bean = new ApplyServiceRequestBean();
        bean.json = "{\"token\":\"" + AYPrefUtils.getAuthToken() + "\",\"condition\":{\"user_id\":\"" + AYPrefUtils.getUserId() + "\"},\"apply\":{\"brand_name\":\"" + brand_name + "\",\"name\":\"" + name + "\",\"category\":\"" + category + "\",\"phone\":\"" + phone + "\",\"city\":\"" + city + "\"}}";

        bean.url = DataConstant.APPLY_SERVICE_URL;

        return OSSInfoApi.getOssInfo().flatMap(new Function<OssInfoResponseBean, Observable<ApplyServiceResponseBean>>() {
            @Override
            public Observable<ApplyServiceResponseBean> apply(OssInfoResponseBean b) throws Exception {
                if ("ok".equals(b.status)){
                    return execute(bean, ApplyServiceResponseBean.class);
                }
                ApplyServiceResponseBean sb = new ApplyServiceResponseBean();
                sb.error = new ApplyServiceResponseBean.ErrorBean();
                if (b!=null && b.error!=null){
                    sb.error.code = b.error.code;
                    sb.error.message = b.error.message;
                }
                return Observable.just(sb);
            }
        });

//        return execute(bean, ApplyServiceResponseBean.class);
    }

    public static Observable<UserInfoResponseBean> queryUserInfo() {
        final UserInfoRequestBean bean = new UserInfoRequestBean();
        bean.json = "{\"token\":\"" + AYPrefUtils.getAuthToken() + "\",\"condition\":{\"user_id\":\"" + AYPrefUtils.getUserId() + "\"}}";

        bean.url = DataConstant.QUERY_USER_INFO_URL;

        return OSSInfoApi.getOssInfo().flatMap(new Function<OssInfoResponseBean, Observable<UserInfoResponseBean>>() {
            @Override
            public Observable<UserInfoResponseBean> apply(OssInfoResponseBean b) throws Exception {
                if ("ok".equals(b.status)){
                    return execute(bean, UserInfoResponseBean.class);
                }
                UserInfoResponseBean sb = new UserInfoResponseBean();
                sb.error = new UserInfoResponseBean.ErrorBean();
                if (b!=null && b.error!=null){
                    sb.error.code = b.error.code;
                    sb.error.message = b.error.message;
                }
                return Observable.just(sb);
            }
        });

//        return execute(bean, UserInfoResponseBean.class);
    }

    /**
     * 遍历品牌下的所有位置
     * @return
     */
    public static Observable<BrandAllLocResponseBean> getBrandAllLocation(String brand_id) {
        final BrandAllLocRequestBean bean = new BrandAllLocRequestBean();
        bean.json = "{\"token\":\""+AYPrefUtils.getAuthToken()+"\",\"brand_id\":\""+brand_id+"\"}";

        bean.url = DataConstant.BRAND_ALL_LOC_URL;

        return OSSInfoApi.getOssInfo().flatMap(new Function<OssInfoResponseBean, Observable<BrandAllLocResponseBean>>() {
            @Override
            public Observable<BrandAllLocResponseBean> apply(OssInfoResponseBean b) throws Exception {
                if ("ok".equals(b.status)){
                    return execute(bean, BrandAllLocResponseBean.class);
                }
                BrandAllLocResponseBean sb = new BrandAllLocResponseBean();
                sb.error = new BrandAllLocResponseBean.ErrorBean();
                if (b!=null && b.error!=null){
                    sb.error.code = b.error.code;
                    sb.error.message = b.error.message;
                }
                return Observable.just(sb);
            }
        });

//        return execute(bean, BrandAllLocResponseBean.class);
    }

    /**
     * 遍历品牌下的所有位置
     * @return
     */
    public static Observable<LocAllServiceResponseBean> getLocAllService(String json,String locations) {
        final LocAllServiceRequestBean bean = new LocAllServiceRequestBean();
        bean.json = "{\"token\":\""+AYPrefUtils.getAuthToken()+"\",\"locations\":[\""+locations+"\"]}";

        bean.url = DataConstant.LOC_ALL_SERVICE_URL;

        return OSSInfoApi.getOssInfo().flatMap(new Function<OssInfoResponseBean, Observable<LocAllServiceResponseBean>>() {
            @Override
            public Observable<LocAllServiceResponseBean> apply(OssInfoResponseBean b) throws Exception {
                if ("ok".equals(b.status)){
                    return execute(bean, LocAllServiceResponseBean.class);
                }
                LocAllServiceResponseBean sb = new LocAllServiceResponseBean();
                sb.error = new LocAllServiceResponseBean.ErrorBean();
                if (b!=null && b.error!=null){
                    sb.error.code = b.error.code;
                    sb.error.message = b.error.message;
                }
                return Observable.just(sb);
            }
        });

//        return execute(bean, LocAllServiceResponseBean.class);
    }

    /**
     * 发布招生
     * @return
     */
    public static Observable<EnrolResponseBean> enrol(String json) {
        final EnrolRequestBean bean = new EnrolRequestBean();
        bean.json = json;

        bean.url = DataConstant.ENROL_URL;

        return OSSInfoApi.getOssInfo().flatMap(new Function<OssInfoResponseBean, Observable<EnrolResponseBean>>() {
            @Override
            public Observable<EnrolResponseBean> apply(OssInfoResponseBean b) throws Exception {
                if ("ok".equals(b.status)){
                    return execute(bean, EnrolResponseBean.class);
                }
                EnrolResponseBean sb = new EnrolResponseBean();
                sb.error = new EnrolResponseBean.ErrorBean();
                if (b!=null && b.error!=null){
                    sb.error.code = b.error.code;
                    sb.error.message = b.error.message;
                }
                return Observable.just(sb);
            }
        });

//        return execute(bean, EnrolResponseBean.class);
    }

    /**
     * 获取收藏列表
     * @return
     */
    public static Observable<LikeResponseBean> getLikeData() {
        final LikeRequestBean bean = new LikeRequestBean();
        bean.json="{\"token\":\""+ AYPrefUtils.getAuthToken()+"\",\"condition\":{\"user_id\":\""+ AYPrefUtils.getUserId()+"\"}}";

        bean.url = DataConstant.LIKE_QUERY_LIST_URL;

        return OSSInfoApi.getOssInfo().flatMap(new Function<OssInfoResponseBean, Observable<LikeResponseBean>>() {
            @Override
            public Observable<LikeResponseBean> apply(OssInfoResponseBean b) throws Exception {
                if ("ok".equals(b.status)){
                    return execute(bean, LikeResponseBean.class);
                }
                LikeResponseBean sb = new LikeResponseBean();
                sb.error = new LikeResponseBean.ErrorBean();
                if (b!=null && b.error!=null){
                    sb.error.code = b.error.code;
                    sb.error.message = b.error.message;
                }
                return Observable.just(sb);
            }
        });

        //        return execute(bean, EnrolResponseBean.class);
    }

    /**
     * 获取附近的服务
     * @return
     */
    public static Observable<NearServiceResponseBean> getNearService(double latitude, double longitude) {
        final NearServiceRequestBean bean = new NearServiceRequestBean();

        bean.json="{\"token\":\""+ AYPrefUtils.getAuthToken()+"\",\"condition\":{\"user_id\":\""+ AYPrefUtils.getUserId()+"\",\"pin\":{\"latitude\":"+latitude+",\"longitude\":"+longitude+"}}}";


        bean.url = DataConstant.NEAR_SERVICE_URL;

        return OSSInfoApi.getOssInfo().flatMap(new Function<OssInfoResponseBean, Observable<NearServiceResponseBean>>() {
            @Override
            public Observable<NearServiceResponseBean> apply(OssInfoResponseBean b) throws Exception {
                if ("ok".equals(b.status)){
                    return execute(bean, NearServiceResponseBean.class);
                }
                NearServiceResponseBean sb = new NearServiceResponseBean();
                sb.error = new NearServiceResponseBean.ErrorBean();
                if (b!=null && b.error!=null){
                    sb.error.code = b.error.code;
                    sb.error.message = b.error.message;
                }
                return Observable.just(sb);
            }
        });

        //        return execute(bean, EnrolResponseBean.class);
    }

    /**
     * 详细信息
     * @return
     */
    public static Observable<DetailInfoResponseBean> getDetailInfo(String service_id) {
        final DetailInfoRequestBean bean = new DetailInfoRequestBean();

        bean.json = "{\"token\":\"" + AYPrefUtils.getAuthToken() + "\",\"condition\":{\"service_id\":\"" + service_id + "\"}}";


        bean.url = DataConstant.SERVICE_DETAIL_URL;

        return OSSInfoApi.getOssInfo().flatMap(new Function<OssInfoResponseBean, Observable<DetailInfoResponseBean>>() {
            @Override
            public Observable<DetailInfoResponseBean> apply(OssInfoResponseBean b) throws Exception {
                if ("ok".equals(b.status)){
                    return execute(bean, DetailInfoResponseBean.class);
                }
                DetailInfoResponseBean sb = new DetailInfoResponseBean();
                sb.error = new DetailInfoResponseBean.ErrorBean();
                if (b!=null && b.error!=null){
                    sb.error.code = b.error.code;
                    sb.error.message = b.error.message;
                }
                return Observable.just(sb);
            }
        });
        //        return execute(bean, EnrolResponseBean.class);
    }
}
