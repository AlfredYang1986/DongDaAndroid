package com.blackmirror.dongda.data.model.response

import java.io.Serializable

/**
 * Create By Ruge at 2018-05-15
 */
class BrandAllLocResponseBean : BaseResponseBean() {


    /**
     * result : {"locations":[{"location_images":[{"tag":"生活区","image":"654e1931-91f6-4e29-8a0f-58c505cf2c4b"},{"tag":"阅读区","image":"cb45c31c-f3fd-4b4e-b1c3-ddde845a2505"}],"location_id":"5a66fdea59a6270918508f26","address":"东城区永定门西滨河路8号院1号楼中海紫御公馆底商1-17号","friendliness":["新风系统","实时监控"],"pin":{"latitude":39.87063980102539,"longitude":116.39083862304688}}]}
     */

    var result: ResultBean? = null

    class ResultBean : Serializable {
        var locations: MutableList<LocationsBean>? = null

        class LocationsBean : Serializable {
            /**
             * location_images : [{"tag":"生活区","image":"654e1931-91f6-4e29-8a0f-58c505cf2c4b"},{"tag":"阅读区","image":"cb45c31c-f3fd-4b4e-b1c3-ddde845a2505"}]
             * location_id : 5a66fdea59a6270918508f26
             * address : 东城区永定门西滨河路8号院1号楼中海紫御公馆底商1-17号
             * friendliness : ["新风系统","实时监控"]
             * pin : {"latitude":39.87063980102539,"longitude":116.39083862304688}
             */

            var location_id: String? = null
            var address: String? = null
            var pin: PinBean? = null
            var location_images: MutableList<LocationImagesBean>? = null
            var friendliness: MutableList<String>? = null

            class PinBean : Serializable {
                /**
                 * latitude : 39.87063980102539
                 * longitude : 116.39083862304688
                 */

                var latitude: Double = 0.toDouble()
                var longitude: Double = 0.toDouble()
            }

            class LocationImagesBean : Serializable {
                /**
                 * tag : 生活区
                 * image : 654e1931-91f6-4e29-8a0f-58c505cf2c4b
                 */

                var tag: String? = null
                var image: String? = null
            }
        }
    }
}
