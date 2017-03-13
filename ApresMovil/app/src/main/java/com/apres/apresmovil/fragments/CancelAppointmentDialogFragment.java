package com.apres.apresmovil.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.apres.apresmovil.R;
import com.apres.apresmovil.activities.MainActivity;

/**
 * Created by javierlara on 3/8/17.
 */

public class CancelAppointmentDialogFragment extends DialogFragment {

    private static final String BUNDLE_KEY = "appointmentId";

    public static CancelAppointmentDialogFragment newInstance(String appointmentId) {
        CancelAppointmentDialogFragment frag = new CancelAppointmentDialogFragment();
        Bundle args = new Bundle();
        args.putString(BUNDLE_KEY, appointmentId);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String appointmentId = getArguments().getString(BUNDLE_KEY);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.cancel_dialog_title)
                .setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((MainActivity)getActivity()).doPositiveClick(appointmentId);
                            }
                        }
                )
                .setNegativeButton(R.string.alert_dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((MainActivity)getActivity()).doNegativeClick();
                            }
                        }
                )
                .create();
    }
}