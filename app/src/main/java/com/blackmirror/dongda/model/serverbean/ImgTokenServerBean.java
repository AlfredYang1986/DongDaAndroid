package com.blackmirror.dongda.model.serverbean;

import com.blackmirror.dongda.model.BaseServerBean;

import java.io.Serializable;

/**
 * Created by Ruge on 2018-04-13 下午5:53
 */
public class ImgTokenServerBean extends BaseServerBean {


    /**
     * result : {"OssConnectInfo":{"accessKeyId":"STS.JhPXNQ5LRqaB95uJv3tZQMiWE","SecurityToken":"CAIShQJ1q6Ft5B2yfSjIq6vlE/Tl2JNzxqOpOxPErnZmeNV9oqz8pzz2IHlMfnVhAe0asv03lGtR6PgflqJ5T5ZORknFd9F39My3KZA6oM6T1fau5Jko1beHewHKeTOZsebWZ+LmNqC/Ht6md1HDkAJq3LL+bk/Mdle5MJqP+/UFB5ZtKWveVzddA8pMLQZPsdITMWCrVcygKRn3mGHdfiEK00he8TohuPrimJDDsEWG0Aahk7Yvyt6vcsT+Xa5FJ4xiVtq55utye5fa3TRYgxowr/4u1vAVoWqb4ojFUwIIvUvbKZnd9tx+MQl+fbMmHK1Jqvfxk/Bis/DUjZ7wzxtduhT90f5oresagAF8vJRFBCC8aXWC+be9dtGfvRH+pbLhbv9AVL/YFNuDg/86DwOF/ZxYrMzTg5DGXvglMH3ssP+rtYzihxVjLTwSlcnikTA3h8UwA7hOVbgwYKBZqJQ80ZcrerG6JvtpluPXL23tyKvL1dgcADuLxtZIlyHzi5K7cUkvFqz+px45wg==","RequestId":"9E266ADA-6FC4-4DB5-A576-2C7E7CBAA4CD","accessKeySecret":"FBZnNyehh5LVBY2Ye1u5Z7vVeYVS3gv7wbKfPzaFZNzQ","Expiration":"2018-04-13T10:13:26Z"}}
     */

    public ResultBean result;

    public static class ResultBean implements Serializable{
        /**
         * OssConnectInfo : {"accessKeyId":"STS.JhPXNQ5LRqaB95uJv3tZQMiWE","SecurityToken":"CAIShQJ1q6Ft5B2yfSjIq6vlE/Tl2JNzxqOpOxPErnZmeNV9oqz8pzz2IHlMfnVhAe0asv03lGtR6PgflqJ5T5ZORknFd9F39My3KZA6oM6T1fau5Jko1beHewHKeTOZsebWZ+LmNqC/Ht6md1HDkAJq3LL+bk/Mdle5MJqP+/UFB5ZtKWveVzddA8pMLQZPsdITMWCrVcygKRn3mGHdfiEK00he8TohuPrimJDDsEWG0Aahk7Yvyt6vcsT+Xa5FJ4xiVtq55utye5fa3TRYgxowr/4u1vAVoWqb4ojFUwIIvUvbKZnd9tx+MQl+fbMmHK1Jqvfxk/Bis/DUjZ7wzxtduhT90f5oresagAF8vJRFBCC8aXWC+be9dtGfvRH+pbLhbv9AVL/YFNuDg/86DwOF/ZxYrMzTg5DGXvglMH3ssP+rtYzihxVjLTwSlcnikTA3h8UwA7hOVbgwYKBZqJQ80ZcrerG6JvtpluPXL23tyKvL1dgcADuLxtZIlyHzi5K7cUkvFqz+px45wg==","RequestId":"9E266ADA-6FC4-4DB5-A576-2C7E7CBAA4CD","accessKeySecret":"FBZnNyehh5LVBY2Ye1u5Z7vVeYVS3gv7wbKfPzaFZNzQ","Expiration":"2018-04-13T10:13:26Z"}
         */

        public OssConnectInfoBean OssConnectInfo;

        public static class OssConnectInfoBean implements Serializable{
            /**
             * accessKeyId : STS.JhPXNQ5LRqaB95uJv3tZQMiWE
             * SecurityToken : CAIShQJ1q6Ft5B2yfSjIq6vlE/Tl2JNzxqOpOxPErnZmeNV9oqz8pzz2IHlMfnVhAe0asv03lGtR6PgflqJ5T5ZORknFd9F39My3KZA6oM6T1fau5Jko1beHewHKeTOZsebWZ+LmNqC/Ht6md1HDkAJq3LL+bk/Mdle5MJqP+/UFB5ZtKWveVzddA8pMLQZPsdITMWCrVcygKRn3mGHdfiEK00he8TohuPrimJDDsEWG0Aahk7Yvyt6vcsT+Xa5FJ4xiVtq55utye5fa3TRYgxowr/4u1vAVoWqb4ojFUwIIvUvbKZnd9tx+MQl+fbMmHK1Jqvfxk/Bis/DUjZ7wzxtduhT90f5oresagAF8vJRFBCC8aXWC+be9dtGfvRH+pbLhbv9AVL/YFNuDg/86DwOF/ZxYrMzTg5DGXvglMH3ssP+rtYzihxVjLTwSlcnikTA3h8UwA7hOVbgwYKBZqJQ80ZcrerG6JvtpluPXL23tyKvL1dgcADuLxtZIlyHzi5K7cUkvFqz+px45wg==
             * RequestId : 9E266ADA-6FC4-4DB5-A576-2C7E7CBAA4CD
             * accessKeySecret : FBZnNyehh5LVBY2Ye1u5Z7vVeYVS3gv7wbKfPzaFZNzQ
             * Expiration : 2018-04-13T10:13:26Z
             */

            public String accessKeyId;
            public String SecurityToken;
            public String RequestId;
            public String accessKeySecret;
            public String Expiration;
        }
    }
}
