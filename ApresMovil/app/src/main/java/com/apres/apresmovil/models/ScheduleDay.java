package com.apres.apresmovil.models;

import java.util.ArrayList;

/**
 * Created by javierlara on 2/15/17.
 */
public class ScheduleDay {
    public final String day;
    public final ArrayList<ScheduleSlot> slots;

    public ScheduleDay(String day, ArrayList<ScheduleSlot> slots) {
        this.day = day;
        this.slots = slots;
    }
}
