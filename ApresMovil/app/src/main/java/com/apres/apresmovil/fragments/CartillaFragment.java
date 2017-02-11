package com.apres.apresmovil.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CartillaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CartillaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartillaFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private ApiHelper mApiHelper = new ApiHelper();

    private Context mContext;

    private String mSpecialityId;

    private String mPlanId;

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

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void onCartillaButton() {
        Switch locationSwitch = (Switch) getView().findViewById(R.id.location_filter);
        boolean location_filter = locationSwitch.isChecked();

//        mApiHelper.getDoctors(new ApiHelper.ApiHelperCallback() {
//            @Override
//            public void onSuccess(List list) {
//                Log.i("DOCTORS", list.toString());
//            }
//
//            @Override
//            public void onError(Exception e) {
//                Log.e("DOCTORS", e.getMessage());
//            }
//        });

        mApiHelper.getCartilla(mPlanId, mSpecialityId, "", new ApiHelper.ApiHelperCallback() {
            @Override
            public void onSuccess(List list) {
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
}
