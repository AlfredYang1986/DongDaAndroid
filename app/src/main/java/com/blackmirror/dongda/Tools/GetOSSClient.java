package com.blackmirror.dongda.Tools;



/**
 * Created by Ruge on 2018-04-13 下午5:08
 */
public class GetOSSClient {

//    public  OSS oss;

    private GetOSSClient(){
    }

    public static class SingleHolder{
        private static final GetOSSClient INSTANCE=new GetOSSClient();
    }

    public static GetOSSClient INSTANCE(){
        return SingleHolder.INSTANCE;
    }



    public void initOSS(String accessKeyId, String secretKeyId, String securityToken) {
        /*String endpoint = "https://oss-cn-beijing.aliyuncs.com/upload";
        // 在移动端建议使用STS的方式初始化OSSClient，更多信息参考：[访问控制]
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(accessKeyId, secretKeyId, securityToken);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        oss = new OSSClient(AYApplication.getAppContext(), endpoint, credentialProvider, conf);*/

    }

}
