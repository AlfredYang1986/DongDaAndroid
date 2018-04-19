package com.blackmirror.dongda.model;

import com.amap.api.maps2d.model.Marker;

/**
 * Created by Ruge on 2018-04-18 下午5:45
 */
public class MarkerIndexBean {
    public Marker marker;
    public int list_index;
    public boolean is_select;

    public MarkerIndexBean(Marker marker, int list_index) {
        this.marker = marker;
        this.list_index = list_index;
    }

    public MarkerIndexBean() {

    }
}
