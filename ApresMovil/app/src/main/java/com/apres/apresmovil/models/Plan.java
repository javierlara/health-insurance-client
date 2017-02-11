package com.apres.apresmovil.models;

/**
 * Created by javierlara on 2/10/17.
 */
public class Plan {
    public final String id;
    public final String name;
//    public final HealthCenter[] healthCenters;
//    public final Doctor[] doctors;

    public Plan(String id, String name) {
        this.id = id;
        this.name = name;
//        this.healthCenters = healthCenters;
//        this.doctors = doctors;
    }

    @Override
    public String toString() {
        return name;
    }
}
