package com.blackmirror.dongda.model.serverbean;

import com.blackmirror.dongda.model.BaseBean;

import java.util.List;

/**
 * Created by Ruge on 2018-04-16 下午3:37
 */
public class ArtMoreServerBean extends BaseBean {

    /**
     * result : {"services":[{"service_tags":[""],"is_collected":false,"punchline":"9科教育,发觉孩子无限可能,孩子从现在开始爱上学。","service_leaf":"日间看顾","brand_id":"5a66fdff59a6270918509a5b","location_id":"5a66fdff59a6270918509a5c","operation":["幼小衔接","资深外教","自主教研"],"service_image":"ca186590-b61b-4401-b3aa-513ba58b89db","brand_name":"ONE ART北青","service_type":"艺术","address":"朝阳区北苑小街8号东行30米","category":"课程","pin":{"latitude":40.026084899902344,"longitude":116.43865966796875},"service_id":"5a66fdff59a6270918509a5d"},{"service_tags":["国学"],"is_collected":false,"punchline":"以国学底蕴为载体,动静结合。","service_leaf":"日间看顾","brand_id":"5a66fe0259a6270918509c66","location_id":"5a66fe0259a6270918509c67","operation":["早教","艺术家","自主教研"],"service_image":"b9510884-75da-494b-b55b-351169a610f9","brand_name":"米涂","service_type":"艺术","address":"朝阳区东风南路9号晶羽文化产业集团院内","category":"课程","pin":{"latitude":39.95439910888672,"longitude":116.50338745117188},"service_id":"5a66fe0259a6270918509c6d"}]}
     */

    public ResultBean result;

    public static class ResultBean {
        public List<ServicesBean> services;

        public static class ServicesBean {
            /**
             * service_tags : [""]
             * is_collected : false
             * punchline : 9科教育,发觉孩子无限可能,孩子从现在开始爱上学。
             * service_leaf : 日间看顾
             * brand_id : 5a66fdff59a6270918509a5b
             * location_id : 5a66fdff59a6270918509a5c
             * operation : ["幼小衔接","资深外教","自主教研"]
             * service_image : ca186590-b61b-4401-b3aa-513ba58b89db
             * brand_name : ONE ART北青
             * service_type : 艺术
             * address : 朝阳区北苑小街8号东行30米
             * category : 课程
             * pin : {"latitude":40.026084899902344,"longitude":116.43865966796875}
             * service_id : 5a66fdff59a6270918509a5d
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
                 * latitude : 40.026084899902344
                 * longitude : 116.43865966796875
                 */

                public double latitude;
                public double longitude;
            }
        }
    }
}
