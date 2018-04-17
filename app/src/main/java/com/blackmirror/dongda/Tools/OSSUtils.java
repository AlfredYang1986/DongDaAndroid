package com.blackmirror.dongda.Tools;

import android.util.Log;

/**
 * Created by Ruge on 2018-04-16 上午11:35
 */
public class OSSUtils {

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
        Log.d("xcx","signature "+signature);


        return  signature;
    }
}
