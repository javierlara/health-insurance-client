package com.apres.apresmovil.models;

/**
 * Created by javierlara on 2/10/17.
 */
public class Speciality {
    public final String description;
//    public final Doctor[] doctors;
    public final String id;
    public final String name;

    public Speciality(String id, String name, String description) {
        this.description = description;
//        this.doctors = doctors;
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
