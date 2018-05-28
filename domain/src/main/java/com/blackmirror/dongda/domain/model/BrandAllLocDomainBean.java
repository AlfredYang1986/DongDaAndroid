package com.blackmirror.dongda.domain.model;

import java.util.List;

/**
 * Create By Ruge at 2018-05-15
 */
public class BrandAllLocDomainBean extends BaseDataBean {
    public List<LocationsBean> locations;

    public static class LocationsBean {
        /**
         * location_images : [{"tag":"生活区","image":"654e1931-91f6-4e29-8a0f-58c505cf2c4b"},{"tag":"阅读区","image":"cb45c31c-f3fd-4b4e-b1c3-ddde845a2505"}]
         * location_id : 5a66fdea59a6270918508f26
         * address : 东城区永定门西滨河路8号院1号楼中海紫御公馆底商1-17号
         * friendliness : ["新风系统","实时监控"]
         * pin : {"latitude":39.87063980102539,"longitude":116.39083862304688}
         */

        public String location_id;
        public String address;
        public PinBean pin;
        public List<LocationImagesBean> location_images;
        public List<String> friendliness;

        public static class PinBean {
            /**
             * latitude : 39.87063980102539
             * longitude : 116.39083862304688
             */

            public double latitude;
            public double longitude;
        }

        public static class LocationImagesBean {
            /**
             * tag : 生活区
             * image : 654e1931-91f6-4e29-8a0f-58c505cf2c4b
             */

            public String tag;
            public String image;
        }
    }
}
