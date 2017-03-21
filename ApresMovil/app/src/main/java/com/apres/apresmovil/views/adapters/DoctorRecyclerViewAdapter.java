package com.apres.apresmovil.views.adapters;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.apres.apresmovil.R;
import com.apres.apresmovil.fragments.CartillaFragment;
import com.apres.apresmovil.models.Doctor;
import com.apres.apresmovil.utils.Utils;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by javierlara on 2/11/17.
 */
public class DoctorRecyclerViewAdapter extends RecyclerView.Adapter<DoctorRecyclerViewAdapter.ViewHolder> {

    private final List<Doctor> mValues;
    private final CartillaFragment.OnListFragmentInteractionListener mListener;
    protected Context mContext;
    private Location currentLocation;

    public DoctorRecyclerViewAdapter(List<Doctor> items, CartillaFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_cartillaitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).name);
        LatLng doctorLocation = holder.mItem.getLocation();
        if(currentLocation != null) {
            String distance = String.format("%.2f", Utils.distance((float) currentLocation.getLatitude(), (float) currentLocation.getLongitude(), (float) doctorLocation.latitude, (float) doctorLocation.longitude));
            holder.mAddressView.setText(mValues.get(position).address + " (" + distance + " km)");
        } else {
            holder.mAddressView.setText(mValues.get(position).address);
        }
        holder.mTelephoneView.setText(mValues.get(position).telephone);

        holder.mSelectDoctorButton.setOnClickListener(new View.OnClickListener() {
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

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mAddressView;
        public final TextView mTelephoneView;
        public final Button mSelectDoctorButton;
        public Doctor mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.doctor_name);
            mAddressView = (TextView) view.findViewById(R.id.doctor_address);
            mTelephoneView = (TextView) view.findViewById(R.id.doctor_telephone);
            mSelectDoctorButton = (Button) view.findViewById(R.id.select_doctor_button);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }

    }
}