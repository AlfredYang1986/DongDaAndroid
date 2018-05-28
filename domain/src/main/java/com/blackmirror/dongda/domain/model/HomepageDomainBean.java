package com.blackmirror.dongda.domain.model;

import java.util.List;

/**
 * Create By Ruge at 2018-05-11
 */
public class HomepageDomainBean extends BaseDataBean {


    /**
     * service_type : 看顾
     * totalCount : 619
     * services : [{"service_tags":["德国BMBF"],"is_collected":false,"service_leaf":"日间看顾","brand_id":"5a66fdeb59a6270918508f3a","location_id":"5a66fdeb59a6270918508f3b","operation":["低龄"],"service_image":"c58112f0-c1af-42c6-a30f-a4710b5f499d","brand_name":"柯莱特","service_type":"看顾","address":"朝阳区芳园南里9号院丽都水岸7号楼2层","category":"看顾","pin":{"latitude":39.96918487548828,"longitude":116.48291778564453},"service_id":"5a66fdeb59a6270918508f41"},{"service_tags":["蒙特梭利"],"is_collected":false,"service_leaf":"日间看顾","brand_id":"5a66fdeb59a6270918508f4c","location_id":"5a66fdeb59a6270918508f53","operation":["蒙特梭利","资深外教"],"service_image":"5c971a1c-a97e-4e93-b6cd-669b37235ba8","brand_name":"悦享时光","service_type":"看顾","address":"大兴区天华西路天华园三里六区10号楼","category":"看顾","pin":{"latitude":39.79732894897461,"longitude":116.49700927734375},"service_id":"5a66fdeb59a6270918508f54"},{"service_tags":["美国RIE"],"is_collected":false,"service_leaf":"日间看顾","brand_id":"5a66fdeb59a6270918508f6a","location_id":"5a66fdeb59a6270918508f6b","operation":["蒙特梭利","资深外教","浸入式双语","低龄"],"service_image":"b2915764-9025-4c60-8230-40ecfd73c750","brand_name":"iHommy艾荷美","service_type":"看顾","address":"朝阳区望京南湖西园南路北京香颂238号楼一层","category":"看顾","pin":{"latitude":39.9901008605957,"longitude":116.45325469970703},"service_id":"5a66fdeb59a6270918508f71"},{"service_tags":["蒙特梭利"],"is_collected":false,"service_leaf":"日间看顾","brand_id":"5a66fdee59a6270918509069","location_id":"5a66fdee59a627091850906a","operation":["蒙特梭利","浸入式双语"],"service_image":"135088b9-15bd-406b-aa37-41ff36176115","brand_name":"东方悦稚","service_type":"看顾","address":"海淀区双槐树路392号四季青敬老院旁","category":"看顾","pin":{"latitude":39.941341400146484,"longitude":116.25218963623047},"service_id":"5a66fdee59a6270918509070"},{"service_tags":["蒙特梭利"],"is_collected":false,"service_leaf":"日间看顾","brand_id":"5a66fdee59a627091850907b","location_id":"5a66fdee59a627091850907c","operation":["蒙特梭利","浸入式双语"],"service_image":"bdc382da-f3d2-4f64-b267-7f965050e62f","brand_name":"童年小院","service_type":"看顾","address":"朝阳区望京北纬40度社区东区二期12号楼1单元102","category":"看顾","pin":{"latitude":40.02293395996094,"longitude":116.46407318115234},"service_id":"5a66fdee59a6270918509082"},{"service_tags":["福沃德艺术","蒙特梭利"],"is_collected":false,"service_leaf":"日间看顾","brand_id":"5a66fded59a627091850900d","location_id":"5a66fded59a627091850900e","operation":["蒙特梭利","资深外教","浸入式双语","自主教研"],"service_image":"4bc1b5b3-3e82-4eaf-89db-55c01852a5d2","brand_name":"卡尔\u2022安娜","service_type":"看顾","address":"朝阳区观唐东路5号浩华宫2号","category":"看顾","pin":{"latitude":40.02610778808594,"longitude":116.51115417480469},"service_id":"5a66fded59a6270918509014"}]
     */

    public List<HomepageServicesBean> homepage_services;

    public static class HomepageServicesBean {

        public String service_type;
        public int totalCount;
        public List<ServicesBean> services;//对应主页的四个标签 0 看顾 1 艺术 2 运动 3 科学

        public static class ServicesBean {
            /**
             * service_tags : ["德国BMBF"]
             * is_collected : false
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

                @Override
                public String toString() {
                    return "PinBean{" +
                            "latitude=" + latitude +
                            ", longitude=" + longitude +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "ServicesBean{" +
                        "is_collected=" + is_collected +
                        ", service_leaf='" + service_leaf + '\'' +
                        ", brand_id='" + brand_id + '\'' +
                        ", location_id='" + location_id + '\'' +
                        ", service_image='" + service_image + '\'' +
                        ", brand_name='" + brand_name + '\'' +
                        ", service_type='" + service_type + '\'' +
                        ", address='" + address + '\'' +
                        ", category='" + category + '\'' +
                        ", pin=" + pin +
                        ", service_id='" + service_id + '\'' +
                        ", service_tags=" + service_tags +
                        ", operation=" + operation +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "HomepageServicesBean{" +
                    "service_type='" + service_type + '\'' +
                    ", totalCount=" + totalCount +
                    ", services=" + services +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "HomepageDomainBean{" +
                "homepage_services=" + homepage_services +
                '}';
    }
}
