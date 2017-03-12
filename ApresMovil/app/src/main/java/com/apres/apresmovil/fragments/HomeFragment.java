package com.apres.apresmovil.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.apres.apresmovil.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add((Button) view.findViewById(R.id.new_home));
        buttons.add((Button) view.findViewById(R.id.centers_home));
        buttons.add((Button) view.findViewById(R.id.cartilla_home));
        buttons.add((Button) view.findViewById(R.id.my_appointments_home));
        buttons.add((Button) view.findViewById(R.id.sac_home));
        buttons.add((Button) view.findViewById(R.id.emergency_home));

        for (Button button : buttons) {
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onButtonPressed((Button) v);
                }
            });
        }

        return view;
    }

    public void onButtonPressed(Button button) {
        if (mListener != null) {
            mListener.onHomeButtonSelected(button);
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onHomeButtonSelected(Button button);
    }
}
