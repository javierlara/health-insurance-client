package com.apres.apresmovil.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.apres.apresmovil.R;
import com.apres.apresmovil.models.Member;
import com.apres.apresmovil.models.Plan;
import com.apres.apresmovil.network.ApiHelper;
import com.apres.apresmovil.utils.Session;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private List<Plan> mPlans;
    private String mPlanId;
    private ApiHelper mApiHelper;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mApiHelper = new ApiHelper(this);
        session = new Session(this);

        mApiHelper.getPlans(new ApiHelper.ApiHelperCallback() {
            @Override
            public void onSuccess(List list) {
                setPlanList(list);
                Log.i("PLANS", list.toString());
                mPlans = list;
            }

            @Override
            public void onError(Exception e) {
                Log.e("PLANS", e.getMessage());
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_up_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

    }

    public void attemptRegister() {
        String nroSocio = ((AutoCompleteTextView)findViewById(R.id.register_nro_socio)).getText().toString();
        String nombre = ((AutoCompleteTextView)findViewById(R.id.register_nombre)).getText().toString();
        String direccion = ((AutoCompleteTextView)findViewById(R.id.register_direccion)).getText().toString();
        String telefono = ((AutoCompleteTextView)findViewById(R.id.register_telefono)).getText().toString();

        boolean cancel = false;
        View focusView = null;

//        if (TextUtils.isEmpty(nroSocio)) {
//            mNroSocioView.setError(getString(R.string.error_field_required));
//            focusView = mNroSocioView;
//            cancel = true;
//        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            showProgress(true);
//            mAuthTask = new UserLoginTask(nroSocio);
//            mAuthTask.execute((Void) null);

            final Activity activity = this;

            mApiHelper.createMember(nroSocio, nombre, direccion, telefono, mPlanId, new ApiHelper.ApiHelperJsonCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(activity, "Usuario registrado", Toast.LENGTH_SHORT).show();
//                    boolean success = false;
//                    if (list.size() > 0) {
//                        session.setCurrentMember((Member)list.get(0));
//                        success = true;
//                        Log.i("MEMBER", list.toString());
//                    } else {
//                        // TODO: send to register
//                        Log.e("MEMBER", "NOT FOUND");
//                    }
//
//                    onGetMember(success);

                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    activity.finish();

                }

                @Override
                public void onError(Exception e) {
                    Log.e("MEMBER", e.getMessage());
                    Toast.makeText(activity, "Hubo un error. Intente más tarde", Toast.LENGTH_SHORT).show();
                }
            });


//                    new ApiHelper.ApiHelperCallback() {
//                @Override
//                public void onSuccess(List list) {
//                    boolean success = false;
//                    if (list.size() > 0) {
//                        session.setCurrentMember((Member)list.get(0));
//                        success = true;
//                        Log.i("MEMBER", list.toString());
//                    } else {
//                        // TODO: send to register
//                        Log.e("MEMBER", "NOT FOUND");
//                    }
//
//                    onGetMember(success);
//                }
//
//                @Override
//                public void onError(Exception e) {
//                    Log.e("MEMBER", "Member does not exist");
//                    onGetMember(false);
//                }
//            });
        }
    }

    public void setPlanList(List list) {
        Spinner spinner = (Spinner) findViewById(R.id.register_plan);
        ArrayAdapter<Plan> adapter = new ArrayAdapter<Plan>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Plan plan = (Plan) parent.getSelectedItem();
                mPlanId = plan.id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void onGetMember(boolean success) {
//        showProgress(false);

        if (success) {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            this.finish();
        } else {
//            mNroSocioView.requestFocus();
//            mNroSocioView.setError("Ingresa un número válido");
        }
    }

}
