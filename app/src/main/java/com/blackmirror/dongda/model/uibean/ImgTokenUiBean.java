package com.blackmirror.dongda.model.uibean;

import com.blackmirror.dongda.model.serverbean.ImgTokenServerBean; /**
 * Created by Ruge on 2018-04-13 下午5:56
 */
public class ImgTokenUiBean {
    public boolean isSuccess;
    public String accessKeyId;
    public String SecurityToken;
    public String RequestId;
    public String accessKeySecret;
    public String Expiration;


    public ImgTokenUiBean(ImgTokenServerBean bean) {
        if (bean!=null && "ok".equals(bean.status) && bean.result!=null && bean.result.OssConnectInfo!=null){
            ImgTokenServerBean.ResultBean.OssConnectInfoBean info = bean.result.OssConnectInfo;
            isSuccess=true;
            this.accessKeyId=info.accessKeyId;
            this.SecurityToken=info.SecurityToken;
            this.RequestId=info.RequestId;
            this.accessKeySecret=info.accessKeySecret;
            this.Expiration=info.Expiration;
        }
    }
}
