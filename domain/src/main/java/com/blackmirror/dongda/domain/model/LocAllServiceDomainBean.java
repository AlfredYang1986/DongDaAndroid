package com.blackmirror.dongda.domain.model;

import java.util.List;

/**
 * Create By Ruge at 2018-05-15
 */
public class LocAllServiceDomainBean extends BaseDataBean {
    public List<ServicesBean> services;

    public static class ServicesBean {
        /**
         * service_tags : ["多元智能"]
         * punchline : 让孩子多一份对不同文化的理解和习得,丰富学前孩子的体验,打开世界多元文化的窗口。
         * service_leaf : 日间看顾
         * operation : ["幼小衔接"]
         * service_image : 57d7dd88-a04e-43ea-9766-aa12df22a58e
         * service_type : 看顾
         * category : 看顾
         * service_id : 5a66fdea59a6270918508f2c
         */

        public String punchline;
        public String service_leaf;
        public String service_image;
        public String service_type;
        public String category;
        public String service_id;
        public List<String> service_tags;
        public List<String> operation;

        @Override
        public String toString() {
            return "ServicesBean{" +
                    "punchline='" + punchline + '\'' +
                    ", service_leaf='" + service_leaf + '\'' +
                    ", service_image='" + service_image + '\'' +
                    ", service_type='" + service_type + '\'' +
                    ", category='" + category + '\'' +
                    ", service_id='" + service_id + '\'' +
                    ", service_tags=" + service_tags +
                    ", operation=" + operation +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LocAllServiceDomainBean{" +
                "services=" + services +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", isSuccess=" + isSuccess +
                '}';
    }
}
