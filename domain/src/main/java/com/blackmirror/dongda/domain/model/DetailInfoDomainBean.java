package com.blackmirror.dongda.domain.model;

import java.util.List;

/**
 * Create By Ruge at 2018-05-11
 */
public class DetailInfoDomainBean extends BaseDataBean {
    public int class_max_stu;
//    public LocationBean location;
    public boolean is_collected;
    public String description;
    public int min_age;
    public String punchline;
    public int teacher_num;
    public String service_leaf;
//    public BrandBean brand;
    public int max_age;
    public String service_type;
    public String category;
    public String album;
    public String service_id;
    public List<String> service_tags;
    public List<String> operation;
    public List<ServiceImagesBean> service_images;

    /**
     * location_images : [{"tag":"教学区","image":"8ef2a771-033e-42c3-93af-06850db943c5"},{"tag":"教学区","image":"430043cf-9f64-4fb1-a619-63cf9d0005b1"},{"tag":"家长休息区","image":"de7cd0cf-38d6-453f-9171-4693940d5e0a"},{"tag":"寄存区","image":"2b2d1349-cee9-462b-a170-10fe90182440"},{"tag":"阅读区","image":"ce39cde8-3aad-4e77-a091-0092afb5372d"}]
     * location_id : 5a66fe0059a6270918509ab6
     * address : 朝阳区东三环中路双井富力广场四层429
     * friendliness : ["空气净化器","防摔地板","无线WI-FI"]
     * pin : {"latitude":39.89374923706055,"longitude":116.46073913574219}
     */

    public String location_id;
    public String address;
    public List<LocationImagesBean> location_images;
    public List<String> friendliness;

    /**
     * latitude : 39.89374923706055
     * longitude : 116.46073913574219
     */

    public double latitude;
    public double longitude;

    /**
     * BrandBean ---> brand
     * brand_id : 5a66fe0059a6270918509aa0
     * date : 1516699136189
     * brand_name : 卓美
     * brand_tag : Z
     * about_brand : 由著名导演冯小刚注资,以戏剧促进孩子成长。引入全球领先的海伦·奥格雷迪（Helen O'Grady ）英文教育戏剧（Drama in Education)课程体系,结合中国孩子和家长对教育的需求,希望更多的孩子能子在戏剧中遇见自己。青少儿英文剧团,配备国际化教师团队,开设戏剧表演及音乐剧课程,为7岁以上有表演天赋、希望系统学习戏剧表演的青少儿提供持续的成长平台。
     */
    public String brand_id;
    public long date;
    public String brand_name;
    public String brand_tag;
    public String about_brand;



    public static class LocationImagesBean {
        /**
         * tag : 教学区
         * image : 8ef2a771-033e-42c3-93af-06850db943c5
         */

        public String tag;
        public String image;
    }

    public static class ServiceImagesBean {
        /**
         * tag : 8
         * image : 597ec6a9-79c6-4a6f-806f-f5a58f9629f5
         */

        public String tag;
        public String image;

    }
}
