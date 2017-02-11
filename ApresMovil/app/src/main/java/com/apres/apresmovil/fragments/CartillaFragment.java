package com.apres.apresmovil.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.apres.apresmovil.R;
import com.apres.apresmovil.models.Doctor;
import com.apres.apresmovil.models.Plan;
import com.apres.apresmovil.models.Speciality;
import com.apres.apresmovil.network.ApiHelper;
import com.apres.apresmovil.views.adapters.DoctorRecyclerViewAdapter;
import com.apres.apresmovil.views.adapters.MyNewsItemRecyclerViewAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CartillaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CartillaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartillaFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    protected static final String LOCATION_TAG = "Location";

    private OnFragmentInteractionListener mListener;
    private OnListFragmentInteractionListener mListenerList;

    private ApiHelper mApiHelper = new ApiHelper();

    private Context mContext;

    private String mSpecialityId;
    private String mPlanId;
    private Location mLocation;

    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest locationRequest;
    protected FusedLocationProviderApi fusedLocationProviderApi;

    private List<Doctor> mDoctorList;
    private DoctorRecyclerViewAdapter mDoctorAdapter;

    public CartillaFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CartillaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartillaFragment newInstance() {
        CartillaFragment fragment = new CartillaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLocation();
    }

    private void getLocation() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        fusedLocationProviderApi = LocationServices.FusedLocationApi;
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
            return;
        }
        fusedLocationProviderApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);

    }

    @Override
    public void onLocationChanged(Location location) {
        mLocation = location;
        Log.d(LOCATION_TAG, "change to " + mLocation.toString());
    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(LOCATION_TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {

        Log.i(LOCATION_TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_cartilla, container, false);

        mApiHelper.getPlans(new ApiHelper.ApiHelperCallback() {
            @Override
            public void onSuccess(List list) {
                Spinner spinner = (Spinner) getView().findViewById(R.id.plan_filter);
                ArrayAdapter<Plan> adapter = new ArrayAdapter<Plan>(getActivity(), android.R.layout.simple_spinner_dropdown_item, list);
                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        Plan plan = (Plan) parent.getSelectedItem();
                        mPlanId = plan.id;
//                        Toast.makeText(getActivity(), "Plan ID: " + plan.id + ",  Plan Name : " + plan.name, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                Log.i("PLANS", list.toString());
            }

            @Override
            public void onError(Exception e) {
                Log.e("PLANS", e.getMessage());
            }
        });

        mApiHelper.getSpecialitites(new ApiHelper.ApiHelperCallback() {
            @Override
            public void onSuccess(List list) {
                Spinner spinner = (Spinner) view.findViewById(R.id.speciality_filter);
                ArrayAdapter<Speciality> adapter = new ArrayAdapter<Speciality>(getActivity(), android.R.layout.simple_spinner_dropdown_item, list);
                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        Speciality speciality = (Speciality) parent.getSelectedItem();
                        mSpecialityId = speciality.id;
//                        Toast.makeText(getActivity(), "Speciality ID: " + speciality.id + ",  Speciality Name : " + speciality.name, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                Log.i("SPECIALITIES", list.toString());
            }

            @Override
            public void onError(Exception e) {
                Log.e("SPECIALITIES", e.getMessage());
            }
        });

        Button clickButton = (Button) view.findViewById(R.id.cartilla_button_submit);
        clickButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onCartillaButton();
            }
        });

        mDoctorList = new ArrayList<>();
        mDoctorAdapter = new DoctorRecyclerViewAdapter(mDoctorList, mListenerList);

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.cartilla_list);;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mDoctorAdapter);

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void onCartillaButton() {
        Switch locationSwitch = (Switch) getView().findViewById(R.id.location_filter);
        boolean location_filter_enabled = locationSwitch.isChecked();
        String latitude = "";
        String longitude = "";
        if(location_filter_enabled && mLocation != null) {
            latitude = String.valueOf(mLocation.getLatitude());
            longitude = String.valueOf(mLocation.getLongitude());
        }

        mApiHelper.getCartilla(mPlanId, mSpecialityId, latitude, longitude, new ApiHelper.ApiHelperCallback() {
            @Override
            public void onSuccess(List list) {
                mDoctorList.clear();
                mDoctorList.addAll(list);
                mDoctorAdapter.notifyDataSetChanged();
                Log.i("CARTILLA", list.toString());
            }

            @Override
            public void onError(Exception e) {
                Log.e("CARTILLA", e.getMessage());
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Doctor item);
    }
}
