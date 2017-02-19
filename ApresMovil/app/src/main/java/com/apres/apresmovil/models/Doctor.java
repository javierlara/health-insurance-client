package com.apres.apresmovil.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by javierlara on 2/10/17.
 */
public class Doctor implements Parcelable {
    public final String id;
    public final String name;
    public final String address;
    public final String telephone;
    private final String location;
//    public final Plan[] plans;
//    public final Speciality[] specialities;

    public Doctor(String id, String name, String address, String telephone, String location) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.location = location;
//        this.plans = plans;
//        this.specialities = specialities;
    }

    public LatLng getLocation() {
        String[] latlong =  this.location.split(",");
        double latitude = Double.parseDouble(latlong[0].substring(1));
        double longitude = Double.parseDouble(latlong[1].substring(0, latlong[1].length()-1));
        return new LatLng(latitude, longitude);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel write, int flags) {
        write.writeString(id);
        write.writeString(name);
        write.writeString(address);
        write.writeString(telephone);
        write.writeString(location);
    }
}
