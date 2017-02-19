package com.apres.apresmovil.models;

/**
 * Created by javierlara on 2/15/17.
 */
public class ScheduleSlot {
    public final String start;
    public final String end;

    public ScheduleSlot(String start, String end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return start;
    }
}
