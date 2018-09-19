package com.blackmirror.dongda.kdomain.model

/**
 * Create By Ruge at 2018-05-11
 */
class DetailInfoDomainBean : BaseDataBean() {
    var class_max_stu: Int = 0
    //    public LocationBean location;
    var is_collected: Boolean = false
    var description: String? = null
    var min_age: Int = 0
    var punchline: String? = null
    var teacher_num: Int = 0
    var service_leaf: String? = null
    //    public BrandBean brand;
    var max_age: Int = 0
    var service_type: String? = null
    var category: String? = null
    var album: String? = null
    var service_id: String? = null
    var service_tags: MutableList<String>? = null
    var operation: MutableList<String>? = null
    var service_images: MutableList<ServiceImagesBean>? = null

    /**
     * location_images : [{"tag":"教学区","image":"8ef2a771-033e-42c3-93af-06850db943c5"},{"tag":"教学区","image":"430043cf-9f64-4fb1-a619-63cf9d0005b1"},{"tag":"家长休息区","image":"de7cd0cf-38d6-453f-9171-4693940d5e0a"},{"tag":"寄存区","image":"2b2d1349-cee9-462b-a170-10fe90182440"},{"tag":"阅读区","image":"ce39cde8-3aad-4e77-a091-0092afb5372d"}]
     * location_id : 5a66fe0059a6270918509ab6
     * address : 朝阳区东三环中路双井富力广场四层429
     * friendliness : ["空气净化器","防摔地板","无线WI-FI"]
     * pin : {"latitude":39.89374923706055,"longitude":116.46073913574219}
     */

    var location_id: String? = null
    var address: String? = null
    var location_images: MutableList<LocationImagesBean>? = null
    var friendliness: MutableList<String>? = null

    /**
     * latitude : 39.89374923706055
     * longitude : 116.46073913574219
     */

    var latitude: Double = 0.toDouble()
    var longitude: Double = 0.toDouble()

    /**
     * BrandBean ---> brand
     * brand_id : 5a66fe0059a6270918509aa0
     * date : 1516699136189
     * brand_name : 卓美
     * brand_tag : Z
     * about_brand : 由著名导演冯小刚注资,以戏剧促进孩子成长。引入全球领先的海伦·奥格雷迪（Helen O'Grady ）英文教育戏剧（Drama in Education)课程体系,结合中国孩子和家长对教育的需求,希望更多的孩子能子在戏剧中遇见自己。青少儿英文剧团,配备国际化教师团队,开设戏剧表演及音乐剧课程,为7岁以上有表演天赋、希望系统学习戏剧表演的青少儿提供持续的成长平台。
     */
    var brand_id: String? = null
    var date: Long = 0
    var brand_name: String? = null
    var brand_tag: String? = null
    var about_brand: String? = null


    class LocationImagesBean {
        /**
         * tag : 教学区
         * image : 8ef2a771-033e-42c3-93af-06850db943c5
         */

        var tag: String? = null
        var image: String? = null
    }

    class ServiceImagesBean {
        /**
         * tag : 8
         * image : 597ec6a9-79c6-4a6f-806f-f5a58f9629f5
         */

        var tag: String? = null
        var image: String? = null

    }
}
