package com.blackmirror.dongda.data.model.response

import java.io.Serializable

/**
 * Create By Ruge at 2018-05-15
 */
class LocAllServiceResponseBean : BaseResponseBean() {


    /**
     * result : {"services":[{"service_tags":["多元智能"],"punchline":"让孩子多一份对不同文化的理解和习得,丰富学前孩子的体验,打开世界多元文化的窗口。","service_leaf":"日间看顾","operation":["幼小衔接"],"service_image":"57d7dd88-a04e-43ea-9766-aa12df22a58e","service_type":"看顾","category":"看顾","service_id":"5a66fdea59a6270918508f2c"},{"service_tags":["多元智能"],"punchline":"爱是一切教育的灵魂,为孩子们创造全英文Daycare环境。","service_leaf":"日间看顾","operation":[""],"service_image":"787890c8-ac2b-4fd1-bd5d-535907a3c54e","service_type":"看顾","category":"看顾","service_id":"5a66fdea59a6270918508f27"}]}
     */

    var result: ResultBean? = null

    class ResultBean : Serializable {
        var services: List<ServicesBean>? = null

        class ServicesBean : Serializable {
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
            var service_tags: MutableList<String>? = null
            var operation: MutableList<String>? = null
        }
    }
}
