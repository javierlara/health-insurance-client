package com.apres.apresmovil.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apres.apresmovil.R;
import com.apres.apresmovil.fragments.MyAppointmentsFragment.OnListFragmentInteractionListener;
import com.apres.apresmovil.models.Appointment;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Appointment} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyAppointmentHistoryRecyclerViewAdapter extends RecyclerView.Adapter<MyAppointmentHistoryRecyclerViewAdapter.ViewHolder> {

    private final List<Appointment> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyAppointmentHistoryRecyclerViewAdapter(List<Appointment> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_appointmenthistory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mStartDayView.setText(mValues.get(position).getStartDay());
        holder.mStartTimeView.setText(mValues.get(position).getStartTime());
        holder.mDoctorNameView.setText(mValues.get(position).doctor.name);
        holder.mDoctorAddressView.setText(mValues.get(position).doctor.address);

        holder.mDeleteAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onAppointmentCancel(holder.mItem);
                }
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mStartDayView;
        public final TextView mStartTimeView;
        public final TextView mDoctorNameView;
        public final TextView mDoctorAddressView;
        public final ImageView mDeleteAppointment;
        public Appointment mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mStartDayView = (TextView) view.findViewById(R.id.start_day);
            mStartTimeView = (TextView) view.findViewById(R.id.start_time);
            mDoctorNameView = (TextView) view.findViewById(R.id.doctor_name);
            mDoctorAddressView = (TextView) view.findViewById(R.id.doctor_address);
            mDeleteAppointment = (ImageView) view.findViewById(R.id.delete_appointment);
        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
    }
}
