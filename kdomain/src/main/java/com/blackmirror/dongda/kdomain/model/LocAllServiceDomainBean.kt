package com.blackmirror.dongda.kdomain.model

/**
 * Create By Ruge at 2018-05-15
 */
class LocAllServiceDomainBean : BaseDataBean() {
    var services: List<ServicesBean>? = null

    class ServicesBean {
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

        var punchline: String? = null
        var service_leaf: String? = null
        var service_image: String? = null
        var service_type: String? = null
        var category: String? = null
        var service_id: String? = null
        var service_tags: List<String>? = null
        var operation: List<String>? = null

        override fun toString(): String {
            return "ServicesBean{" +
                    "punchline='" + punchline + '\''.toString() +
                    ", service_leaf='" + service_leaf + '\''.toString() +
                    ", service_image='" + service_image + '\''.toString() +
                    ", service_type='" + service_type + '\''.toString() +
                    ", category='" + category + '\''.toString() +
                    ", service_id='" + service_id + '\''.toString() +
                    ", service_tags=" + service_tags +
                    ", operation=" + operation +
                    '}'.toString()
        }
    }

    override fun toString(): String {
        return "LocAllServiceDomainBean{" +
                "services=" + services +
                ", code=" + code +
                ", message='" + message + '\''.toString() +
                ", isSuccess=" + isSuccess +
                '}'.toString()
    }
}
