package com.apres.apresmovil.models;

import android.transition.Scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

    public String getAvailableSlotDays() {
        String message = "";
        ArrayList<ScheduleDay> days = payload;
        Collections.sort(days, new Comparator<ScheduleDay>() {

            @Override
            public int compare(ScheduleDay o1, ScheduleDay o2) {
                return o1.compare(o2);
            }
        });
        for(ScheduleDay scheduleDay : payload) {
            message += scheduleDay.day + ",";
        }

        return removeLastChar(message);
    }

    private String removeLastChar(String str) {
        if (str != null && str.length() > 0) {
            str = str.substring(0, str.length()-1);
        }
        return str;
    }
}
