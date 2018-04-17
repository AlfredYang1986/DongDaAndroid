package com.blackmirror.dongda.Tools;

import com.blackmirror.dongda.R;

/**
 * Created by Ruge on 2018-04-17 上午11:28
 */
public enum LocationEnum {
    WIND(R.drawable.new_wind_sys,"新风系统"),
    AIR_CLEAR(R.drawable.air_clear_sys,"空气净化器"),
    SAFE_POWER(R.drawable.safe_power,"安全插座"),
    PROTECT(R.drawable.real_time_protect,"实时监控"),
    WIFI(R.drawable.wifi,"无线WI-FI"),
    FLOOR(R.drawable.floor,"防摔地板"),
    AIR_HUMID(R.drawable.air_humid,"加湿器"),
    GUARD(R.drawable.guard,"安全护栏"),
    KIT(R.drawable.kit,"急救包");




    public int value;
    public String dec;

    LocationEnum(int value,String dec) {
        this.value = value;
        this.dec=dec;
    }

}
