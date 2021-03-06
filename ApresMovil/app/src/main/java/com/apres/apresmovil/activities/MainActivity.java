package com.apres.apresmovil.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.apres.apresmovil.R;
import com.apres.apresmovil.fragments.AppointmentFragment;
import com.apres.apresmovil.fragments.CancelAppointmentDialogFragment;
import com.apres.apresmovil.fragments.CartillaFragment;
import com.apres.apresmovil.fragments.HealthCenterItemFragment;
import com.apres.apresmovil.fragments.MyAppointmentsFragment;
import com.apres.apresmovil.fragments.NewsItemFragment;
import com.apres.apresmovil.models.Appointment;
import com.apres.apresmovil.models.Doctor;
import com.apres.apresmovil.models.HealthCenter;
import com.apres.apresmovil.models.News;
import com.apres.apresmovil.network.ApiHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements
            NavigationView.OnNavigationItemSelectedListener,
            HealthCenterItemFragment.OnListFragmentInteractionListener,
            NewsItemFragment.OnListFragmentInteractionListener,
//            CartillaFragment.OnFragmentInteractionListener,
            CartillaFragment.OnListFragmentInteractionListener,
            AppointmentFragment.OnFragmentInteractionListener,
            MyAppointmentsFragment.OnListFragmentInteractionListener
{

    private ApiHelper mApiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mApiHelper = new ApiHelper(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_health_centers) {
            Fragment fragment = HealthCenterItemFragment.newInstance();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        } else if (id == R.id.nav_news) {
            Fragment fragment = NewsItemFragment.newInstance();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        } else if (id == R.id.nav_cartilla) {
            Fragment fragment = CartillaFragment.newInstance();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        } else if (id == R.id.nav_my_appointments) {
            Fragment fragment = MyAppointmentsFragment.newInstance(1);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onListFragmentInteraction(HealthCenter item) {

    }

    public void onListFragmentInteraction(News item) {

    }

    public void onListFragmentInteraction(Appointment item) {

    }

    public void onFragmentInteraction(Uri uri) {

    }

    public void onListFragmentInteraction(Doctor doctor) {
        Fragment fragment = AppointmentFragment.newInstance(doctor);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack("appointment_" + doctor.id)
                .commit();
    }

    public void onAppointmentCancel(Appointment appointment) {
        if(appointment != null) {
            CancelAppointmentDialogFragment newFragment = CancelAppointmentDialogFragment.newInstance(appointment.id);
            newFragment.show(getFragmentManager().beginTransaction(), "dialog");
        }
    }


    public void doPositiveClick(String appointmentId) {
        if(appointmentId != null) {
            mApiHelper.deleteAppointment(appointmentId, new ApiHelper.ApiHelperCallback() {
                @Override
                public void onSuccess(List list) {
                    Log.i("APPOINTMENT_DELETED", list.toString());
                }

                @Override
                public void onError(Exception error) {
                    Log.e("APPOINTMENT_DELETED", error.getMessage());
                }
            });
        }
    }

    public void doNegativeClick() {
        // Do stuff here.
        Log.i("APPOINTMENT_DELETED", "not deleted");
    }

}
