package com.apres.apresmovil.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apres.apresmovil.R;
import com.apres.apresmovil.models.Appointment;
import com.apres.apresmovil.models.AppointmentContainer;
import com.apres.apresmovil.models.Member;
import com.apres.apresmovil.network.ApiHelper;
import com.apres.apresmovil.utils.Session;
import com.apres.apresmovil.views.adapters.MyAppointmentHistoryRecyclerViewAdapter;
import com.apres.apresmovil.views.itemdecorations.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MyAppointmentsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private Session session;
    private ApiHelper mApiHelper;
    private List<Appointment> mAppointmentList;
    private MyAppointmentHistoryRecyclerViewAdapter mAppointmentAdapter;

    public static final int DIALOG_FRAGMENT = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MyAppointmentsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MyAppointmentsFragment newInstance(int columnCount) {
        MyAppointmentsFragment fragment = new MyAppointmentsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointmenthistory_list, container, false);

        mAppointmentList = new ArrayList<>();
        mAppointmentAdapter = new MyAppointmentHistoryRecyclerViewAdapter(mAppointmentList, mListener);

        Member member = session.getCurrentMember();

        mApiHelper.getAppointments(member.id, new ApiHelper.ApiHelperCallback() {
            @Override
            public void onSuccess(List list) {
                AppointmentContainer appointmentContainer = (AppointmentContainer) list.get(0);
                mAppointmentList.clear();
                ArrayList<Appointment> sortedAppointments = appointmentContainer.appointments;

                Collections.sort(sortedAppointments, new Comparator<Appointment>() {

                    @Override
                    public int compare(Appointment o1, Appointment o2) {
                        return o1.compare(o2);
                    }
                });
                mAppointmentList.addAll(sortedAppointments);
                mAppointmentAdapter.notifyDataSetChanged();
                Log.i("Appointments", mAppointmentList.toString());
            }

            @Override
            public void onError(Exception e) {
                Log.e("NEWSERROR", e.getMessage());
            }
        });

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
            recyclerView.setAdapter(mAppointmentAdapter);
        }
        return view;
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
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Appointment item);

        void onAppointmentCancel(Appointment mItem);
    }
}
