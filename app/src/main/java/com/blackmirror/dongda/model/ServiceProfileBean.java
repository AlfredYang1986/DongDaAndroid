package com.blackmirror.dongda.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ruge on 2018-04-28 上午10:52
 */
public class ServiceProfileBean implements Parcelable {

    public int res_id;
    public String brand_tag;
    public String brand_name;
    public String about_brand;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.res_id);
        dest.writeString(this.brand_tag);
        dest.writeString(this.brand_name);
        dest.writeString(this.about_brand);
    }

    public ServiceProfileBean() {
    }

    protected ServiceProfileBean(Parcel in) {
        this.res_id = in.readInt();
        this.brand_tag = in.readString();
        this.brand_name = in.readString();
        this.about_brand = in.readString();
    }

    public static final Parcelable.Creator<ServiceProfileBean> CREATOR = new Parcelable.Creator<ServiceProfileBean>() {
        @Override
        public ServiceProfileBean createFromParcel(Parcel source) {
            return new ServiceProfileBean(source);
        }

        @Override
        public ServiceProfileBean[] newArray(int size) {
            return new ServiceProfileBean[size];
        }
    };
}
