package com.apres.apresmovil.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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

    public String getStartTime() {
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

    public String getStartDay() {
        SimpleDateFormat inputFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat returnFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

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

    public boolean canCancel() {
        Date date = getStartDate();
        Date now = new Date();
        return now.before(date);
    }

    public Date getStartDate() {
        SimpleDateFormat inputFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
        Date date;
        try {
            date = inputFormat.parse(start);
        } catch (ParseException e) {
            return null;
        }
        return date;
    }

    public int compare(Appointment o2) {
        Date date1 = getStartDate();
        Date date2 = o2.getStartDate();
        return date1.after(date2) ? -1 : date1 == date2 ? 0 : 1;
    }
}