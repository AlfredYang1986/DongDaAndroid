package com.blackmirror.dongda.model.serverbean;

import com.blackmirror.dongda.model.BaseServerBean;

import java.util.List;

/**
 * Created by Ruge on 2018-04-11 下午1:40
 */
public class HomeInfoServerBean extends BaseServerBean {


    /**
     * result : {"homepage_services":[{"service_type":"看顾","totalCount":619,"services":[{"service_tags":["德国BMBF"],"is_collected":false,"service_leaf":"日间看顾","brand_id":"5a66fdeb59a6270918508f3a","location_id":"5a66fdeb59a6270918508f3b","operation":["低龄"],"service_image":"c58112f0-c1af-42c6-a30f-a4710b5f499d","brand_name":"柯莱特","service_type":"看顾","address":"朝阳区芳园南里9号院丽都水岸7号楼2层","category":"看顾","pin":{"latitude":39.96918487548828,"longitude":116.48291778564453},"service_id":"5a66fdeb59a6270918508f41"},{"service_tags":["蒙特梭利"],"is_collected":false,"service_leaf":"日间看顾","brand_id":"5a66fdeb59a6270918508f4c","location_id":"5a66fdeb59a6270918508f53","operation":["蒙特梭利","资深外教"],"service_image":"5c971a1c-a97e-4e93-b6cd-669b37235ba8","brand_name":"悦享时光","service_type":"看顾","address":"大兴区天华西路天华园三里六区10号楼","category":"看顾","pin":{"latitude":39.79732894897461,"longitude":116.49700927734375},"service_id":"5a66fdeb59a6270918508f54"},{"service_tags":["美国RIE"],"is_collected":false,"service_leaf":"日间看顾","brand_id":"5a66fdeb59a6270918508f6a","location_id":"5a66fdeb59a6270918508f6b","operation":["蒙特梭利","资深外教","浸入式双语","低龄"],"service_image":"b2915764-9025-4c60-8230-40ecfd73c750","brand_name":"iHommy艾荷美","service_type":"看顾","address":"朝阳区望京南湖西园南路北京香颂238号楼一层","category":"看顾","pin":{"latitude":39.9901008605957,"longitude":116.45325469970703},"service_id":"5a66fdeb59a6270918508f71"},{"service_tags":["蒙特梭利"],"is_collected":false,"service_leaf":"日间看顾","brand_id":"5a66fdee59a6270918509069","location_id":"5a66fdee59a627091850906a","operation":["蒙特梭利","浸入式双语"],"service_image":"135088b9-15bd-406b-aa37-41ff36176115","brand_name":"东方悦稚","service_type":"看顾","address":"海淀区双槐树路392号四季青敬老院旁","category":"看顾","pin":{"latitude":39.941341400146484,"longitude":116.25218963623047},"service_id":"5a66fdee59a6270918509070"},{"service_tags":["蒙特梭利"],"is_collected":false,"service_leaf":"日间看顾","brand_id":"5a66fdee59a627091850907b","location_id":"5a66fdee59a627091850907c","operation":["蒙特梭利","浸入式双语"],"service_image":"bdc382da-f3d2-4f64-b267-7f965050e62f","brand_name":"童年小院","service_type":"看顾","address":"朝阳区望京北纬40度社区东区二期12号楼1单元102","category":"看顾","pin":{"latitude":40.02293395996094,"longitude":116.46407318115234},"service_id":"5a66fdee59a6270918509082"},{"service_tags":["福沃德艺术","蒙特梭利"],"is_collected":false,"service_leaf":"日间看顾","brand_id":"5a66fded59a627091850900d","location_id":"5a66fded59a627091850900e","operation":["蒙特梭利","资深外教","浸入式双语","自主教研"],"service_image":"4bc1b5b3-3e82-4eaf-89db-55c01852a5d2","brand_name":"卡尔\u2022安娜","service_type":"看顾","address":"朝阳区观唐东路5号浩华宫2号","category":"看顾","pin":{"latitude":40.02610778808594,"longitude":116.51115417480469},"service_id":"5a66fded59a6270918509014"}]},{"service_type":"艺术","totalCount":472,"services":[{"service_tags":[""],"is_collected":false,"service_leaf":"戏剧","brand_id":"5a66fe0059a6270918509aa0","location_id":"5a66fe0059a6270918509ab6","operation":["艺术家","资深外教","自主教研","明星"],"service_image":"897750fb-b285-4d0d-8170-9e5383de90f2","brand_name":"卓美","service_type":"艺术","address":"朝阳区东三环中路双井富力广场四层429","category":"课程","pin":{"latitude":39.89374923706055,"longitude":116.46073913574219},"service_id":"5a66fe0059a6270918509abc"},{"service_tags":[""],"is_collected":false,"service_leaf":"创意美术","brand_id":"5a66fe0259a6270918509c7e","location_id":"5a66fe0259a6270918509c7f","operation":["自主教研"],"service_image":"96b85ce4-7c4f-40a5-8bff-55a86efce066","brand_name":"橡皮擦","service_type":"艺术","address":"朝阳区双桥中路聚乐汇3层","category":"课程","pin":{"latitude":39.89385223388672,"longitude":116.59056091308594},"service_id":"5a66fe0259a6270918509c80"},{"service_tags":[""],"is_collected":false,"service_leaf":"","brand_id":"5a66fdff59a6270918509a5b","location_id":"5a66fdff59a6270918509a5c","operation":["幼小衔接","艺术家","自主教研"],"service_image":"55cb1e06-1225-4023-b0bd-4687e018912d","brand_name":"ONE ART北青","service_type":"艺术","address":"朝阳区北苑小街8号东行30米","category":"课程","pin":{"latitude":40.026084899902344,"longitude":116.43865966796875},"service_id":"5a66fdff59a6270918509a67"},{"service_tags":[""],"is_collected":false,"service_leaf":"书法","brand_id":"5a66fe0359a6270918509cd1","location_id":"5a66fe0359a6270918509cd2","operation":["艺术家","自主教研"],"service_image":"e5076c6b-3b60-4dd5-8ee8-8bb6615de767","brand_name":"品悦国艺","service_type":"艺术","address":"朝阳区常营北辰福第V中心C座413-416","category":"课程","pin":{"latitude":39.92640686035156,"longitude":116.60003662109375},"service_id":"5a66fe0359a6270918509cd8"}]},{"service_type":"科学","totalCount":544,"services":[{"service_tags":["STEAM"],"is_collected":false,"service_leaf":"","brand_id":"5a66fdfb59a6270918509804","location_id":"5a66fdfb59a6270918509805","operation":["资深外教","STEAM","自主教研"],"service_image":"61aabae9-1c84-423c-8eb4-42285174bae7","brand_name":"TechTrek科界","service_type":"科学","address":"朝阳区望京SOHO塔一B座2306室","category":"课程","pin":{"latitude":39.996795654296875,"longitude":116.48104858398438},"service_id":"5a66fdfb59a6270918509806"},{"service_tags":[""],"is_collected":false,"service_leaf":"化学","brand_id":"5a66fdfc59a627091850986f","location_id":"5a66fdfc59a627091850987b","operation":["自主教研","名校团队","STEAM"],"service_image":"895886a5-e6d2-462a-98a6-5f08d82ef191","brand_name":"德拉学院","service_type":"科学","address":"海淀区中关村东路18号财智国际大厦C座1602","category":"课程","pin":{"latitude":39.988014221191406,"longitude":116.33370208740234},"service_id":"5a66fdfc59a6270918509881"},{"service_tags":["STEAM"],"is_collected":false,"service_leaf":"","brand_id":"5a66fdfd59a62709185098f1","location_id":"5a66fdfd59a62709185098f2","operation":["名校团队","自主教研","STEAM"],"service_image":"8dca5820-b1ae-4c80-b4e8-3a7c5cac4aa6","brand_name":"火星人","service_type":"科学","address":"海淀区清华东路中关村学院一分院102","category":"课程","pin":{"latitude":39.9998893737793,"longitude":116.34319305419922},"service_id":"5a66fdfd59a62709185098f8"},{"service_tags":[""],"is_collected":false,"service_leaf":"","brand_id":"5a66fdfb59a62709185097b9","location_id":"5a66fdfb59a62709185097ba","operation":["名校团队","自主教研","STEAM"],"service_image":"88a94cdd-6a2f-4e0f-a3e6-cd4147a01574","brand_name":"艾科思科学","service_type":"科学","address":"海淀区万柳中路15号205室","category":"课程","pin":{"latitude":39.966514587402344,"longitude":116.29718780517578},"service_id":"5a66fdfb59a62709185097bb"}]},{"service_type":"运动","totalCount":310,"services":[{"service_tags":[""],"is_collected":false,"service_leaf":"篮球","brand_id":"5a66fdf059a627091850911b","location_id":"5a66fdf059a627091850911c","operation":["资深外教","明星","自主教研"],"service_image":"e3890287-8767-4192-be96-29caaa8474b2","brand_name":"NBA姚明篮球俱乐部","service_type":"运动","address":"朝阳区辛庄一街26号泛海国际兰海园会所","category":"课程","pin":{"latitude":39.9412956237793,"longitude":116.50691986083984},"service_id":"5a66fdf059a627091850911d"},{"service_tags":[""],"is_collected":false,"service_leaf":"击剑","brand_id":"5a66fdfa59a62709185097a4","location_id":"5a66fdfa59a62709185097a5","operation":["修身养性","冠军","自主教研"],"service_image":"25e76821-08b4-4d76-9d26-ca5c7dbf621d","brand_name":"万国体育","service_type":"运动","address":"朝阳区安定路1号国家奥林匹克体育中心国奥训练馆内","category":"课程","pin":{"latitude":39.98515701293945,"longitude":116.40081787109375},"service_id":"5a66fdfa59a62709185097a6"},{"service_tags":[""],"is_collected":false,"service_leaf":"冰球","brand_id":"5a66fdf359a62709185092ec","location_id":"5a66fdf359a62709185092ed","operation":["极限运动","自主教研"],"service_image":"ba7b3f3c-c7e0-4d56-a15c-d00bebbc6428","brand_name":"虎仔冰球","service_type":"运动","address":"昌平区观镇黄土村西路与育知东路东南角吉晟别墅北门西侧","category":"课程","pin":{"latitude":40.066097259521484,"longitude":116.3396224975586},"service_id":"5a66fdf359a62709185092ee"},{"service_tags":[""],"is_collected":false,"service_leaf":"跆拳道","brand_id":"5a66fdf859a6270918509636","location_id":"5a66fdf859a6270918509637","operation":["自主教研","冠军"],"service_image":"f7fb83fe-16a2-4362-923a-730ceade0d1a","brand_name":"吴静钰","service_type":"运动","address":"海淀区中关村南大街2号数码大厦B座西门B1层","category":"课程","pin":{"latitude":39.96629333496094,"longitude":116.32305908203125},"service_id":"5a66fdf859a627091850963d"}]}]}
     */

    public ResultBean result;

    public static class ResultBean {
        public List<HomepageServicesBean> homepage_services;

        public static class HomepageServicesBean {
            /**
             * service_type : 看顾
             * totalCount : 619
             * services : [{"service_tags":["德国BMBF"],"is_collected":false,"service_leaf":"日间看顾","brand_id":"5a66fdeb59a6270918508f3a","location_id":"5a66fdeb59a6270918508f3b","operation":["低龄"],"service_image":"c58112f0-c1af-42c6-a30f-a4710b5f499d","brand_name":"柯莱特","service_type":"看顾","address":"朝阳区芳园南里9号院丽都水岸7号楼2层","category":"看顾","pin":{"latitude":39.96918487548828,"longitude":116.48291778564453},"service_id":"5a66fdeb59a6270918508f41"},{"service_tags":["蒙特梭利"],"is_collected":false,"service_leaf":"日间看顾","brand_id":"5a66fdeb59a6270918508f4c","location_id":"5a66fdeb59a6270918508f53","operation":["蒙特梭利","资深外教"],"service_image":"5c971a1c-a97e-4e93-b6cd-669b37235ba8","brand_name":"悦享时光","service_type":"看顾","address":"大兴区天华西路天华园三里六区10号楼","category":"看顾","pin":{"latitude":39.79732894897461,"longitude":116.49700927734375},"service_id":"5a66fdeb59a6270918508f54"},{"service_tags":["美国RIE"],"is_collected":false,"service_leaf":"日间看顾","brand_id":"5a66fdeb59a6270918508f6a","location_id":"5a66fdeb59a6270918508f6b","operation":["蒙特梭利","资深外教","浸入式双语","低龄"],"service_image":"b2915764-9025-4c60-8230-40ecfd73c750","brand_name":"iHommy艾荷美","service_type":"看顾","address":"朝阳区望京南湖西园南路北京香颂238号楼一层","category":"看顾","pin":{"latitude":39.9901008605957,"longitude":116.45325469970703},"service_id":"5a66fdeb59a6270918508f71"},{"service_tags":["蒙特梭利"],"is_collected":false,"service_leaf":"日间看顾","brand_id":"5a66fdee59a6270918509069","location_id":"5a66fdee59a627091850906a","operation":["蒙特梭利","浸入式双语"],"service_image":"135088b9-15bd-406b-aa37-41ff36176115","brand_name":"东方悦稚","service_type":"看顾","address":"海淀区双槐树路392号四季青敬老院旁","category":"看顾","pin":{"latitude":39.941341400146484,"longitude":116.25218963623047},"service_id":"5a66fdee59a6270918509070"},{"service_tags":["蒙特梭利"],"is_collected":false,"service_leaf":"日间看顾","brand_id":"5a66fdee59a627091850907b","location_id":"5a66fdee59a627091850907c","operation":["蒙特梭利","浸入式双语"],"service_image":"bdc382da-f3d2-4f64-b267-7f965050e62f","brand_name":"童年小院","service_type":"看顾","address":"朝阳区望京北纬40度社区东区二期12号楼1单元102","category":"看顾","pin":{"latitude":40.02293395996094,"longitude":116.46407318115234},"service_id":"5a66fdee59a6270918509082"},{"service_tags":["福沃德艺术","蒙特梭利"],"is_collected":false,"service_leaf":"日间看顾","brand_id":"5a66fded59a627091850900d","location_id":"5a66fded59a627091850900e","operation":["蒙特梭利","资深外教","浸入式双语","自主教研"],"service_image":"4bc1b5b3-3e82-4eaf-89db-55c01852a5d2","brand_name":"卡尔\u2022安娜","service_type":"看顾","address":"朝阳区观唐东路5号浩华宫2号","category":"看顾","pin":{"latitude":40.02610778808594,"longitude":116.51115417480469},"service_id":"5a66fded59a6270918509014"}]
             */

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
                }
            }
        }
    }
}
