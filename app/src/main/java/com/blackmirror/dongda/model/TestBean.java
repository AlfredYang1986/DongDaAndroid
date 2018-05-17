package com.blackmirror.dongda.model;

/**
 * Create By Ruge at 2018-05-17
 */
public class TestBean {

    /**
     * token : bearer2737d748bce21504447cf27d7b1f4f99
     * recruit : {"service_id":"5a66fdea59a6270918508f27","age_boundary":{"lbl":2,"ubl":8},"stud_boundary":{"min":10,"max":15},"payment_time":{"price":1000,"length":45,"times":20}}
     */

    public String token;
    public RecruitBean recruit;

    public static class RecruitBean {
        /**
         * service_id : 5a66fdea59a6270918508f27
         * age_boundary : {"lbl":2,"ubl":8}
         * stud_boundary : {"min":10,"max":15}
         * payment_time : {"price":1000,"length":45,"times":20}
         */

        public String service_id;
        public AgeBoundaryBean age_boundary;
        public StudBoundaryBean stud_boundary;
        public PaymentTimeBean payment_time;

        public static class AgeBoundaryBean {
            /**
             * lbl : 2
             * ubl : 8
             */

            public int lbl;
            public int ubl;
        }

        public static class StudBoundaryBean {
            /**
             * min : 10
             * max : 15
             */

            public int min;
            public int max;
        }

        public static class PaymentTimeBean {
            /**
             * price : 1000
             * length : 45
             * times : 20
             */

            public int price;
            public int length;
            public int times;
        }
    }
}
