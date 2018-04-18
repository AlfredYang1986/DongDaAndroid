package com.blackmirror.dongda.model.serverbean;

import com.blackmirror.dongda.model.BaseServerBean;

import java.util.List;

/**
 * Created by Ruge on 2018-04-18 下午3:26
 */
public class NearServiceServerBean extends BaseServerBean {


    /**
     * result : {"services":[{"service_tags":["国学"],"is_collected":false,"punchline":"以国学底蕴为载体,动静结合。","service_leaf":"日间看顾","brand_id":"5a66fe0259a6270918509c66","location_id":"5a66fe0259a6270918509c72","operation":["早教","自主教研","艺术家"],"service_image":"003efc51-aa79-45f3-8117-d42ddbe0396a","brand_name":"米涂","service_type":"艺术","address":"大兴区华佗路1号院106融汇小区西门底商","category":"课程","pin":{"latitude":39.67801284790039,"longitude":116.32026672363281},"service_id":"5a66fe0259a6270918509c78"},{"service_tags":["国学"],"is_collected":false,"punchline":"根据国学经典故事学习美感认知。","service_leaf":"创意美术","brand_id":"5a66fe0259a6270918509c66","location_id":"5a66fe0259a6270918509c72","operation":["艺术家","自主教研"],"service_image":"6f5afc04-4dc0-4b66-981f-b6f25fb6c9d3","brand_name":"米涂","service_type":"艺术","address":"大兴区华佗路1号院106融汇小区西门底商","category":"课程","pin":{"latitude":39.67801284790039,"longitude":116.32026672363281},"service_id":"5a66fe0259a6270918509c73"},{"service_tags":["STEAM"],"is_collected":false,"punchline":"科技,激发自主学习和探究问题能力,培养小小思考者。","service_leaf":"机器人","brand_id":"5a66fdfc59a6270918509887","location_id":"5a66fdfd59a62709185098bc","operation":["自主教研","STEAM"],"service_image":"4e30fbec-b0bd-4c27-8584-2b6d31df5c8f","brand_name":"帕皮科技","service_type":"科学","address":"大兴区天宫院街道龙湖时代天街2层2F-53","category":"课程","pin":{"latitude":39.687862396240234,"longitude":116.32331848144531},"service_id":"5a66fdfd59a62709185098d1"},{"service_tags":["STEAM"],"is_collected":false,"punchline":"人人动手,乐于创作,培养小小发明家。","service_leaf":"机器人","brand_id":"5a66fdfc59a6270918509887","location_id":"5a66fdfd59a62709185098bc","operation":["自主教研","STEAM"],"service_image":"6a619ad4-9ddd-4927-9b66-2fbd9999d3db","brand_name":"帕皮科技","service_type":"科学","address":"大兴区天宫院街道龙湖时代天街2层2F-53","category":"课程","pin":{"latitude":39.687862396240234,"longitude":116.32331848144531},"service_id":"5a66fdfd59a62709185098cc"},{"service_tags":["STEAM"],"is_collected":false,"punchline":"掌握学习力,成就梦想家。","service_leaf":"注意力训练","brand_id":"5a66fdfc59a6270918509887","location_id":"5a66fdfd59a62709185098bc","operation":["自主教研","STEAM"],"service_image":"31a833d0-8797-458d-9a7c-135d5144bb2c","brand_name":"帕皮科技","service_type":"科学","address":"大兴区天宫院街道龙湖时代天街2层2F-53","category":"课程","pin":{"latitude":39.687862396240234,"longitude":116.32331848144531},"service_id":"5a66fdfd59a62709185098c7"},{"service_tags":["STEAM"],"is_collected":false,"punchline":"小小机器人,大大梦想家。","service_leaf":"机器人","brand_id":"5a66fdfc59a6270918509887","location_id":"5a66fdfd59a62709185098bc","operation":["自主教研","STEAM"],"service_image":"c1c0aad4-0436-4395-8331-d01a46f976a2","brand_name":"帕皮科技","service_type":"科学","address":"大兴区天宫院街道龙湖时代天街2层2F-53","category":"课程","pin":{"latitude":39.687862396240234,"longitude":116.32331848144531},"service_id":"5a66fdfd59a62709185098c2"},{"service_tags":["STEAM"],"is_collected":false,"punchline":"激发好奇心,开启儿童兴趣之门,培养小小科学家。","service_leaf":"","brand_id":"5a66fdfc59a6270918509887","location_id":"5a66fdfd59a62709185098bc","operation":["自主教研","STEAM"],"service_image":"b1800294-026b-461f-8533-b7a8c168c94b","brand_name":"帕皮科技","service_type":"科学","address":"大兴区天宫院街道龙湖时代天街2层2F-53","category":"课程","pin":{"latitude":39.687862396240234,"longitude":116.32331848144531},"service_id":"5a66fdfd59a62709185098bd"},{"service_tags":["高瞻HighScope"],"is_collected":false,"punchline":"高质陪伴,让宝宝健康快乐成长、让家长安心放心工作！","service_leaf":"日间看顾","brand_id":"5a66fdec59a6270918508f8c","location_id":"5a66fdec59a6270918508f9f","operation":["低龄"],"service_image":"7535b12d-0dd8-4284-ae76-9f47d93149c2","brand_name":"妈咪助理","service_type":"看顾","address":"大兴区金融街融汇小区8号楼1单元","category":"看顾","pin":{"latitude":39.67801284790039,"longitude":116.32026672363281},"service_id":"5a66fdec59a6270918508fa0"}]}
     */

    public ResultBean result;

    public static class ResultBean {
        public List<ServicesBean> services;

        public static class ServicesBean {
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
                 * latitude : 39.67801284790039
                 * longitude : 116.32026672363281
                 */

                public double latitude;
                public double longitude;
            }
        }
    }
}
