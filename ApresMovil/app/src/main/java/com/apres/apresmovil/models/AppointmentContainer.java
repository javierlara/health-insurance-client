package com.apres.apresmovil.models;

import java.util.ArrayList;

/**
 * Created by javierlara on 3/6/17.
 */

public class AppointmentContainer {
    public final ArrayList<Appointment> appointments;

    public AppointmentContainer(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }
}
