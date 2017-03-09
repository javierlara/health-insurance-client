package com.apres.apresmovil.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;

import com.apres.apresmovil.R;
import com.apres.apresmovil.models.Doctor;
import com.apres.apresmovil.models.Member;
import com.apres.apresmovil.models.ScheduleContainer;
import com.apres.apresmovil.models.ScheduleSlot;
import com.apres.apresmovil.network.ApiHelper;
import com.apres.apresmovil.utils.Session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AppointmentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AppointmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppointmentFragment extends Fragment {
    private static final String ARG_DOCTOR = "doctor";

    private Doctor mDoctor;

    private OnFragmentInteractionListener mListener;

    private ApiHelper mApiHelper;

    private CalendarView mCalendar;

    private Session session;

    private int currentMonth;
    private int currentDay;

    private ScheduleContainer mScheduleContainer;

    private Spinner mAppointmentSpinner;

    private ScheduleSlot mSlot;

    public AppointmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param doctor Doctor.
     * @return A new instance of fragment AppointmentFragment.
     */
    public static AppointmentFragment newInstance(Doctor doctor) {
        AppointmentFragment fragment = new AppointmentFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_DOCTOR, doctor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDoctor = getArguments().getParcelable(ARG_DOCTOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_appointment, container, false);

        if(mDoctor != null) {
            TextView doctorName = (TextView) view.findViewById(R.id.appointment_doctor_name);
            doctorName.setText(mDoctor.name);

            mAppointmentSpinner = (Spinner) view.findViewById(R.id.appointment_time_start);
            mAppointmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    mSlot = (ScheduleSlot) parent.getSelectedItem();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

        initializeCalendar(view);

        Button clickButton = (Button) view.findViewById(R.id.appointment_button_submit);
        clickButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onAppointmentButton();
            }
        });

        return view;
    }

    public void initializeCalendar(View view) {
        mCalendar = (CalendarView) view.findViewById(R.id.appointmentCalendarView);

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        getSchedules(month, year);
        currentDay = day;

        mCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                getSchedules(month, year);
                currentDay = day;
                selectedDay();
            };
        });
    }

    void getSchedules(int month, int year) {
        if(currentMonth != month) {
            mApiHelper.getSchedule(mDoctor.id, String.valueOf(month + 1), String.valueOf(year), new ApiHelper.ApiHelperCallback() {
                @Override
                public void onSuccess(List list) {
                    if (list.size() == 1) {
                        Log.i("SCHEDULE", list.toString());
                        mScheduleContainer = (ScheduleContainer) list.get(0);
                        selectedDay();
                    }
                }

                @Override
                public void onError(Exception e) {
                    Log.e("SCHEDULE", e.getMessage());
                }
            });
            currentMonth = month;
        }
    }

    public void selectedDay() {
        if(mScheduleContainer != null && currentDay > 0) {
            ArrayList<ScheduleSlot> slots = mScheduleContainer.getSlots(currentDay);
            if(slots.size() <= 0) {
                mSlot = null;
            }
            ArrayAdapter<ScheduleSlot> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, slots);
            mAppointmentSpinner.setAdapter(adapter);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        commonOnAttach(context);
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        commonOnAttach(context);
    }

    public void commonOnAttach(Context context) {
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        mApiHelper = new ApiHelper(context);

        session = new Session(getActivity());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void onAppointmentButton() {
        if(mSlot != null) {
            Log.i("APPOINTMENT", mSlot.toString());
            Member member = session.getCurrentMember();
            mApiHelper.postAppointment(mDoctor.id, member.id, mSlot.getStartMilliseconds(), new ApiHelper.ApiHelperCallback() {
                @Override
                public void onSuccess(List list) {
                    if (list.size() == 1) {
                        Log.i("APPOINTMENT", list.toString());
                    }
                }

                @Override
                public void onError(Exception e) {
                    Log.e("APPOINTMENT", e.getMessage());
                }
            });
        }
    }
}
