package com.apres.apresmovil.models;

/**
 * Created by javierlara on 3/6/17.
 */

public class Appointment {
    public final String id;
    public final Integer memberId;
    public final Doctor doctor;
    public final String start;
    public final String end;

    public Appointment(String id, Integer memberId, Doctor doctor, String start, String end) {
        this.id = id;
        this.memberId = memberId;
        this.doctor = doctor;
        this.start = start;
        this.end = end;
    }
}