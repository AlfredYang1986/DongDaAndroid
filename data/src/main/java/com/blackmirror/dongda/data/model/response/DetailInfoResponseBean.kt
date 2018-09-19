package com.blackmirror.dongda.data.model.response

import com.blackmirror.dongda.utils.isNumber
import java.io.Serializable

/**
 * Create By Ruge at 2018-05-15
 */
class DetailInfoResponseBean : BaseResponseBean() {

    /**
     * result : {"service":{"class_max_stu":12,"service_tags":[""],"location":{"location_images":[{"tag":"教学区","image":"8ef2a771-033e-42c3-93af-06850db943c5"},{"tag":"教学区","image":"430043cf-9f64-4fb1-a619-63cf9d0005b1"},{"tag":"家长休息区","image":"de7cd0cf-38d6-453f-9171-4693940d5e0a"},{"tag":"寄存区","image":"2b2d1349-cee9-462b-a170-10fe90182440"},{"tag":"阅读区","image":"ce39cde8-3aad-4e77-a091-0092afb5372d"}],"location_id":"5a66fe0059a6270918509ab6","address":"朝阳区东三环中路双井富力广场四层429","friendliness":["空气净化器","防摔地板","无线WI-FI"],"pin":{"latitude":39.89374923706055,"longitude":116.46073913574219}},"is_collected":false,"description":"HOG课程Lower Primary社会化起步阶段是语言与创造性表达的融合。采用英国课程体系、英国总部指派外教、双语授课模式。通过生动有趣的戏剧活动帮孩子建立第二语言思维认知,让孩子开口讲英文。课程内容：热身洗脑、发音练习、形体韵律、即兴表演、情境对话、戏剧游戏。","min_age":4,"punchline":"尊重孩子独特性,让快乐大于学习。","teacher_num":2,"service_leaf":"戏剧","brand":{"brand_id":"5a66fe0059a6270918509aa0","date":1516699136189,"brand_name":"卓美","brand_tag":"Z","about_brand":"由著名导演冯小刚注资,以戏剧促进孩子成长。引入全球领先的海伦·奥格雷迪（Helen O'Grady ）英文教育戏剧（Drama in Education)课程体系,结合中国孩子和家长对教育的需求,希望更多的孩子能子在戏剧中遇见自己。青少儿英文剧团,配备国际化教师团队,开设戏剧表演及音乐剧课程,为7岁以上有表演天赋、希望系统学习戏剧表演的青少儿提供持续的成长平台。"},"operation":["艺术家","资深外教","自主教研","明星"],"max_age":6,"service_images":[{"tag":"8","image":"597ec6a9-79c6-4a6f-806f-f5a58f9629f5"},{"tag":"4","image":"d9b42bed-36cd-46a1-bafa-a5db388b8ad4"},{"tag":"5","image":"cc146093-12a4-4a26-9dd0-cce5969404e1"},{"tag":"6","image":"b910e17e-0d57-4dd3-aac2-ff98aeb8575f"},{"tag":"1","image":"897750fb-b285-4d0d-8170-9e5383de90f2"},{"tag":"2","image":"ab0fca51-0f80-45b6-8a4e-3d9f94a6bd10"},{"tag":"7","image":"03a7c923-e4a3-4d71-a1bb-a6d20e5c3c92"},{"tag":"3","image":"209cd0fe-4af2-41f1-9e02-85ef22bc10ac"}],"service_type":"艺术","category":"课程","album":"","service_id":"5a66fe0059a6270918509abc"}}
     */

    var result: ResultBean? = null

    class ResultBean : Serializable {
        /**
         * service : {"class_max_stu":12,"service_tags":[""],"location":{"location_images":[{"tag":"教学区","image":"8ef2a771-033e-42c3-93af-06850db943c5"},{"tag":"教学区","image":"430043cf-9f64-4fb1-a619-63cf9d0005b1"},{"tag":"家长休息区","image":"de7cd0cf-38d6-453f-9171-4693940d5e0a"},{"tag":"寄存区","image":"2b2d1349-cee9-462b-a170-10fe90182440"},{"tag":"阅读区","image":"ce39cde8-3aad-4e77-a091-0092afb5372d"}],"location_id":"5a66fe0059a6270918509ab6","address":"朝阳区东三环中路双井富力广场四层429","friendliness":["空气净化器","防摔地板","无线WI-FI"],"pin":{"latitude":39.89374923706055,"longitude":116.46073913574219}},"is_collected":false,"description":"HOG课程Lower Primary社会化起步阶段是语言与创造性表达的融合。采用英国课程体系、英国总部指派外教、双语授课模式。通过生动有趣的戏剧活动帮孩子建立第二语言思维认知,让孩子开口讲英文。课程内容：热身洗脑、发音练习、形体韵律、即兴表演、情境对话、戏剧游戏。","min_age":4,"punchline":"尊重孩子独特性,让快乐大于学习。","teacher_num":2,"service_leaf":"戏剧","brand":{"brand_id":"5a66fe0059a6270918509aa0","date":1516699136189,"brand_name":"卓美","brand_tag":"Z","about_brand":"由著名导演冯小刚注资,以戏剧促进孩子成长。引入全球领先的海伦·奥格雷迪（Helen O'Grady ）英文教育戏剧（Drama in Education)课程体系,结合中国孩子和家长对教育的需求,希望更多的孩子能子在戏剧中遇见自己。青少儿英文剧团,配备国际化教师团队,开设戏剧表演及音乐剧课程,为7岁以上有表演天赋、希望系统学习戏剧表演的青少儿提供持续的成长平台。"},"operation":["艺术家","资深外教","自主教研","明星"],"max_age":6,"service_images":[{"tag":"8","image":"597ec6a9-79c6-4a6f-806f-f5a58f9629f5"},{"tag":"4","image":"d9b42bed-36cd-46a1-bafa-a5db388b8ad4"},{"tag":"5","image":"cc146093-12a4-4a26-9dd0-cce5969404e1"},{"tag":"6","image":"b910e17e-0d57-4dd3-aac2-ff98aeb8575f"},{"tag":"1","image":"897750fb-b285-4d0d-8170-9e5383de90f2"},{"tag":"2","image":"ab0fca51-0f80-45b6-8a4e-3d9f94a6bd10"},{"tag":"7","image":"03a7c923-e4a3-4d71-a1bb-a6d20e5c3c92"},{"tag":"3","image":"209cd0fe-4af2-41f1-9e02-85ef22bc10ac"}],"service_type":"艺术","category":"课程","album":"","service_id":"5a66fe0059a6270918509abc"}
         */

        var service: ServiceBean? = null

        class ServiceBean : Serializable {
            /**
             * class_max_stu : 12
             * service_tags : [""]
             * location : {"location_images":[{"tag":"教学区","image":"8ef2a771-033e-42c3-93af-06850db943c5"},{"tag":"教学区","image":"430043cf-9f64-4fb1-a619-63cf9d0005b1"},{"tag":"家长休息区","image":"de7cd0cf-38d6-453f-9171-4693940d5e0a"},{"tag":"寄存区","image":"2b2d1349-cee9-462b-a170-10fe90182440"},{"tag":"阅读区","image":"ce39cde8-3aad-4e77-a091-0092afb5372d"}],"location_id":"5a66fe0059a6270918509ab6","address":"朝阳区东三环中路双井富力广场四层429","friendliness":["空气净化器","防摔地板","无线WI-FI"],"pin":{"latitude":39.89374923706055,"longitude":116.46073913574219}}
             * is_collected : false
             * description : HOG课程Lower Primary社会化起步阶段是语言与创造性表达的融合。采用英国课程体系、英国总部指派外教、双语授课模式。通过生动有趣的戏剧活动帮孩子建立第二语言思维认知,让孩子开口讲英文。课程内容：热身洗脑、发音练习、形体韵律、即兴表演、情境对话、戏剧游戏。
             * min_age : 4
             * punchline : 尊重孩子独特性,让快乐大于学习。
             * teacher_num : 2
             * service_leaf : 戏剧
             * brand : {"brand_id":"5a66fe0059a6270918509aa0","date":1516699136189,"brand_name":"卓美","brand_tag":"Z","about_brand":"由著名导演冯小刚注资,以戏剧促进孩子成长。引入全球领先的海伦·奥格雷迪（Helen O'Grady ）英文教育戏剧（Drama in Education)课程体系,结合中国孩子和家长对教育的需求,希望更多的孩子能子在戏剧中遇见自己。青少儿英文剧团,配备国际化教师团队,开设戏剧表演及音乐剧课程,为7岁以上有表演天赋、希望系统学习戏剧表演的青少儿提供持续的成长平台。"}
             * operation : ["艺术家","资深外教","自主教研","明星"]
             * max_age : 6
             * service_images : [{"tag":"8","image":"597ec6a9-79c6-4a6f-806f-f5a58f9629f5"},{"tag":"4","image":"d9b42bed-36cd-46a1-bafa-a5db388b8ad4"},{"tag":"5","image":"cc146093-12a4-4a26-9dd0-cce5969404e1"},{"tag":"6","image":"b910e17e-0d57-4dd3-aac2-ff98aeb8575f"},{"tag":"1","image":"897750fb-b285-4d0d-8170-9e5383de90f2"},{"tag":"2","image":"ab0fca51-0f80-45b6-8a4e-3d9f94a6bd10"},{"tag":"7","image":"03a7c923-e4a3-4d71-a1bb-a6d20e5c3c92"},{"tag":"3","image":"209cd0fe-4af2-41f1-9e02-85ef22bc10ac"}]
             * service_type : 艺术
             * category : 课程
             * album :
             * service_id : 5a66fe0059a6270918509abc
             */

            var class_max_stu: Int = 0
            var location: LocationBean? = null
            var is_collected: Boolean = false
            var description: String? = null
            var min_age: Int = 0
            var punchline: String? = null
            var teacher_num: Int = 0
            var service_leaf: String? = null
            var brand: BrandBean? = null
            var max_age: Int = 0
            var service_type: String? = null
            var category: String? = null
            var album: String? = null
            var service_id: String? = null
            var service_tags: MutableList<String>? = null
            var operation: MutableList<String>? = null
            var service_images: MutableList<ServiceImagesBean>? = null

            class LocationBean : Serializable {
                /**
                 * location_images : [{"tag":"教学区","image":"8ef2a771-033e-42c3-93af-06850db943c5"},{"tag":"教学区","image":"430043cf-9f64-4fb1-a619-63cf9d0005b1"},{"tag":"家长休息区","image":"de7cd0cf-38d6-453f-9171-4693940d5e0a"},{"tag":"寄存区","image":"2b2d1349-cee9-462b-a170-10fe90182440"},{"tag":"阅读区","image":"ce39cde8-3aad-4e77-a091-0092afb5372d"}]
                 * location_id : 5a66fe0059a6270918509ab6
                 * address : 朝阳区东三环中路双井富力广场四层429
                 * friendliness : ["空气净化器","防摔地板","无线WI-FI"]
                 * pin : {"latitude":39.89374923706055,"longitude":116.46073913574219}
                 */

                var location_id: String? = null
                var address: String? = null
                var pin: PinBean? = null
                var location_images: MutableList<LocationImagesBean>? = null
                var friendliness: MutableList<String>? = null

                class PinBean : Serializable {
                    /**
                     * latitude : 39.89374923706055
                     * longitude : 116.46073913574219
                     */

                    var latitude: Double = 0.toDouble()
                    var longitude: Double = 0.toDouble()
                }

                class LocationImagesBean : Serializable {
                    /**
                     * tag : 教学区
                     * image : 8ef2a771-033e-42c3-93af-06850db943c5
                     */

                    var tag: String? = null
                    var image: String? = null
                }
            }

            class BrandBean : Serializable {
                /**
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
            }

            class ServiceImagesBean : Serializable, Comparable<ServiceImagesBean> {
                /**
                 * tag : 8
                 * image : 597ec6a9-79c6-4a6f-806f-f5a58f9629f5
                 */

                var tag: String? = null
                var image: String? = null

                override fun compareTo(o: ServiceImagesBean): Int {
                    if (tag == null || o.tag == null || !tag!!.isNumber() || !o.tag!!.isNumber()) {
                        -1
                    }
                    return if (tag!!.toInt() >= o.tag!!.toInt()) {
                        1
                    } else {
                        -1
                    }
                }
            }
        }
    }
}
