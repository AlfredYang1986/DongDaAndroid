package com.blackmirror.dongda.data.net;

import com.blackmirror.dongda.data.DataConstant;
import com.blackmirror.dongda.data.model.request.ApplyServiceRequestBean;
import com.blackmirror.dongda.data.model.request.BrandAllLocRequestBean;
import com.blackmirror.dongda.data.model.request.EnrolRequestBean;
import com.blackmirror.dongda.data.model.request.LikePushRequestBean;
import com.blackmirror.dongda.data.model.request.LocAllServiceRequestBean;
import com.blackmirror.dongda.data.model.request.SearchServiceRequestBean;
import com.blackmirror.dongda.data.model.request.UserInfoRequestBean;
import com.blackmirror.dongda.data.model.response.ApplyServiceResponseBean;
import com.blackmirror.dongda.data.model.response.BrandAllLocResponseBean;
import com.blackmirror.dongda.data.model.response.CareMoreResponseBean;
import com.blackmirror.dongda.data.model.response.EnrolResponseBean;
import com.blackmirror.dongda.data.model.response.LikePushResponseBean;
import com.blackmirror.dongda.data.model.response.LocAllServiceResponseBean;
import com.blackmirror.dongda.data.model.response.SearchServiceResponseBean;
import com.blackmirror.dongda.data.model.response.UserInfoResponseBean;
import com.blackmirror.dongda.utils.AYPrefUtils;

import io.reactivex.Observable;

/**
 * Create By Ruge at 2018-05-10
 */
public class CommonApi extends AYRemoteApi {
    public static Observable<SearchServiceResponseBean> searchService() {
        SearchServiceRequestBean bean = new SearchServiceRequestBean();
        bean.json = "{ \"token\": \"" + AYPrefUtils.getAuthToken() + "\", \"condition\": { \"user_id\": \"" + AYPrefUtils.getUserId() + "\", \"service_type_list\": [{ \"service_type\": \"看顾\", \"count\": 6 }, { \"service_type\": \"艺术\", \"count\": 4 }, { \"service_type\": \"运动\", \"count\": 4 }, { \"service_type\": \"科学\", \"count\": 4 }]}}";
        bean.url = DataConstant.HOME_PAGE_URL;

        return execute(bean, SearchServiceResponseBean.class);
    }

    public static Observable<LikePushResponseBean> likePush(String service_id) {
        LikePushRequestBean bean = new LikePushRequestBean();
        bean.json = "{\"token\":\"" + AYPrefUtils.getAuthToken() + "\",\"condition\": {\"user_id\":\"" + AYPrefUtils.getUserId() + "\",\"service_id\":\"" + service_id + "\"},\"collections\":{\"user_id\": \"" + AYPrefUtils.getUserId() + "\",\"service_id\":\"" + service_id + "\"}}";
        bean.url = DataConstant.LIKE_PUSH_URL;

        return execute(bean, LikePushResponseBean.class);
    }

    public static Observable<LikePushResponseBean> likePop(String service_id) {
        LikePushRequestBean bean = new LikePushRequestBean();
        bean.json = "{\"token\":\"" + AYPrefUtils.getAuthToken() + "\",\"condition\": {\"user_id\":\"" + AYPrefUtils.getUserId() + "\",\"service_id\":\"" + service_id + "\"},\"collections\":{\"user_id\": \"" + AYPrefUtils.getUserId() + "\",\"service_id\":\"" + service_id + "\"}}";
        bean.url = DataConstant.LIKE_POP_URL;

        return execute(bean, LikePushResponseBean.class);
    }

    public static Observable<CareMoreResponseBean> getServiceMoreData(int skipCount, int takeCount, String serviceType) {
        SearchServiceRequestBean bean = new SearchServiceRequestBean();
        bean.json = "{\"skip\" : " + skipCount + ",\"take\" : " + takeCount + ",\"token\": \"" + AYPrefUtils.getAuthToken() + "\",\"condition\": {\"user_id\":\"" + AYPrefUtils.getUserId() + "\",\"service_type\": \"" + serviceType + "\"}}";

        bean.url = DataConstant.SERVICE_MORE_URL;

        return execute(bean, CareMoreResponseBean.class);
    }

    public static Observable<ApplyServiceResponseBean> apply(String brand_name, String name, String category, String phone, String city) {
        ApplyServiceRequestBean bean = new ApplyServiceRequestBean();
        bean.json = "{\"token\":\"" + AYPrefUtils.getAuthToken() + "\",\"condition\":{\"user_id\":\"" + AYPrefUtils.getUserId() + "\"},\"apply\":{\"brand_name\":\"" + brand_name + "\",\"name\":\"" + name + "\",\"category\":\"" + category + "\",\"phone\":\"" + phone + "\",\"city\":\"" + city + "\"}}";

        bean.url = DataConstant.APPLY_SERVICE_URL;

        return execute(bean, ApplyServiceResponseBean.class);
    }

    public static Observable<UserInfoResponseBean> queryUserInfo() {
        UserInfoRequestBean bean = new UserInfoRequestBean();
        bean.json = "{\"token\":\"" + AYPrefUtils.getAuthToken() + "\",\"condition\":{\"user_id\":\"" + AYPrefUtils.getUserId() + "\"}}";

        bean.url = DataConstant.QUERY_USER_INFO_URL;

        return execute(bean, UserInfoResponseBean.class);
    }

    /**
     * 遍历品牌下的所有位置
     * @return
     */
    public static Observable<BrandAllLocResponseBean> getBrandAllLocation(String brand_id) {
        BrandAllLocRequestBean bean = new BrandAllLocRequestBean();
        bean.json = "{\"token\":\""+AYPrefUtils.getAuthToken()+"\",\"brand_id\":\""+brand_id+"\"}";

        bean.url = DataConstant.BRAND_ALL_LOC_URL;

        return execute(bean, BrandAllLocResponseBean.class);
    }

    /**
     * 遍历品牌下的所有位置
     * @return
     */
    public static Observable<LocAllServiceResponseBean> getLocAllService(String json,String locations) {
        LocAllServiceRequestBean bean = new LocAllServiceRequestBean();
        bean.json = "{\"token\":\""+AYPrefUtils.getAuthToken()+"\",\"locations\":[\""+locations+"\"]}";

        bean.url = DataConstant.LOC_ALL_SERVICE_URL;

        return execute(bean, LocAllServiceResponseBean.class);
    }

    /**
     * 发布招生
     * @return
     */
    public static Observable<EnrolResponseBean> enrol(String json) {
        EnrolRequestBean bean = new EnrolRequestBean();
        bean.json = json;

        bean.url = DataConstant.ENROL_URL;

        return execute(bean, EnrolResponseBean.class);
    }
}
