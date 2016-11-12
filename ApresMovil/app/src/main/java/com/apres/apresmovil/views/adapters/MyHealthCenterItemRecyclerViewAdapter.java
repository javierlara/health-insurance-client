package com.apres.apresmovil.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apres.apresmovil.R;
import com.apres.apresmovil.fragments.HealthCenterItemFragment.OnListFragmentInteractionListener;
import com.apres.apresmovil.models.HealthCenter;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link HealthCenter} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyHealthCenterItemRecyclerViewAdapter extends RecyclerView.Adapter<MyHealthCenterItemRecyclerViewAdapter.ViewHolder> {

    private final List<HealthCenter> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyHealthCenterItemRecyclerViewAdapter(List<HealthCenter> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_healthcenteritem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).name);
        holder.mAddressView.setText(mValues.get(position).address);
        holder.mTelephoneView.setText(mValues.get(position).telephone);

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
        public final TextView mNameView;
        public final TextView mAddressView;
        public final TextView mTelephoneView;
        public HealthCenter mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.name);
            mAddressView = (TextView) view.findViewById(R.id.address);
            mTelephoneView = (TextView) view.findViewById(R.id.telephone);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
