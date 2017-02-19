package com.apres.apresmovil.models;

import java.util.ArrayList;

/**
 * Created by javierlara on 2/15/17.
 */
public class ScheduleContainer {
    public final boolean success;

    public final ArrayList<ScheduleDay> payload;

    public ScheduleContainer(boolean succes, ArrayList<ScheduleDay> payload) {
        this.success = succes;
        this.payload = payload;
    }

    @Override
    public String toString() {
        String result = "";
        if(this.payload.size() > 0) {
            result = this.payload.get(0).day;
        }
        return result;
    }

    public ArrayList<ScheduleSlot> getSlots(int day) {
        for(ScheduleDay scheduleDay : payload) {
            if(scheduleDay.day == String.valueOf(day)) {
                return scheduleDay.slots;
            }
        }
        return new ArrayList<>();
    }
}
