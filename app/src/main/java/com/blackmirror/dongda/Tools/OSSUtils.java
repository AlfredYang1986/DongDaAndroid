package com.blackmirror.dongda.Tools;

import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;

/**
 * Created by Ruge on 2018-04-16 上午11:35
 */
public class OSSUtils {

    public static OSS oss;

    public static String getSignedUrl(String imgUrl){
        return getSignedUrl(imgUrl, 30*60);
    }

    public static String getSignedUrl(String imgUrl,long time){
        long t= System.currentTimeMillis()/1000+time;
        String verb="GET\n";
        String Content_MD5="\n";
        String Content_Type="\n";
        String Date=t+"\n";
        String ss="/bm-dongda/"+imgUrl+".jpg?security-token=";


        StringBuilder sb = new StringBuilder();
        sb.append(verb)
                .append(Content_MD5)
                .append(Content_Type)
                .append(Date)
                .append(ss)
                .append(BasePrefUtils.getSecurityToken());
        String sign = sign( BasePrefUtils.getAccesskeySecret(), sb.toString());
        StringBuilder sb2=new StringBuilder();

        sb2.append("https://bm-dongda.oss-cn-beijing.aliyuncs.com/"+imgUrl+".jpg?Expires=".trim())
                .append(t)
                .append("&OSSAccessKeyId=".trim())
                .append(BasePrefUtils.getAccesskeyId())
                .append("&Signature=")
                .append(HttpUtil.urlEncode(sign, AppConstant.DEFAULT_CHARSET_NAME))
                .append("&security-token=")
                .append(HttpUtil.urlEncode(BasePrefUtils.getSecurityToken(), AppConstant.DEFAULT_CHARSET_NAME));
        return sb2.toString();
    }

    /**
     * 根据ak/sk、content生成token
     *
     * @param screctKey
     * @param content
     * @return
     */
    public static String sign(String screctKey, String content) {

        String signature;

        try {
            signature = new HmacSHA1Signature().computeSignature(screctKey, content);
            signature = signature.trim();
        } catch (Exception e) {
            throw new IllegalStateException("Compute signature failed!", e);
        }
//        Log.d("xcx","signature "+signature);


        return  signature;
    }

    public static OSS getOSS(){
        if (oss==null){
            initOSS(AYApplication.getAppConext(),BasePrefUtils.getAccesskeyId(),BasePrefUtils.getAccesskeySecret(),BasePrefUtils.getSecurityToken());
        }
        return oss;
    }

    public static void initOSS(Context context,String accessKeyId, String secretKeyId, String securityToken) {
        String endpoint = "https://oss-cn-beijing.aliyuncs.com/upload";
        // 在移动端建议使用STS的方式初始化OSSClient，更多信息参考：[访问控制]
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(accessKeyId, secretKeyId, securityToken);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        oss = new OSSClient(context, endpoint, credentialProvider, conf);
    }

    public static void destoryOSS(){
        oss=null;
    }

}
