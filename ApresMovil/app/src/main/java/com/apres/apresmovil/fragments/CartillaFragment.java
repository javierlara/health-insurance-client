package com.apres.apresmovil.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.apres.apresmovil.R;
import com.apres.apresmovil.models.Doctor;
import com.apres.apresmovil.models.Plan;
import com.apres.apresmovil.models.Speciality;
import com.apres.apresmovil.network.ApiHelper;
import com.apres.apresmovil.views.adapters.DoctorRecyclerViewAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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

    private ApiHelper mApiHelper;

    private Context mContext;

    private String mSpecialityId;
    private String mPlanId;
    private Location mLocation;

    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest locationRequest;
    protected FusedLocationProviderApi fusedLocationProviderApi;

    private List<Doctor> mDoctorList;
    private DoctorRecyclerViewAdapter mDoctorAdapter;

    private List<Plan> mPlans;
    private List<Speciality> mSpecialities;

    private LinearLayout mSearchForm;
    private LinearLayout mDoctorListLayout;
    private ImageButton mBackButton;
    private ImageButton mMapButton;
    private Switch mLocationFilterSwitch;

    MapView mMapView;
    private GoogleMap googleMap;
    private List<MarkerOptions> mMarkers;

    private boolean mapLoaded;

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
        mMarkers = new ArrayList<>();
        mapLoaded = false;
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
        if(mLocation == null) {
            mLocation = location;
        } else if(mapLoaded){
            mLocation = location;
            setupCamera();
        }
        Log.d(LOCATION_TAG, "change to " + mLocation.toString());
    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.e(LOCATION_TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
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

        mPlans = mListener.getPlans();
        mSpecialities = mListener.getSpecialities();

        if (mPlans.size() <= 0) {
            mApiHelper.getPlans(new ApiHelper.ApiHelperCallback() {
                @Override
                public void onSuccess(List list) {
                    setPlanList(view, list);
                    Log.i("PLANS", list.toString());
                    mPlans = list;
                    mListener.setPlans(mPlans);
                }

                @Override
                public void onError(Exception e) {
                    Log.e("PLANS", e.getMessage());
                }
            });
        } else {
            setPlanList(view, mPlans);
        }

        if (mSpecialities.size() <= 0) {
            mApiHelper.getSpecialitites(new ApiHelper.ApiHelperCallback() {
                @Override
                public void onSuccess(List list) {
                    setSpecialityList(view, list);
                    Log.i("SPECIALITIES", list.toString());
                    mSpecialities = list;
                    mListener.setSpecialities(mSpecialities);
                }

                @Override
                public void onError(Exception e) {
                    Log.e("SPECIALITIES", e.getMessage());
                }
            });
        } else {
            setSpecialityList(view, mSpecialities);
        }

        Button clickButton = (Button) view.findViewById(R.id.cartilla_button_submit);
        clickButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onCartillaButton();
            }
        });

        mDoctorList = mListener.getDoctors();

        mDoctorAdapter = new DoctorRecyclerViewAdapter(mDoctorList, mListenerList);


        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.cartilla_list);
        ;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mDoctorAdapter);

        mSearchForm = (LinearLayout) view.findViewById(R.id.search_form);
        mDoctorListLayout = (LinearLayout) view.findViewById(R.id.doctor_list);
        mBackButton = (ImageButton) view.findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchForm.setVisibility(View.VISIBLE);
                mDoctorListLayout.setVisibility(View.GONE);
                googleMap.clear();
                mMarkers.clear();
            }
        });

        mMapButton = (ImageButton) view.findViewById(R.id.map_button);
        mMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMapView.getVisibility() == View.GONE) {
                    mMapView.setVisibility(View.VISIBLE);
                    googleMap.clear();
                    mMarkers.clear();
                    for(Doctor doctor : mDoctorList) {
                        MarkerOptions doctorMarker = new MarkerOptions().position(doctor.getLocation()).title(doctor.name).snippet(doctor.address);
                        googleMap.addMarker(doctorMarker);
                        mMarkers.add(doctorMarker);
                    }
                    googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                        @Override
                        public void onMapLoaded() {
                            mapLoaded = true;
                            setupCamera();
                        }
                    });

                } else {
                    mMapView.setVisibility(View.GONE);
                }
            }
        });

        if (mDoctorList.size() > 0) {
            mDoctorListLayout.setVisibility(View.VISIBLE);
            mSearchForm.setVisibility(View.GONE);
        }

        setMapView(view, savedInstanceState);

        mLocationFilterSwitch = (Switch) view.findViewById(R.id.location_filter);
        mLocationFilterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    googleMap.clear();
                    mMapView.setVisibility(View.VISIBLE);
                } else {
                    mMapView.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }

    private void setupCamera() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (MarkerOptions marker : mMarkers) {
            builder.include(marker.getPosition());
        }
        builder.include(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()));
        LatLngBounds bounds = builder.build();
        int padding = 15; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        googleMap.animateCamera(cu);
    }

    private void setMapView(View rootView, Bundle savedInstanceState) {
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateMapLocation();
    }

    private void updateMapLocation() {
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.setMyLocationEnabled(true);
            }
        });
    }

    public void setPlanList(View view, List list) {
        Spinner spinner = (Spinner) view.findViewById(R.id.plan_filter);
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
    }

    public void setSpecialityList(View view, List list) {
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
    }

    public void onCartillaButton() {
        boolean location_filter_enabled = mLocationFilterSwitch.isChecked();
        String latitude = "";
        String longitude = "";
        if(location_filter_enabled && mLocation != null) {
            latitude = String.valueOf(mLocation.getLatitude());
            longitude = String.valueOf(mLocation.getLongitude());
            mDoctorAdapter.setCurrentLocation(mLocation);
        }

        mApiHelper.getCartilla(mPlanId, mSpecialityId, latitude, longitude, new ApiHelper.ApiHelperCallback() {
            @Override
            public void onSuccess(List list) {
                mDoctorList.clear();
                mDoctorList.addAll(list);
                mDoctorAdapter.notifyDataSetChanged();

                mDoctorListLayout.setVisibility(View.VISIBLE);
                mSearchForm.setVisibility(View.GONE);
                mMapView.setVisibility(View.GONE);

                mListener.setDoctors(mDoctorList);

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
        commonOnAttach(context);
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        commonOnAttach(context);
    }

    public void commonOnAttach(Context context) {
        if (context instanceof OnListFragmentInteractionListener && context instanceof OnFragmentInteractionListener) {
            mListenerList = (OnListFragmentInteractionListener) context;
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
        mApiHelper = new ApiHelper(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mListenerList = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
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
        void setDoctors(List<Doctor> doctors);
        List<Doctor> getDoctors();

        void setPlans(List<Plan> plans);
        List<Plan> getPlans();

        void setSpecialities(List<Speciality> specialities);
        List<Speciality> getSpecialities();
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Doctor item);
    }
}
