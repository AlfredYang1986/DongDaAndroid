package com.blackmirror.dongda.data.model.response

import java.io.Serializable

/**
 * Created by Ruge on 2018-04-16 下午12:14
 */
class CareMoreResponseBean : BaseResponseBean() {

    /**
     * result : {"services":[{"service_tags":["德国BMBF"],"is_collected":false,"punchline":"一起来过\u201c发现自我\u201d,陪\u201c摇晃期\u201d宝贝稳稳向前走。","service_leaf":"日间看顾","brand_id":"5a66fdeb59a6270918508f3a","location_id":"5a66fdeb59a6270918508f3b","operation":["低龄"],"service_image":"c58112f0-c1af-42c6-a30f-a4710b5f499d","brand_name":"柯莱特","service_type":"看顾","address":"朝阳区芳园南里9号院丽都水岸7号楼2层","category":"看顾","pin":{"latitude":39.96918487548828,"longitude":116.48291778564453},"service_id":"5a66fdeb59a6270918508f41"}]}
     */

    var result: ResultBean? = null

    class ResultBean : Serializable {
        var services: List<ServicesBean>? = null

        class ServicesBean : Serializable {
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

            class PinBean : Serializable {
                /**
                 * latitude : 39.96918487548828
                 * longitude : 116.48291778564453
                 */

                var latitude: Double = 0.toDouble()
                var longitude: Double = 0.toDouble()
            }
        }
    }
}
