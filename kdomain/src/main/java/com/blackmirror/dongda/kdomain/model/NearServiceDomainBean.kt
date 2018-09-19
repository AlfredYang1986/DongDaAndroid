package com.blackmirror.dongda.kdomain.model

/**
 * Create By Ruge at 2018-05-11
 */
class NearServiceDomainBean : BaseDataBean() {
    var services: MutableList<ServicesBean>? = null

    class ServicesBean {
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

        var is_collected: Boolean = false
        var punchline: String? = null
        var service_leaf: String? = null
        var brand_id: String? = null
        var location_id: String? = null
        var service_image: String? = null
        var brand_name: String? = null
        var service_type: String? = null
        var address: String? = null
        var category: String? = null
        var pin: PinBean? = null
        var service_id: String? = null
        var service_tags: List<String>? = null
        var operation: List<String>? = null

        class PinBean {
            /**
             * latitude : 39.67801284790039
             * longitude : 116.32026672363281
             */

            var latitude: Double = 0.toDouble()
            var longitude: Double = 0.toDouble()

            override fun toString(): String {
                return "PinBean{" +
                        "latitude=" + latitude +
                        ", longitude=" + longitude +
                        '}'.toString()
            }
        }
    }
}
