package com.blackmirror.dongda.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Create By Ruge at 2018-10-15
 */
public class DateUtils {

    public static int[] getYearAndMonthAndDay(Date date) {

        int[] d = new int[3];

        Calendar instance = Calendar.getInstance();
        instance.setTime(date);

        d[0] = instance.get(Calendar.YEAR);
        d[1] = instance.get(Calendar.MONTH) + 1;
        d[2] = instance.get(Calendar.DAY_OF_MONTH);

        return d;
    }
}
