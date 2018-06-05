package com.blackmirror.dongda.kdomain.model;

import java.util.List;

/**
 * Create By Ruge at 2018-05-11
 */
public class NearServiceDomainBean extends BaseDataBean {
    public List<ServicesBean> services;

    public static class ServicesBean {
        /**
         * service_tags : ["国学"]
         * is_collected : false
         * punchline : 以国学底蕴为载体,动静结合。
         * service_leaf : 日间看顾
         * brand_id : 5a66fe0259a6270918509c66
         * location_id : 5a66fe0259a6270918509c72
         * operation : ["早教","自主教研","艺术家"]
         * service_image : 003efc51-aa79-45f3-8117-d42ddbe0396a
         * brand_name : 米涂
         * service_type : 艺术
         * address : 大兴区华佗路1号院106融汇小区西门底商
         * category : 课程
         * pin : {"latitude":39.67801284790039,"longitude":116.32026672363281}
         * service_id : 5a66fe0259a6270918509c78
         */

        public boolean is_collected;
        public String punchline;
        public String service_leaf;
        public String brand_id;
        public String location_id;
        public String service_image;
        public String brand_name;
        public String service_type;
        public String address;
        public String category;
        public PinBean pin;
        public String service_id;
        public List<String> service_tags;
        public List<String> operation;

        public static class PinBean {
            /**
             * latitude : 39.67801284790039
             * longitude : 116.32026672363281
             */

            public double latitude;
            public double longitude;

            @Override
            public String toString() {
                return "PinBean{" +
                        "latitude=" + latitude +
                        ", longitude=" + longitude +
                        '}';
            }
        }
    }
}
