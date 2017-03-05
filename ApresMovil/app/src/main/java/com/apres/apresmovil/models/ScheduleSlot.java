package com.apres.apresmovil.models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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
        SimpleDateFormat inputFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat returnFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        returnFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Date date;
        try {
            date = inputFormat.parse(start);
        } catch (ParseException e) {
            return start;
        }
        return returnFormat.format(date);
    }
}
