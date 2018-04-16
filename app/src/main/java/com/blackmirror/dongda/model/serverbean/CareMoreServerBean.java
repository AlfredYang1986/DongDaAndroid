package com.blackmirror.dongda.model.serverbean;

import com.blackmirror.dongda.model.BaseBean;

import java.util.List;

/**
 * Created by Ruge on 2018-04-16 下午12:14
 */
public class CareMoreServerBean extends BaseBean {

    /**
     * result : {"services":[{"service_tags":["德国BMBF"],"is_collected":false,"punchline":"一起来过\u201c发现自我\u201d,陪\u201c摇晃期\u201d宝贝稳稳向前走。","service_leaf":"日间看顾","brand_id":"5a66fdeb59a6270918508f3a","location_id":"5a66fdeb59a6270918508f3b","operation":["低龄"],"service_image":"c58112f0-c1af-42c6-a30f-a4710b5f499d","brand_name":"柯莱特","service_type":"看顾","address":"朝阳区芳园南里9号院丽都水岸7号楼2层","category":"看顾","pin":{"latitude":39.96918487548828,"longitude":116.48291778564453},"service_id":"5a66fdeb59a6270918508f41"}]}
     */

    public ResultBean result;

    public static class ResultBean {
        public List<ServicesBean> services;

        public static class ServicesBean {
            /**
             * service_tags : ["德国BMBF"]
             * is_collected : false
             * punchline : 一起来过“发现自我”,陪“摇晃期”宝贝稳稳向前走。
             * service_leaf : 日间看顾
             * brand_id : 5a66fdeb59a6270918508f3a
             * location_id : 5a66fdeb59a6270918508f3b
             * operation : ["低龄"]
             * service_image : c58112f0-c1af-42c6-a30f-a4710b5f499d
             * brand_name : 柯莱特
             * service_type : 看顾
             * address : 朝阳区芳园南里9号院丽都水岸7号楼2层
             * category : 看顾
             * pin : {"latitude":39.96918487548828,"longitude":116.48291778564453}
             * service_id : 5a66fdeb59a6270918508f41
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
                 * latitude : 39.96918487548828
                 * longitude : 116.48291778564453
                 */

                public double latitude;
                public double longitude;
            }
        }
    }
}
