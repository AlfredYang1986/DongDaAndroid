package com.blackmirror.dongda.data.net;

import com.blackmirror.dongda.data.DataConstant;
import com.blackmirror.dongda.data.model.request.LikePushRequestBean;
import com.blackmirror.dongda.data.model.request.SearchServiceRequestBean;
import com.blackmirror.dongda.data.model.response.CareMoreResponseBean;
import com.blackmirror.dongda.data.model.response.LikePushResponseBean;
import com.blackmirror.dongda.data.model.response.SearchServiceResponseBean;
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
        bean.json = "{\"skip\" : " + skipCount + ",\"take\" : " + takeCount + ",\"token\": \"" + AYPrefUtils.getAuthToken() + "\",\"condition\": {\"user_id\":\"" + AYPrefUtils.getUserId() + "\",\"service_type\": \""+serviceType+"\"}}";

        bean.url = DataConstant.SERVICE_MORE_URL;

        return execute(bean, CareMoreResponseBean.class);
    }
}
